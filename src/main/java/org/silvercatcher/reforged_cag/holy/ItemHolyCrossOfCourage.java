package org.silvercatcher.reforged_cag.holy;

import java.util.UUID;

import org.silvercatcher.reforged_cag.util.NBTHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;

public class ItemHolyCrossOfCourage extends ItemHolyCross {

	private final String sinnerTag = "marked_sinners";
	
	public ItemHolyCrossOfCourage() {
		super("courage", 30, 1f, 1f, 30, 12d);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

		if(!player.worldObj.isRemote) {
			if(entity instanceof EntityLiving && ((EntityLiving) entity).isEntityUndead()) {
				if(entity.isEntityAlive()) {
					NBTTagCompound compound = NBTHelper.getCompound(stack);
					NBTTagList sinners = NBTHelper.getTagList(sinnerTag, compound);
					sinners.appendTag(new NBTTagString(entity.getPersistentID().toString()));
					NBTHelper.saveTagList(sinnerTag, sinners, compound, stack);
				}
			}
		}
		return false;
	}


	@Override
	protected boolean whenReady(EntityPlayer player, ItemStack stack) {
		
		boolean striked = false;
		NBTTagCompound compound = NBTHelper.getCompound(stack);
		NBTTagList sinners = NBTHelper.getTagList(sinnerTag, compound);
		
		MinecraftServer server = MinecraftServer.getServer();
				
		for(int i = 0; i < sinners.tagCount(); i++) {

			UUID uuid = UUID.fromString(sinners.getStringTagAt(i));
			
			EntityLivingBase sinner = (EntityLivingBase) server.getEntityFromUuid(uuid);
			
			if(sinner == null || !sinner.isEntityAlive()) {
				sinners.removeTag(i);
			} else if(sinner.getDistanceToEntity(player) > reach) {
				continue;
			} else {
				punish(player.worldObj, sinner, player);
				striked = true;
				break;
			}
		}
		NBTHelper.saveTagList(sinnerTag, sinners, compound, stack);
		return striked;
	}
}

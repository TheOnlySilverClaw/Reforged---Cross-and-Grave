package org.silvercatcher.reforged_cag.holy;

import java.util.Collections;
import java.util.LinkedList;
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
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemHolyCrossWooden extends ItemHolyCross {

	private static final String SINNER_TAG = "sinners";
	
	public ItemHolyCrossWooden() {
		
		super(1);
		delay = 25;
		reach = 12;
		setMaxDamage(40);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		
		if(!player.worldObj.isRemote) {
			
			if(entity instanceof EntityLiving && ((EntityLiving) entity).isEntityUndead()) {
				
				entity.attackEntityFrom(causeHolyDamage(player), 2);

				if(entity.isEntityAlive()) {
					NBTTagCompound compound = NBTHelper.getCompound(stack);
					NBTTagList sinners = NBTHelper.getTagList(SINNER_TAG, compound);
					sinners.appendTag(new NBTTagString(entity.getPersistentID().toString()));
					NBTHelper.saveTagList(SINNER_TAG, sinners, compound, stack);
				}
			}
		}
		return true;
	}

	@Override
	protected boolean punish(ItemStack stack, EntityPlayer player) {

		NBTTagCompound compound = NBTHelper.getCompound(stack);
		NBTTagList sinners = NBTHelper.getTagList(SINNER_TAG, compound);
		
		MinecraftServer server = MinecraftServer.getServer();
		
		System.out.println(sinners.tagCount());
		
		for(int i = 0; i < sinners.tagCount(); i++) {
			
			UUID uuid = UUID.fromString(sinners.getStringTagAt(i));
			
			EntityLivingBase sinner = (EntityLivingBase) server.getEntityFromUuid(uuid);
			
			if(sinner == null || !sinner.isEntityAlive()) {
				sinners.removeTag(i);
			} else {
				punish(sinner, player);
				NBTHelper.saveTagList(SINNER_TAG, sinners, compound, stack);
				return true;
			}
		}
		NBTHelper.saveTagList(SINNER_TAG, sinners, compound, stack);
		return false;
	}
}

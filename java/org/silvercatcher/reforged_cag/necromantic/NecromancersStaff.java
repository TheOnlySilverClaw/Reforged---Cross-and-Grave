package org.silvercatcher.reforged_cag.necromantic;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class NecromancersStaff extends Item {

	private final String necro_properties = "necro";
	
	public NecromancersStaff() {

		setUnlocalizedName("necromancers_staff_tier1");
		setCreativeTab(CrossAndGraveMod.crossAndGraveTab);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

		if(!player.worldObj.isRemote && entity instanceof EntityLivingBase) {
			
			EntityLivingBase living = (EntityLivingBase) entity;
			
			NecromanticTransformation<EntityLivingBase> transformation
				= NecromanticSettings.getTransformation(living.getClass());

			if(transformation == null) {
				player.addChatMessage(new ChatComponentText(
						"A " + living.getName() + " cannot be made a minion, foolish mortal.")
						.setChatStyle(NecromanticSettings.necromanticcChatStyle));
			} else {
				if(transformation.checkTransformable(player, living)) {
					
					NecromanticMinionProperties properties = (NecromanticMinionProperties)
							living.getExtendedProperties(necro_properties);
					
					if(properties == null) {
						possess(player, living);
					} else {
						EntityPlayer master = properties.getMaster();
						if(properties.getMaster() == null) {
							possess(player, living);
						} else {
							if(properties.getMaster() == player) {
								inform(player, living);
							} else {
								warn(player, master, living);
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	private void warn(EntityPlayer player, EntityPlayer master, EntityLivingBase living) {
		
		player.addChatMessage(
				new ChatComponentText("This one serves " + master.getName())
				.setChatStyle(NecromanticSettings.necromanticcChatStyle));
		master.addChatComponentMessage(
				new ChatComponentText(player.getName() +  " tried to steal one of your minions!")
				.setChatStyle(NecromanticSettings.necromanticcChatStyle.setBold(true)));
	}

	protected void inform(EntityPlayer player, EntityLivingBase living) {
		
		String healthReport;
		
		float relativeHealth = living.getHealth() / living.getMaxHealth();

		if(relativeHealth > 0.5f) {
			if(relativeHealth > 0.75f) {
				healthReport = "and in good shape.";
			} else {
				healthReport = "and still has all his limbs.";
			}
		} else {
			if(relativeHealth > 0.25f) {
				healthReport = ", but could use some spare parts.";
			} else {
				healthReport = ", but is falling apart.";
			}
		}
		player.addChatMessage(new ChatComponentText("This one is a loyal servant " + healthReport));
	}

	protected void possess(EntityPlayer player, EntityLivingBase living) {
		
		living.registerExtendedProperties(necro_properties, 
				new NecromanticMinionProperties(player));
		player.addChatMessage(new ChatComponentText("This one serves us, now."));
	}
}

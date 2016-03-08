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
		
		
		if(entity instanceof EntityLivingBase) {
			
			EntityLivingBase living = (EntityLivingBase) entity;
			
			NecromanticTransformation<EntityLivingBase> transformation
				= NecromanticSettings.getTransformation(living.getClass());

			if(transformation == null) {
				player.addChatMessage(new ChatComponentText(
						"A " + living.getName() + "cannot be made a minion...yet.")
						.setChatStyle(NecromanticSettings.necromanticcChatStyle));
			} else {
				if(transformation.checkTransformable(player, living)) {
					
					NecromanticMinionProperties properties = (NecromanticMinionProperties)
							living.getExtendedProperties(necro_properties);
					
					if(properties == null) {
						System.out.println("no properties");
					} else {
						if(properties.getMaster() == null) {
							System.out.println("no master");
						}
					}
				}
			}
		}
		return true;
	}
}

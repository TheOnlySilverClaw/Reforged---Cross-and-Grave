package org.silvercatcher.reforged_cag.holy;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemHolyCrossOfWrath extends ItemHolyCross {

	public ItemHolyCrossOfWrath() {
		super("wrath", 25, 1.5f, 3f, 40, 8d);
	}

	@Override
	protected boolean whenReady(EntityPlayer player, ItemStack stack) {
		
		List<EntityLiving> sinners =
				player.worldObj.getEntitiesWithinAABB(EntityLiving.class,
				player.getEntityBoundingBox().expand(reach, reach / 2, reach),
				living -> living.isEntityUndead());
				
		if(sinners.isEmpty()) return false;

		for(EntityLiving target : sinners) { 
			punish(player.worldObj, target, player);;
		}
		return true;
	}
}

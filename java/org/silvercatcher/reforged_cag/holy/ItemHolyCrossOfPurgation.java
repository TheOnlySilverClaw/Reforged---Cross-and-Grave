package org.silvercatcher.reforged_cag.holy;

import java.util.List;

import com.google.common.base.Predicate;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;

public class ItemHolyCrossOfPurgation extends ItemHolyCross {
	
	private float shortRange = 6f;
	private float shortInterval = 10;
	
	public ItemHolyCrossOfPurgation() {
		super("purgation", 70, 3f, 4f, 60, 15);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		
		if(count % shortInterval == 0) {
			for(EntityLiving sinner :
					player.worldObj.getEntitiesWithinAABB(EntityLiving.class,
					player.getEntityBoundingBox()
					.expand(shortRange, shortRange / 2, shortRange),
					living -> living.isEntityUndead()
					)) {
				sinner.attackEntityFrom(new EntityDamageSource("holy", player), 1f);
			}
		}
		super.onUsingTick(stack, player, count);
	}

	@Override
	protected boolean whenReady(EntityPlayer player, ItemStack stack) {

		List<EntityLiving> sinners =
				player.worldObj.getEntitiesWithinAABB(EntityLiving.class,
				player.getEntityBoundingBox().expand(reach, reach / 2, reach),
				new Predicate<EntityLiving>() {
					@Override
					public boolean apply(EntityLiving living) {
						return living.isEntityUndead();
					}
				});
				
		if(sinners.isEmpty()) return false;

		for(EntityLiving target : sinners) { 
			strikeLightning(player.worldObj, target, player);;
		}
		return true;
	}
}

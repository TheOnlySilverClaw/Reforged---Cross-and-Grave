package org.silvercatcher.reforged_cag.holy;

import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Predicate;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemHolyCrossGolden extends ItemHolyCross {
	
	public ItemHolyCrossGolden() {
		
		super(2);
		delay = 40;
		reach = 8;
		setMaxDamage(25);
	}

	@Override
	protected boolean punish(ItemStack stack, EntityPlayer punisher) {
		
		World world = punisher.worldObj;
		
		if(world.isRemote) return false;
		
		List<EntityLiving> sinners =
				world.getEntitiesWithinAABB(EntityLiving.class,
				punisher.getEntityBoundingBox().expand(reach, reach / 2, reach),
				new Predicate<EntityLiving>() {
					@Override
					public boolean apply(EntityLiving living) {
						return living.isEntityUndead();
					}
				});
				
		if(sinners.isEmpty()) return false;
		
		for(EntityLiving target : sinners) { 
			punish(target, punisher);
		}
		return true;
	}
}

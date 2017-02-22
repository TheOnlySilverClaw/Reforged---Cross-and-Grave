package org.silvercatcher.reforged_cag.necromantic;

import java.util.List;
import java.util.Random;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;
import org.silvercatcher.reforged_cag.necromantic.minions.EntitySkeletonMinion;
import org.silvercatcher.reforged_cag.necromantic.minions.EntityZombieMinion;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStaffOfRevival extends Item {

	/**
	 * for any ItemStack where stacksize is maxStackSize / safePart
	 * or greater, summoning is guaranteed to work
	 */
	private int safePart = 2;
	private final Random random = new Random();
	private final float searchRange = 4f;
	
	public ItemStaffOfRevival() {
		
		setUnlocalizedName("staff_reanimation");
		setMaxStackSize(1);
		setMaxDamage(100);
		setCreativeTab(CrossAndGraveMod.crossAndGraveTab);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		
		if(!worldIn.isRemote) {
			List<EntityItem> entityItems = worldIn.getEntitiesWithinAABB(EntityItem.class,
					playerIn.getEntityBoundingBox().expand(
							searchRange, 0,  searchRange));
			
			for(EntityItem entityItem : entityItems) {
				
				ItemStack entityStack = entityItem.getEntityItem();
				Item item = entityItem.getEntityItem().getItem();
				if(item == null) continue;

				int propability = entityStack.getMaxStackSize()
						/ safePart - entityStack.stackSize;
				
				int r = random.nextInt();
	
				if(propability <= 0) {
					r = 0;
				} else {
					r = random.nextInt(propability);
				}
				
				if(r == 0) {	
					if(item == Items.rotten_flesh && r == 0) {
						new EntityZombieMinion(worldIn)
							.reanimate(playerIn, entityItem);
					} else if(item == Items.bone) {
						new EntitySkeletonMinion(worldIn)
							.reanimate(playerIn, entityItem);
					}
					
					entityItem.setDead();
					stack.damageItem(1, playerIn);
				}
			}
		}
		stack.damageItem(1, playerIn);
		return stack;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 20;
	}
}

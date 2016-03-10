package org.silvercatcher.reforged_cag.necromantic;

import java.util.List;
import java.util.Random;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;

import com.google.common.base.Predicate;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemStaffOfRevival extends Item {

	/**
	 * for any ItemStack where stacksize is safeLine * maxStackSize
	 * or greater, summoning is guaranteed to work
	 */
	private float safeLine = 0.5f;
	private final Random random = new Random();
	private final float searchRange = 4f;
	/*private final Predicate<EntityItem> filter = new Predicate<EntityItem>() {

		@Override
		public boolean apply(EntityItem entityItem) {
			
			// let's try if the itemstack can be null here...
			entityItem.getEntityItem() != null;
		}
	};*/
	
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

				int propability = entityStack.getMaxStackSize() / 2 - entityStack.stackSize;
				
				int r = random.nextInt();
	
				if(propability <= 0) {
					r = 0;
				} else {
					r = random.nextInt(propability);
				}
				
				if(item == Items.rotten_flesh && r == 0) {
					entityItem.setDead();
					EntityZombie summoned = new EntityZombie(worldIn);
					summoned.setPosition(entityItem.posX, entityItem.posY, entityItem.posZ);
					worldIn.spawnEntityInWorld(summoned);
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

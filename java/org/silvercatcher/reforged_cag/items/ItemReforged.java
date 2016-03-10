package org.silvercatcher.reforged_cag.items;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public abstract class ItemReforged extends Item {

	public final int defaultUseDuration = 72000;
	
	public ItemReforged(String name, int durability) {
		
		this(name, durability, 1);
	}
	
	public ItemReforged(String name, int durability, int stackSize) {
		
		setUnlocalizedName(name);
		setMaxStackSize(stackSize);
		setMaxDamage(durability);
		setCreativeTab(CrossAndGraveMod.crossAndGraveTab);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return defaultUseDuration;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}
}

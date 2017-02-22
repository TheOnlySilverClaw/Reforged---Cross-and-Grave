package org.silvercatcher.reforged_cag.necromantic;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemStaffOfSacrifice extends Item {

	private final int useDuration = 80;
	private final float powerPerTick = ((float) useDuration) / 100;
	private final int hurtInterval = 10;
	private final DamageSource soulDrain = new DamageSource("souldrain")
			.setMagicDamage().setDamageBypassesArmor();
	private final float range = 2.5f;
	
	public ItemStaffOfSacrifice() {
		
		setUnlocalizedName("staff_sacrifice");
		setCreativeTab(CrossAndGraveMod.crossAndGraveTab);
		setMaxStackSize(1);
		setMaxDamage(100);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		return super.onItemRightClick(itemStackIn, worldIn, playerIn);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return useDuration;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {

		if(count % hurtInterval == 0) {
			player.attackEntityFrom(soulDrain, 1f);
		}
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn,
			EntityPlayer playerIn, int timeLeft) {
		
		if(!worldIn.isRemote) {
			summon(worldIn, playerIn, powerPerTick * (useDuration - timeLeft));
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		
		if(!worldIn.isRemote) {
			summon(worldIn, playerIn, powerPerTick * useDuration);
		}
		return stack;
	}
	
	private void summon(World world, EntityPlayer player, float power) {
		
		if(power > 10) {
			player.addChatComponentMessage(new ChatComponentText(
					String.format("Rolling with %.1f power", power)));
		}
	}
}

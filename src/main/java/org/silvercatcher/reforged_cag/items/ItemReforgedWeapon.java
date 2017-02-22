package org.silvercatcher.reforged_cag.items;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class ItemReforgedWeapon extends ItemReforged {

	protected final float meleeDamage;
	
	public ItemReforgedWeapon(String name, int durability, float meleeDamage) {
		
		super(name, durability);
		this.meleeDamage = meleeDamage;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		stack.attemptDamageItem(1, itemRand);
		return super.onLeftClickEntity(stack, player, entity);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos,
			EntityLivingBase playerIn) {
		stack.attemptDamageItem(1, itemRand);
		return super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack) {

		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(stack);
        map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
        		new AttributeModifier(itemModifierUUID, "Weapon modifier", meleeDamage, 0));
		return map;
	}
	
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BLOCK;
	}
}

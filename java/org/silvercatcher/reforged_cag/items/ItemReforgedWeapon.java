package org.silvercatcher.reforged_cag.items;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public abstract class ItemReforgedWeapon extends ItemReforged {

	protected final float meleeDamage;
	
	public ItemReforgedWeapon(String name, int durability, float meleeDamage) {
		
		super(name, durability);
		this.meleeDamage = meleeDamage;
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

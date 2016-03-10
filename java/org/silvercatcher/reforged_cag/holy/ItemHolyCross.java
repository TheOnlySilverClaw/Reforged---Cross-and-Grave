package org.silvercatcher.reforged_cag.holy;

import java.util.UUID;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;
import org.silvercatcher.reforged_cag.items.ItemReforgedWeapon;

import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public abstract class ItemHolyCross extends ItemReforgedWeapon {
	
	protected final float holyDamage;
	protected final double reach;
	protected final int delay;
	
	public ItemHolyCross(String valor, int durability,
			float meleeDamage, float holyDamage, int delay, double reach) {
		super("holy_cross_" + valor, durability, meleeDamage);
		this.holyDamage = holyDamage;
		this.delay = delay;
		this.reach = reach;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		if(!player.worldObj.isRemote && count % delay == 0) {
			if(whenReady(player, stack)) {
				stack.damageItem(1, player);
			}
		}
	}
	
	protected abstract boolean whenReady(EntityPlayer player, ItemStack stack);
	
	protected void strikeLightning(World world,
			EntityLivingBase sinner, EntityPlayer punisher) {
		
			world.addWeatherEffect((new EntityLightningBolt(
				world, sinner.posX, sinner.posY, sinner.posZ)));
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack) {
		
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(stack);
		map.put(HolyDamage.attribute.getAttributeUnlocalizedName(),
				new AttributeModifier(HolyDamage.uuid,
						HolyDamage.modifierName, holyDamage, 0));
		return map;
	}
}

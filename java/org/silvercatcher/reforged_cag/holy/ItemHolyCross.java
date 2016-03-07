package org.silvercatcher.reforged_cag.holy;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;
import org.silvercatcher.reforged_cag.util.NBTHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.tools.nsc.doc.base.comment.Link;

public abstract class ItemHolyCross extends Item {

	protected double reach;
	protected int delay;

	public ItemHolyCross(int tier) {
		
		setUnlocalizedName("holy_cross_tier"  + tier);
		setCreativeTab(CrossAndGraveMod.crossAndGraveTab);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}
	
	public DamageSource causeHolyDamage(EntityPlayer punisher) {
		return new EntityDamageSource("holy", punisher);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		
		if(!worldIn.isRemote) {
			if(punish(stack, playerIn)) {
				stack.damageItem(1, playerIn);
			}
		}
		return stack;
	}
		
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {

		return delay;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		return EnumAction.BLOCK;
	}

	/**
	 * @return true, if a sinner was punished
	 */
	protected abstract boolean punish(ItemStack stack, EntityPlayer punisher);
	
	protected void punish(EntityLivingBase sinner, EntityPlayer punisher) {
			sinner.worldObj.addWeatherEffect(new EntityLightningBolt(
					sinner.worldObj, sinner.posX, sinner.posY, sinner.posZ));
			sinner.attackEntityFrom(causeHolyDamage(punisher), 0.5f);
	}
}

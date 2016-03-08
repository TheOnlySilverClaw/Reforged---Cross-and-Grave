package org.silvercatcher.reforged_cag.necromantic;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public abstract class NecromanticTransformation <T extends EntityLivingBase>{
	
	/**
	 * @return the cost to transform a target, can be a constant value
	 * or change, depending on parameters
	 */
	public abstract int getTransformationCost(
			EntityPlayer necromancer, T target);
	
	/** 
	 * @return true, if transformation can happen
	 * please use ChatMessages to give feedback to the necromancer
	 */
	public boolean checkTransformable(
			EntityPlayer necromancer, T creature) {
		return true;
	}
	
	/**
	 * Overwrite this method to manipulate a slave when transforming,
	 * for example to change AI tasks, change health, apply other effects
	 * or even change it into a different entity.
	 * 
	 * @return the transformed slave
	 */
	public abstract EntityLivingBase transform(
			EntityPlayer master, T slave);
}

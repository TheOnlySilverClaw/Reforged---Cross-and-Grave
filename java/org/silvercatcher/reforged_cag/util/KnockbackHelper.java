package org.silvercatcher.reforged_cag.util;

import net.minecraft.entity.Entity;

public class KnockbackHelper {

	/**
	 * Knockback without changing y axis
	 * @param power
	 * @param knocker
	 * @param target
	 */
	public static final void knockBack2D(float power, Entity knocker, Entity target) {
		
		target.addVelocity(
				CircleHelper.calcX(power, knocker.rotationYaw), 0, 
				CircleHelper.calcZ(power, knocker.rotationYaw));
	}
	
	
	public static final void knockBack3D(float power, Entity knocker, Entity target) {
		
		target.addVelocity(
				CircleHelper.calcX(power, knocker.rotationYaw),
				CircleHelper.calcY(power, knocker.rotationPitch), 
				CircleHelper.calcZ(power, knocker.rotationYaw));
	}
}

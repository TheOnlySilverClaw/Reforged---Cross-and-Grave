package org.silvercatcher.reforged_cag.util;

import net.minecraft.util.MathHelper;

public class CircleHelper {

	public static final float DEG = (float) (Math.PI / 180);
	
	public static final float calcX(float range, float yaw) {
		
		return -range * MathHelper.sin(yaw * DEG);
	}
	
	public static final float calcZ(float range, float yaw) {
		
		return range * MathHelper.cos(yaw * DEG);
	}
}

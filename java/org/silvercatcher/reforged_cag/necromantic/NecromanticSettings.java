package org.silvercatcher.reforged_cag.necromantic;

import java.util.HashMap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class NecromanticSettings {

	public static final ChatStyle necromanticcChatStyle =
			new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE).setItalic(true);
	
	private static final HashMap<Class<? extends EntityLivingBase>,
		NecromanticTransformation<? extends EntityLivingBase>>
		transformations = new HashMap<>();
	
	
	public static final <T extends EntityLivingBase> void registerTranformation(
			Class<T> entityClass, NecromanticTransformation<T> transformation 
			) {
		transformations.put(entityClass, transformation);
	}
	
	public static final <T extends EntityLivingBase> NecromanticTransformation<T>
		getExactTransformation(Class<T> entityClass) {
		
		return (NecromanticTransformation<T>) transformations.get(entityClass);
	}
	
	public static final NecromanticTransformation<EntityLivingBase>
		getTransformation(Class<?> entityClass) {
		
		return (NecromanticTransformation<EntityLivingBase>) transformations.get(entityClass);
	}
}

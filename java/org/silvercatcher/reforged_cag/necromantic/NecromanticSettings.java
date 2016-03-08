package org.silvercatcher.reforged_cag.necromantic;

import java.util.HashMap;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class NecromanticSettings {

	public static final ChatStyle necromanticcChatStyle =
			new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE).setItalic(true);
	
	private static final HashMap<Class<? extends EntityLiving>,
		NecromanticTransformation<? extends EntityLiving>>
		transformations = new HashMap<>();
	
	
	public static final <T extends EntityLiving> void registerTranformation(
			Class<T> entityClass, NecromanticTransformation<T> transformation 
			) {
		transformations.put(entityClass, transformation);
	}
	
	public static final <T extends EntityLiving> NecromanticTransformation<T>
		getExactTransformation(Class<T> entityClass) {
		
		return (NecromanticTransformation<T>) transformations.get(entityClass);
	}
	
	public static final NecromanticTransformation<EntityLiving>
		getTransformation(Class<?> entityClass) {
		
		return (NecromanticTransformation<EntityLiving>) transformations.get(entityClass);
	}
}

package org.silvercatcher.reforged_cag.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTHelper {

	
	public static final NBTTagCompound getCompound(ItemStack stack) {
		
		NBTTagCompound compound = stack.getTagCompound();
		if(compound == null) {
			compound = new NBTTagCompound();
		}
		return compound;
	}
	
	public static final NBTTagList getTagList(String tag, ItemStack stack) {
		
		return getTagList(tag, getCompound(stack));
	}
	
	public static final NBTTagList getTagList(String tag, NBTTagCompound compound) {
		
		return compound.getTagList(tag, 8);
	}
	
	public static final void saveTagList(String listTag, NBTTagList list,
			NBTTagCompound compound, ItemStack stack) {
		
		compound.setTag(listTag, list);
		stack.setTagCompound(compound);
	}
}

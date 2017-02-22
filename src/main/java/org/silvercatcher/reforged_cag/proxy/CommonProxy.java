package org.silvercatcher.reforged_cag.proxy;

import java.io.File;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;
import org.silvercatcher.reforged_cag.CrossAndGraveSettings;
import org.silvercatcher.reforged_cag.holy.HolyEvents;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		File configDirectory = event.getSuggestedConfigurationFile();
		
		if(!configDirectory.isDirectory()) {
			configDirectory.mkdir();
		}
		File cagConfigFile = new File(configDirectory, "cross_and_grave.cfg");
		Configuration cagConfig = new Configuration(cagConfigFile);
		
		CrossAndGraveSettings.epicPunishing = 
				cagConfig.getBoolean("epic-punishing", "Behaviour",
				CrossAndGraveSettings.epicPunishing,
				"true: Holy Crosses use lightning to destroy undead creatures. (more epic)"
				+ "\nfalse: Holy Crosses damage undead creatures "
				+ "\nand set them on fire (safer and better for looting)");
		
		CrossAndGraveSettings.enableStaffs = true;
		
		if(cagConfig.hasChanged()) {
			cagConfig.save();
		}
		MinecraftForge.EVENT_BUS.register(new HolyEvents());
		registerItems();
	}
	
	public void init(FMLInitializationEvent event) {
	
		registerRecipes();
	}

	private void registerRecipes() {
		
		GameRegistry.addRecipe(
				new ItemStack(CrossAndGraveMod.COURAGE),
				" s ",
				"sgs",
				" s ",
				's', Items.stick,
				'g', Items.gold_ingot
				);
		
		GameRegistry.addRecipe(new ItemStack(CrossAndGraveMod.WRATH),
				" g ",
				"gdg",
				" g ",
				'g', Items.gold_ingot,
				'd', Items.diamond);
		
		GameRegistry.addRecipe(new ItemStack(CrossAndGraveMod.PURGATION),
				" d ",
				"ded",
				" d ",
				'd', Items.diamond,
				'e', Items.emerald);
		
		if(CrossAndGraveSettings.enableStaffs) {
		GameRegistry.addRecipe(new ItemStack(CrossAndGraveMod.SACRIFICE),
				"  r",
				" o ",
				"o  ",
				'r', Items.redstone,
				'o', Item.getItemFromBlock(Blocks.obsidian));
		
		GameRegistry.addRecipe(new ItemStack(CrossAndGraveMod.REVIVAL),
				"  l",
				" o ",
				"o  ",
				'l', new ItemStack(Items.dye, 1, 4),
				'o', Item.getItemFromBlock(Blocks.obsidian));
		}
	}

	private void registerItems() {
				
		GameRegistry.registerItem(CrossAndGraveMod.COURAGE, "HolyCrossOfCourage");
		GameRegistry.registerItem(CrossAndGraveMod.WRATH, "HolyCrossOfWrath");
		GameRegistry.registerItem(CrossAndGraveMod.PURGATION, "HolyCrossOfPurgation");
		
		GameRegistry.registerItem(CrossAndGraveMod.SACRIFICE, "StaffOfSacrifice");
		GameRegistry.registerItem(CrossAndGraveMod.REVIVAL, "StaffOfRevival");
	}
}

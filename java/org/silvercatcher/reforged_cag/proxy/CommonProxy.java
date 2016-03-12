package org.silvercatcher.reforged_cag.proxy;

import java.io.File;
import java.util.Map.Entry;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;
import org.silvercatcher.reforged_cag.CrossAndGraveSettings;
import org.silvercatcher.reforged_cag.holy.HolyEvents;
import org.silvercatcher.reforged_cag.necromantic.minions.EntityZombieMinion;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
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
		
		if(cagConfig.hasChanged()) {
			cagConfig.save();
		}
		MinecraftForge.EVENT_BUS.register(new HolyEvents());
		registerItems();
		registerEntities();
	}
	
	public void init(FMLInitializationEvent event) {
	
		registerRecipes();
	}

	private void registerRecipes() {
		
		GameRegistry.addRecipe(
				new ItemStack(CrossAndGraveMod.items.get("holy_cross")[0]),
				" s ",
				"sgs",
				" s ",
				's', Items.stick,
				'g', Items.gold_ingot
				);
		
		GameRegistry.addRecipe(new ItemStack(CrossAndGraveMod.items.get("holy_cross")[1]),
				" g ",
				"gdg",
				" g ",
				'g', Items.gold_ingot,
				'd', Items.diamond);
		
		GameRegistry.addRecipe(new ItemStack(CrossAndGraveMod.items.get("holy_cross")[2]),
				" d ",
				"ded",
				" d ",
				'd', Items.diamond,
				'e', Items.emerald);
		
		GameRegistry.addRecipe(new ItemStack(CrossAndGraveMod.items.get("necromancers_staff")[0]),
				"  r",
				" o ",
				"o  ",
				'r', Items.redstone,
				'o', Item.getItemFromBlock(Blocks.obsidian));
		
		GameRegistry.addRecipe(new ItemStack(CrossAndGraveMod.items.get("necromancers_staff")[1]),
				"  l",
				" o ",
				"o  ",
				'l', new ItemStack(Items.dye, 1, 4),
				'o', Item.getItemFromBlock(Blocks.obsidian));
	}

	private void registerEntities() {
		
		EntityRegistry.registerModEntity(EntityZombieMinion.class, "zombie_minion",
				0, CrossAndGraveMod.instance, 40, 20, true);
		EntityRegistry.registerEgg(EntityZombieMinion.class, 474, 284);

	}

	private void registerItems() {
	
		for(Entry<String, Item[]> entry : CrossAndGraveMod.items.entrySet()) {
			
			Item [] tier = entry.getValue();
			
			for(int i = 0; i < tier.length; i++) {
				
				GameRegistry.registerItem(tier[i], tier[i].getUnlocalizedName().substring(5));
			}
		}
	}
}

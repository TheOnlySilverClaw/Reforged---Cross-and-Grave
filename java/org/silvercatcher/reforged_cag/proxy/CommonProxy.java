package org.silvercatcher.reforged_cag.proxy;

import java.util.Map.Entry;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;
import org.silvercatcher.reforged_cag.necromantic.NecromanticEvents;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.world.gen.structure.StructureMineshaftPieces.Cross;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(new NecromanticEvents());

	}
	
	public void init(FMLInitializationEvent event) {
		
		for(Entry<String, Item[]> entry : CrossAndGraveMod.items.entrySet()) {
			
			Item [] tier = entry.getValue();
			
			for(int i = 0; i < tier.length; i++) {
				
				GameRegistry.registerItem(tier[i], tier[i].getUnlocalizedName().substring(5));
			}
		}
	}
}

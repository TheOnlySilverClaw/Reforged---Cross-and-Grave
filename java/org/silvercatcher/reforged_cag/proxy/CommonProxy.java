package org.silvercatcher.reforged_cag.proxy;

import java.util.Map.Entry;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;
import org.silvercatcher.reforged_cag.necromantic.NecromanticEvents;
import org.silvercatcher.reforged_cag.necromantic.NecromanticMinionProperties;
import org.silvercatcher.reforged_cag.necromantic.NecromanticSettings;
import org.silvercatcher.reforged_cag.necromantic.NecromanticSettings;
import org.silvercatcher.reforged_cag.necromantic.NecromanticTransformation;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.gen.structure.StructureMineshaftPieces.Cross;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		//MinecraftForge.EVENT_BUS.register(new NecromanticEvents());
		registerItems();
	}
	
	public void init(FMLInitializationEvent event) {
		
		registerRecipes();
		registerNecromanticTransformations();
	}

	private void registerRecipes() {
		
		GameRegistry.addRecipe(
				new ItemStack(CrossAndGraveMod.items.get("holy_cross")[0]),
				" s ",
				"ses",
				" s ",
				's', Items.stick,
				'e', Items.emerald
				);
		
		GameRegistry.addRecipe(new ItemStack(CrossAndGraveMod.items.get("holy_cross")[1]),
				" g ",
				"gdg",
				" g ",
				'g', Items.gold_ingot,
				'd', Items.diamond);
	}

	private void registerNecromanticTransformations() {
		
		NecromanticSettings.registerTranformation(EntityZombie.class,
				new NecromanticTransformation<EntityZombie>() {

			@Override
			public int getTransformationCost(EntityPlayer necromancer, EntityZombie target) {
				return 5;
			}

			@Override
			public EntityLiving transform(EntityPlayer master, EntityZombie slave) {
				
				extendProperties(master, slave);
				return slave;
			}
		});
		
		NecromanticSettings.registerTranformation(EntitySkeleton.class,
				new NecromanticTransformation<EntitySkeleton>() {

					@Override
					public int getTransformationCost(EntityPlayer necromancer, EntitySkeleton target) {
						return 15;
					}

					@Override
					public EntityLiving transform(EntityPlayer master, EntitySkeleton slave) {
						
						extendProperties(master, slave);
						return slave;
					}
		});

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

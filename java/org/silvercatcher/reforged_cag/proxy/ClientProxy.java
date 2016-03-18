package org.silvercatcher.reforged_cag.proxy;

import java.util.Map.Entry;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;
import org.silvercatcher.reforged_cag.necromantic.minions.EntitySkeletonMinion;
import org.silvercatcher.reforged_cag.necromantic.minions.EntityZombieMinion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		super.preInit(event);
		registerEntityRenderers();
	}
	
	protected void registerEntityRenderers() {
		
		RenderingRegistry.registerEntityRenderingHandler(
				EntityZombieMinion.class, RenderZombie::new);
		
		RenderingRegistry.registerEntityRenderingHandler(
				EntitySkeletonMinion.class, RenderSkeleton::new);
	}
	
	@Override
	public void init(FMLInitializationEvent event) {

		super.init(event);
		registerItemRenderers();
	}
	
	protected void registerItemRenderers() {
		
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		String inventory = "inventory";
		
		for(Entry<String, Item[]> entry : CrossAndGraveMod.items.entrySet()) {
			
			Item [] tiers = entry.getValue();
			
			for(int i = 0; i < tiers.length; i++) {				
				mesher.register(tiers[i], 0, new ModelResourceLocation(
						CrossAndGraveMod.ID + ":" + tiers[i].getUnlocalizedName().substring(5), inventory));
			}
		}
	}
}

package org.silvercatcher.reforged_cag;

import java.util.HashMap;

import org.silvercatcher.reforged_cag.holy.ItemHolyCross;
import org.silvercatcher.reforged_cag.holy.ItemHolyCrossGolden;
import org.silvercatcher.reforged_cag.holy.ItemHolyCrossWooden;
import org.silvercatcher.reforged_cag.necromantic.NecromancersStaff;
import org.silvercatcher.reforged_cag.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = CrossAndGraveMod.ID,
		version = CrossAndGraveMod.VERSION,
		name = CrossAndGraveMod.NAME)
public class CrossAndGraveMod
{
	public static final String NAME = "Reforged - Cross and Grave";
    public static final String ID = "reforged_cag";
    public static final String VERSION = "1.0";
    
	public static final HashMap<String, Item []> items = new HashMap<>();
	
	private static Item tabItem;
	
    public static final CreativeTabs crossAndGraveTab = new CreativeTabs(CrossAndGraveMod.ID) {
		
		@Override
		public Item getTabIconItem() {
			
			return tabItem;
		}
	};
	
	static {
		
		items.put("holy_cross", new Item [] {
				new ItemHolyCrossWooden(),
				new ItemHolyCrossGolden()
		});
		
		items.put("necromancers_staff", new Item [] {
				new NecromancersStaff()
		});
		
		tabItem = items.get("holy_cross")[1];
	}
	
	
    @Instance(ID)
    public static CrossAndGraveMod instance;
    
    @SidedProxy(modId = ID,
    		clientSide = "org.silvercatcher.reforged_cag.proxy.ClientProxy",
    		serverSide = "org.silvercatcher.reforged_cag.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}

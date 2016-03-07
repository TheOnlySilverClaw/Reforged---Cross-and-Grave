package org.silvercatcher.reforged_cag.necromantic;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NecromanticEvents {

	@SubscribeEvent
	@SideOnly(Side.SERVER)
	public void minionDeath(LivingDeathEvent event) {
		
		if(event.isCanceled()) return;
		
		System.out.println(event.entityLiving);
	}
}

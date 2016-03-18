package org.silvercatcher.reforged_cag.necromantic.minions;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface UndeadMinion <T extends EntityLiving> {

	EntityPlayer getMaster();
	
	void setMaster(EntityPlayer master);
	
	int getPowerDrain();
	
	T summon(EntityPlayer summoner, float range);
	
	T reanimate(EntityPlayer summoner, EntityItem item);
}

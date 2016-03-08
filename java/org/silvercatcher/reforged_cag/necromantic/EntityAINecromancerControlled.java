package org.silvercatcher.reforged_cag.necromantic;

import java.util.Objects;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;

public class EntityAINecromancerControlled extends EntityAIBase {

	private final double speed = 1;
	private final int updateInterval = 40;
	
	private final EntityLiving slave;
	private final NecromanticMinionProperties properties;
	
	private int updateCountdownDown;
	
	
	public EntityAINecromancerControlled(EntityLiving slave) {
		
		this.slave = Objects.requireNonNull(slave);
		this.properties =(NecromanticMinionProperties)
				slave.getExtendedProperties(NecromanticSettings.necroProperties);
	}

	@Override
	public boolean shouldExecute() {

		return properties.getMaster() != null && updateCountdownDown-- == 0;
	}

	@Override
	public void startExecuting() {
	
		System.out.println("executing");
		
		EntityPlayer master = properties.getMaster();
	}
	
	@Override
	public void resetTask() {
		
		updateCountdownDown = updateInterval;
	}
}

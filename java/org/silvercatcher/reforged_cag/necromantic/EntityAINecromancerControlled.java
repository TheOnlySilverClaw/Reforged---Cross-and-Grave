package org.silvercatcher.reforged_cag.necromantic;

import java.util.Objects;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAINecromancerControlled extends EntityAIBase {

	private final double speed = 1;
	private final int updateInterval = 40;
	
	private final EntityLiving slave;
	
	private int updateCountdownDown;
	
	
	public EntityAINecromancerControlled(EntityLiving slave) {
		
		this.slave = Objects.requireNonNull(slave);
	}

	@Override
	public boolean shouldExecute() {

		return updateCountdownDown-- == 0;
	}

	@Override
	public void startExecuting() {
	
		System.out.println("executing");

	}
	
	@Override
	public void resetTask() {
		
		updateCountdownDown = updateInterval;
	}
}

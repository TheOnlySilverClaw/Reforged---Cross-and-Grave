package org.silvercatcher.reforged_cag.necromantic.minions;

import org.silvercatcher.reforged_cag.util.CircleHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntitySkeletonMinion extends EntitySkeleton
	implements UndeadMinion<EntitySkeleton>{

	private EntityPlayer master;
	
	public EntitySkeletonMinion(World worldIn) {
		super(worldIn);
	}

	@Override
	public int getPowerDrain() {
		return 15;
	}
	
	// workaround for now!
	@Override
	public void setAttackTarget(EntityLivingBase target) {
		if(target == master) return;
		super.setAttackTarget(target);
	}
	
	@Override
	public EntitySkeleton summon(EntityPlayer summoner, float range) {

		setPosition(
				summoner.posX + CircleHelper.calcX(range, summoner.rotationYaw),
				summoner.posY,
				summoner.posZ + CircleHelper.calcZ(range, summoner.rotationYaw));
		setCurrentItemOrArmor(0, new ItemStack(Items.bow));
		worldObj.spawnEntityInWorld(this);
		setMaster(summoner);
		return this;
	}

	@Override
	public EntitySkeleton reanimate(EntityPlayer summoner, EntityItem item) {

		setPosition(item.posX + rand.nextFloat(),
				item.posY, item.posZ + rand.nextFloat());
		worldObj.spawnEntityInWorld(this);
		return this;
	}
	
	@Override
	public EntityPlayer getMaster() {
		return master;
	}

	@Override
	public void setMaster(EntityPlayer master) {
		this.master = master;
	}
}

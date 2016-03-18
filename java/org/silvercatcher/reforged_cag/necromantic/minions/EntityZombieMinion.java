package org.silvercatcher.reforged_cag.necromantic.minions;

import org.silvercatcher.reforged_cag.util.CircleHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityZombieMinion extends EntityZombie
	implements UndeadMinion<EntityZombie> {
		
	private EntityPlayer master;
	
	public EntityZombieMinion(World worldIn) {
		super(worldIn);
	}

	@Override
	public EntityZombie summon(EntityPlayer summoner, float range) {
		
		setPosition(
				summoner.posX + CircleHelper.calcX(range, summoner.rotationYaw),
				summoner.posY,
				summoner.posZ + CircleHelper.calcZ(range, summoner.rotationYaw));
		worldObj.spawnEntityInWorld(this);
		return this;
	}
	
	@Override
	public EntityZombie reanimate(EntityPlayer summoner, EntityItem item) {
		
		setPosition(item.posX + rand.nextFloat(),
				item.posY, item.posZ + rand.nextFloat());
		worldObj.spawnEntityInWorld(this);
		return this;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth)
			.setBaseValue(15);
	}
	
	@Override
	public int getPowerDrain() {
		return 5;
	}
	
	@Override
	public boolean interact(EntityPlayer player) {
		
		if(player == getMaster()) {
			ItemStack offeredStack = player.getCurrentEquippedItem();
			System.out.println(offeredStack);
			if(offeredStack == null) {
				return false;
			} else {
				Item offeredItem = offeredStack.getItem();
				
				if(offeredItem instanceof ItemArmor) {
					ItemArmor offeredArmor = (ItemArmor) offeredItem;
					// 0: helmet, 1: plate: 2: legs, 3: boots
					if(offeredArmor.isValidArmor(offeredStack, offeredArmor.armorType, this)) {
						setCurrentItemOrArmor(offeredArmor.armorType + 1, offeredStack);
					}
				} else {
					setCurrentItemOrArmor(0, offeredStack);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	// workaround for now!
	@Override
	public void setAttackTarget(EntityLivingBase target) {
		if(target == master) return;
		super.setAttackTarget(target);
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

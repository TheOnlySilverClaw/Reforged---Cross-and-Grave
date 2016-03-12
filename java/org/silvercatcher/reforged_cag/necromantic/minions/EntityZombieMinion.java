package org.silvercatcher.reforged_cag.necromantic.minions;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityZombieMinion extends EntityZombie implements UndeadMinion {

	private EntityPlayer master;
	
	public EntityZombieMinion(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
	}
	
	public int getPowerDrain() {
		return 5;
	}
	
	@Override
	public boolean interact(EntityPlayer player) {
		
		if(player == master) {
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
}

package org.silvercatcher.reforged_cag.necromantic;

import org.silvercatcher.reforged_cag.CrossAndGraveMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ItemNecromancersStaff extends Item {
	
	public ItemNecromancersStaff() {

		setUnlocalizedName("necromancers_staff_tier2");
		setCreativeTab(CrossAndGraveMod.crossAndGraveTab);
	}


	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn,
			BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {

		if(!worldIn.isRemote) {
			
			if(playerIn.isSneaking() && side != EnumFacing.DOWN) {
				
				EntityZombie conjuredZombie = new EntityZombie(worldIn);
				conjuredZombie.setHealth(5);
				conjuredZombie.setPosition(
						pos.getX() + hitX,
						pos.getY() + conjuredZombie.height * 0.75f,
						pos.getZ() + hitZ);
				
				worldIn.spawnEntityInWorld(conjuredZombie);
				playerIn.addChatMessage(new ChatComponentText("Conjured a weak zombie"));
			}
		}
		return false;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		
		NecromancerProperties np = getNecromancerProperties(playerIn);
		
		for(EntityLiving minion : np.getMinions()) {

			if(minion.getDistanceSqToEntity(playerIn) > 10) {
				minion.getNavigator().tryMoveToEntityLiving(playerIn, 1);
			}
		}
		return stack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		
		return 20;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		return EnumAction.BLOCK;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

		if(!player.worldObj.isRemote && entity instanceof EntityLiving) {
			
			EntityLiving living = (EntityLiving) entity;
			
			NecromanticTransformation<EntityLiving> transformation
				= NecromanticSettings.getTransformation(living.getClass());

			if(transformation == null) {
				taunt(player, living);
			} else {
				if(transformation.checkTransformable(player, living)) {
					
					NecromanticMinionProperties properties = (NecromanticMinionProperties)
							living.getExtendedProperties(NecromanticMinionProperties.key);
					
					if(properties == null) {
						possess(player, living, transformation);
					} else {
						EntityPlayer master = properties.getMaster();
						if(properties.getMaster() == null) {
							possess(player, living, transformation);
						} else {
							if(properties.getMaster() == player) {
								inform(player, living);
							} else {
								warn(player, master, living);
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	private void taunt(EntityPlayer player, EntityLiving living) {
		player.addChatMessage(new ChatComponentText(
				"A " + living.getName() + " cannot be made a minion, foolish mortal.")
				.setChatStyle(NecromanticSettings.necromanticcChatStyle));
	}

	private void warn(EntityPlayer player, EntityPlayer master, EntityLiving living) {
		
		player.addChatMessage(
				new ChatComponentText("This one serves " + master.getName())
				.setChatStyle(NecromanticSettings.necromanticcChatStyle));
		master.addChatComponentMessage(
				new ChatComponentText(player.getName() +  " tried to steal one of your minions!")
				.setChatStyle(NecromanticSettings.necromanticcChatStyle.setBold(true)));
	}

	protected void inform(EntityPlayer player, EntityLiving living) {
		
		String healthReport;
		
		float relativeHealth = living.getHealth() / living.getMaxHealth();

		if(relativeHealth > 0.5f) {
			if(relativeHealth > 0.75f) {
				healthReport = "and in good shape.";
			} else {
				healthReport = "and still has all his limbs.";
			}
		} else {
			if(relativeHealth > 0.25f) {
				healthReport = ", but could use some spare parts.";
			} else {
				healthReport = ", but is falling apart.";
			}
		}
		player.addChatMessage(new ChatComponentText("This one is a loyal servant " + healthReport));
	}

	protected void possess(EntityPlayer player, EntityLiving living,
			NecromanticTransformation<EntityLiving> transformation) {
		
		getNecromancerProperties(player).getMinions().add(living);
		transformation.transform(player, living);
		player.addChatMessage(new ChatComponentText("This one serves us, now."));
	}
	
	protected NecromancerProperties getNecromancerProperties(EntityPlayer player) {
		
		IExtendedEntityProperties extendedPropterties
			= player.getExtendedProperties(NecromancerProperties.key);
	
		NecromancerProperties necromancerProperties;
		if(extendedPropterties instanceof NecromancerProperties) {
			necromancerProperties = (NecromancerProperties) extendedPropterties;
		} else {
			necromancerProperties = new NecromancerProperties();
			necromancerProperties.init(player, player.worldObj);
			player.registerExtendedProperties(
					NecromancerProperties.key, necromancerProperties);
		}
		return necromancerProperties;
	}
}

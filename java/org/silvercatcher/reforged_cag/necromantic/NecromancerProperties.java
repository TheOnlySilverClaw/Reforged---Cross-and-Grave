package org.silvercatcher.reforged_cag.necromantic;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.silvercatcher.reforged_cag.util.NBTHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class NecromancerProperties implements IExtendedEntityProperties {

	public static final String key = "necromancer";
	
	private List<EntityLiving> minions;
	private MinecraftServer server;
	private World world;
	private EntityPlayer master;
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		
		System.out.println("saving");
		NBTTagList list = new NBTTagList();
		for(EntityLiving minion : minions) {
			list.appendTag(new NBTTagString(minion.getPersistentID().toString()));
		}
		compound.setTag(key, list);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		
		System.out.println("loading");
		NBTTagList slaveUUIDList = NBTHelper.getTagList("slaves", compound);
		
		for(int i = 0; i < slaveUUIDList.tagCount(); i++) {
			
			EntityLiving slave = (EntityLiving) server.getEntityFromUuid(
					UUID.fromString(slaveUUIDList.getStringTagAt(i)));
			
			if(slave == null || !slave.isEntityAlive()) {
				slaveUUIDList.removeTag(i);
				System.out.println("removed slave: " + slave);
			} else {
				minions.add(slave);
			}
		}
	}

	public List<EntityLiving> getMinions() {
		return minions;
	}
	
	/**
	 * @param entity Will crash if it's not of EntityPlayer...I warned you.
	 */
	@Override
	public void init(Entity entity, World world) {
		
		System.out.println("initializing");
		this.server = MinecraftServer.getServer();
		this.master = (EntityPlayer) entity;
		this.world = world;
		this.minions = new LinkedList<>();
	}

}

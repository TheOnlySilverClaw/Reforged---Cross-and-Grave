package org.silvercatcher.reforged_cag.necromantic;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class NecromanticMinionProperties
	implements IExtendedEntityProperties {

	public static final String key = "necro_minion";
	
	private World world;
	private EntityPlayer master;
	
	public NecromanticMinionProperties(EntityPlayer master) {
		
		this.master = master;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		
		if(master != null) {
			compound.setString("master", master.getPersistentID().toString());
		}
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		master = world.getPlayerEntityByUUID(
				UUID.fromString(compound.getString("master")));
	}

	@Override
	public void init(Entity entity, World world) {
		
		this.world = world;
	}
	
	public EntityPlayer getMaster() {
		return master;
	}
}

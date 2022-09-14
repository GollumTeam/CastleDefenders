package com.gollum.castledefenders.common.entities;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

import net.minecraft.world.World;

public class EntityArcher2 extends EntityArcher {

	public EntityArcher2(World world) {
		super(world);
		this.blockSpawn = ModBlocks.ARCHER2;
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.archer2Capacities; }
	
}

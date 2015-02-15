package com.gollum.castledefenders.common.entities;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class EntityKnight2 extends EntityKnight {

	public EntityKnight2(World world) {
		super(world);
		this.blockSpawn      = ModCastleDefenders.blockKnight2;
		this.defaultHeldItem = new ItemStack(Items.diamond_sword, 1);
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.knight2Capacities; }
	
}

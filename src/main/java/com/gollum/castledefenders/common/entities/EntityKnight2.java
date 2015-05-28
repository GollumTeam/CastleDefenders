package com.gollum.castledefenders.common.entities;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class EntityKnight2 extends EntityKnight {

	public EntityKnight2(World world) {
		super(world);
		this.blockSpawnId    = ModCastleDefenders.blockKnight2.blockID;
		this.defaultHeldItem = new ItemStack(Item.swordDiamond, 1);
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.knight2Capacities; }
	
}

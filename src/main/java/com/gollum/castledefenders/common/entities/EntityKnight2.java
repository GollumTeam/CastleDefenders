package com.gollum.castledefenders.common.entities;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityKnight2 extends EntityKnight {

	public EntityKnight2(World world) {
		super(world);
		this.blockSpawn      = ModBlocks.KNIGHT2;
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD, 1));
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.knight2Capacities; }
	
}

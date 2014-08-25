package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import mods.gollum.core.common.config.container.MobCapacitiesConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityKnight2 extends EntityKnight {

	public EntityKnight2(World world) {
		super(world);
		this.blockSpawnId    = ModCastleDefenders.blockKnight2.blockID;
		this.defaultHeldItem = new ItemStack(Item.swordDiamond, 1);
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfig getCapacities () { return ModCastleDefenders.config.knight2Capacities; }
	
}

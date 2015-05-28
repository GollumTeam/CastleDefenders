package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import mods.gollum.core.common.config.container.ItemStackConfigType;
import mods.gollum.core.common.config.container.MobCapacitiesConfigType;
import net.minecraft.world.World;

public class EntityArcher2 extends EntityArcher {

	public EntityArcher2(World world) {
		super(world);
		this.blockSpawnId = ModCastleDefenders.blockArcher2.blockID;
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.archer2Capacities; }
	
}

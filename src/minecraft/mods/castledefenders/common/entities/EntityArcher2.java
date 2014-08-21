package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import mods.gollum.core.config.container.ItemStackConfig;
import mods.gollum.core.config.container.MobCapacitiesConfig;
import net.minecraft.world.World;

public class EntityArcher2 extends EntityArcher {

	public EntityArcher2(World world) {
		super(world);
		this.blockSpawnId = ModCastleDefenders.blockArcher2.blockID;
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfig getCapacities () { return ModCastleDefenders.config.healerCapacities; }
	/**
	 * @return les capacitées du mod
	 */
	protected ItemStackConfig[] getCost () { return ModCastleDefenders.config.healerCost; }
	
}

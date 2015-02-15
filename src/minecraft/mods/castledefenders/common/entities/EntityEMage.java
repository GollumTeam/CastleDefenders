package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import mods.castledefenders.common.aientities.EntityAIDistanceAttack;
import net.minecraft.world.World;

import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class EntityEMage extends EntityEnemy {
	
	public EntityEMage(World world) {
		super(world);
		this.blockSpawn = ModCastleDefenders.blockEMage;
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), EntityAIDistanceAttack.TYPE_FIRE));
		
	}
	
	/**
     * Determines if an entity can be despawned, used on idle far away entities
     */
	protected boolean canDespawn() {
		return false;
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.eMageCapacities; }
}

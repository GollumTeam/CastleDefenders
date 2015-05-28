package com.gollum.castledefenders.common.entities;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.aientities.EntityAIDistanceAttack;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class EntityMage extends EntityDefender {

	public EntityMage(World world) {

		super(world);
		this.blockSpawnId = ModCastleDefenders.blockMage.blockID;
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), EntityAIDistanceAttack.TYPE_FIRE));
		
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget (this, IMob.class, 0, true));
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
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.mageCapacities; }
	
}

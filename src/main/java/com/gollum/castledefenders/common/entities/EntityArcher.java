package com.gollum.castledefenders.common.entities;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.aientities.EntityAIDistanceAttack;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class EntityArcher extends EntityDefender {

	public EntityArcher(World world) {

		super(world);
		this.blockSpawn    = ModCastleDefenders.blockArcher;
		this.defaultHeldItem = new ItemStack(Items.bow, 1);
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), EntityAIDistanceAttack.TYPE_ARROW));
		
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
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.archerCapacities; }
	
}
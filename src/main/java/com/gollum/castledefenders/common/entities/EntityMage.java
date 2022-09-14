package com.gollum.castledefenders.common.entities;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.aientities.EntityAIDistanceAttack;
import com.gollum.castledefenders.common.aientities.EntityAIDistanceAttack.PROJECTILE_TYPE;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;
import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMage extends EntityDefender {

	public EntityMage(World world) {
		super(world);
		this.blockSpawn = ModBlocks.MAGE;
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModBlocks.EKNIGHT, 1));
	}
	
    protected void initEntityAI() {
    	super.initEntityAI();
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), PROJECTILE_TYPE.FIRE));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget<EntityLiving> (this, EntityLiving.class, 0, true, false, new Predicate<Entity>() {
			public boolean apply(Entity entity) {
				return 
					entity instanceof IMob
				;
			}
		}));
    	
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

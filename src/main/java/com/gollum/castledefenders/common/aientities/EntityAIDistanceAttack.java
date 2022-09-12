package com.gollum.castledefenders.common.aientities;

import java.util.Random;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.entities.EntityFireBall;
import com.gollum.castledefenders.common.entities.ICastleEntity;
import com.gollum.core.tools.registered.RegisteredObjects;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityAIDistanceAttack extends EntityAIBase
{
	public enum PROJECTILE_TYPE {
		ARROW,
		FIRE
	}
	
	World world;
	EntityLiving entityHost;
	EntityLivingBase attackTarget;
	double rangedAttackTime = 0.0D;
	double rangedAttackDistance = 0.0D;
	double entityMoveSpeed = 0.0D;
	double maxRangedAttackTime = 10;
	int nbTarget = 0;
	PROJECTILE_TYPE projectileType;

	public EntityAIDistanceAttack(EntityLiving entityLiving, double entityMoveSpeed, double rangedAttackDistance, double rangedAttackTime, PROJECTILE_TYPE projectileType) {
		this.entityHost = entityLiving;
		this.world = entityLiving.world;
		this.entityMoveSpeed = entityMoveSpeed;
		this.rangedAttackDistance = rangedAttackDistance;
		this.maxRangedAttackTime = rangedAttackTime;
		this.projectileType = projectileType;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		
		EntityLivingBase target = this.entityHost.getAttackTarget();
		if (target == null) {
			return false;
		}
		this.attackTarget = target;
		return true;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {
		return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
	}

	/**
	 * Resets the task
	 */
	public void resetTask() {
		this.attackTarget = null;
	}

	/**
	 * Updates the task
	 */
	public void updateTask() {
		
		double distance = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.getEntityBoundingBox().minY, this.attackTarget.posZ);
		boolean canSeeEntity = this.entityHost.getEntitySenses().canSee(this.attackTarget);
		
		if (canSeeEntity) {
			this.nbTarget++;
		} else {
			this.nbTarget = 0;
		}
		
		if (this.nbTarget >= 20) {
			this.entityHost.getNavigator().clearPath();
		} else {
			this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
		}
		
		this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, (float)this.rangedAttackDistance, (float)this.rangedAttackDistance);
		this.rangedAttackTime = Math.max(this.rangedAttackTime - 1, 0);
		
		if (this.rangedAttackTime <= 0 && canSeeEntity) {
			ModCastleDefenders.logger.debug ("updateTask : rangedAttackTime="+this.rangedAttackTime);
			this.doRangedAttack();
			this.rangedAttackTime = this.maxRangedAttackTime;
		}
	}
	
	private void doRangedAttack() {

		int x;
		int y;
		int z;
		Random random = new Random();
		
		switch (this.projectileType) {
			
			default:
			case ARROW:
				EntityArrow eArrow = new EntityTippedArrow(this.world, this.entityHost);
				if (this.entityHost instanceof ICastleEntity) {
					eArrow.setDamage(((ICastleEntity)this.entityHost).getAttackStrength());	
				}
				
				double d0 = this.attackTarget.posX - this.entityHost.posX;
		        double d1 = this.attackTarget.getEntityBoundingBox().minY + (double)(this.attackTarget.height / 3.0F) - eArrow.posY;
		        double d2 = this.attackTarget.posZ - this.entityHost.posZ;
		        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
		        eArrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - this.world.getDifficulty().getDifficultyId() * 4));
				this.entityHost.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0f, 1.0F / (this.world.rand.nextFloat() * 0.4F + 0.8F));
				this.world.spawnEntity(eArrow);
				
				break;
				
			case FIRE:
				
				ModCastleDefenders.logger.debug ("Attack Fire");
				
				EntityFireBall eFireball = new EntityFireBall(this.world, this.entityHost, this.attackTarget);
				if (this.entityHost instanceof ICastleEntity) {
					eFireball.damage = (float)((ICastleEntity)this.entityHost).getAttackStrength();	
				}
				this.entityHost.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 0.6f, 0.8F + random.nextFloat() * 0.2F);
				this.world.spawnEntity(eFireball);
				
				
				break;
		}
	}
	
}

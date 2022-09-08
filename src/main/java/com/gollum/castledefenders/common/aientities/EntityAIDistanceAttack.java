package com.gollum.castledefenders.common.aientities;

import java.util.Random;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.core.tools.registered.RegisteredObjects;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityAIDistanceAttack extends EntityAIBase
{
	public static final int TYPE_ARROW = 0;
	public static final int TYPE_FIRE = 1;
	
	World world;
	EntityLiving entityHost;
	EntityLivingBase attackTarget;
	double rangedAttackTime = 0.0D;
	double rangedAttackDistance = 0.0D;
	double entityMoveSpeed = 0.0D;
	double maxRangedAttackTime = 10;
	int nbTarget = 0;
	int rangedAttackID;

	public EntityAIDistanceAttack(EntityLiving entityLiving, double entityMoveSpeed, double rangedAttackDistance, double rangedAttackTime, int rangedAttackID) {
		this.entityHost = entityLiving;
		this.world = entityLiving.world;
		this.entityMoveSpeed = entityMoveSpeed;
		this.rangedAttackDistance = rangedAttackDistance;
		this.maxRangedAttackTime = rangedAttackTime;
		this.rangedAttackID = rangedAttackID;
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
		
		switch (this.rangedAttackID) {
			
			default:
			case TYPE_ARROW:
				
//				EntityArrow eArrow = new EntityArrow(this.world, this.entityHost, this.attackTarget, 1.6F, 12.0F);
				this.world.playSound(
					this.entityHost.posX,
					this.entityHost.posY,
					this.entityHost.posZ,
					RegisteredObjects.instance().getSoundEvent("random.bow"),
					SoundCategory.HOSTILE,
					1.0F,
					1.0F / (this.entityHost.getRNG().nextFloat() * 0.4F + 0.8F),
					false
				);
//				this.world.spawnEntity(eArrow);
				
				break;
				
			case TYPE_FIRE:
				
				ModCastleDefenders.logger.debug ("Attack Fire");
				
				x = MathHelper.floor(this.attackTarget.posX) + random.nextInt(5) - 2;
				y = MathHelper.floor(this.attackTarget.posY);
				z = MathHelper.floor(this.attackTarget.posZ + random.nextInt(5) - 2);
				BlockPos pos = new BlockPos(x, y, z);
				
				for (int i = 0; i < 2 && !this.world.isAirBlock(pos); i++) {
					y++;
				}
				
				if (this.world.isAirBlock(pos)) {
					
					this.world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 3);
					this.world.playSound(x, y, z, RegisteredObjects.instance().getSoundEvent("ambient.weather.thunder"), SoundCategory.WEATHER, 2.0F, 0.8F + random.nextFloat() * 0.2F, false);
					this.world.playSound(x, y, z, RegisteredObjects.instance().getSoundEvent("random.explode"), SoundCategory.HOSTILE, 2.0F, 0.6F + random.nextFloat() * 0.2F, false);
					
					if (random.nextInt(3) == 0) {
						this.entityHost.attackEntityAsMob(this.attackTarget);
					}
					
				}
				
				break;
		}
	}
	
}

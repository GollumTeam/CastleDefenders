package mods.castledefenders.common.aientities;

import java.util.Random;

import mods.castledefenders.ModCastleDefenders;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIDistanceAttack extends EntityAIBase
{
	public static final int TYPE_ARROW = 0;
	public static final int TYPE_FIRE = 1;
	
	World worldObj;
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
		this.worldObj = entityLiving.worldObj;
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
		
		double distance = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.boundingBox.minY, this.attackTarget.posZ);
		boolean canSeeEntity = this.entityHost.getEntitySenses().canSee(this.attackTarget);
		
		if (canSeeEntity) {
			this.nbTarget++;
		} else {
			this.nbTarget = 0;
		}
		
		if (this.nbTarget >= 20) {
			this.entityHost.getNavigator().clearPathEntity();
		} else {
			this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
		}
		
		this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, (float)this.rangedAttackDistance, (float)this.rangedAttackDistance);
		this.rangedAttackTime = Math.max(this.rangedAttackTime - 1, 0);
		
		if (this.rangedAttackTime <= 0 && canSeeEntity) {
			ModCastleDefenders.log.debug ("updateTask : rangedAttackTime="+this.rangedAttackTime);
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
				
				EntityArrow eArrow = new EntityArrow(this.worldObj, this.entityHost, this.attackTarget, 1.6F, 12.0F);
				this.worldObj.playSoundAtEntity(this.entityHost, "random.bow", 1.0F, 1.0F / (this.entityHost.getRNG().nextFloat() * 0.4F + 0.8F));
				this.worldObj.spawnEntityInWorld(eArrow);
				
				break;
				
			case TYPE_FIRE:
				
				ModCastleDefenders.log.debug ("Attack Fire");
				
				x = MathHelper.floor_double(this.attackTarget.posX) + random.nextInt(5) - 2;
				y = MathHelper.floor_double(this.attackTarget.posY);
				z = MathHelper.floor_double(this.attackTarget.posZ + random.nextInt(5) - 2);
				
				for (int i = 0; i < 2 && this.worldObj.isAirBlock(x, y, z); i++) {
					y++;
				}
				
				if (this.worldObj.isAirBlock(x, y, z)) {
					
					this.worldObj.setBlock(x, y, z, Blocks.fire, 0, 2);
					this.worldObj.playSoundEffect(x, y, z, "ambient.weather.thunder", 2.0F, 0.8F + random.nextFloat() * 0.2F);
					this.worldObj.playSoundEffect(x, y, z, "random.explode", 2.0F, 0.6F + random.nextFloat() * 0.2F);
					
					if (random.nextInt(3) == 0) {
						this.entityHost.attackEntityAsMob(this.attackTarget);
					}
					
				}
				
				break;
		}
	}
	
}

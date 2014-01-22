package mods.castledefenders.common.aientities;

import java.util.Random;

import mods.castledefenders.common.ModCastleDefenders;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIArcherArrowAttack extends EntityAIBase
{
	World worldObj;
	EntityLiving entityHost;
	EntityLivingBase attackTarget;
	double rangedAttackTime = 0.0D;
	double rangedAttackDistance = 0.0D;
	double entityMoveSpeed = 0.0D;
	double maxRangedAttackTime = 10;
	int nbTarget = 0;
	int rangedAttackID;

	public EntityAIArcherArrowAttack(EntityLiving entityLiving, double entityMoveSpeed, double rangedAttackDistance, double rangedAttackTime, int rangedAttackID) {
		this.entityHost = entityLiving;
		this.worldObj = entityLiving.worldObj;
		this.entityMoveSpeed = entityMoveSpeed;
		this.rangedAttackDistance = rangedAttackDistance;
		this.maxRangedAttackTime = Math.min(10.D - rangedAttackTime, 1.D);
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
		
		double maxDistance = 100.0D;
		double distance = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.boundingBox.minY, this.attackTarget.posZ);
		boolean canSeeEntity = this.entityHost.getEntitySenses().canSee(this.attackTarget);
		
		if (canSeeEntity) {
			this.nbTarget++;
		} else {
			this.nbTarget = 0;
		}
		
		if (distance <= maxDistance && this.nbTarget >= 20) {
			this.entityHost.getNavigator().clearPathEntity();
		} else {
			this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
		}
		
		this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, (float)this.rangedAttackDistance, (float)this.rangedAttackDistance);
		this.rangedAttackTime = Math.max(this.rangedAttackTime - 1, 0);
		
		if (this.rangedAttackTime <= 0 && distance <= maxDistance && canSeeEntity) {
			this.doRangedAttack();
			this.rangedAttackTime = this.maxRangedAttackTime;
		}
	}
	
	private void doRangedAttack() {

		int x;
		int y;
		int z;
		Random random = new Random();
		
		// TODO Pourle moment l'id n'est que de 1
		switch (this.rangedAttackID) {
			
			default:
			case 1:
				
				EntityArrow eArrow = new EntityArrow(this.worldObj, this.entityHost, this.attackTarget, 1.6F, 12.0F);
				this.worldObj.playSoundAtEntity(this.entityHost, "random.bow", 1.0F, 1.0F / (this.entityHost.getRNG().nextFloat() * 0.4F + 0.8F));
				this.worldObj.spawnEntityInWorld(eArrow);
				
				break;
				
			case 2:
				
				x = MathHelper.floor_double(this.attackTarget.posX);
				y = MathHelper.floor_double(this.attackTarget.posY);
				z = MathHelper.floor_double(this.attackTarget.posZ);
				
				this.worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, this.attackTarget.posX + random.nextInt(3) - 1, this.attackTarget.posY, this.attackTarget.posZ + random.nextInt(3) - 1));
				
				if (this.worldObj.getBlockId(x, y, z) == 0) {
					this.worldObj.setBlock(x + random.nextInt(3) - 1, y, z + random.nextInt(3) - 1, Block.fire.blockID, 0, 2);
				}
				
				break;
			
			case 3:
				
				x= MathHelper.floor_double(this.attackTarget.posX);
				y = MathHelper.floor_double(this.attackTarget.posY);
				z = MathHelper.floor_double(this.attackTarget.posZ);
				
				if (this.worldObj.getBlockId(x, y, z) == 0) {
					this.worldObj.setBlock(x, y, z, Block.fire.blockID, 0, 2);
				}
				
				switch (random.nextInt(5)) {
					
					case 0: if (this.worldObj.getBlockId(x + 1, y    , z    ) == 0) this.worldObj.setBlock(x + 1, y    , z    , Block.fire.blockID, 0, 2); break;
					case 1: if (this.worldObj.getBlockId(x    , y    , z + 1) == 0) this.worldObj.setBlock(x    , y    , z + 1, Block.fire.blockID, 0, 2); break;
					case 2: if (this.worldObj.getBlockId(x    , y + 1, z    ) == 0) this.worldObj.setBlock(x    , y + 1, z    , Block.fire.blockID, 0, 2); break;
					case 3: if (this.worldObj.getBlockId(x - 1, y    , z    ) == 0) this.worldObj.setBlock(x - 1, y    , z    , Block.fire.blockID, 0, 2); break;
					case 4: if (this.worldObj.getBlockId(x    , y    , z - 1) == 0) this.worldObj.setBlock(x    , y    , z - 1, Block.fire.blockID, 0, 2); break;
				}
				
				if (random.nextInt(10) == 1) {
					this.worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, this.attackTarget.posX, this.attackTarget.posY, this.attackTarget.posZ));
				}
				break;
		}
	}
	
}

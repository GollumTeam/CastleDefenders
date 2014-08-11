package mods.castledefenders.common.aientities;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIMageAttack extends EntityAIBase {
	World worldObj;
	EntityLiving attacker;
	EntityLivingBase entityTarget;
	int attackTick;
	double field_75440_e;
	boolean field_75437_f;
	PathEntity entityPathEntity;
	Class classTarget;
	private int field_75445_i;
	int doattack;

	public EntityAIMageAttack(EntityLiving var1, Class var2, double d,
			boolean var4) {
		this(var1, d, var4);
		this.classTarget = var2;
	}

	public EntityAIMageAttack(EntityLiving var1, double d, boolean var3) {
		this.doattack = 0;
		this.attackTick = 0;
		this.attacker = var1;
		this.worldObj = var1.worldObj;
		this.field_75440_e = d;
		this.field_75437_f = var3;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		EntityLivingBase var1 = this.attacker.getAttackTarget();

		if (var1 == null) {
			return false;
		} else if (this.classTarget != null
				&& !this.classTarget.isAssignableFrom(var1.getClass())) {
			return false;
		} else {
			this.entityTarget = var1;
			this.entityPathEntity = this.attacker.getNavigator()
					.getPathToEntityLiving(this.entityTarget);
			return this.entityPathEntity != null;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {
		return this.shouldExecute() || !this.attacker.getNavigator().noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.attacker.getNavigator().setPath(this.entityPathEntity,
				this.field_75440_e);
		this.field_75445_i = 0;
	}

	/**
	 * Resets the task
	 */
	public void resetTask() {
		this.entityTarget = null;
		this.attacker.getNavigator().clearPathEntity();
		this.doattack = 0;
	}

	/**
	 * Updates the task
	 */
	public void updateTask() {
		this.attacker.getLookHelper().setLookPositionWithEntity(this.entityTarget, 30.0F, 30.0F);

		if ((this.field_75437_f || this.attacker.getEntitySenses().canSee(this.entityTarget)) && --this.field_75445_i <= 0) {
			this.field_75445_i = 4 + this.attacker.getRNG().nextInt(7);
			this.attacker.getNavigator().tryMoveToEntityLiving(this.entityTarget, this.field_75440_e);
		}

		this.attackTick = Math.max(this.attackTick - 1, 0);
		double var1 = (double) (this.attacker.width * 2.0F * this.attacker.width * 2.0F);

		if (this.doattack == 0) {
			this.attacker.attackEntityAsMob(this.entityTarget);
			this.doattack = 1;
		}

		Random var3 = new Random();
		int var4 = MathHelper.floor_double(this.entityTarget.posX);
		int var5 = MathHelper.floor_double(this.entityTarget.posY);
		int var6 = MathHelper.floor_double(this.entityTarget.posZ);

		if (this.worldObj.getBlockId(var4, var5, var6) == 0) {
			this.worldObj.setBlock(var4, var5, var6, Block.fire.blockID, 0, 2);
			this.worldObj.setBlock(var4 - 1, var5, var6, Block.fire.blockID, 0, 2);
		}

		int var7 = var3.nextInt(5);

		if (this.worldObj.getBlockId(var4 + 1, var5, var6) == 0 && var7 == 0) {
			this.worldObj.setBlock(var4 + 1, var5, var6, Block.fire.blockID, 0, 2);
		}

		if (this.worldObj.getBlockId(var4, var5, var6 + 1) == 0 && var7 == 1) {
			this.worldObj.setBlock(var4, var5, var6 + 1, Block.fire.blockID, 0, 2);
		}

		if (this.worldObj.getBlockId(var4, var5 + 1, var6) == 0 && var7 == 2) {
			this.worldObj.setBlock(var4, var5 + 1, var6, Block.fire.blockID, 0, 2);
		}

		if (this.worldObj.getBlockId(var4 - 1, var5, var6) == 0 && var7 == 3) {
			this.worldObj.setBlock(var4 - 1, var5, var6, Block.fire.blockID, 0, 2);
		}

		if (this.worldObj.getBlockId(var4, var5, var6 - 1) == 0 && var7 == 4) {
			this.worldObj.setBlock(var4, var5, var6 - 1, Block.fire.blockID, 0, 2);
		}

		int var8 = var3.nextInt(20);

		if (var8 == 1) {
			this.worldObj.spawnEntityInWorld(new EntityLightningBolt(
					this.worldObj, this.entityTarget.posX,
					this.entityTarget.posY, this.entityTarget.posZ));
		}
	}
}

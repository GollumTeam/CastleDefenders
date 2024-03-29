package com.gollum.castledefenders.common.entities;

import java.util.List;

import com.gollum.core.common.config.type.MobCapacitiesConfigType;
import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class EntityEnemy extends EntityMob implements ICastleEntity {
	
	protected Block blockSpawn;
	private int idTask = 0;
	private int idTargetTask = 0;


	public EntityEnemy(World world) {
		super(world);
		
		this.setSize(1.1F, 1.8F);

		((PathNavigateGround)this.getNavigator()).setBreakDoors(true); // Permet d'ouvrir les port
		((PathNavigateGround)this.getNavigator()).setEnterDoors(true); // Permet d'ouvrir les port
		((PathNavigateGround)this.getNavigator()).setCanSwim(true); // Peux nager l'eau

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)    .setBaseValue(this.getMaxHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE)  .setBaseValue(this.getFollowRange ());
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) .setBaseValue(this.getAttackStrength ());
	}
	
    protected void initEntityAI() {
		this.tasks.addTask(this.nextIdTask (), new EntityAIWanderAvoidWater(this, 1.D));
		this.tasks.addTask(this.nextIdTask (), new EntityAISwimming(this));
		this.tasks.addTask(this.nextIdTask (), new EntityAIWander(this, this.getMoveSpeed()));
		
		this.targetTasks.addTask(this.nextIdTask (), new EntityAINearestAttackableTarget<EntityLivingBase>(this, EntityLivingBase.class, 0, true, false, new Predicate<Entity>() {
			public boolean apply(Entity entity) {
				return 
					entity instanceof EntityPlayer ||
					entity instanceof EntityDefender ||
					entity instanceof EntityMercenary ||
					entity instanceof EntityIronGolem
				;
			}
		}));
    }

	/**
	 * Next Id Task
	 * @return
	 */
	public int nextIdTask () {
		return this.idTask++;
	}
	
	/**
	 * Next target Id Task
	 * @return
	 */
	public int nextIdTargetTask () {
		return this.idTargetTask++;
	}
	
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return this.getCapacities ().moveSpeed; }
	/**
	 * @return Point de vie du mod
	 */
	public double getMaxHealt () { return this.getCapacities ().maxHealt; }
	/**
	 * @return Point de vie du mod
	 */
	public double getAttackStrength () { return this.getCapacities ().attackStrength; }
	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return this.getCapacities ().followRange; }
	/**
	 * @return Vitesse de tir du mod
	 */
	public double getTimeRange() { return this.getCapacities ().timeRange; }
	
	/**
	 * @return les capacitées du mod
	 */
	protected abstract MobCapacitiesConfigType getCapacities ();
	
	
	/**
	 * Affecte les attributs de l'entity
	 */
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes ();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) .setBaseValue(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)     .setBaseValue(this.getMaxHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE)   .setBaseValue(this.getFollowRange ());
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)  .setBaseValue(this.getAttackStrength ());
	}
	
	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	protected int getDropItemId() {
		return 0;
	}
	
	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		
		int x = MathHelper.floor(this.posX);
		int y = MathHelper.floor(this.getEntityBoundingBox().minY);
		int z = MathHelper.floor(this.posZ);
		
		BlockPos pos = new BlockPos(x, y, z);
		
		IBlockState stateBlock = this.world.getBlockState(pos.down());
		IBlockState stateUp1 = this.world.getBlockState(pos);
		IBlockState stateUp2 = this.world.getBlockState(pos.up());
		

		List<? extends EntityEnemy> entityListBlockArround = this.world.getEntitiesWithinAABB(
			this.getClass(), 
			new AxisAlignedBB(
				this.posX,        this.posY,        this.posZ,
				this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D
			).expand(2.0D, 2.0D, 2.0D)
		);
		
		boolean found = false;
		for (Object arroundEntity : entityListBlockArround) {
			if (arroundEntity.getClass() == this.getClass()) {
				found = true;
			}
		}
		
		return
			stateBlock.getBlock() == this.blockSpawn &&
			(stateUp1 == null || stateUp1.getBlock() instanceof BlockAir || !stateUp1.getBlock().isCollidable()) &&
			(stateUp2 == null || stateUp2.getBlock() instanceof BlockAir || !stateUp2.getBlock().isCollidable()) &&
			!found
		;
	}
	
}

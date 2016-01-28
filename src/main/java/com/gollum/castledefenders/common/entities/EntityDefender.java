package com.gollum.castledefenders.common.entities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.gollum.castledefenders.inits.ModItems;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public abstract class EntityDefender extends EntityAnimal {
	
	protected ItemStack defaultHeldItem = null;
	protected Block blockSpawn;
	private int idTask = 0;
	private int idTargetTask = 0;


	public EntityDefender(World world) {
		super(world);
		
		this.setSize(1.1F, 1.8F);
		
		((PathNavigateGround)this.getNavigator()).setBreakDoors(true); // Permet d'ouvrir les port
		((PathNavigateGround)this.getNavigator()).setAvoidsWater(true); // Evite l'eau
		
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)    .setBaseValue(this.getMaxHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)  .setBaseValue(this.getFollowRange ());
		
		this.tasks.addTask(this.nextIdTask (), new EntityAITempt(this, 0.35F, ModItems.itemMedallion, false));
		this.tasks.addTask(this.nextIdTask (), new EntityAISwimming(this));
		this.tasks.addTask(this.nextIdTask (), new EntityAIWander(this, this.getMoveSpeed ()));
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
	protected double getMoveSpeed () { return this.getCapacities ().moveSpeed; }
	/**
	 * @return Point de vie du mod
	 */
	protected double getMaxHealt () { return this.getCapacities ().maxHealt; }
	/**
	 * @return Point de vie du mod
	 */
	protected double getAttackStrength () { return this.getCapacities ().attackStrength; }
	/**
	 * @return Zone de detection du mod
	 */
	protected double getFollowRange () { return this.getCapacities ().followRange; }
	/**
	 * @return Vitesse de tir du mod
	 */
	protected double getTimeRange() { return this.getCapacities ().timeRange; }
	
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
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed) .setBaseValue(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)     .setBaseValue(this.getMaxHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)   .setBaseValue(this.getFollowRange ());
	}
	
	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden
	 * by each mob to define their attack.
	 */
// TODO
//	@Override
//	protected void attackEntity(Entity entity, float var2) {
//		if (
//				this.attackTime <= 0 && var2 < 2.0F && 
//				entity.getEntityBoundingBox().maxY > this.getEntityBoundingBox().minY &&
//				entity.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY
//			) {
//			
//			this.attackTime = 10;
//			this.attackEntityAsMob(entity);
//		}
//	}
	
	// TODO
//	/**
//	 * Called when the entity is attacked.
//	 */
//	@Override
//	public boolean attackEntityFrom(DamageSource damageSource, float strength) {
//		
//		if (super.attackEntityFrom(damageSource, strength)) {
//			Entity entity = damageSource.getEntity();
//
//			if (this.riddenByEntity != entity && this.ridingEntity != entity) {
//				if (entity != this) {
//					this.entityToAttack = entity;
//				}
//
//				return true;
//			} else {
//				return true;
//			}
//		} else {
//			return false;
//		}
//	}

	/**
	 * Returns true if this entity can attack entities of the specified class.
	 */
	@Override
	public boolean canAttackClass(Class entityClass) {
		return EntityGhast.class != entityClass;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity) {
		double strength = this.getAttackStrength();

		if (this.isPotionActive(Potion.damageBoost)) {
			strength += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
		}

		if (this.isPotionActive(Potion.weakness)) {
			strength -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
		}

		return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)strength);
	}
	
	
	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.getEntityBoundingBox().minY);
		int z = MathHelper.floor_double(this.posZ);
		
		BlockPos pos = new BlockPos(x, y, z);
		
		IBlockState stateBlock = this.worldObj.getBlockState(pos.down());
		IBlockState stateUp1 = this.worldObj.getBlockState(pos);
		IBlockState stateUp2 = this.worldObj.getBlockState(pos.up());
		

		List entityListBlockArround = this.worldObj.getEntitiesWithinAABB(
			this.getClass(), 
			AxisAlignedBB.fromBounds(
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
	
	/**
	 * Returns the item that this EntityLiving is holding, if any.
	 */
	@Override
	public ItemStack getHeldItem() {
		
		if (this.defaultHeldItem == null) {
			return super.getHeldItem ();
		}
		
		return this.defaultHeldItem;
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}
}

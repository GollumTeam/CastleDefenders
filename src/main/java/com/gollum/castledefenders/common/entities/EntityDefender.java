package com.gollum.castledefenders.common.entities;

import java.util.List;

import com.gollum.castledefenders.inits.ModItems;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;
import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class EntityDefender extends EntityAnimal implements ICastleEntity {
	
	protected Block blockSpawn;
	private int idTask = 0;
	private int idTargetTask = 0;


	public EntityDefender(World world) {
		super(world);
		
		this.setSize(1.1F, 1.8F);
		
		((PathNavigateGround)this.getNavigator()).setBreakDoors(true); // Permet d'ouvrir les port
		((PathNavigateGround)this.getNavigator()).setEnterDoors(true); // Permet d'ouvrir les port
		((PathNavigateGround)this.getNavigator()).setCanSwim(true); // Peux nager l'eau

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.getMoveSpeed());
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)    .setBaseValue(this.getMaxHealt());
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE)  .setBaseValue(this.getFollowRange());
	}
	
    protected void initEntityAI() {
		this.tasks.addTask(this.nextIdTask (), new EntityAITempt(this, 0.35F, ModItems.MEDALLION, false));
		this.tasks.addTask(this.nextIdTask (), new EntityAISwimming(this));
		this.tasks.addTask(this.nextIdTask (), new EntityAIWander(this, this.getMoveSpeed ()));

        this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAIHurtByTarget(this, false, new Class[] {
    		EntityPlayer.class
        }));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget<EntityLiving> (this, EntityLiving.class, 0, true, false, new Predicate<Entity>() {
			public boolean apply(Entity entity) {
				return
					entity instanceof IMob
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
	}
	
	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float strength) {
		
		if (super.attackEntityFrom(damageSource, strength)) {
			
			Entity entity = damageSource.getImmediateSource();
			
			if (this.isPassenger(entity) && this.getRidingEntity().equals(entity)) {
				if (entity != this && entity instanceof EntityLivingBase) {
					this.setAttackTarget((EntityLivingBase)entity);
					return true;
				}
			}
		}
		return false;
	}

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

		if (this.isPotionActive(MobEffects.STRENGTH)) {
			strength += 3 << this.getActivePotionEffect(MobEffects.STRENGTH).getAmplifier();
		}

		if (this.isPotionActive(MobEffects.WEAKNESS)) {
			strength -= 2 << this.getActivePotionEffect(MobEffects.WEAKNESS).getAmplifier();
		}

		return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)strength);
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
		

		List entityListBlockArround = this.world.getEntitiesWithinAABB(
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
	
	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}
}

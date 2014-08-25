package mods.castledefenders.common.entities;

import java.util.List;

import mods.castledefenders.ModCastleDefenders;
import mods.gollum.core.common.config.container.MobCapacitiesConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityDefender extends EntityAnimal {
	
	protected ItemStack defaultHeldItem = null;
	protected int blockSpawnId;
	private int idTask = 0;
	private int idTargetTask = 0;


	public EntityDefender(World world) {
		super(world);
		
		this.setSize(1.1F, 1.8F);
		
		this.getNavigator().setBreakDoors(true); // Permet d'ouvrir les port
		this.getNavigator().setAvoidsWater(true); // Evite l'eau
		
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)    .setAttribute(this.getMaxHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)  .setAttribute(this.getFollowRange ());
		
		this.tasks.addTask(this.nextIdTask (), new EntityAITempt(this, 0.35F, ModCastleDefenders.itemMedallion.itemID, false));
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
	protected abstract MobCapacitiesConfig getCapacities ();
	
	
	/**
	 * Affecte les attributs de l'entity
	 */
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes ();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed) .setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)     .setAttribute(this.getMaxHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)   .setAttribute(this.getFollowRange ());
	}
	
	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled() {
		return true;
	}
	
	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden
	 * by each mob to define their attack.
	 */
	@Override
	protected void attackEntity(Entity entity, float var2) {
		if (
				this.attackTime <= 0 && var2 < 2.0F && 
				entity.boundingBox.maxY > this.boundingBox.minY &&
				entity.boundingBox.minY < this.boundingBox.maxY
			) {
			
			this.attackTime = 10;
			this.attackEntityAsMob(entity);
		}
	}
	
	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float strength) {
		
		if (super.attackEntityFrom(damageSource, strength)) {
			Entity entity = damageSource.getEntity();

			if (this.riddenByEntity != entity && this.ridingEntity != entity) {
				if (entity != this) {
					this.entityToAttack = entity;
				}

				return true;
			} else {
				return true;
			}
		} else {
			return false;
		}
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
		int y = MathHelper.floor_double(this.boundingBox.minY);
		int z = MathHelper.floor_double(this.posZ);
		
		int idBlock = this.worldObj.getBlockId(x, y - 1, z);
		int up1 = this.worldObj.getBlockId(x, y, z);
		int up2 = this.worldObj.getBlockId(x, y + 1, z);
		
		List entityListBlockArround = this.worldObj.getEntitiesWithinAABB(
			this.getClass(), 
			AxisAlignedBB.getBoundingBox(
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
			idBlock == this.blockSpawnId &&
			(up1 == 0 || !Block.blocksList[up1].isCollidable()) &&
			(up2 == 0 || !Block.blocksList[up2].isCollidable()) &&
			!found;
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

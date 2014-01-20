package mods.castledefenders.common.entities;

import java.util.List;

import mods.castledefenders.common.ModCastleDefenders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityEnemy extends EntityMob {
	
	public ItemStack defaultHeldItem = null;
	public int blockSpawnId;


	public EntityEnemy(World world) {
		super(world);

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)    .setAttribute(this.getHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)  .setAttribute(this.getFollowRange ());
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage) .setAttribute(this.getAttackStrength ());
		
		this.tasks.addTask(1, new EntityAITempt(this, 0.35F, ModCastleDefenders.itemMedallion.itemID, false));
		this.tasks.addTask(2, new EntityAISwimming(this));
	}

	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return 20.D; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return 0.1D; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return 0.0D; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return 10; }
	
	
	/**
	 * Affecte les attributs de l'entity
	 */
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes ();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed) .setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)     .setAttribute(this.getHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)   .setAttribute(this.getFollowRange ());
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)  .setAttribute(this.getAttackStrength ());
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
		int strength = this.getAttackStrength();

		if (this.isPotionActive(Potion.damageBoost)) {
			strength += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
		}

		if (this.isPotionActive(Potion.weakness)) {
			strength -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
		}

		return entity.attackEntityFrom(DamageSource.causeMobDamage(this), strength);
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
		
		List entityListBlockArround = this.worldObj.getEntitiesWithinAABB(
			this.getClass(), 
			AxisAlignedBB.getBoundingBox(
				this.posX,        this.posY,        this.posZ,
				this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D
			).expand(2.0D, 2.0D, 2.0D)
		);
		
		return
			idBlock == this.blockSpawnId &&
			this.worldObj.checkNoEntityCollision(this.boundingBox) &&
			this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 &&
			entityListBlockArround.isEmpty();
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
	
}

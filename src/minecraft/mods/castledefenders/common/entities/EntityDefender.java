package mods.castledefenders.common.entities;

import java.util.List;

import mods.castledefenders.common.ModCastleDefenders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDefender extends EntityMob {
	
	public EntityDefender(World world) {
		super(world);

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed) .setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)     .setAttribute(this.getHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)   .setAttribute(this.getFollowRange ());
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)   .setAttribute(this.getAttackStrength ());
		
		this.tasks.addTask(1, new EntityAITempt(this, 0.35F, ModCastleDefenders.ItemMedallion.itemID, false));
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
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes ();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed) .setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)     .setAttribute(this.getHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)   .setAttribute(this.getFollowRange ());
//		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)  .setAttribute(this.getAttackStrength ());
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
	protected void attackEntity(Entity var1, float var2) {
		if (this.attackTime <= 0 && var2 < 2.0F
				&& var1.boundingBox.maxY > this.boundingBox.minY
				&& var1.boundingBox.minY < this.boundingBox.maxY) {
			this.attackTime = 10;
			this.attackEntityAsMob(var1);
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	// TODO @Override
	public boolean attackEntityFrom(DamageSource var1, float var2) {
		if (super.attackEntityFrom(var1, var2)) {
			Entity var3 = var1.getEntity();

			if (this.riddenByEntity != var3 && this.ridingEntity != var3) {
				if (var3 != this) {
					this.entityToAttack = var3;
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
	public boolean canAttackClass(Class var1) {
		return EntityGhast.class != var1;
	}

	@Override
	public boolean attackEntityAsMob(Entity var1) {
		int var2 = this.getAttackStrength();

		if (this.isPotionActive(Potion.damageBoost)) {
			var2 += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
		}

		if (this.isPotionActive(Potion.weakness)) {
			var2 -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
		}

		return var1.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
	}
	

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		
		if (this.worldObj.countEntities(this.getClass()) >= 15) {
			return false;
		} else {
			
			int var1 = MathHelper.floor_double(this.posX);
			int var2 = MathHelper.floor_double(this.boundingBox.minY);
			int var3 = MathHelper.floor_double(this.posZ);
			this.worldObj.getBlockId(var1, var2 - 1, var3);
			
			List var5 = this.worldObj.getEntitiesWithinAABB(
				EntityKnight.class,
				AxisAlignedBB.getBoundingBox(
					this.posX       , this.posY       , this.posZ,
					this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D
				).expand(
					2.0D, 2.0D, 2.0D
				)
			);
			
			return 
				this.worldObj.getBlockId(var1, var2 - 1, var3) == ModCastleDefenders.BlockKnightID && 
				this.worldObj.checkNoEntityCollision(this.boundingBox) && 
				this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && var5.isEmpty()
			;
		}
	}
}

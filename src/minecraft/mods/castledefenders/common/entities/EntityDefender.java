package mods.castledefenders.common.entities;

import mods.castledefender.common.ModCastleDefenders;
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
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityDefender extends EntityMob {
	
	public EntityDefender(World world) {
		super(world);
		
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
}

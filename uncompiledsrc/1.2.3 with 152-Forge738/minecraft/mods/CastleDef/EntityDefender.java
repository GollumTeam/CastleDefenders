package mods.CastleDef;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityDefender extends EntityAnimal
{
    protected int attackStrength = 10;

    public EntityDefender(World var1)
    {
        super(var1);
        this.moveSpeed = 0.1F;
        this.tasks.addTask(1, new EntityAITempt(this, 0.35F, mod_castledef.ItemMedallion.itemID, false));
        this.tasks.addTask(2, new EntityAISwimming(this));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity var1, float var2)
    {
        if (this.attackTime <= 0 && var2 < 2.0F && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 10;
            this.attackEntityAsMob(var1);
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource var1, int var2)
    {
        if (super.attackEntityFrom(var1, var2))
        {
            Entity var3 = var1.getEntity();

            if (this.riddenByEntity != var3 && this.ridingEntity != var3)
            {
                if (var3 != this)
                {
                    this.entityToAttack = var3;
                }

                return true;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns true if this entity can attack entities of the specified class.
     */
    public boolean canAttackClass(Class var1)
    {
        return EntityGhast.class != var1;
    }

    public boolean attackEntityAsMob(Entity var1)
    {
        int var2 = this.attackStrength;

        if (this.isPotionActive(Potion.damageBoost))
        {
            var2 += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }

        if (this.isPotionActive(Potion.weakness))
        {
            var2 -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
        }

        return var1.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
    }

    public EntityAgeable createChild(EntityAgeable var1)
    {
        return null;
    }

    public int getMaxHealth()
    {
        return 0;
    }
}

package net.minecraft.src;

public abstract class EntityDefender extends EntityCreature
{
    protected int attackStrength;

    public EntityDefender(World world)
    {
        super(world);
        attackStrength = 10;
        moveSpeed = 0.1F;
        tasks.addTask(1, new EntityAITempt(this, 0.25F, mod_castle2.medallion.shiftedIndex, false));
        tasks.addTask(2, new EntityAISwimming(this));
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
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && f < 2.0F && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            attackTime = 10;
            attackEntityAsMob(entity);
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();

            if (riddenByEntity == entity || ridingEntity == entity)
            {
                return true;
            }

            if (entity != this)
            {
                entityToAttack = entity;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean attackEntityAsMob(Entity entity)
    {
        int i = attackStrength;

        if (isPotionActive(Potion.damageBoost))
        {
            i += 3 << getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }

        if (isPotionActive(Potion.weakness))
        {
            i -= 2 << getActivePotionEffect(Potion.weakness).getAmplifier();
        }

        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);
    }
}

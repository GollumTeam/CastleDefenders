package net.minecraft.src;

import java.util.Random;

public class EntityAIMageAttack extends EntityAIBase
{
    World worldObj;
    EntityLiving entityHost;
    EntityLiving attackTarget;
    int rangedAttackTime;
    float field_48370_e;
    int field_48367_f;
    int rangedAttackID;
    int maxRangedAttackTime;

    public EntityAIMageAttack(EntityLiving entityliving, float f, int i, int j)
    {
        rangedAttackTime = 0;
        field_48367_f = 0;
        entityHost = entityliving;
        worldObj = entityliving.worldObj;
        field_48370_e = f;
        rangedAttackID = i;
        maxRangedAttackTime = j;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLiving entityliving = entityHost.getAttackTarget();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            attackTarget = entityliving;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return shouldExecute() || !entityHost.getNavigator().noPath();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        attackTarget = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        double d = 100D;
        double d1 = entityHost.getDistanceSq(attackTarget.posX, attackTarget.boundingBox.minY, attackTarget.posZ);
        boolean flag = entityHost.func_48090_aM().canSee(attackTarget);

        if (flag)
        {
            field_48367_f++;
        }
        else
        {
            field_48367_f = 0;
        }

        if (d1 > d || field_48367_f < 20)
        {
            entityHost.getNavigator().func_48667_a(attackTarget, field_48370_e);
        }
        else
        {
            entityHost.getNavigator().clearPathEntity();
        }

        entityHost.getLookHelper().setLookPositionWithEntity(attackTarget, 30F, 30F);
        rangedAttackTime = Math.max(rangedAttackTime - 1, 0);

        if (rangedAttackTime > 0)
        {
            return;
        }

        if (d1 > d || !flag)
        {
            return;
        }
        else
        {
            doRangedAttack();
            rangedAttackTime = maxRangedAttackTime;
            return;
        }
    }

    private void doRangedAttack()
    {
        if (rangedAttackID == 1)
        {
            Random random = new Random();
            int i = MathHelper.floor_double(attackTarget.posX);
            int k = MathHelper.floor_double(attackTarget.posY);
            int i1 = MathHelper.floor_double(attackTarget.posZ);

            if (worldObj.getBlockId(i, k, i1) == 0)
            {
                worldObj.setBlockWithNotify(i, k, i1, Block.fire.blockID);
            }

            int k1 = random.nextInt(16);

            if (k1 == 1)
            {
                worldObj.spawnEntityInWorld(new EntityLightningBolt(worldObj, attackTarget.posX, attackTarget.posY, attackTarget.posZ));
            }
        }
        else
        {
            Random random1 = new Random();
            int j = MathHelper.floor_double(attackTarget.posX);
            int l = MathHelper.floor_double(attackTarget.posY);
            int j1 = MathHelper.floor_double(attackTarget.posZ);
            worldObj.spawnEntityInWorld(new EntityLightningBolt(worldObj, attackTarget.posX, attackTarget.posY, attackTarget.posZ));
        }
    }
}

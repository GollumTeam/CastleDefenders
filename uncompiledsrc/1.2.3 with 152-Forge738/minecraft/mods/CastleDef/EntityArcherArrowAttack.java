package mods.CastleDef;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityArcherArrowAttack extends EntityAIBase
{
    World worldObj;
    EntityLiving entityHost;
    EntityLiving attackTarget;
    int rangedAttackTime = 0;
    float entityMoveSpeed;
    int field_75318_f = 0;
    int rangedAttackID;
    int maxRangedAttackTime;

    public EntityArcherArrowAttack(EntityLiving var1, float var2, int var3, int var4)
    {
        this.entityHost = var1;
        this.worldObj = var1.worldObj;
        this.entityMoveSpeed = var2;
        this.rangedAttackID = var3;
        this.maxRangedAttackTime = var4;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLiving var1 = this.entityHost.getAttackTarget();

        if (var1 == null)
        {
            return false;
        }
        else
        {
            this.attackTarget = var1;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.attackTarget = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        double var1 = 100.0D;
        double var3 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.boundingBox.minY, this.attackTarget.posZ);
        boolean var5 = this.entityHost.getEntitySenses().canSee(this.attackTarget);

        if (var5)
        {
            ++this.field_75318_f;
        }
        else
        {
            this.field_75318_f = 0;
        }

        if (var3 <= var1 && this.field_75318_f >= 20)
        {
            this.entityHost.getNavigator().clearPathEntity();
        }
        else
        {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
        }

        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
        this.rangedAttackTime = Math.max(this.rangedAttackTime - 1, 0);

        if (this.rangedAttackTime <= 0 && var3 <= var1 && var5)
        {
            this.doRangedAttack();
            this.rangedAttackTime = this.maxRangedAttackTime;
        }
    }

    private void doRangedAttack()
    {
        if (this.rangedAttackID == 1)
        {
            EntityArrow var1 = new EntityArrow(this.worldObj, this.entityHost, this.attackTarget, 1.6F, 12.0F);
            this.worldObj.playSoundAtEntity(this.entityHost, "random.bow", 1.0F, 1.0F / (this.entityHost.getRNG().nextFloat() * 0.4F + 0.8F));
            this.worldObj.spawnEntityInWorld(var1);
        }
        else
        {
            int var2;
            int var3;
            int var4;
            int var5;
            int var6;
            Random var7;

            if (this.rangedAttackID == 2)
            {
                var7 = new Random();
                var2 = MathHelper.floor_double(this.attackTarget.posX);
                var3 = MathHelper.floor_double(this.attackTarget.posY);
                var4 = MathHelper.floor_double(this.attackTarget.posZ);
                var5 = var7.nextInt(2);
                var6 = var7.nextInt(2);
                this.worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, this.attackTarget.posX + (double)var5 - (double)var6, this.attackTarget.posY, this.attackTarget.posZ + (double)var5 - (double)var6));

                if (this.worldObj.getBlockId(var2, var3, var4) == 0)
                {
                    this.worldObj.setBlock(var2 + var5 - var6, var3, var4 + var5 - var6, Block.fire.blockID, 0, 2);
                }
            }
            else if (this.rangedAttackID == 3)
            {
                var7 = new Random();
                var2 = MathHelper.floor_double(this.attackTarget.posX);
                var3 = MathHelper.floor_double(this.attackTarget.posY);
                var4 = MathHelper.floor_double(this.attackTarget.posZ);

                if (this.worldObj.getBlockId(var2, var3, var4) == 0)
                {
                    this.worldObj.setBlock(var2, var3, var4, Block.fire.blockID, 0, 2);
                }

                var5 = var7.nextInt(5);

                if (this.worldObj.getBlockId(var2 + 1, var3, var4) == 0 && var5 == 0)
                {
                    this.worldObj.setBlock(var2 + 1, var3, var4, Block.fire.blockID, 0, 2);
                }

                if (this.worldObj.getBlockId(var2, var3, var4 + 1) == 0 && var5 == 1)
                {
                    this.worldObj.setBlock(var2, var3, var4 + 1, Block.fire.blockID, 0, 2);
                }

                if (this.worldObj.getBlockId(var2, var3 + 1, var4) == 0 && var5 == 2)
                {
                    this.worldObj.setBlock(var2, var3 + 1, var4, Block.fire.blockID, 0, 2);
                }

                if (this.worldObj.getBlockId(var2 - 1, var3, var4) == 0 && var5 == 3)
                {
                    this.worldObj.setBlock(var2 - 1, var3, var4, Block.fire.blockID, 0, 2);
                }

                if (this.worldObj.getBlockId(var2, var3, var4 - 1) == 0 && var5 == 4)
                {
                    this.worldObj.setBlock(var2, var3, var4 - 1, Block.fire.blockID, 0, 2);
                }

                var6 = var7.nextInt(10);

                if (var6 == 1)
                {
                    this.worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, this.attackTarget.posX, this.attackTarget.posY, this.attackTarget.posZ));
                }
            }
        }
    }
}

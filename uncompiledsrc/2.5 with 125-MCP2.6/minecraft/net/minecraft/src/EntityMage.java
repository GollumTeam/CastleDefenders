package net.minecraft.src;

import java.util.List;

public class EntityMage extends EntityDefender
{
    protected int attackStrength;

    public EntityMage(World world)
    {
        super(world);
        texture = "/Mage.png";
        health = 15;
        moveSpeed = 0.0F;
        setSize(0.9F, 1.8F);
        isImmuneToFire = true;
        attackStrength = 0;
        tasks.addTask(2, new EntityAIMageAttack(this, moveSpeed, 1, 5));
        tasks.addTask(2, new EntityAIWander(this, moveSpeed));
        targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, net.minecraft.src.IMob.class, 30F, 0, false));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return 0;
    }

    public int getMaxHealth()
    {
        return 20;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        if (worldObj.countEntities(getClass()) >= 500)
        {
            return false;
        }
        else
        {
            int i = MathHelper.floor_double(posX);
            int j = MathHelper.floor_double(boundingBox.minY);
            int k = MathHelper.floor_double(posZ);
            int l = worldObj.getBlockId(i, j - 1, k);
            List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityMage.class, AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(2D, 2D, 2D));
            return worldObj.getBlockId(i, j - 1, k) == 230 && worldObj.checkIfAABBIsClear(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && list.isEmpty();
        }
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 0;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return false;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }
}

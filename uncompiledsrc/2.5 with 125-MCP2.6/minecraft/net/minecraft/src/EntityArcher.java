package net.minecraft.src;

import java.util.List;

public class EntityArcher extends EntityDefender
{
    private static final ItemStack defaultHeldItem;

    public EntityArcher(World world)
    {
        super(world);
        texture = "/archer.png";
        health = 20;
        moveSpeed = 0.0F;
        setSize(1.1F, 1.8F);
        tasks.addTask(1, new EntityAIArrowAttack(this, moveSpeed, 1, 30));
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
            List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityArcher.class, AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(2D, 2D, 2D));
            return worldObj.getBlockId(i, j - 1, k) == 239 && worldObj.checkIfAABBIsClear(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && list.isEmpty();
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return false;
    }

    public int getMaxHealth()
    {
        return 20;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 0;
    }

    /**
     * Returns the item that this EntityLiving is holding, if any.
     */
    public ItemStack getHeldItem()
    {
        return defaultHeldItem;
    }

    static
    {
        defaultHeldItem = new ItemStack(Item.bow, 1);
    }
}

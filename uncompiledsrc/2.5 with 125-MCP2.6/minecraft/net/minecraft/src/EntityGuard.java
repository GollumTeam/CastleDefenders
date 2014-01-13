package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EntityGuard extends EntityTameable
{
    private boolean looksWithInterest;
    private float field_25048_b;
    private float field_25054_c;
    private boolean isShaking;
    private boolean field_25052_g;
    private float timeGuardIsShaking;
    private float prevTimeGuardIsShaking;
    private static final ItemStack defaultHeldItem;
    protected int attackStrength;

    public EntityGuard(World world)
    {
        super(world);
        looksWithInterest = false;
        texture = "/merc2.png";
        setSize(1.0F, 1.8F);
        moveSpeed = 0.3F;
        health = 20;
        attackStrength = 6;
        getNavigator().func_48664_a(true);
        tasks.addTask(1, new EntityAISwimming(this));
        tasks.addTask(2, aiSit);
        tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        tasks.addTask(4, new EntityAIAttackOnCollide(this, moveSpeed, true));
        tasks.addTask(5, new EntityAIFollowOwner(this, moveSpeed, 10F, 2.0F));
        tasks.addTask(7, new EntityAIWander(this, moveSpeed));
        tasks.addTask(9, new EntityAIWatchClosest(this, net.minecraft.src.EntityPlayer.class, 8F));
        tasks.addTask(9, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        targetTasks.addTask(4, new EntityAITargetNonTamed(this, net.minecraft.src.IMob.class, 16F, 200, false));
        targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, net.minecraft.src.IMob.class, 16F, 0, true));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void setAttackTarget(EntityLiving entityliving)
    {
        super.setAttackTarget(entityliving);

        if (entityliving instanceof EntityPlayer)
        {
            setAngry(true);
        }
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void updateAITick()
    {
        dataWatcher.updateObject(18, Integer.valueOf(getHealth()));
    }

    public int getMaxHealth()
    {
        return isTamed() ? 20 : 8;
    }

    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(18, new Integer(getHealth()));
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    /**
     * Returns the item that this EntityLiving is holding, if any.
     */
    public ItemStack getHeldItem()
    {
        return defaultHeldItem;
    }

    /**
     * Returns the texture's file path as a String.
     */
    public String getTexture()
    {
        if (isSitting())
        {
            return "/merc1.png";
        }

        if (isTamed())
        {
            return "/merc2.png";
        }
        else
        {
            return super.getTexture();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Angry", isAngry());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setAngry(nbttagcompound.getBoolean("Angry"));
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return isAngry();
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
            List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityGuard.class, AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(2D, 2D, 2D));
            return worldObj.getBlockId(i, j - 1, k) == 234 && worldObj.checkIfAABBIsClear(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && list.isEmpty();
        }
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return -1;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!worldObj.isRemote && isShaking && !field_25052_g && !hasPath() && onGround)
        {
            field_25052_g = true;
            timeGuardIsShaking = 0.0F;
            prevTimeGuardIsShaking = 0.0F;
            worldObj.setEntityState(this, (byte)8);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        field_25054_c = field_25048_b;

        if (looksWithInterest)
        {
            field_25048_b = field_25048_b + (1.0F - field_25048_b) * 0.4F;
        }
        else
        {
            field_25048_b = field_25048_b + (0.0F - field_25048_b) * 0.4F;
        }

        if (looksWithInterest)
        {
            numTicksToChaseTarget = 10;
        }

        if (isWet())
        {
            isShaking = true;
            field_25052_g = false;
            timeGuardIsShaking = 0.0F;
            prevTimeGuardIsShaking = 0.0F;
        }
        else if ((isShaking || field_25052_g) && field_25052_g)
        {
            if (timeGuardIsShaking == 0.0F)
            {
                worldObj.playSoundAtEntity(this, "mob.Guard.shake", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }

            prevTimeGuardIsShaking = timeGuardIsShaking;
            timeGuardIsShaking += 0.05F;

            if (prevTimeGuardIsShaking >= 2.0F)
            {
                isShaking = false;
                field_25052_g = false;
                prevTimeGuardIsShaking = 0.0F;
                timeGuardIsShaking = 0.0F;
            }

            if (timeGuardIsShaking > 0.4F)
            {
                float f = (float)boundingBox.minY;
                int i = (int)(MathHelper.sin((timeGuardIsShaking - 0.4F) * (float)Math.PI) * 7F);

                for (int j = 0; j < i; j++)
                {
                    float f1 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
                    float f2 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
                }
            }
        }
    }

    public boolean getGuardShaking()
    {
        return isShaking;
    }

    public float getInterestedAngle(float f)
    {
        return (field_25054_c + (field_25048_b - field_25054_c) * f) * 0.15F * (float)Math.PI;
    }

    public float getEyeHeight()
    {
        return height * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        if (isSitting())
        {
            return 20;
        }
        else
        {
            return super.getVerticalFaceSpeed();
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        Entity entity = damagesource.getEntity();
        aiSit.func_48407_a(false);

        if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
        {
            i = (i + 1) / 2;
        }

        return super.attackEntityFrom(damagesource, i);
    }

    public boolean attackEntityAsMob(Entity entity)
    {
        byte byte0 = (byte)(isTamed() ? 4 : 2);
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), byte0);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if (!isTamed())
        {
            if (itemstack != null && itemstack.itemID == Item.ingotGold.shiftedIndex && !isAngry())
            {
                if (!entityplayer.capabilities.isCreativeMode)
                {
                    itemstack.stackSize--;
                }

                if (itemstack.stackSize <= 0)
                {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }

                if (!worldObj.isRemote)
                {
                    setTamed(true);
                    setPathToEntity(null);
                    setAttackTarget(null);
                    aiSit.func_48407_a(true);
                    setEntityHealth(20);
                    setOwner(entityplayer.username);
                    func_48142_a(false);
                    worldObj.setEntityState(this, (byte)7);
                    moveSpeed = 0.4F;
                }

                return true;
            }
        }
        else
        {
            if (itemstack != null && (Item.itemsList[itemstack.itemID] instanceof ItemFood))
            {
                ItemFood itemfood = (ItemFood)Item.itemsList[itemstack.itemID];

                if (itemfood.isWolfsFavoriteMeat() && dataWatcher.getWatchableObjectInt(18) < 20)
                {
                    if (!entityplayer.capabilities.isCreativeMode)
                    {
                        itemstack.stackSize--;
                    }

                    heal(itemfood.getHealAmount());

                    if (itemstack.stackSize <= 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }

                    return true;
                }
            }

            if (entityplayer.username.equalsIgnoreCase(getOwnerName()) && !worldObj.isRemote && !isWheat(itemstack))
            {
                aiSit.func_48407_a(!isSitting());
                isJumping = false;
                setPathToEntity(null);
            }
        }

        return super.interact(entityplayer);
    }

    public void handleHealthUpdate(byte byte0)
    {
        if (byte0 == 8)
        {
            field_25052_g = true;
            timeGuardIsShaking = 0.0F;
            prevTimeGuardIsShaking = 0.0F;
        }
        else
        {
            super.handleHealthUpdate(byte0);
        }
    }

    public float getTailRotation()
    {
        if (isAngry())
        {
            return 1.53938F;
        }

        if (isTamed())
        {
            return (0.55F - (float)(20 - dataWatcher.getWatchableObjectInt(18)) * 0.02F) * (float)Math.PI;
        }
        else
        {
            return ((float)Math.PI / 5F);
        }
    }

    /**
     * Checks if the parameter is an wheat item.
     */
    public boolean isWheat(ItemStack itemstack)
    {
        if (itemstack == null)
        {
            return false;
        }

        if (!(Item.itemsList[itemstack.itemID] instanceof ItemFood))
        {
            return false;
        }
        else
        {
            return ((ItemFood)Item.itemsList[itemstack.itemID]).isWolfsFavoriteMeat();
        }
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 8;
    }

    public boolean isAngry()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    public void setAngry(boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);

        if (flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 2)));
        }
        else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -3)));
        }
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal spawnBabyAnimal(EntityAnimal entityanimal)
    {
        EntityGuard entityguard = new EntityGuard(worldObj);
        entityguard.setOwner(getOwnerName());
        entityguard.setTamed(true);
        return entityguard;
    }

    public void func_48150_h(boolean flag)
    {
        looksWithInterest = flag;
    }

    public boolean func_48135_b(EntityAnimal entityanimal)
    {
        if (entityanimal == this)
        {
            return false;
        }

        if (!isTamed())
        {
            return false;
        }

        if (!(entityanimal instanceof EntityGuard))
        {
            return false;
        }

        EntityGuard entityguard = (EntityGuard)entityanimal;

        if (!entityguard.isTamed())
        {
            return false;
        }

        if (entityguard.isSitting())
        {
            return false;
        }
        else
        {
            return isInLove() && entityguard.isInLove();
        }
    }

    static
    {
        defaultHeldItem = new ItemStack(Item.swordSteel, 1);
    }
}

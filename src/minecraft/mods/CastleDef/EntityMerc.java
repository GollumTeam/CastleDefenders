package mods.CastleDef;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMerc extends EntityTameable
{
    private boolean looksWithInterest = false;
    private float field_25048_b;
    private float field_25054_c;
    private boolean isShaking;
    private boolean field_25052_g;
    private float timeGuardIsShaking;
    private float prevTimeGuardIsShaking;
    protected int attackStrength;
    public static ItemStack defaultHeldItem = new ItemStack(Item.swordIron, 1);

    public EntityMerc(World var1)
    {
    	// TODO
		super(var1);
//		this.texture = "/mods/merc2.png";
		this.setSize(1.0F, 1.8F);
//		this.health = 25;
//		this.moveSpeed = 0.3F;
		this.attackStrength = 6;
		this.getNavigator().setAvoidsWater(false);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
//		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
//		this.tasks.addTask(5, new EntityAIFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
//		this.tasks.addTask(7, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
//		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, IMob.class, 16.0F, 200, false));
//		this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, IMob.class, 16.0F, 0, true));
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
    public void setAttackTarget(EntityLivingBase var1)
    {
        super.setAttackTarget(var1);

        if (var1 instanceof EntityPlayer)
        {
            this.setAngry(true);
        }
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void updateAITick()
    {
    	// TODO
        //this.dataWatcher.updateObject(18, Integer.valueOf(this.getHealth()));
    }

    /**
     * Play the taming effect, will either be hearts or smoke depending on status
     */
    protected void playTameEffect(boolean var1) {}

    public int getMaxHealth()
    {
        return !this.isTamed() ? 8 : 20;
    }

    protected void entityInit()
    {
        super.entityInit();
     // TODO
//        this.dataWatcher.addObject(18, new Integer(this.getHealth()));
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
     * Returns the texture's file path as a String.
     */
    // TODO
//    public String getTexture()
//    {
//        return this.isSitting() ? "/mods/merc1.png" : (this.isTamed() ? "/mods/merc2.png" : super.getTexture());
//    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setBoolean("Angry", this.isAngry());
    }

    /**
     * Returns true if this entity can attack entities of the specified class.
     */
    public boolean canAttackClass(Class var1)
    {
        return EntityGhast.class != var1;
    }

    /**
     * Makes entity wear random armor based on difficulty
     */
    public void addRandomArmor()
    {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(Item.swordIron));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        this.setAngry(var1.getBoolean("Angry"));
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !this.isTamed();
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        if (this.worldObj.countEntities(this.getClass()) >= 500)
        {
            return false;
        }
        else
        {
            int var1 = MathHelper.floor_double(this.posX);
            int var2 = MathHelper.floor_double(this.boundingBox.minY);
            int var3 = MathHelper.floor_double(this.posZ);
            this.worldObj.getBlockId(var1, var2 - 1, var3);
            List var5 = this.worldObj.getEntitiesWithinAABB(EntityMerc.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(2.0D, 2.0D, 2.0D));
            return this.worldObj.getBlockId(var1, var2 - 1, var3) == mod_castledef.BlockMercID && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && var5.isEmpty();
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

        if (!this.isTamed())
        {
            this.aiSit.setSitting(!this.isSitting());
        }

        if (!this.worldObj.isRemote && this.isShaking && !this.field_25052_g && !this.hasPath() && this.onGround)
        {
            this.field_25052_g = true;
            this.timeGuardIsShaking = 0.0F;
            this.prevTimeGuardIsShaking = 0.0F;
            this.worldObj.setEntityState(this, (byte)8);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.isTamed())
        {
            this.aiSit.setSitting(!this.isSitting());
        }

        this.field_25054_c = this.field_25048_b;

        if (this.looksWithInterest)
        {
            this.field_25048_b += (1.0F - this.field_25048_b) * 0.4F;
        }
        else
        {
            this.field_25048_b += (0.0F - this.field_25048_b) * 0.4F;
        }

        if (this.looksWithInterest)
        {
            this.numTicksToChaseTarget = 10;
        }

        if (this.isWet())
        {
            this.isShaking = true;
            this.field_25052_g = false;
            this.timeGuardIsShaking = 0.0F;
            this.prevTimeGuardIsShaking = 0.0F;
        }
        else if ((this.isShaking || this.field_25052_g) && this.field_25052_g)
        {
            if (this.timeGuardIsShaking == 0.0F)
            {
                this.worldObj.playSoundAtEntity(this, "mob.Guard.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.prevTimeGuardIsShaking = this.timeGuardIsShaking;
            this.timeGuardIsShaking += 0.05F;

            if (this.prevTimeGuardIsShaking >= 2.0F)
            {
                this.isShaking = false;
                this.field_25052_g = false;
                this.prevTimeGuardIsShaking = 0.0F;
                this.timeGuardIsShaking = 0.0F;
            }

            if (this.timeGuardIsShaking > 0.4F)
            {
                float var1 = (float)this.boundingBox.minY;
                int var2 = (int)(MathHelper.sin((this.timeGuardIsShaking - 0.4F) * (float)Math.PI) * 7.0F);

                for (int var3 = 0; var3 < var2; ++var3)
                {
                    float var4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    float var5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                }
            }
        }
    }

    public boolean getGuardShaking()
    {
        return this.isShaking;
    }

    public float getInterestedAngle(float var1)
    {
        return (this.field_25054_c + (this.field_25048_b - this.field_25054_c) * var1) * 0.15F * (float)Math.PI;
    }

    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource var1, int var2)
    {
        Entity var3 = var1.getEntity();
        this.aiSit.setSitting(false);

        if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow))
        {
            var2 = (var2 + 1) / 2;
        }

        return super.attackEntityFrom(var1, var2);
    }

    public boolean attackEntityAsMob(Entity var1)
    {
        byte var2 = (byte)(this.isTamed() ? 4 : 2);
        return var1.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer var1)
    {
        ItemStack var2 = var1.inventory.getCurrentItem();

        if (!this.isTamed())
        {
            if (var2 != null && var2.itemID == Item.ingotGold.itemID && !this.isAngry())
            {
                if (!var1.capabilities.isCreativeMode)
                {
                    --var2.stackSize;
                }

                if (var2.stackSize <= 0)
                {
                    var1.inventory.setInventorySlotContents(var1.inventory.currentItem, (ItemStack)null);
                }

                if (!this.worldObj.isRemote)
                {
                    this.setTamed(true);
                    this.setPathToEntity((PathEntity)null);
                    this.setAttackTarget((EntityLiving)null);
                    this.setSitting(false);
                    this.setEntityHealth(20);
                    this.setOwner(var1.username);
                    this.isJumping = false;
                    this.worldObj.setEntityState(this, (byte)7);
                    // TODO
//                    this.moveSpeed = 0.3F;
                }

                return true;
            }
        }
        else
        {
            if (var2 != null && Item.itemsList[var2.itemID] instanceof ItemFood)
            {
                ItemFood var3 = (ItemFood)Item.itemsList[var2.itemID];

                if (var3.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectInt(18) < 20)
                {
                    if (!var1.capabilities.isCreativeMode)
                    {
                        --var2.stackSize;
                    }

                    this.heal(var3.getHealAmount());

                    if (var2.stackSize <= 0)
                    {
                        var1.inventory.setInventorySlotContents(var1.inventory.currentItem, (ItemStack)null);
                    }

                    return true;
                }
            }

            if (var1.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && !this.isWheat(var2))
            {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity((PathEntity)null);
            }
        }

        return super.interact(var1);
    }

    public void handleHealthUpdate(byte var1)
    {
        if (var1 == 8)
        {
            this.field_25052_g = true;
            this.timeGuardIsShaking = 0.0F;
            this.prevTimeGuardIsShaking = 0.0F;
        }
        else
        {
            super.handleHealthUpdate(var1);
        }
    }

    public float getTailRotation()
    {
        return this.isAngry() ? 1.53938F : (this.isTamed() ? (0.55F - (float)(20 - this.dataWatcher.getWatchableObjectInt(18)) * 0.02F) * (float)Math.PI : ((float)Math.PI / 5F));
    }

    public boolean isWheat(ItemStack var1)
    {
        return var1 == null ? false : (!(Item.itemsList[var1.itemID] instanceof ItemFood) ? false : ((ItemFood)Item.itemsList[var1.itemID]).isWolfsFavoriteMeat());
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
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    public void setAngry(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 2)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -3)));
        }
    }

    public EntityAnimal spawnBabyAnimal(EntityAnimal var1)
    {
        EntityMerc var2 = new EntityMerc(this.worldObj);
        var2.setOwner(this.getOwnerName());
        var2.setTamed(true);
        return var2;
    }

    public void func_48150_h(boolean var1)
    {
        this.looksWithInterest = var1;
    }

    public boolean func_48135_b(EntityAnimal var1)
    {
        if (var1 == this)
        {
            return false;
        }
        else if (!this.isTamed())
        {
            return false;
        }
        else if (!(var1 instanceof EntityMerc))
        {
            return false;
        }
        else
        {
            EntityMerc var2 = (EntityMerc)var1;
            return !var2.isTamed() ? false : (var2.isSitting() ? false : this.isInLove() && var2.isInLove());
        }
    }

    /**
     * Returns the item that this EntityLiving is holding, if any.
     */
    public ItemStack getHeldItem()
    {
        return defaultHeldItem;
    }

    public EntityAgeable createChild(EntityAgeable var1)
    {
        return null;
    }
}

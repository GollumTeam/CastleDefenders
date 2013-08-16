package mods.CastleDef;

import java.util.List;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityArcherM extends EntityTameable
{
    private static final ItemStack defaultHeldItem = new ItemStack(Item.bow, 1);

    public EntityArcherM(World var1)
    {
        super(var1);
        this.texture = "/mods/archerM.png";
        this.health = 20;
        this.moveSpeed = 0.4F;
        this.setSize(1.1F, 1.8F);
        this.tasks.addTask(1, this.aiSit);
        this.tasks.addTask(2, new EntityAIFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
        this.tasks.addTask(3, new EntityArcherArrowAttack(this, this.moveSpeed, 1, 15));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, IMob.class, 30.0F, 0, true));
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
     * Returns the item that this EntityLiving is holding, if any.
     */
    public ItemStack getHeldItem()
    {
        return defaultHeldItem;
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
            List var5 = this.worldObj.getEntitiesWithinAABB(EntityArcherM.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(2.0D, 2.0D, 2.0D));
            return this.worldObj.getBlockId(var1, var2 - 1, var3) == mod_castledef.BlockArcherMID && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && var5.isEmpty();
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
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer var1)
    {
        ItemStack var2 = var1.inventory.getCurrentItem();

        if (!this.isTamed() && var2 != null && var2.itemID == Item.ingotGold.itemID)
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
                this.setSitting(false);
                this.setEntityHealth(20);
                this.setOwner(var1.username);
                this.isJumping = false;
                this.worldObj.setEntityState(this, (byte)7);
                this.moveSpeed = 0.4F;
            }

            return true;
        }
        else
        {
            if (var1.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote)
            {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity((PathEntity)null);
            }

            return super.interact(var1);
        }
    }

    /**
     * Returns true if this entity can attack entities of the specified class.
     */
    public boolean canAttackClass(Class var1)
    {
        return EntityGhast.class != var1;
    }

    /**
     * Play the taming effect, will either be hearts or smoke depending on status
     */
    protected void playTameEffect(boolean var1) {}

    public EntityAgeable createChild(EntityAgeable var1)
    {
        return null;
    }
}

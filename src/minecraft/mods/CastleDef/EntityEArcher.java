package mods.CastleDef;

import java.util.List;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityEArcher extends EntityMob
{
    private static final ItemStack defaultHeldItem = new ItemStack(Item.bow, 1);

    public EntityEArcher(World var1)
    {
		super(var1);
		// TODO
//		this.texture = "/mods/ArcherE.png";
//		this.health = 20;
//		this.moveSpeed = 0.0F;
		this.setSize(1.1F, 1.8F);
//		this.tasks.addTask(1, new EntityArcherArrowAttack(this, this.moveSpeed, 1, 40));
//		this.tasks.addTask(2, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(3, new EntityAISwimming(this));
//		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 20.0F, 0, true));
//		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityDefender.class, 20.0F, 0, true));
//		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityMerc.class, 16.0F, 0, true));
//		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityArcherM.class, 16.0F, 0, true));
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
            List var5 = this.worldObj.getEntitiesWithinAABB(EntityEArcher.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(2.0D, 2.0D, 2.0D));
            return this.worldObj.getBlockId(var1, var2 - 1, var3) == mod_castledef.BlockEArcherID && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && var5.isEmpty();
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
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
}

package mods.CastleDef;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityBlockMage extends TileEntity
{
    public int delay = -1;
    private String mobID = "Mage";
    private NBTTagCompound spawnerTags = null;
    public double yaw;
    public double yaw2 = 0.0D;
    private int minSpawnDelay = 200;
    private int maxSpawnDelay = 800;
    private int spawnCount = 4;
    private Entity spawnedMob;

    public TileEntityBlockMage()
    {
        this.delay = 20;
    }

    public String getMobID()
    {
        return this.mobID;
    }

    public void setMobID(String var1)
    {
        this.mobID = var1;
    }

    public boolean anyPlayerInRange()
    {
        return this.worldObj.getClosestPlayer((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D, 16.0D) != null;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        if (this.anyPlayerInRange())
        {
            if (this.worldObj.isRemote)
            {
                double var1 = (double)((float)this.xCoord + this.worldObj.rand.nextFloat());
                double var3 = (double)((float)this.yCoord + this.worldObj.rand.nextFloat());
                double var5 = (double)((float)this.zCoord + this.worldObj.rand.nextFloat());
                this.worldObj.spawnParticle("smoke", var1, var3, var5, 0.0D, 0.0D, 0.0D);
                this.worldObj.spawnParticle("flame", var1, var3, var5, 0.0D, 0.0D, 0.0D);
                this.yaw2 = this.yaw % 360.0D;
                this.yaw += 4.545454502105713D;
            }
            else
            {
                if (this.delay == -1)
                {
                    this.updateDelay();
                }

                if (this.delay > 0)
                {
                    --this.delay;
                    return;
                }

                for (int var11 = 0; var11 < this.spawnCount; ++var11)
                {
                    Entity var2 = EntityList.createEntityByName(this.mobID, this.worldObj);

                    if (var2 == null)
                    {
                        return;
                    }

                    int var12 = this.worldObj.getEntitiesWithinAABB(var2.getClass(), AxisAlignedBB.getAABBPool().getAABB((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 1), (double)(this.zCoord + 1)).expand(8.0D, 4.0D, 8.0D)).size();

                    if (var12 >= 6)
                    {
                        this.updateDelay();
                        return;
                    }

                    if (var2 != null)
                    {
                        double var4 = (double)this.xCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0D;
                        double var6 = (double)(this.yCoord + this.worldObj.rand.nextInt(3) - 1);
                        double var8 = (double)this.zCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0D;
                        EntityLiving var10 = var2 instanceof EntityLiving ? (EntityLiving)var2 : null;
                        var2.setLocationAndAngles(var4, var6, var8, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);

                        if (var10 == null || var10.getCanSpawnHere())
                        {
                            this.writeNBTTagsTo(var2);
                            this.worldObj.spawnEntityInWorld(var2);
                            this.worldObj.playAuxSFX(2004, this.xCoord, this.yCoord, this.zCoord, 0);

                            if (var10 != null)
                            {
                                var10.spawnExplosionParticle();
                            }

                            this.updateDelay();
                        }
                    }
                }
            }

            super.updateEntity();
        }
    }

    public void writeNBTTagsTo(Entity var1)
    {
        if (this.spawnerTags != null)
        {
            NBTTagCompound var2 = new NBTTagCompound();
            var1.addEntityID(var2);
            Iterator var3 = this.spawnerTags.getTags().iterator();

            while (var3.hasNext())
            {
                NBTBase var4 = (NBTBase)var3.next();
                var2.setTag(var4.getName(), var4.copy());
            }

            var1.readFromNBT(var2);
        }
    }

    private void updateDelay()
    {
        this.delay = this.minSpawnDelay + this.worldObj.rand.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.mobID = var1.getString("EntityId");
        this.delay = var1.getShort("Delay");

        if (var1.hasKey("SpawnData"))
        {
            this.spawnerTags = var1.getCompoundTag("SpawnData");
        }
        else
        {
            this.spawnerTags = null;
        }

        if (var1.hasKey("MinSpawnDelay"))
        {
            this.minSpawnDelay = var1.getShort("MinSpawnDelay");
            this.maxSpawnDelay = var1.getShort("MaxSpawnDelay");
            this.spawnCount = var1.getShort("SpawnCount");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setString("EntityId", this.mobID);
        var1.setShort("Delay", (short)this.delay);
        var1.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
        var1.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
        var1.setShort("SpawnCount", (short)this.spawnCount);

        if (this.spawnerTags != null)
        {
            var1.setCompoundTag("SpawnData", this.spawnerTags);
        }
    }

    public Entity getMobEntity()
    {
        if (this.spawnedMob == null)
        {
            Entity var1 = EntityList.createEntityByName(this.getMobID(), (World)null);
            this.writeNBTTagsTo(var1);
            this.spawnedMob = var1;
        }

        return this.spawnedMob;
    }

    public Packet132TileEntityData getAuxillaryInfoPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }
}

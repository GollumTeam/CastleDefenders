package cpw.mods.fml.common.registry;

import com.google.common.base.Function;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.EntitySpawnPacket;
import net.minecraft.entity.Entity;

public class EntityRegistry$EntityRegistration
{
    private Class entityClass;
    private ModContainer container;
    private String entityName;
    private int modId;
    private int trackingRange;
    private int updateFrequency;
    private boolean sendsVelocityUpdates;
    private Function customSpawnCallback;
    private boolean usesVanillaSpawning;

    final EntityRegistry this$0;

    public EntityRegistry$EntityRegistration(EntityRegistry var1, ModContainer var2, Class var3, String var4, int var5, int var6, int var7, boolean var8)
    {
        this.this$0 = var1;
        this.container = var2;
        this.entityClass = var3;
        this.entityName = var4;
        this.modId = var5;
        this.trackingRange = var6;
        this.updateFrequency = var7;
        this.sendsVelocityUpdates = var8;
    }

    public Class getEntityClass()
    {
        return this.entityClass;
    }

    public ModContainer getContainer()
    {
        return this.container;
    }

    public String getEntityName()
    {
        return this.entityName;
    }

    public int getModEntityId()
    {
        return this.modId;
    }

    public int getTrackingRange()
    {
        return this.trackingRange;
    }

    public int getUpdateFrequency()
    {
        return this.updateFrequency;
    }

    public boolean sendsVelocityUpdates()
    {
        return this.sendsVelocityUpdates;
    }

    public boolean usesVanillaSpawning()
    {
        return this.usesVanillaSpawning;
    }

    public boolean hasCustomSpawning()
    {
        return this.customSpawnCallback != null;
    }

    public Entity doCustomSpawning(EntitySpawnPacket var1) throws Exception
    {
        return (Entity)this.customSpawnCallback.apply(var1);
    }

    public void setCustomSpawning(Function var1, boolean var2)
    {
        this.customSpawnCallback = var1;
        this.usesVanillaSpawning = var2;
    }
}

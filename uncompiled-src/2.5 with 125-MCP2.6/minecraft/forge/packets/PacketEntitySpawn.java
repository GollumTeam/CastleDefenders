package forge.packets;

import forge.*;
import java.io.*;
import net.minecraft.src.*;

public class PacketEntitySpawn extends ForgePacket
{
    public int modID;
    public int entityID;
    public int typeID;
    public int posX;
    public int posY;
    public int posZ;
    public byte yaw;
    public byte pitch;
    public byte yawHead;
    public int throwerID;
    public int speedX;
    public int speedY;
    public int speedZ;
    public Object metadata;
    private ISpawnHandler handler;

    public PacketEntitySpawn()
    {
    }

    public PacketEntitySpawn(Entity entity, NetworkMod networkmod, int i)
    {
        entityID = entity.entityId;
        posX = MathHelper.floor_double(entity.posX * 32D);
        posY = MathHelper.floor_double(entity.posY * 32D);
        posZ = MathHelper.floor_double(entity.posZ * 32D);
        typeID = i;
        modID = MinecraftForge.getModID(networkmod);
        yaw = (byte)(int)((entity.rotationYaw * 256F) / 360F);
        pitch = (byte)(int)((entity.rotationPitch * 256F) / 360F);
        yawHead = (byte)(int)((entity instanceof EntityLiving) ? (((EntityLiving)entity).rotationYawHead * 256F) / 360F : 0.0F);
        metadata = entity.getDataWatcher();

        if (entity instanceof IThrowableEntity)
        {
            Entity entity1 = ((IThrowableEntity)entity).getThrower();
            throwerID = entity1 != null ? entity1.entityId : entity.entityId;
            double d = 3.8999999999999999D;
            double d1 = entity.motionX;
            double d2 = entity.motionY;
            double d3 = entity.motionZ;

            if (d1 < -d)
            {
                d1 = -d;
            }

            if (d2 < -d)
            {
                d2 = -d;
            }

            if (d3 < -d)
            {
                d3 = -d;
            }

            if (d1 > d)
            {
                d1 = d;
            }

            if (d2 > d)
            {
                d2 = d;
            }

            if (d3 > d)
            {
                d3 = d;
            }

            speedX = (int)(d1 * 8000D);
            speedY = (int)(d2 * 8000D);
            speedZ = (int)(d3 * 8000D);
        }

        if (entity instanceof ISpawnHandler)
        {
            handler = (ISpawnHandler)entity;
        }
    }

    public void writeData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(modID);
        dataoutputstream.writeInt(entityID);
        dataoutputstream.writeByte(typeID & 0xff);
        dataoutputstream.writeInt(posX);
        dataoutputstream.writeInt(posY);
        dataoutputstream.writeInt(posZ);
        dataoutputstream.writeByte(yaw);
        dataoutputstream.writeByte(pitch);
        dataoutputstream.writeByte(yawHead);
        ((DataWatcher)metadata).writeWatchableObjects(dataoutputstream);
        dataoutputstream.writeInt(throwerID);

        if (throwerID != 0)
        {
            dataoutputstream.writeShort(speedX);
            dataoutputstream.writeShort(speedY);
            dataoutputstream.writeShort(speedZ);
        }

        if (handler != null)
        {
            handler.writeSpawnData(dataoutputstream);
        }
    }

    public void readData(DataInputStream datainputstream) throws IOException
    {
        modID = datainputstream.readInt();
        entityID = datainputstream.readInt();
        typeID = datainputstream.readByte() & 0xff;
        posX = datainputstream.readInt();
        posY = datainputstream.readInt();
        posZ = datainputstream.readInt();
        yaw = datainputstream.readByte();
        pitch = datainputstream.readByte();
        yawHead = datainputstream.readByte();
        metadata = DataWatcher.readWatchableObjects(datainputstream);
        throwerID = datainputstream.readInt();

        if (throwerID != 0)
        {
            speedX = datainputstream.readShort();
            speedY = datainputstream.readShort();
            speedZ = datainputstream.readShort();
        }
    }

    public int getID()
    {
        return 1;
    }
}

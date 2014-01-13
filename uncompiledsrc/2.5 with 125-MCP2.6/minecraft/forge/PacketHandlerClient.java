package forge;

import forge.packets.ForgePacket;
import forge.packets.PacketEntitySpawn;
import forge.packets.PacketHandlerBase;
import forge.packets.PacketMissingMods;
import forge.packets.PacketModList;
import forge.packets.PacketOpenGUI;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class PacketHandlerClient extends PacketHandlerBase
{
    public PacketHandlerClient()
    {
    }

    public void onPacketData(NetworkManager networkmanager, String s, byte abyte0[])
    {
        DataInputStream datainputstream = new DataInputStream(new ByteArrayInputStream(abyte0));

        try
        {
            Object obj = null;
            NetClientHandler netclienthandler = (NetClientHandler)networkmanager.getNetHandler();
            int i = datainputstream.read();

            switch (i)
            {
                case 1:
                    PacketEntitySpawn packetentityspawn = new PacketEntitySpawn();
                    packetentityspawn.readData(datainputstream);
                    onEntitySpawnPacket((PacketEntitySpawn)packetentityspawn, datainputstream, ModLoader.getMinecraftInstance().theWorld);
                    break;

                case 2:
                    PacketModList packetmodlist = new PacketModList(false);
                    packetmodlist.readData(datainputstream);
                    onModListCheck(netclienthandler, (PacketModList)packetmodlist);
                    break;

                case 3:
                    PacketMissingMods packetmissingmods = new PacketMissingMods(false);
                    packetmissingmods.readData(datainputstream);
                    onMissingMods((PacketMissingMods)packetmissingmods, netclienthandler);
                    break;

                case 5:
                    PacketOpenGUI packetopengui = new PacketOpenGUI();
                    packetopengui.readData(datainputstream);
                    onOpenGui((PacketOpenGUI)packetopengui);
                    break;
            }
        }
        catch (IOException ioexception)
        {
            ModLoader.getLogger().log(Level.SEVERE, "Exception in PacketHandlerClient.onPacketData", ioexception);
            ioexception.printStackTrace();
        }
    }

    public void onEntitySpawnPacket(PacketEntitySpawn packetentityspawn, DataInputStream datainputstream, World world)
    {
        if (DEBUG)
        {
            System.out.println((new StringBuilder()).append("S->C: ").append(packetentityspawn.toString(true)).toString());
        }

        Class class1 = MinecraftForge.getEntityClass(packetentityspawn.modID, packetentityspawn.typeID);

        if (class1 == null)
        {
            System.out.println((new StringBuilder()).append("Could not find entity info for ").append(Integer.toHexString(packetentityspawn.modID)).append(" : ").append(packetentityspawn.typeID).toString());
            return;
        }

        double d = (double)packetentityspawn.posX / 32D;
        double d1 = (double)packetentityspawn.posY / 32D;
        double d2 = (double)packetentityspawn.posZ / 32D;
        float f = (float)(packetentityspawn.yaw * 360) / 256F;
        float f1 = (float)(packetentityspawn.pitch * 360) / 256F;
        float f2 = (float)(packetentityspawn.yawHead * 360) / 256F;

        try
        {
            Entity entity = (Entity)class1.getConstructor(new Class[]
                    {
                        net.minecraft.src.World.class
                    }).newInstance(new Object[]
                            {
                                world
                            });

            if (entity instanceof IThrowableEntity)
            {
                Minecraft minecraft = ModLoader.getMinecraftInstance();
                Object obj = minecraft.thePlayer.entityId != packetentityspawn.throwerID ? ((Object)(((WorldClient)world).getEntityByID(packetentityspawn.throwerID))) : ((Object)(minecraft.thePlayer));
                ((IThrowableEntity)entity).setThrower(((Entity)(obj)));
            }

            entity.serverPosX = packetentityspawn.posX;
            entity.serverPosY = packetentityspawn.posY;
            entity.serverPosZ = packetentityspawn.posZ;
            Entity aentity[] = entity.getParts();

            if (aentity != null)
            {
                int i = packetentityspawn.entityID - entity.entityId;

                for (int j = 0; j < aentity.length; j++)
                {
                    aentity[j].entityId += i;
                }
            }

            entity.entityId = packetentityspawn.entityID;
            entity.setPositionAndRotation(d, d1, d2, f, f1);

            if (entity instanceof EntityLiving)
            {
                ((EntityLiving)entity).rotationYawHead = f2;
            }

            if (packetentityspawn.metadata != null)
            {
                entity.getDataWatcher().updateWatchedObjectsFromList((List)packetentityspawn.metadata);
            }

            if (packetentityspawn.throwerID > 0)
            {
                entity.setVelocity((double)packetentityspawn.speedX / 8000D, (double)packetentityspawn.speedY / 8000D, (double)packetentityspawn.speedZ / 8000D);
            }

            if (entity instanceof ISpawnHandler)
            {
                ((ISpawnHandler)entity).readSpawnData(datainputstream);
            }

            ((WorldClient)world).addEntityToWorld(packetentityspawn.entityID, entity);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            ModLoader.getLogger().throwing("ForgeHooksClient", "onEntitySpawnPacket", exception);
            ModLoader.throwException(String.format("Error spawning entity of type %d for %s.", new Object[]
                    {
                        Integer.valueOf(packetentityspawn.typeID), MinecraftForge.getModByID(packetentityspawn.modID)
                    }), exception);
        }
    }

    private void onModListCheck(NetClientHandler netclienthandler, PacketModList packetmodlist)
    {
        if (DEBUG)
        {
            System.out.println((new StringBuilder()).append("S->C: ").append(packetmodlist.toString(true)).toString());
        }

        ForgeHooks.networkMods.clear();
        NetworkMod anetworkmod[] = MinecraftForge.getNetworkMods();
        NetworkMod anetworkmod1[] = anetworkmod;
        int i = anetworkmod1.length;
        label0:

        for (int j = 0; j < i; j++)
        {
            NetworkMod networkmod = anetworkmod1[j];
            Iterator iterator1 = packetmodlist.ModIDs.entrySet().iterator();

            do
            {
                if (!iterator1.hasNext())
                {
                    continue label0;
                }

                java.util.Map.Entry entry = (java.util.Map.Entry)iterator1.next();

                if (networkmod.toString().equals(entry.getValue()))
                {
                    ForgeHooks.networkMods.put(entry.getKey(), networkmod);
                }
            }
            while (true);
        }

        ArrayList arraylist = new ArrayList();
        NetworkMod anetworkmod2[] = anetworkmod;
        int k = anetworkmod2.length;

        for (int l = 0; l < k; l++)
        {
            NetworkMod networkmod1 = anetworkmod2[l];

            if (MinecraftForge.getModID(networkmod1) == -1 && networkmod1.serverSideRequired())
            {
                arraylist.add(networkmod1);
            }
        }

        PacketModList packetmodlist1 = new PacketModList(false);
        packetmodlist1.Mods = new String[ModLoader.getLoadedMods().size()];
        k = 0;

        for (Iterator iterator = ModLoader.getLoadedMods().iterator(); iterator.hasNext();)
        {
            BaseMod basemod = (BaseMod)iterator.next();
            packetmodlist1.Mods[k++] = basemod.toString();
        }

        netclienthandler.addToSendQueue(packetmodlist1.getPacket());

        if (DEBUG)
        {
            System.out.println((new StringBuilder()).append("C->S: ").append(packetmodlist1.toString(true)).toString());
        }
    }

    private void onMissingMods(PacketMissingMods packetmissingmods, NetClientHandler netclienthandler)
    {
        if (DEBUG)
        {
            System.out.println((new StringBuilder()).append("S->C: ").append(packetmissingmods.toString(true)).toString());
        }

        netclienthandler.disconnect();
        Minecraft minecraft = ModLoader.getMinecraftInstance();
        minecraft.changeWorld1(null);
        minecraft.displayGuiScreen(new GuiMissingMods(packetmissingmods));
    }

    private void onOpenGui(PacketOpenGUI packetopengui)
    {
        if (DEBUG)
        {
            System.out.println((new StringBuilder()).append("S->C: ").append(packetopengui.toString(true)).toString());
        }

        NetworkMod networkmod = MinecraftForge.getModByID(packetopengui.ModID);

        if (networkmod != null)
        {
            EntityPlayerSP entityplayersp = ModLoader.getMinecraftInstance().thePlayer;
            entityplayersp.openGui(networkmod, packetopengui.GuiID, entityplayersp.worldObj, packetopengui.X, packetopengui.Y, packetopengui.Z);
            entityplayersp.craftingInventory.windowId = packetopengui.WindowID;
        }
    }

    public void sendPacket(NetworkManager networkmanager, Packet packet)
    {
        NetClientHandler netclienthandler = (NetClientHandler)networkmanager.getNetHandler();
        netclienthandler.addToSendQueue(packet);
    }
}

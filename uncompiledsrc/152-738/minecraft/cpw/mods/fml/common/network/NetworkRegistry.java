package cpw.mods.fml.common.network;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.FMLPacket$Type;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class NetworkRegistry
{
    private static final NetworkRegistry INSTANCE = new NetworkRegistry();
    private Multimap activeChannels = ArrayListMultimap.create();
    private Multimap universalPacketHandlers = ArrayListMultimap.create();
    private Multimap clientPacketHandlers = ArrayListMultimap.create();
    private Multimap serverPacketHandlers = ArrayListMultimap.create();
    private Set connectionHandlers = Sets.newLinkedHashSet();
    private Map serverGuiHandlers = Maps.newHashMap();
    private Map clientGuiHandlers = Maps.newHashMap();
    private List chatListeners = Lists.newArrayList();

    public static NetworkRegistry instance()
    {
        return INSTANCE;
    }

    byte[] getPacketRegistry(Side var1)
    {
        return Joiner.on('\u0000').join(Iterables.concat(Arrays.asList(new String[] {"FML"}), this.universalPacketHandlers.keySet(), var1.isClient() ? this.clientPacketHandlers.keySet() : this.serverPacketHandlers.keySet())).getBytes(Charsets.UTF_8);
    }

    public boolean isChannelActive(String var1, Player var2)
    {
        return this.activeChannels.containsEntry(var2, var1);
    }

    public void registerChannel(IPacketHandler var1, String var2)
    {
        if (!Strings.isNullOrEmpty(var2) && (var2 == null || var2.length() <= 16))
        {
            this.universalPacketHandlers.put(var2, var1);
        }
        else
        {
            FMLLog.severe("Invalid channel name \'%s\' : %s", new Object[] {var2, Strings.isNullOrEmpty(var2) ? "Channel name is empty" : "Channel name is too long (16 chars is maximum)"});
            throw new RuntimeException("Channel name is invalid");
        }
    }

    public void registerChannel(IPacketHandler var1, String var2, Side var3)
    {
        if (var3 == null)
        {
            this.registerChannel(var1, var2);
        }
        else if (!Strings.isNullOrEmpty(var2) && (var2 == null || var2.length() <= 16))
        {
            if (var3.isClient())
            {
                this.clientPacketHandlers.put(var2, var1);
            }
            else
            {
                this.serverPacketHandlers.put(var2, var1);
            }
        }
        else
        {
            FMLLog.severe("Invalid channel name \'%s\' : %s", new Object[] {var2, Strings.isNullOrEmpty(var2) ? "Channel name is empty" : "Channel name is too long (16 chars is maximum)"});
            throw new RuntimeException("Channel name is invalid");
        }
    }

    void activateChannel(Player var1, String var2)
    {
        this.activeChannels.put(var1, var2);
    }

    void deactivateChannel(Player var1, String var2)
    {
        this.activeChannels.remove(var1, var2);
    }

    public void registerConnectionHandler(IConnectionHandler var1)
    {
        this.connectionHandlers.add(var1);
    }

    public void registerChatListener(IChatListener var1)
    {
        this.chatListeners.add(var1);
    }

    void playerLoggedIn(EntityPlayerMP var1, NetServerHandler var2, INetworkManager var3)
    {
        this.generateChannelRegistration(var1, var2, var3);
        Iterator var4 = this.connectionHandlers.iterator();

        while (var4.hasNext())
        {
            IConnectionHandler var5 = (IConnectionHandler)var4.next();
            var5.playerLoggedIn((Player)var1, var2, var3);
        }
    }

    String connectionReceived(NetLoginHandler var1, INetworkManager var2)
    {
        Iterator var3 = this.connectionHandlers.iterator();
        String var5;

        do
        {
            if (!var3.hasNext())
            {
                return null;
            }

            IConnectionHandler var4 = (IConnectionHandler)var3.next();
            var5 = var4.connectionReceived(var1, var2);
        }
        while (Strings.isNullOrEmpty(var5));

        return var5;
    }

    void connectionOpened(NetHandler var1, String var2, int var3, INetworkManager var4)
    {
        Iterator var5 = this.connectionHandlers.iterator();

        while (var5.hasNext())
        {
            IConnectionHandler var6 = (IConnectionHandler)var5.next();
            var6.connectionOpened(var1, var2, var3, var4);
        }
    }

    void connectionOpened(NetHandler var1, MinecraftServer var2, INetworkManager var3)
    {
        Iterator var4 = this.connectionHandlers.iterator();

        while (var4.hasNext())
        {
            IConnectionHandler var5 = (IConnectionHandler)var4.next();
            var5.connectionOpened(var1, var2, var3);
        }
    }

    void clientLoggedIn(NetHandler var1, INetworkManager var2, Packet1Login var3)
    {
        this.generateChannelRegistration(var1.getPlayer(), var1, var2);
        Iterator var4 = this.connectionHandlers.iterator();

        while (var4.hasNext())
        {
            IConnectionHandler var5 = (IConnectionHandler)var4.next();
            var5.clientLoggedIn(var1, var2, var3);
        }
    }

    void connectionClosed(INetworkManager var1, EntityPlayer var2)
    {
        Iterator var3 = this.connectionHandlers.iterator();

        while (var3.hasNext())
        {
            IConnectionHandler var4 = (IConnectionHandler)var3.next();
            var4.connectionClosed(var1);
        }

        this.activeChannels.removeAll(var2);
    }

    void generateChannelRegistration(EntityPlayer var1, NetHandler var2, INetworkManager var3)
    {
        Packet250CustomPayload var4 = new Packet250CustomPayload();
        var4.channel = "REGISTER";
        var4.data = this.getPacketRegistry(var1 instanceof EntityPlayerMP ? Side.SERVER : Side.CLIENT);
        var4.length = var4.data.length;
        var3.addToSendQueue(var4);
    }

    void handleCustomPacket(Packet250CustomPayload var1, INetworkManager var2, NetHandler var3)
    {
        if ("REGISTER".equals(var1.channel))
        {
            this.handleRegistrationPacket(var1, (Player)var3.getPlayer());
        }
        else if ("UNREGISTER".equals(var1.channel))
        {
            this.handleUnregistrationPacket(var1, (Player)var3.getPlayer());
        }
        else
        {
            this.handlePacket(var1, var2, (Player)var3.getPlayer());
        }
    }

    private void handlePacket(Packet250CustomPayload var1, INetworkManager var2, Player var3)
    {
        String var4 = var1.channel;
        Iterator var5 = Iterables.concat(this.universalPacketHandlers.get(var4), var3 instanceof EntityPlayerMP ? this.serverPacketHandlers.get(var4) : this.clientPacketHandlers.get(var4)).iterator();

        while (var5.hasNext())
        {
            IPacketHandler var6 = (IPacketHandler)var5.next();
            var6.onPacketData(var2, var1, var3);
        }
    }

    private void handleRegistrationPacket(Packet250CustomPayload var1, Player var2)
    {
        List var3 = this.extractChannelList(var1);
        Iterator var4 = var3.iterator();

        while (var4.hasNext())
        {
            String var5 = (String)var4.next();
            this.activateChannel(var2, var5);
        }
    }

    private void handleUnregistrationPacket(Packet250CustomPayload var1, Player var2)
    {
        List var3 = this.extractChannelList(var1);
        Iterator var4 = var3.iterator();

        while (var4.hasNext())
        {
            String var5 = (String)var4.next();
            this.deactivateChannel(var2, var5);
        }
    }

    private List extractChannelList(Packet250CustomPayload var1)
    {
        String var2 = new String(var1.data, Charsets.UTF_8);
        ArrayList var3 = Lists.newArrayList(Splitter.on('\u0000').split(var2));
        return var3;
    }

    public void registerGuiHandler(Object var1, IGuiHandler var2)
    {
        ModContainer var3 = FMLCommonHandler.instance().findContainerFor(var1);

        if (var3 == null)
        {
            var3 = Loader.instance().activeModContainer();
            FMLLog.log(Level.WARNING, "Mod %s attempted to register a gui network handler during a construction phase", new Object[] {var3.getModId()});
        }

        NetworkModHandler var4 = FMLNetworkHandler.instance().findNetworkModHandler(var3);

        if (var4 == null)
        {
            FMLLog.log(Level.FINE, "The mod %s needs to be a @NetworkMod to register a Networked Gui Handler", new Object[] {var3.getModId()});
        }
        else
        {
            this.serverGuiHandlers.put(var3, var2);
        }

        this.clientGuiHandlers.put(var3, var2);
    }

    void openRemoteGui(ModContainer var1, EntityPlayerMP var2, int var3, World var4, int var5, int var6, int var7)
    {
        IGuiHandler var8 = (IGuiHandler)this.serverGuiHandlers.get(var1);
        NetworkModHandler var9 = FMLNetworkHandler.instance().findNetworkModHandler(var1);

        if (var8 != null && var9 != null)
        {
            Container var10 = (Container)var8.getServerGuiElement(var3, var2, var4, var5, var6, var7);

            if (var10 != null)
            {
                var2.incrementWindowID();
                var2.closeInventory();
                int var11 = var2.currentWindowId;
                Packet250CustomPayload var12 = new Packet250CustomPayload();
                var12.channel = "FML";
                var12.data = FMLPacket.makePacket(FMLPacket$Type.GUIOPEN, new Object[] {Integer.valueOf(var11), Integer.valueOf(var9.getNetworkId()), Integer.valueOf(var3), Integer.valueOf(var5), Integer.valueOf(var6), Integer.valueOf(var7)});
                var12.length = var12.data.length;
                var2.playerNetServerHandler.sendPacketToPlayer(var12);
                var2.openContainer = var10;
                var2.openContainer.windowId = var11;
                var2.openContainer.addCraftingToCrafters(var2);
            }
        }
    }

    void openLocalGui(ModContainer var1, EntityPlayer var2, int var3, World var4, int var5, int var6, int var7)
    {
        IGuiHandler var8 = (IGuiHandler)this.clientGuiHandlers.get(var1);
        FMLCommonHandler.instance().showGuiScreen(var8.getClientGuiElement(var3, var2, var4, var5, var6, var7));
    }

    public Packet3Chat handleChat(NetHandler var1, Packet3Chat var2)
    {
        Side var3 = Side.CLIENT;

        if (var1 instanceof NetServerHandler)
        {
            var3 = Side.SERVER;
        }

        IChatListener var5;

        for (Iterator var4 = this.chatListeners.iterator(); var4.hasNext(); var2 = var3.isClient() ? var5.clientChat(var1, var2) : var5.serverChat(var1, var2))
        {
            var5 = (IChatListener)var4.next();
        }

        return var2;
    }

    public void handleTinyPacket(NetHandler var1, Packet131MapData var2)
    {
        NetworkModHandler var3 = FMLNetworkHandler.instance().findNetworkModHandler(Integer.valueOf(var2.itemID));

        if (var3 == null)
        {
            FMLLog.info("Received a tiny packet for network id %d that is not recognised here", new Object[] {Short.valueOf(var2.itemID)});
        }
        else
        {
            if (var3.hasTinyPacketHandler())
            {
                var3.getTinyPacketHandler().handle(var1, var2);
            }
            else
            {
                FMLLog.info("Received a tiny packet for a network mod that does not accept tiny packets %s", new Object[] {var3.getContainer().getModId()});
            }
        }
    }
}

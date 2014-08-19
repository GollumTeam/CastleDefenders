package net.minecraft.src;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.TickType;
import java.util.Map;
import java.util.Random;
import net.minecraft.client.Minecraft;

public abstract class BaseMod implements cpw.mods.fml.common.modloader.BaseMod
{
    public BaseMod()
    {
    }

    public void keyBindingEvent(Object obj)
    {
        keyboardEvent((KeyBinding)obj);
    }

    public final boolean doTickInGame(TickType ticktype, boolean flag, Object obj, Object aobj[])
    {
        Minecraft minecraft = (Minecraft)obj;
        boolean flag1 = minecraft.theWorld != null;

        if ((flag && ticktype == TickType.GAME || flag && ticktype == TickType.RENDER) && flag1)
        {
            return onTickInGame(((Float)aobj[0]).floatValue(), minecraft);
        }

        if (flag && ticktype == TickType.WORLDGUI || flag && ticktype == TickType.GUI)
        {
            return onTickInGUI(((Float)aobj[0]).floatValue(), minecraft, (GuiScreen)aobj[1]);
        }

        if (ticktype == TickType.WORLDLOAD && flag1)
        {
            return onTickInGame(0.0F, minecraft);
        }

        if (ticktype == TickType.GUILOAD)
        {
            return onTickInGUI(0.0F, minecraft, minecraft.currentScreen);
        }
        else
        {
            return true;
        }
    }

    public final void onRenderHarvest(Map map)
    {
        addRenderer(map);
    }

    public final void onRegisterAnimations()
    {
        registerAnimation(FMLClientHandler.instance().getClient());
    }

    public final void onCrafting(Object aobj[])
    {
        takenFromCrafting((EntityPlayer)aobj[0], (ItemStack)aobj[1], (IInventory)aobj[2]);
    }

    public final void onSmelting(Object aobj[])
    {
        takenFromFurnace((EntityPlayer)aobj[0], (ItemStack)aobj[1]);
    }

    public final boolean dispense(double d, double d1, double d2, byte byte0, byte byte1, Object aobj[])
    {
        return dispenseEntity((World)aobj[0], d, d1, d2, byte0, byte1, (ItemStack)aobj[1]);
    }

    public final boolean onChat(Object aobj[])
    {
        receiveChatPacket(((Packet3Chat)aobj[0]).message);
        return true;
    }

    public final void onServerLogin(Object obj)
    {
        serverConnect((NetClientHandler)obj);
    }

    public final void onServerLogout()
    {
        serverDisconnect();
    }

    public final void onPlayerLogin(Object obj)
    {
        onClientLogin((EntityPlayer)obj);
    }

    public final void onPlayerLogout(Object obj)
    {
        onClientLogout((EntityPlayer)obj);
    }

    public final void onPlayerChangedDimension(Object obj)
    {
        onClientDimensionChanged((EntityPlayer)obj);
    }

    public final void onPacket250Packet(Object aobj[])
    {
        receiveCustomPacket((Packet250CustomPayload)aobj[0]);
    }

    public final void notifyPickup(Object aobj[])
    {
        EntityItem entityitem = (EntityItem)aobj[0];
        EntityPlayer entityplayer = (EntityPlayer)aobj[1];
        onItemPickup(entityplayer, entityitem.item);
    }

    public final void generate(Random random, int i, int j, Object aobj[])
    {
        World world = (World)aobj[0];
        IChunkProvider ichunkprovider = (IChunkProvider)aobj[1];

        if (ichunkprovider instanceof ChunkProviderGenerate)
        {
            generateSurface(world, random, i << 4, j << 4);
        }
        else if (ichunkprovider instanceof ChunkProviderHell)
        {
            generateNether(world, random, i << 4, j << 4);
        }
    }

    public final boolean handleCommand(String s, Object aobj[])
    {
        return false;
    }

    public int addFuel(int i, int j)
    {
        return 0;
    }

    public void addRenderer(Map map)
    {
    }

    public boolean dispenseEntity(World world, double d, double d1, double d2, int i, int j, ItemStack itemstack)
    {
        return false;
    }

    public void generateNether(World world, Random random, int i, int j)
    {
    }

    public void generateSurface(World world, Random random, int i, int j)
    {
    }

    public String getName()
    {
        return getClass().getSimpleName();
    }

    public String getPriorities()
    {
        return "";
    }

    public abstract String getVersion();

    public void keyboardEvent(KeyBinding keybinding)
    {
    }

    public abstract void load();

    public void modsLoaded()
    {
    }

    public void onItemPickup(EntityPlayer entityplayer, ItemStack itemstack)
    {
    }

    public boolean onTickInGame(float f, Minecraft minecraft)
    {
        return false;
    }

    public boolean onTickInGUI(float f, Minecraft minecraft, GuiScreen guiscreen)
    {
        return false;
    }

    public void receiveChatPacket(String s)
    {
    }

    public void receiveCustomPacket(Packet250CustomPayload packet250custompayload)
    {
    }

    public void registerAnimation(Minecraft minecraft)
    {
    }

    public void renderInvBlock(RenderBlocks renderblocks, Block block, int i, int j)
    {
    }

    public boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
    {
        return false;
    }

    public void serverConnect(NetClientHandler netclienthandler)
    {
    }

    public void serverDisconnect()
    {
    }

    public void takenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory)
    {
    }

    public void takenFromFurnace(EntityPlayer entityplayer, ItemStack itemstack)
    {
    }

    public String toString()
    {
        return (new StringBuilder()).append(getName()).append(" ").append(getVersion()).toString();
    }

    public void onPacket250Received(EntityPlayer entityplayer, Packet250CustomPayload packet250custompayload)
    {
    }

    public boolean onChatMessageReceived(EntityPlayer entityplayer, Packet3Chat packet3chat)
    {
        return false;
    }

    public boolean onServerCommand(String s, String s1, Object obj)
    {
        return false;
    }

    public void onClientLogin(EntityPlayer entityplayer)
    {
    }

    public void onClientLogout(EntityPlayer entityplayer)
    {
    }

    public void onClientDimensionChanged(EntityPlayer entityplayer)
    {
    }
}

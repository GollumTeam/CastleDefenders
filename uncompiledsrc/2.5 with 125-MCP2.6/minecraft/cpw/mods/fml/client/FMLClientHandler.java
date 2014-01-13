package cpw.mods.fml.client;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLModLoaderContainer;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.IDispenseHandler;
import cpw.mods.fml.common.IFMLSidedHandler;
import cpw.mods.fml.common.IKeyHandler;
import cpw.mods.fml.common.INetworkHandler;
import cpw.mods.fml.common.IPickupNotifier;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.ProxyInjector;
import cpw.mods.fml.common.ReflectionHelper;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.modloader.ModLoaderHelper;
import cpw.mods.fml.common.modloader.ModLoaderModContainer;
import cpw.mods.fml.common.modloader.ModProperty;
import cpw.mods.fml.common.registry.FMLRegistry;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.BaseMod;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.ClientRegistry;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.MLProp;
import net.minecraft.src.ModTextureStatic;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Profiler;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderManager;
import net.minecraft.src.RenderPlayer;
import net.minecraft.src.SidedProxy;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.TextureFX;
import net.minecraft.src.TexturePackBase;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.World;
import net.minecraft.src.WorldType;
import org.lwjgl.opengl.GL11;

public class FMLClientHandler implements IFMLSidedHandler
{
    private static final FMLClientHandler INSTANCE = new FMLClientHandler();
    private Minecraft client;
    private BiomeGenBase defaultOverworldBiomes[];
    private int nextRenderId;
    private TexturePackBase fallbackTexturePack;
    private NetClientHandler networkClient;
    private ModContainer animationCallbackMod;
    private HashMap overrideInfo;
    private HashMap blockModelIds;
    private HashMap keyBindings;
    private List keyHandlers;
    private HashSet animationSet;
    private List addedTextureFX;
    private boolean firstTick;
    private OptifineModContainer optifineContainer;
    private HashMap textureDims;
    private IdentityHashMap effectTextures;

    public FMLClientHandler()
    {
        nextRenderId = 30;
        overrideInfo = new HashMap();
        blockModelIds = new HashMap();
        keyBindings = new HashMap();
        keyHandlers = new ArrayList();
        animationSet = new HashSet();
        addedTextureFX = new ArrayList();
        textureDims = new HashMap();
        effectTextures = new IdentityHashMap();
    }

    public void onPreLoad(Minecraft minecraft)
    {
        client = minecraft;
        ReflectionHelper.detectObfuscation(net.minecraft.src.World.class);
        FMLCommonHandler.instance().beginLoading(this);
        FMLRegistry.registerRegistry(new ClientRegistry());

        try
        {
            Class class1 = Class.forName("Config", false, Loader.instance().getModClassLoader());
            optifineContainer = new OptifineModContainer(class1);
        }
        catch (Exception exception)
        {
            optifineContainer = null;
        }

        if (optifineContainer != null)
        {
            try
            {
                ModMetadata modmetadata = readMetadataFrom(Loader.instance().getModClassLoader().getResourceAsStream("optifinemod.info"), optifineContainer);
                optifineContainer.setMetadata(modmetadata);
            }
            catch (Exception exception1) { }

            FMLCommonHandler.instance().getFMLLogger().info(String.format("Forge Mod Loader has detected optifine %s, enabling compatibility features", new Object[]
                    {
                        optifineContainer.getVersion()
                    }));
        }

        Loader.instance().loadMods();
    }

    public void onLoadComplete()
    {
        Loader.instance().initializeMods();

        for (Iterator iterator = Loader.getModList().iterator(); iterator.hasNext();)
        {
            ModContainer modcontainer = (ModContainer)iterator.next();
            modcontainer.gatherRenderers(RenderManager.instance.getRendererList());
            Iterator iterator1 = RenderManager.instance.getRendererList().values().iterator();

            while (iterator1.hasNext())
            {
                Render render = (Render)iterator1.next();
                render.setRenderManager(RenderManager.instance);
            }
        }

        GameSettings gamesettings = client.gameSettings;
        KeyBinding akeybinding[] = harvestKeyBindings();
        KeyBinding akeybinding1[] = new KeyBinding[gamesettings.keyBindings.length + akeybinding.length];
        System.arraycopy(gamesettings.keyBindings, 0, akeybinding1, 0, gamesettings.keyBindings.length);
        System.arraycopy(akeybinding, 0, akeybinding1, gamesettings.keyBindings.length, akeybinding.length);
        gamesettings.keyBindings = akeybinding1;
        gamesettings.loadOptions();
        firstTick = true;
    }

    public KeyBinding[] harvestKeyBindings()
    {
        List list = FMLCommonHandler.instance().gatherKeyBindings();
        KeyBinding akeybinding[] = new KeyBinding[list.size()];
        int i = 0;
        IKeyHandler ikeyhandler;

        for (Iterator iterator = list.iterator(); iterator.hasNext(); keyBindings.put((KeyBinding)ikeyhandler.getKeyBinding(), ikeyhandler.getOwningContainer()))
        {
            ikeyhandler = (IKeyHandler)iterator.next();
            akeybinding[i++] = (KeyBinding)ikeyhandler.getKeyBinding();
        }

        keyHandlers = list;
        return akeybinding;
    }

    public void onPreWorldTick()
    {
        if (client.theWorld != null)
        {
            FMLCommonHandler.instance().tickStart(EnumSet.of(TickType.WORLD, TickType.GAME, TickType.WORLDGUI), new Object[]
                    {
                        Float.valueOf(0.0F), client.currentScreen, client.theWorld
                    });
        }
    }

    public void onPostWorldTick()
    {
        if (client.theWorld != null)
        {
            FMLCommonHandler.instance().tickEnd(EnumSet.of(TickType.WORLD, TickType.GAME, TickType.WORLDGUI), new Object[]
                    {
                        Float.valueOf(0.0F), client.currentScreen, client.theWorld
                    });
        }

        IKeyHandler ikeyhandler;

        for (Iterator iterator = keyHandlers.iterator(); iterator.hasNext(); ikeyhandler.onEndTick())
        {
            ikeyhandler = (IKeyHandler)iterator.next();
        }
    }

    public void onWorldLoadTick()
    {
        if (client.theWorld != null)
        {
            if (firstTick)
            {
                loadTextures(fallbackTexturePack);
                firstTick = false;
            }

            FMLCommonHandler.instance().tickStart(EnumSet.of(TickType.WORLDLOAD, TickType.GUILOAD), new Object[0]);
        }
    }

    public void onRenderTickStart(float f)
    {
        FMLCommonHandler.instance().tickStart(EnumSet.of(TickType.RENDER, TickType.GUI), new Object[]
                {
                    Float.valueOf(f), client.currentScreen
                });
    }

    public void onRenderTickEnd(float f)
    {
        FMLCommonHandler.instance().tickEnd(EnumSet.of(TickType.RENDER, TickType.GUI), new Object[]
                {
                    Float.valueOf(f), client.currentScreen
                });
    }

    public Minecraft getClient()
    {
        return client;
    }

    public Logger getMinecraftLogger()
    {
        return null;
    }

    public void onChunkPopulate(IChunkProvider ichunkprovider, int i, int j, World world, IChunkProvider ichunkprovider1)
    {
        FMLCommonHandler.instance().handleWorldGeneration(i, j, world.getSeed(), new Object[]
                {
                    world, ichunkprovider1, ichunkprovider
                });
    }

    public boolean isModLoaderMod(Class class1)
    {
        return (net.minecraft.src.BaseMod.class).isAssignableFrom(class1);
    }

    public ModContainer loadBaseModMod(Class class1, File file)
    {
        Class class2 = class1;
        return new ModLoaderModContainer(class2, file);
    }

    public void notifyItemPickup(EntityItem entityitem, EntityPlayer entityplayer)
    {
        Iterator iterator = Loader.getModList().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ModContainer modcontainer = (ModContainer)iterator.next();

            if (modcontainer.wantsPickupNotification())
            {
                modcontainer.getPickupNotifier().notifyPickup(new Object[]
                        {
                            entityitem, entityplayer
                        });
            }
        }
        while (true);
    }

    public boolean tryDispensingEntity(World world, double d, double d1, double d2, byte byte0, byte byte1, ItemStack itemstack)
    {
        for (Iterator iterator = Loader.getModList().iterator(); iterator.hasNext();)
        {
            ModContainer modcontainer = (ModContainer)iterator.next();

            if (modcontainer.wantsToDispense() && modcontainer.getDispenseHandler().dispense(d, d1, d2, byte0, byte1, new Object[]
                    {
                        world, itemstack
                    }))
            {
                return true;
            }
        }

        return false;
    }

    public static FMLClientHandler instance()
    {
        return INSTANCE;
    }

    public BiomeGenBase[] getDefaultOverworldBiomes()
    {
        if (defaultOverworldBiomes == null)
        {
            ArrayList arraylist = new ArrayList(20);

            for (int i = 0; i < 23; i++)
            {
                if (!"Sky".equals(BiomeGenBase.biomeList[i].biomeName) && !"Hell".equals(BiomeGenBase.biomeList[i].biomeName))
                {
                    arraylist.add(BiomeGenBase.biomeList[i]);
                }
            }

            defaultOverworldBiomes = new BiomeGenBase[arraylist.size()];
            arraylist.toArray(defaultOverworldBiomes);
        }

        return defaultOverworldBiomes;
    }

    public void onItemCrafted(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory)
    {
        Iterator iterator = Loader.getModList().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ModContainer modcontainer = (ModContainer)iterator.next();

            if (modcontainer.wantsCraftingNotification())
            {
                modcontainer.getCraftingHandler().onCrafting(new Object[]
                        {
                            entityplayer, itemstack, iinventory
                        });
            }
        }
        while (true);
    }

    public void onItemSmelted(EntityPlayer entityplayer, ItemStack itemstack)
    {
        Iterator iterator = Loader.getModList().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ModContainer modcontainer = (ModContainer)iterator.next();

            if (modcontainer.wantsCraftingNotification())
            {
                modcontainer.getCraftingHandler().onSmelting(new Object[]
                        {
                            entityplayer, itemstack
                        });
            }
        }
        while (true);
    }

    public boolean handleChatPacket(Packet3Chat packet3chat)
    {
        for (Iterator iterator = Loader.getModList().iterator(); iterator.hasNext();)
        {
            ModContainer modcontainer = (ModContainer)iterator.next();

            if (modcontainer.wantsNetworkPackets() && modcontainer.getNetworkHandler().onChat(new Object[]
                    {
                        packet3chat
                    }))
            {
                return true;
            }
        }

        return false;
    }

    public void handleServerLogin(Packet1Login packet1login, NetClientHandler netclienthandler, NetworkManager networkmanager)
    {
        networkClient = netclienthandler;
        Packet250CustomPayload packet250custompayload = new Packet250CustomPayload();
        packet250custompayload.channel = "REGISTER";
        packet250custompayload.data = FMLCommonHandler.instance().getPacketRegistry();
        packet250custompayload.length = packet250custompayload.data.length;

        if (packet250custompayload.length > 0)
        {
            networkmanager.addToSendQueue(packet250custompayload);
        }

        ModContainer modcontainer;

        for (Iterator iterator = Loader.getModList().iterator(); iterator.hasNext(); modcontainer.getNetworkHandler().onServerLogin(netclienthandler))
        {
            modcontainer = (ModContainer)iterator.next();
        }
    }

    public void handlePacket250(Packet250CustomPayload packet250custompayload)
    {
        if ("REGISTER".equals(packet250custompayload.channel) || "UNREGISTER".equals(packet250custompayload.channel))
        {
            handleServerRegistration(packet250custompayload);
            return;
        }

        ModContainer modcontainer = FMLCommonHandler.instance().getModForChannel(packet250custompayload.channel);

        if (modcontainer != null)
        {
            modcontainer.getNetworkHandler().onPacket250Packet(new Object[]
                    {
                        packet250custompayload
                    });
        }
    }

    private void handleServerRegistration(Packet250CustomPayload packet250custompayload)
    {
        if (packet250custompayload.data == null)
        {
            return;
        }

        try
        {
            String as[] = (new String(packet250custompayload.data, "UTF8")).split("\0");
            int i = as.length;

            for (int j = 0; j < i; j++)
            {
                String s = as[j];

                if (FMLCommonHandler.instance().getModForChannel(s) != null)
                {
                    if ("REGISTER".equals(packet250custompayload.channel))
                    {
                        FMLCommonHandler.instance().activateChannel(client.thePlayer, s);
                    }
                    else
                    {
                        FMLCommonHandler.instance().deactivateChannel(client.thePlayer, s);
                    }
                }
            }
        }
        catch (UnsupportedEncodingException unsupportedencodingexception)
        {
            getMinecraftLogger().warning("Received invalid registration packet");
        }
    }

    public File getMinecraftRootDirectory()
    {
        return client.mcDataDir;
    }

    public void announceLogout(EntityPlayer entityplayer)
    {
        Iterator iterator = Loader.getModList().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ModContainer modcontainer = (ModContainer)iterator.next();

            if (modcontainer.wantsPlayerTracking())
            {
                modcontainer.getPlayerTracker().onPlayerLogout(entityplayer);
            }
        }
        while (true);
    }

    public void announceDimensionChange(EntityPlayer entityplayer)
    {
        Iterator iterator = Loader.getModList().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ModContainer modcontainer = (ModContainer)iterator.next();

            if (modcontainer.wantsPlayerTracking())
            {
                modcontainer.getPlayerTracker().onPlayerChangedDimension(entityplayer);
            }
        }
        while (true);
    }

    public void addBiomeToDefaultWorldGenerator(BiomeGenBase biomegenbase)
    {
        WorldType.DEFAULT.addNewBiome(biomegenbase);
    }

    public Object getMinecraftInstance()
    {
        return client;
    }

    public String getCurrentLanguage()
    {
        return StringTranslate.getInstance().getCurrentLanguage();
    }

    public Properties getCurrentLanguageTable()
    {
        return StringTranslate.getInstance().getTranslationTable();
    }

    public int addNewArmourRendererPrefix(String s)
    {
        return RenderPlayer.addNewArmourPrefix(s);
    }

    public void addNewTextureOverride(String s, String s1, int i)
    {
        if (!overrideInfo.containsKey(s))
        {
            overrideInfo.put(s, new ArrayList());
        }

        ArrayList arraylist = (ArrayList)overrideInfo.get(s);
        OverrideInfo overrideinfo = new OverrideInfo();
        overrideinfo.index = i;
        overrideinfo.override = s1;
        overrideinfo.texture = s;
        arraylist.add(overrideinfo);
        FMLCommonHandler.instance().getFMLLogger().log(Level.FINE, String.format("Overriding %s @ %d with %s. %d slots remaining", new Object[]
                {
                    s, Integer.valueOf(i), s1, Integer.valueOf(SpriteHelper.freeSlotCount(s))
                }));
    }

    public int obtainBlockModelIdFor(BaseMod basemod, boolean flag)
    {
        ModLoaderModContainer modloadermodcontainer = ModLoaderHelper.registerRenderHelper(basemod);
        int i = nextRenderId++;
        BlockRenderInfo blockrenderinfo = new BlockRenderInfo(i, flag, modloadermodcontainer);
        blockModelIds.put(Integer.valueOf(i), blockrenderinfo);
        return i;
    }

    public java.awt.image.BufferedImage loadImageFromTexturePack(RenderEngine renderengine, String s) throws IOException
    {
        InputStream inputstream = client.texturePackList.selectedTexturePack.getResourceAsStream(s);

        if (inputstream == null)
        {
            throw new RuntimeException(String.format("The requested image path %s is not found", new Object[]
                    {
                        s
                    }));
        }

        java.awt.image.BufferedImage bufferedimage = ImageIO.read(inputstream);

        if (bufferedimage == null)
        {
            throw new RuntimeException(String.format("The requested image path %s appears to be corrupted", new Object[]
                    {
                        s
                    }));
        }
        else
        {
            return bufferedimage;
        }
    }

    public void displayGuiScreen(EntityPlayer entityplayer, GuiScreen guiscreen)
    {
        if (client.renderViewEntity == entityplayer && guiscreen != null)
        {
            client.displayGuiScreen(guiscreen);
        }
    }

    public void registerKeyHandler(BaseMod basemod, KeyBinding keybinding, boolean flag)
    {
        ModLoaderModContainer modloadermodcontainer = ModLoaderHelper.registerKeyHelper(basemod);
        modloadermodcontainer.addKeyHandler(new KeyBindingHandler(keybinding, flag, modloadermodcontainer));
    }

    public boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
    {
        if (!blockModelIds.containsKey(Integer.valueOf(l)))
        {
            return false;
        }
        else
        {
            BlockRenderInfo blockrenderinfo = (BlockRenderInfo)blockModelIds.get(Integer.valueOf(l));
            return blockrenderinfo.renderWorldBlock(iblockaccess, i, j, k, block, l, renderblocks);
        }
    }

    public void renderInventoryBlock(RenderBlocks renderblocks, Block block, int i, int j)
    {
        if (!blockModelIds.containsKey(Integer.valueOf(j)))
        {
            return;
        }
        else
        {
            BlockRenderInfo blockrenderinfo = (BlockRenderInfo)blockModelIds.get(Integer.valueOf(j));
            blockrenderinfo.renderInventoryBlock(block, i, j, renderblocks);
            return;
        }
    }

    public boolean renderItemAsFull3DBlock(int i)
    {
        BlockRenderInfo blockrenderinfo = (BlockRenderInfo)blockModelIds.get(Integer.valueOf(i));

        if (blockrenderinfo != null)
        {
            return blockrenderinfo.shouldRender3DInInventory();
        }
        else
        {
            return false;
        }
    }

    public void registerTextureOverrides(RenderEngine renderengine)
    {
        ModContainer modcontainer;

        for (Iterator iterator = Loader.getModList().iterator(); iterator.hasNext(); registerAnimatedTexturesFor(modcontainer))
        {
            modcontainer = (ModContainer)iterator.next();
        }

        OverrideInfo overrideinfo;

        for (Iterator iterator1 = animationSet.iterator(); iterator1.hasNext(); FMLCommonHandler.instance().getFMLLogger().finer(String.format("Registered texture override %d (%d) on %s (%d)", new Object[]
                {
                    Integer.valueOf(overrideinfo.index), Integer.valueOf(overrideinfo.textureFX.iconIndex), overrideinfo.textureFX.getClass().getSimpleName(), Integer.valueOf(overrideinfo.textureFX.tileImage)
                })))
        {
            overrideinfo = (OverrideInfo)iterator1.next();
            renderengine.registerTextureFX(overrideinfo.textureFX);
            addedTextureFX.add(overrideinfo.textureFX);
        }

        for (Iterator iterator2 = overrideInfo.keySet().iterator(); iterator2.hasNext();)
        {
            String s = (String)iterator2.next();
            Iterator iterator3 = ((ArrayList)overrideInfo.get(s)).iterator();

            while (iterator3.hasNext())
            {
                OverrideInfo overrideinfo1 = (OverrideInfo)iterator3.next();

                try
                {
                    java.awt.image.BufferedImage bufferedimage = loadImageFromTexturePack(renderengine, overrideinfo1.override);
                    ModTextureStatic modtexturestatic = new ModTextureStatic(overrideinfo1.index, 1, overrideinfo1.texture, bufferedimage);
                    renderengine.registerTextureFX(modtexturestatic);
                    addedTextureFX.add(modtexturestatic);
                    FMLCommonHandler.instance().getFMLLogger().finer(String.format("Registered texture override %d (%d) on %s (%d)", new Object[]
                            {
                                Integer.valueOf(overrideinfo1.index), Integer.valueOf(modtexturestatic.iconIndex), overrideinfo1.texture, Integer.valueOf(modtexturestatic.tileImage)
                            }));
                }
                catch (IOException ioexception)
                {
                    FMLCommonHandler.instance().getFMLLogger().throwing("FMLClientHandler", "registerTextureOverrides", ioexception);
                }
            }
        }
    }

    private void registerAnimatedTexturesFor(ModContainer modcontainer)
    {
        animationCallbackMod = modcontainer;
        modcontainer.requestAnimations();
        animationCallbackMod = null;
    }

    public String getObjectName(Object obj)
    {
        String s;

        if (obj instanceof Item)
        {
            s = ((Item)obj).getItemName();
        }
        else if (obj instanceof Block)
        {
            s = ((Block)obj).getBlockName();
        }
        else if (obj instanceof ItemStack)
        {
            s = Item.itemsList[((ItemStack)obj).itemID].getItemNameIS((ItemStack)obj);
        }
        else
        {
            throw new IllegalArgumentException(String.format("Illegal object for naming %s", new Object[]
                    {
                        obj
                    }));
        }

        s = (new StringBuilder()).append(s).append(".name").toString();
        return s;
    }

    public ModMetadata readMetadataFrom(InputStream inputstream, ModContainer modcontainer) throws Exception
    {
        argo.jdom.JsonRootNode jsonrootnode = (new JdomParser()).parse(new InputStreamReader(inputstream));
        List list = jsonrootnode.getArrayNode(new Object[0]);
        JsonNode jsonnode = null;
        Object obj = list.iterator();

        do
        {
            if (!((Iterator)(obj)).hasNext())
            {
                break;
            }

            JsonNode jsonnode1 = (JsonNode)((Iterator)(obj)).next();

            if (!modcontainer.getName().equals(jsonnode1.getStringValue(new Object[]
                    {
                        "modid"
                    })))
            {
                continue;
            }
            jsonnode = jsonnode1;
            break;
        }
        while (true);

        if (jsonnode == null)
        {
            FMLCommonHandler.instance().getFMLLogger().fine(String.format("Unable to process JSON modinfo file for %s", new Object[]
                    {
                        modcontainer.getName()
                    }));
            return null;
        }

        obj = new ModMetadata(modcontainer);

        try
        {
            obj.name = jsonnode.getStringValue(new Object[]
                    {
                        "name"
                    });
            obj.description = jsonnode.getStringValue(new Object[]
                    {
                        "description"
                    });
            obj.version = jsonnode.getStringValue(new Object[]
                    {
                        "version"
                    });
            obj.credits = jsonnode.getStringValue(new Object[]
                    {
                        "credits"
                    });
            List list1 = jsonnode.getArrayNode(new Object[]
                    {
                        "authors"
                    });
            StringBuilder stringbuilder = new StringBuilder();

            for (int i = 0; i < list1.size(); i++)
            {
                ((ModMetadata)(obj)).authorList.add(((JsonNode)list1.get(i)).getText());
            }

            obj.logoFile = jsonnode.getStringValue(new Object[]
                    {
                        "logoFile"
                    });
            obj.url = jsonnode.getStringValue(new Object[]
                    {
                        "url"
                    });
            obj.updateUrl = jsonnode.getStringValue(new Object[]
                    {
                        "updateUrl"
                    });
            obj.parent = jsonnode.getStringValue(new Object[]
                    {
                        "parent"
                    });
            List list2 = jsonnode.getArrayNode(new Object[]
                    {
                        "screenshots"
                    });
            obj.screenshots = new String[list2.size()];

            for (int j = 0; j < list2.size(); j++)
            {
                ((ModMetadata)(obj)).screenshots[j] = ((JsonNode)list2.get(j)).getText();
            }
        }
        catch (Exception exception)
        {
            FMLCommonHandler.instance().getFMLLogger().log(Level.FINE, String.format("An error occured reading the info file for %s", new Object[]
                    {
                        modcontainer.getName()
                    }), exception);
        }

        return ((ModMetadata)(obj));
    }

    public void pruneOldTextureFX(TexturePackBase texturepackbase, List list)
    {
        ListIterator listiterator = addedTextureFX.listIterator();

        do
        {
            if (!listiterator.hasNext())
            {
                break;
            }

            TextureFX texturefx = (TextureFX)listiterator.next();

            if (texturefx instanceof FMLTextureFX)
            {
                if (((FMLTextureFX)texturefx).unregister(client.renderEngine, list))
                {
                    listiterator.remove();
                }
            }
            else
            {
                list.remove(texturefx);
                listiterator.remove();
            }
        }
        while (true);
    }

    public void loadTextures(TexturePackBase texturepackbase)
    {
        registerTextureOverrides(client.renderEngine);
    }

    public void onEarlyTexturePackLoad(TexturePackBase texturepackbase)
    {
        if (client == null)
        {
            fallbackTexturePack = texturepackbase;
        }
        else
        {
            loadTextures(texturepackbase);
        }
    }

    public void sendPacket(Packet packet)
    {
        if (networkClient != null)
        {
            networkClient.addToSendQueue(packet);
        }
    }

    public void addAnimation(TextureFX texturefx)
    {
        if (animationCallbackMod == null)
        {
            return;
        }

        OverrideInfo overrideinfo = new OverrideInfo();
        overrideinfo.index = texturefx.iconIndex;
        overrideinfo.imageIndex = texturefx.tileImage;
        overrideinfo.textureFX = texturefx;

        if (animationSet.contains(overrideinfo))
        {
            animationSet.remove(overrideinfo);
        }

        animationSet.add(overrideinfo);
    }

    public void profileStart(String s)
    {
        Profiler.startSection(s);
    }

    public void profileEnd()
    {
        Profiler.endSection();
    }

    public void preGameLoad(String s, String s1)
    {
        Minecraft.fmlReentry(s, s1);
    }

    public void onTexturePackChange(RenderEngine renderengine, TexturePackBase texturepackbase, List list)
    {
        instance().pruneOldTextureFX(texturepackbase, list);
        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            TextureFX texturefx = (TextureFX)iterator.next();

            if (texturefx instanceof ITextureFX)
            {
                ((ITextureFX)texturefx).onTexturePackChanged(renderengine, texturepackbase, getTextureDimensions(texturefx));
            }
        }
        while (true);

        instance().loadTextures(texturepackbase);
    }

    public void setTextureDimensions(int i, int j, int k, List list)
    {
        java.awt.Dimension dimension = new java.awt.Dimension(j, k);
        textureDims.put(Integer.valueOf(i), dimension);
        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            TextureFX texturefx = (TextureFX)iterator.next();

            if (getEffectTexture(texturefx) == i && (texturefx instanceof ITextureFX))
            {
                ((ITextureFX)texturefx).onTextureDimensionsUpdate(j, k);
            }
        }
        while (true);
    }

    public java.awt.Dimension getTextureDimensions(TextureFX texturefx)
    {
        return getTextureDimensions(getEffectTexture(texturefx));
    }

    public java.awt.Dimension getTextureDimensions(int i)
    {
        return (java.awt.Dimension)textureDims.get(Integer.valueOf(i));
    }

    public int getEffectTexture(TextureFX texturefx)
    {
        Integer integer = (Integer)effectTextures.get(texturefx);

        if (integer != null)
        {
            return integer.intValue();
        }
        else
        {
            int i = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
            texturefx.bindImage(client.renderEngine);
            Integer integer1 = Integer.valueOf(GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D));
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, i);
            effectTextures.put(texturefx, integer1);
            return integer1.intValue();
        }
    }

    public boolean onUpdateTextureEffect(TextureFX texturefx)
    {
        Logger logger = FMLCommonHandler.instance().getFMLLogger();
        ITextureFX itexturefx = (texturefx instanceof ITextureFX) ? (ITextureFX)texturefx : null;

        if (itexturefx != null && itexturefx.getErrored())
        {
            return false;
        }

        String s = texturefx.getClass().getSimpleName();
        Profiler.startSection(s);

        try
        {
            if (optifineContainer == null)
            {
                texturefx.onTick();
            }
        }
        catch (Exception exception)
        {
            logger.warning(String.format("Texture FX %s has failed to animate. Likely caused by a texture pack change that they did not respond correctly to", new Object[]
                    {
                        s
                    }));

            if (itexturefx != null)
            {
                itexturefx.setErrored(true);
            }

            Profiler.endSection();
            return false;
        }

        Profiler.endSection();

        if (itexturefx != null)
        {
            java.awt.Dimension dimension = getTextureDimensions(texturefx);
            int i = (dimension.width >> 4) * (dimension.height >> 4) << 2;

            if (texturefx.imageData.length != i)
            {
                logger.warning(String.format("Detected a texture FX sizing discrepancy in %s (%d, %d)", new Object[]
                        {
                            s, Integer.valueOf(texturefx.imageData.length), Integer.valueOf(i)
                        }));
                itexturefx.setErrored(true);
                return false;
            }
        }

        return true;
    }

    public void scaleTextureFXData(byte abyte0[], ByteBuffer bytebuffer, int i, int j)
    {
        int k = (int)Math.sqrt(abyte0.length / 4);
        int l = i / k;
        byte abyte1[] = new byte[4];
        bytebuffer.clear();

        if (l > 1)
        {
            for (int i1 = 0; i1 < k; i1++)
            {
                int j1 = k * i1;
                int k1 = i * i1 * l;

                for (int l1 = 0; l1 < k; l1++)
                {
                    int i2 = (l1 + j1) * 4;
                    abyte1[0] = abyte0[i2 + 0];
                    abyte1[1] = abyte0[i2 + 1];
                    abyte1[2] = abyte0[i2 + 2];
                    abyte1[3] = abyte0[i2 + 3];
                    int j2 = l1 * l + k1;

                    for (int k2 = 0; k2 < l; k2++)
                    {
                        bytebuffer.position((j2 + k2 * i) * 4);

                        for (int l2 = 0; l2 < l; l2++)
                        {
                            bytebuffer.put(abyte1);
                        }
                    }
                }
            }
        }

        bytebuffer.position(0).limit(j);
    }

    public void onPreRegisterEffect(TextureFX texturefx)
    {
        java.awt.Dimension dimension = getTextureDimensions(texturefx);

        if (texturefx instanceof ITextureFX)
        {
            ((ITextureFX)texturefx).onTextureDimensionsUpdate(dimension.width, dimension.height);
        }
    }

    public ModProperty getModLoaderPropertyFor(Field field)
    {
        if (field.isAnnotationPresent(net.minecraft.src.MLProp.class))
        {
            MLProp mlprop = (MLProp)field.getAnnotation(net.minecraft.src.MLProp.class);
            return new ModProperty(mlprop.info(), mlprop.min(), mlprop.max(), mlprop.name());
        }
        else
        {
            return null;
        }
    }

    public void addSpecialModEntries(ArrayList arraylist)
    {
        arraylist.add(new FMLModLoaderContainer());

        if (optifineContainer != null)
        {
            arraylist.add(optifineContainer);
        }
    }

    public List getAdditionalBrandingInformation()
    {
        if (optifineContainer != null)
        {
            return Arrays.asList(new String[]
                    {
                        String.format("Optifine %s", new Object[] {
                                    optifineContainer.getVersion()
                                })
                    });
        }
        else
        {
            return Collections.emptyList();
        }
    }

    public Side getSide()
    {
        return Side.CLIENT;
    }

    public ProxyInjector findSidedProxyOn(cpw.mods.fml.common.modloader.BaseMod basemod)
    {
        Field afield[] = basemod.getClass().getDeclaredFields();
        int i = afield.length;

        for (int j = 0; j < i; j++)
        {
            Field field = afield[j];

            if (field.isAnnotationPresent(net.minecraft.src.SidedProxy.class))
            {
                SidedProxy sidedproxy = (SidedProxy)field.getAnnotation(net.minecraft.src.SidedProxy.class);
                return new ProxyInjector(sidedproxy.clientSide(), sidedproxy.serverSide(), sidedproxy.bukkitSide(), field);
            }
        }

        return null;
    }

    public void removeBiomeFromDefaultWorldGenerator(BiomeGenBase biomegenbase)
    {
        WorldType.DEFAULT.removeBiome(biomegenbase);
    }
}

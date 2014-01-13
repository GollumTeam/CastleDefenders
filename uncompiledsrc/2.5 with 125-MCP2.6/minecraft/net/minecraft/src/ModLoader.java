package net.minecraft.src;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.SpriteHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ReflectionHelper;
import cpw.mods.fml.common.modloader.ModLoaderHelper;
import cpw.mods.fml.common.modloader.ModLoaderModContainer;
import cpw.mods.fml.common.registry.FMLRegistry;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;

public class ModLoader
{
    public static final Map localizedStrings = Collections.emptyMap();

    public ModLoader()
    {
    }

    public static void addAchievementDesc(Achievement achievement, String s, String s1)
    {
        String s2 = achievement.getName();
        addLocalization(s2, s);
        addLocalization((new StringBuilder()).append(s2).append(".desc").toString(), s1);
    }

    public static int addAllFuel(int i, int j)
    {
        return 0;
    }

    public static void addAllRenderers(Map map)
    {
    }

    public static void addAnimation(TextureFX texturefx)
    {
        FMLClientHandler.instance().addAnimation(texturefx);
    }

    public static int addArmor(String s)
    {
        return FMLClientHandler.instance().addNewArmourRendererPrefix(s);
    }

    public static void addBiome(BiomeGenBase biomegenbase)
    {
        FMLClientHandler.instance().addBiomeToDefaultWorldGenerator(biomegenbase);
    }

    public static void addLocalization(String s, String s1)
    {
        addLocalization(s, "en_US", s1);
    }

    public static void addLocalization(String s, String s1, String s2)
    {
        FMLCommonHandler.instance().addStringLocalization(s, s1, s2);
    }

    public static void addName(Object obj, String s)
    {
        addName(obj, "en_US", s);
    }

    public static void addName(Object obj, String s, String s1)
    {
        FMLCommonHandler.instance().addNameForObject(obj, s, s1);
    }

    public static int addOverride(String s, String s1)
    {
        int i = SpriteHelper.getUniqueSpriteIndex(s);
        addOverride(s, s1, i);
        return i;
    }

    public static void addOverride(String s, String s1, int i)
    {
        FMLClientHandler.instance().addNewTextureOverride(s, s1, i);
    }

    public static void addRecipe(ItemStack itemstack, Object aobj[])
    {
        FMLRegistry.addRecipe(itemstack, aobj);
    }

    public static void addShapelessRecipe(ItemStack itemstack, Object aobj[])
    {
        FMLRegistry.addShapelessRecipe(itemstack, aobj);
    }

    public static void addSmelting(int i, ItemStack itemstack)
    {
        FMLRegistry.addSmelting(i, itemstack);
    }

    public static void addSpawn(Class class1, int i, int j, int k, EnumCreatureType enumcreaturetype)
    {
        FMLRegistry.addSpawn(class1, i, j, k, enumcreaturetype, FMLClientHandler.instance().getDefaultOverworldBiomes());
    }

    public static void addSpawn(Class class1, int i, int j, int k, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        FMLRegistry.addSpawn(class1, i, j, k, enumcreaturetype, abiomegenbase);
    }

    public static void addSpawn(String s, int i, int j, int k, EnumCreatureType enumcreaturetype)
    {
        FMLRegistry.addSpawn(s, i, j, k, enumcreaturetype, FMLClientHandler.instance().getDefaultOverworldBiomes());
    }

    public static void addSpawn(String s, int i, int j, int k, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        FMLRegistry.addSpawn(s, i, j, k, enumcreaturetype, abiomegenbase);
    }

    public static boolean dispenseEntity(World world, double d, double d1, double d2, int i, int j, ItemStack itemstack)
    {
        return false;
    }

    public static void genericContainerRemoval(World world, int i, int j, int k)
    {
        TileEntity tileentity = world.getBlockTileEntity(i, j, k);

        if (!(tileentity instanceof IInventory))
        {
            return;
        }

        IInventory iinventory = (IInventory)tileentity;

        for (int l = 0; l < iinventory.getSizeInventory(); l++)
        {
            ItemStack itemstack = iinventory.getStackInSlot(l);

            if (itemstack == null)
            {
                continue;
            }

            float f = world.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

            while (itemstack.stackSize > 0)
            {
                int i1 = world.rand.nextInt(21) + 10;

                if (i1 > itemstack.stackSize)
                {
                    i1 = itemstack.stackSize;
                }

                itemstack.stackSize -= i1;
                EntityItem entityitem = new EntityItem(world, (float)tileentity.xCoord + f, (float)tileentity.yCoord + f1, (float)tileentity.zCoord + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
                float f3 = 0.05F;
                entityitem.motionX = (float)world.rand.nextGaussian() * f3;
                entityitem.motionY = (float)world.rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float)world.rand.nextGaussian() * f3;

                if (itemstack.hasTagCompound())
                {
                    entityitem.item.setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                }

                world.spawnEntityInWorld(entityitem);
            }
        }
    }

    public static List getLoadedMods()
    {
        return ModLoaderModContainer.findAll(net.minecraft.src.BaseMod.class);
    }

    public static Logger getLogger()
    {
        return FMLCommonHandler.instance().getFMLLogger();
    }

    public static Minecraft getMinecraftInstance()
    {
        return FMLClientHandler.instance().getClient();
    }

    public static Object getMinecraftServerInstance()
    {
        return getMinecraftInstance();
    }

    public static Object getPrivateValue(Class class1, Object obj, int i)
    {
        return ReflectionHelper.getPrivateValue(class1, obj, i);
    }

    public static Object getPrivateValue(Class class1, Object obj, String s)
    {
        return ReflectionHelper.getPrivateValue(class1, obj, s);
    }

    public static int getUniqueBlockModelID(BaseMod basemod, boolean flag)
    {
        return FMLClientHandler.instance().obtainBlockModelIdFor(basemod, flag);
    }

    public static int getUniqueEntityId()
    {
        return FMLCommonHandler.instance().nextUniqueEntityListId();
    }

    public static int getUniqueSpriteIndex(String s)
    {
        return SpriteHelper.getUniqueSpriteIndex(s);
    }

    public static boolean isChannelActive(EntityPlayer entityplayer, String s)
    {
        return FMLCommonHandler.instance().isChannelActive(s, entityplayer);
    }

    public static boolean isGUIOpen(Class class1)
    {
        return FMLClientHandler.instance().getClient().currentScreen != null && FMLClientHandler.instance().getClient().currentScreen.equals(class1);
    }

    public static boolean isModLoaded(String s)
    {
        return Loader.isModLoaded(s);
    }

    public static void loadConfig()
    {
    }

    public static java.awt.image.BufferedImage loadImage(RenderEngine renderengine, String s) throws Exception
    {
        return FMLClientHandler.instance().loadImageFromTexturePack(renderengine, s);
    }

    public static void onItemPickup(EntityPlayer entityplayer, ItemStack itemstack)
    {
    }

    public static void onTick(float f, Minecraft minecraft)
    {
    }

    public static void openGUI(EntityPlayer entityplayer, GuiScreen guiscreen)
    {
        FMLClientHandler.instance().displayGuiScreen(entityplayer, guiscreen);
    }

    public static void populateChunk(IChunkProvider ichunkprovider, int i, int j, World world)
    {
    }

    public static void receivePacket(Packet250CustomPayload packet250custompayload)
    {
    }

    public static KeyBinding[] registerAllKeys(KeyBinding akeybinding[])
    {
        return akeybinding;
    }

    public static void registerAllTextureOverrides(RenderEngine renderengine)
    {
    }

    public static void registerBlock(Block block)
    {
        FMLRegistry.registerBlock(block);
    }

    public static void registerBlock(Block block, Class class1)
    {
        FMLRegistry.registerBlock(block, class1);
    }

    public static void registerEntityID(Class class1, String s, int i)
    {
        FMLRegistry.registerEntityID(class1, s, i);
    }

    public static void registerEntityID(Class class1, String s, int i, int j, int k)
    {
        FMLRegistry.registerEntityID(class1, s, i, j, k);
    }

    public static void registerKey(BaseMod basemod, KeyBinding keybinding, boolean flag)
    {
        FMLClientHandler.instance().registerKeyHandler(basemod, keybinding, flag);
    }

    public static void registerPacketChannel(BaseMod basemod, String s)
    {
        FMLCommonHandler.instance().registerChannel(ModLoaderModContainer.findContainerFor(basemod), s);
    }

    public static void registerTileEntity(Class class1, String s)
    {
        FMLRegistry.registerTileEntity(class1, s);
    }

    public static void registerTileEntity(Class class1, String s, TileEntitySpecialRenderer tileentityspecialrenderer)
    {
        ClientRegistry.instance().registerTileEntity(class1, s, tileentityspecialrenderer);
    }

    public static void removeBiome(BiomeGenBase biomegenbase)
    {
        FMLRegistry.removeBiome(biomegenbase);
    }

    public static void removeSpawn(Class class1, EnumCreatureType enumcreaturetype)
    {
        FMLRegistry.removeSpawn(class1, enumcreaturetype, FMLClientHandler.instance().getDefaultOverworldBiomes());
    }

    public static void removeSpawn(Class class1, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        FMLRegistry.removeSpawn(class1, enumcreaturetype, abiomegenbase);
    }

    public static void removeSpawn(String s, EnumCreatureType enumcreaturetype)
    {
        FMLRegistry.removeSpawn(s, enumcreaturetype, FMLClientHandler.instance().getDefaultOverworldBiomes());
    }

    public static void removeSpawn(String s, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        FMLRegistry.removeSpawn(s, enumcreaturetype, abiomegenbase);
    }

    public static boolean renderBlockIsItemFull3D(int i)
    {
        return FMLClientHandler.instance().renderItemAsFull3DBlock(i);
    }

    public static void renderInvBlock(RenderBlocks renderblocks, Block block, int i, int j)
    {
        FMLClientHandler.instance().renderInventoryBlock(renderblocks, block, i, j);
    }

    public static boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
    {
        return FMLClientHandler.instance().renderWorldBlock(renderblocks, iblockaccess, i, j, k, block, l);
    }

    public static void saveConfig()
    {
    }

    public static void sendPacket(Packet packet)
    {
        FMLClientHandler.instance().sendPacket(packet);
    }

    public static void serverChat(String s)
    {
    }

    public static void serverLogin(NetClientHandler netclienthandler, Packet1Login packet1login)
    {
    }

    public static void setInGameHook(BaseMod basemod, boolean flag, boolean flag1)
    {
        ModLoaderHelper.updateStandardTicks(basemod, flag, flag1);
    }

    public static void setInGUIHook(BaseMod basemod, boolean flag, boolean flag1)
    {
        ModLoaderHelper.updateGUITicks(basemod, flag, flag1);
    }

    public static void setPrivateValue(Class class1, Object obj, int i, Object obj1)
    {
        ReflectionHelper.setPrivateValue(class1, obj, i, obj1);
    }

    public static void setPrivateValue(Class class1, Object obj, String s, Object obj1)
    {
        ReflectionHelper.setPrivateValue(class1, obj, s, obj1);
    }

    public static void takenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory)
    {
    }

    public static void takenFromFurnace(EntityPlayer entityplayer, ItemStack itemstack)
    {
    }

    public static void throwException(String s, Throwable throwable)
    {
        FMLCommonHandler.instance().raiseException(throwable, s, true);
    }

    public static void throwException(Throwable throwable)
    {
        throwException("Exception in ModLoader", throwable);
    }
}

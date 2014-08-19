package forge;

import forge.oredict.OreDictionary;
import forge.packets.PacketHandlerBase;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.*;
import net.minecraft.src.*;

public class MinecraftForge
{
    public static class OreQuery
        implements Iterable
    {
        public class OreQueryIterator
            implements Iterator
        {
            LinkedList itering;
            LinkedList output;

            public boolean hasNext()
            {
                return output != null;
            }

            public Object[] next()
            {
                Object aobj[] = output.toArray();

                do
                {
                    if (itering.size() == 0)
                    {
                        output = null;
                        return aobj;
                    }

                    Object obj = itering.getLast();
                    output.removeLast();

                    if (obj instanceof Iterator)
                    {
                        Iterator iterator1 = (Iterator)obj;

                        if (iterator1.hasNext())
                        {
                            output.addLast(iterator1.next());
                            break;
                        }
                    }

                    itering.removeLast();
                }
                while (true);

                for (int i = itering.size(); i < proto.length; i++)
                {
                    if (proto[i] instanceof Collection)
                    {
                        Iterator iterator2 = ((Collection)proto[i]).iterator();

                        if (!iterator2.hasNext())
                        {
                            output = null;
                            break;
                        }

                        itering.addLast(iterator2);
                        output.addLast(iterator2.next());
                    }
                    else
                    {
                        itering.addLast(proto[i]);
                        output.addLast(proto[i]);
                    }
                }

                return aobj;
            }

            public void remove()
            {
            }

            private OreQueryIterator()
            {
                itering = new LinkedList();
                output = new LinkedList();
                Object aobj[] = proto;
                int i = aobj.length;

                for (int j = 0; j < i; j++)
                {
                    Object obj = aobj[j];

                    if (obj instanceof Collection)
                    {
                        Iterator iterator1 = ((Collection)obj).iterator();

                        if (!iterator1.hasNext())
                        {
                            output = null;
                            break;
                        }

                        itering.addLast(iterator1);
                        output.addLast(iterator1.next());
                    }
                    else
                    {
                        itering.addLast(obj);
                        output.addLast(obj);
                    }
                }
            }
        }

        Object proto[];

        public Iterator iterator()
        {
            return new OreQueryIterator();
        }

        private OreQuery(Object aobj[])
        {
            proto = aobj;
        }
    }

    private static LinkedList bucketHandlers = new LinkedList();
    private static int dungeonLootAttempts = 8;
    private static ArrayList dungeonMobs = new ArrayList();
    private static ArrayList dungeonLoot = new ArrayList();
    private static LinkedList achievementPages = new LinkedList();
    private static Map itemForMinecart = new HashMap();
    private static Map minecartForItem = new HashMap();
    private static int isClient = -1;

    public MinecraftForge()
    {
    }

    public static void registerCustomBucketHandler(IBucketHandler ibuckethandler)
    {
        bucketHandlers.add(ibuckethandler);
    }

    public static void registerSleepHandler(ISleepHandler isleephandler)
    {
        ForgeHooks.sleepHandlers.add(isleephandler);
    }

    public static void registerBonemealHandler(IBonemealHandler ibonemealhandler)
    {
        ForgeHooks.bonemealHandlers.add(ibonemealhandler);
    }

    public static void registerHoeHandler(IHoeHandler ihoehandler)
    {
        ForgeHooks.hoeHandlers.add(ihoehandler);
    }

    public static void registerDestroyToolHandler(IDestroyToolHandler idestroytoolhandler)
    {
        ForgeHooks.destroyToolHandlers.add(idestroytoolhandler);
    }

    public static void registerCraftingHandler(ICraftingHandler icraftinghandler)
    {
        ForgeHooks.craftingHandlers.add(icraftinghandler);
    }

    public static void registerMinecartHandler(IMinecartHandler iminecarthandler)
    {
        ForgeHooks.minecartHandlers.add(iminecarthandler);
    }

    public static void registerConnectionHandler(IConnectionHandler iconnectionhandler)
    {
        ForgeHooks.connectionHandlers.add(iconnectionhandler);
    }

    public static void registerChunkLoadHandler(IChunkLoadHandler ichunkloadhandler)
    {
        ForgeHooks.chunkLoadHandlers.add(ichunkloadhandler);
    }

    public static void registerPickupHandler(IPickupHandler ipickuphandler)
    {
        ForgeHooks.pickupHandlers.add(ipickuphandler);
    }

    public static void registerEntityInteractHandler(IEntityInteractHandler ientityinteracthandler)
    {
        ForgeHooks.entityInteractHandlers.add(ientityinteracthandler);
    }

    public static void registerChatHandler(IChatHandler ichathandler)
    {
        ForgeHooks.chatHandlers.add(ichathandler);
    }

    public static void registerSaveHandler(ISaveEventHandler isaveeventhandler)
    {
        ForgeHooks.saveHandlers.add(isaveeventhandler);
    }

    public static void registerFuelHandler(IFuelHandler ifuelhandler)
    {
        ForgeHooks.fuelHandlers.add(ifuelhandler);
    }

    public static void registerSpecialMobSpawnHandler(ISpecialMobSpawnHandler ispecialmobspawnhandler)
    {
        ForgeHooks.specialMobSpawnHandlers.add(ispecialmobspawnhandler);
    }

    public static ItemStack fillCustomBucket(World world, int i, int j, int k)
    {
        for (Iterator iterator = bucketHandlers.iterator(); iterator.hasNext();)
        {
            IBucketHandler ibuckethandler = (IBucketHandler)iterator.next();
            ItemStack itemstack = ibuckethandler.fillCustomBucket(world, i, j, k);

            if (itemstack != null)
            {
                return itemstack;
            }
        }

        return null;
    }

    public static void registerOreHandler(IOreHandler iorehandler)
    {
        OreDictionary.registerOreHandler(iorehandler);
    }

    public static void registerOre(String s, ItemStack itemstack)
    {
        OreDictionary.registerOre(s, itemstack);
    }

    public static List getOreClass(String s)
    {
        return OreDictionary.getOres(s);
    }

    public static OreQuery generateRecipes(Object aobj[])
    {
        return new OreQuery(aobj);
    }

    public static void addGrassPlant(int i, int j, int k)
    {
        ForgeHooks.addPlantGrass(i, j, k);
    }

    public static void addGrassSeed(int i, int j, int k, int l)
    {
        ForgeHooks.addGrassSeed(i, j, k, l);
    }

    public static void setToolClass(Item item, String s, int i)
    {
        ForgeHooks.initTools();
        ForgeHooks.toolClasses.put(Integer.valueOf(item.shiftedIndex), Arrays.asList(new Serializable[]
                {
                    s, Integer.valueOf(i)
                }));
    }

    public static void setBlockHarvestLevel(Block block, int i, String s, int j)
    {
        ForgeHooks.initTools();
        List list = Arrays.asList(new Serializable[]
                {
                    Integer.valueOf(block.blockID), Integer.valueOf(i), s
                });
        ForgeHooks.toolHarvestLevels.put(list, Integer.valueOf(j));
        ForgeHooks.toolEffectiveness.add(list);
    }

    public static void removeBlockEffectiveness(Block block, int i, String s)
    {
        ForgeHooks.initTools();
        List list = Arrays.asList(new Serializable[]
                {
                    Integer.valueOf(block.blockID), Integer.valueOf(i), s
                });
        ForgeHooks.toolEffectiveness.remove(list);
    }

    public static void setBlockHarvestLevel(Block block, String s, int i)
    {
        ForgeHooks.initTools();

        for (int j = 0; j < 16; j++)
        {
            List list = Arrays.asList(new Serializable[]
                    {
                        Integer.valueOf(block.blockID), Integer.valueOf(j), s
                    });
            ForgeHooks.toolHarvestLevels.put(list, Integer.valueOf(i));
            ForgeHooks.toolEffectiveness.add(list);
        }
    }

    public static int getBlockHarvestLevel(Block block, int i, String s)
    {
        ForgeHooks.initTools();
        List list = Arrays.asList(new Serializable[]
                {
                    Integer.valueOf(block.blockID), Integer.valueOf(i), s
                });
        Integer integer = (Integer)ForgeHooks.toolHarvestLevels.get(list);

        if (integer == null)
        {
            return -1;
        }
        else
        {
            return integer.intValue();
        }
    }

    public static void removeBlockEffectiveness(Block block, String s)
    {
        ForgeHooks.initTools();

        for (int i = 0; i < 16; i++)
        {
            List list = Arrays.asList(new Serializable[]
                    {
                        Integer.valueOf(block.blockID), Integer.valueOf(i), s
                    });
            ForgeHooks.toolEffectiveness.remove(list);
        }
    }

    public static void killMinecraft(String s, String s1)
    {
        throw new RuntimeException((new StringBuilder()).append(s).append(": ").append(s1).toString());
    }

    public static void versionDetect(String s, int i, int j, int k)
    {
        if (i != 3)
        {
            killMinecraft(s, (new StringBuilder()).append("MinecraftForge Major Version Mismatch, expecting ").append(i).append(".x.x").toString());
        }
        else if (j != 3)
        {
            if (j > 3)
            {
                killMinecraft(s, (new StringBuilder()).append("MinecraftForge Too Old, need at least ").append(i).append(".").append(j).append(".").append(k).toString());
            }
            else
            {
                System.out.println((new StringBuilder()).append(s).append(": MinecraftForge minor version mismatch, expecting ").append(i).append(".").append(j).append(".x, may lead to unexpected behavior").toString());
            }
        }
        else if (k > 7)
        {
            killMinecraft(s, (new StringBuilder()).append("MinecraftForge Too Old, need at least ").append(i).append(".").append(j).append(".").append(k).toString());
        }
    }

    public static void versionDetectStrict(String s, int i, int j, int k)
    {
        if (i != 3)
        {
            killMinecraft(s, (new StringBuilder()).append("MinecraftForge Major Version Mismatch, expecting ").append(i).append(".x.x").toString());
        }
        else if (j != 3)
        {
            if (j > 3)
            {
                killMinecraft(s, (new StringBuilder()).append("MinecraftForge Too Old, need at least ").append(i).append(".").append(j).append(".").append(k).toString());
            }
            else
            {
                killMinecraft(s, (new StringBuilder()).append("MinecraftForge minor version mismatch, expecting ").append(i).append(".").append(j).append(".x").toString());
            }
        }
        else if (k > 7)
        {
            killMinecraft(s, (new StringBuilder()).append("MinecraftForge Too Old, need at least ").append(i).append(".").append(j).append(".").append(k).toString());
        }
    }

    public static String getVersionString()
    {
        return String.format("Minecraft Forge %d.%d.%d.%d", new Object[]
                {
                    Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(7), Integer.valueOf(135)
                });
    }

    public static void setDungeonLootTries(int i)
    {
        dungeonLootAttempts = i;
    }

    public static int getDungeonLootTries()
    {
        return dungeonLootAttempts;
    }

    public static float addDungeonMob(String s, float f)
    {
        if (f <= 0.0F)
        {
            throw new IllegalArgumentException("Rarity must be greater then zero");
        }

        for (Iterator iterator = dungeonMobs.iterator(); iterator.hasNext();)
        {
            ObjectPair objectpair = (ObjectPair)iterator.next();

            if (s.equals(objectpair.getValue2()))
            {
                objectpair.setValue1(Float.valueOf(((Float)objectpair.getValue1()).floatValue() + f));
                return ((Float)objectpair.getValue1()).floatValue();
            }
        }

        dungeonMobs.add(new ObjectPair(Float.valueOf(f), s));
        return f;
    }

    public static float removeDungeonMob(String s)
    {
        for (Iterator iterator = dungeonMobs.iterator(); iterator.hasNext();)
        {
            ObjectPair objectpair = (ObjectPair)iterator.next();

            if (s.equals(s))
            {
                dungeonMobs.remove(objectpair);
                return ((Float)objectpair.getValue1()).floatValue();
            }
        }

        return 0.0F;
    }

    public static String getRandomDungeonMob(Random random)
    {
        float f = 0.0F;

        for (Iterator iterator = dungeonMobs.iterator(); iterator.hasNext();)
        {
            ObjectPair objectpair = (ObjectPair)iterator.next();
            f += ((Float)objectpair.getValue1()).floatValue();
        }

        float f1 = random.nextFloat() * f;

        for (Iterator iterator1 = dungeonMobs.iterator(); iterator1.hasNext();)
        {
            ObjectPair objectpair1 = (ObjectPair)iterator1.next();

            if (f1 < ((Float)objectpair1.getValue1()).floatValue())
            {
                return (String)objectpair1.getValue2();
            }

            f1 -= ((Float)objectpair1.getValue1()).floatValue();
        }

        return "";
    }

    public static void addDungeonLoot(ItemStack itemstack, float f)
    {
        addDungeonLoot(itemstack, f, 1, 1);
    }

    public static float addDungeonLoot(ItemStack itemstack, float f, int i, int j)
    {
        for (Iterator iterator = dungeonLoot.iterator(); iterator.hasNext();)
        {
            ObjectPair objectpair = (ObjectPair)iterator.next();

            if (((DungeonLoot)objectpair.getValue2()).equals(itemstack, i, j))
            {
                objectpair.setValue1(Float.valueOf(((Float)objectpair.getValue1()).floatValue() + f));
                return ((Float)objectpair.getValue1()).floatValue();
            }
        }

        dungeonLoot.add(new ObjectPair(Float.valueOf(f), new DungeonLoot(itemstack, i, j)));
        return f;
    }

    public static float removeDungeonLoot(ItemStack itemstack)
    {
        return removeDungeonLoot(itemstack, -1, 0);
    }

    public static float removeDungeonLoot(ItemStack itemstack, int i, int j)
    {
        float f = 0.0F;
        ArrayList arraylist = (ArrayList)dungeonLoot.clone();

        if (i < 0)
        {
            Iterator iterator = arraylist.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                ObjectPair objectpair = (ObjectPair)iterator.next();

                if (((DungeonLoot)objectpair.getValue2()).equals(itemstack))
                {
                    dungeonLoot.remove(objectpair);
                    f += ((Float)objectpair.getValue1()).floatValue();
                }
            }
            while (true);
        }
        else
        {
            Iterator iterator1 = arraylist.iterator();

            do
            {
                if (!iterator1.hasNext())
                {
                    break;
                }

                ObjectPair objectpair1 = (ObjectPair)iterator1.next();

                if (((DungeonLoot)objectpair1.getValue2()).equals(itemstack, i, j))
                {
                    dungeonLoot.remove(objectpair1);
                    f += ((Float)objectpair1.getValue1()).floatValue();
                }
            }
            while (true);
        }

        return f;
    }

    public static ItemStack getRandomDungeonLoot(Random random)
    {
        float f = 0.0F;

        for (Iterator iterator = dungeonLoot.iterator(); iterator.hasNext();)
        {
            ObjectPair objectpair = (ObjectPair)iterator.next();
            f += ((Float)objectpair.getValue1()).floatValue();
        }

        float f1 = random.nextFloat() * f;

        for (Iterator iterator1 = dungeonLoot.iterator(); iterator1.hasNext();)
        {
            ObjectPair objectpair1 = (ObjectPair)iterator1.next();

            if (f1 < ((Float)objectpair1.getValue1()).floatValue())
            {
                return ((DungeonLoot)objectpair1.getValue2()).generateStack(random);
            }

            f1 -= ((Float)objectpair1.getValue1()).floatValue();
        }

        return null;
    }

    public static void registerAchievementPage(AchievementPage achievementpage)
    {
        if (getAchievementPage(achievementpage.getName()) != null)
        {
            throw new RuntimeException((new StringBuilder()).append("Duplicate achievement page name \"").append(achievementpage.getName()).append("\"!").toString());
        }
        else
        {
            achievementPages.add(achievementpage);
            return;
        }
    }

    public static AchievementPage getAchievementPage(int i)
    {
        return (AchievementPage)achievementPages.get(i);
    }

    public static AchievementPage getAchievementPage(String s)
    {
        for (Iterator iterator = achievementPages.iterator(); iterator.hasNext();)
        {
            AchievementPage achievementpage = (AchievementPage)iterator.next();

            if (achievementpage.getName().equals(s))
            {
                return achievementpage;
            }
        }

        return null;
    }

    public static Set getAchievementPages()
    {
        return new HashSet(achievementPages);
    }

    public static boolean isAchievementInPages(Achievement achievement)
    {
        for (Iterator iterator = achievementPages.iterator(); iterator.hasNext();)
        {
            AchievementPage achievementpage = (AchievementPage)iterator.next();

            if (achievementpage.getAchievements().contains(achievement))
            {
                return true;
            }
        }

        return false;
    }

    public static void registerMinecart(Class class1, ItemStack itemstack)
    {
        registerMinecart(class1, 0, itemstack);
    }

    public static void registerMinecart(Class class1, int i, ItemStack itemstack)
    {
        MinecartKey minecartkey = new MinecartKey(class1, i);
        itemForMinecart.put(minecartkey, itemstack);
        minecartForItem.put(itemstack, minecartkey);
    }

    public static void removeMinecart(Class class1, int i)
    {
        MinecartKey minecartkey = new MinecartKey(class1, i);
        ItemStack itemstack = (ItemStack)itemForMinecart.remove(minecartkey);

        if (itemstack != null)
        {
            minecartForItem.remove(itemstack);
        }
    }

    public static ItemStack getItemForCart(Class class1)
    {
        return getItemForCart(class1, 0);
    }

    public static ItemStack getItemForCart(Class class1, int i)
    {
        ItemStack itemstack = (ItemStack)itemForMinecart.get(new MinecartKey(class1, i));

        if (itemstack == null)
        {
            return null;
        }
        else
        {
            return itemstack.copy();
        }
    }

    public static ItemStack getItemForCart(EntityMinecart entityminecart)
    {
        return getItemForCart(entityminecart.getClass(), entityminecart.getMinecartType());
    }

    public static Class getCartClassForItem(ItemStack itemstack)
    {
        MinecartKey minecartkey = null;
        Iterator iterator = minecartForItem.entrySet().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

            if (!((ItemStack)entry.getKey()).isItemEqual(itemstack))
            {
                continue;
            }

            minecartkey = (MinecartKey)entry.getValue();
            break;
        }
        while (true);

        if (minecartkey != null)
        {
            return minecartkey.minecart;
        }
        else
        {
            return null;
        }
    }

    public static int getCartTypeForItem(ItemStack itemstack)
    {
        MinecartKey minecartkey = null;
        Iterator iterator = minecartForItem.entrySet().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

            if (!((ItemStack)entry.getKey()).isItemEqual(itemstack))
            {
                continue;
            }

            minecartkey = (MinecartKey)entry.getValue();
            break;
        }
        while (true);

        if (minecartkey != null)
        {
            return minecartkey.type;
        }
        else
        {
            return -1;
        }
    }

    public static Set getAllCartItems()
    {
        HashSet hashset = new HashSet();
        ItemStack itemstack;

        for (Iterator iterator = minecartForItem.keySet().iterator(); iterator.hasNext(); hashset.add(itemstack.copy()))
        {
            itemstack = (ItemStack)iterator.next();
        }

        return hashset;
    }

    public static boolean registerEntity(Class class1, NetworkMod networkmod, int i, int j, int k, boolean flag)
    {
        if (ForgeHooks.entityTrackerMap.containsKey(class1))
        {
            return false;
        }
        else
        {
            ForgeHooks.entityTrackerMap.put(class1, new EntityTrackerInfo(networkmod, i, j, k, flag));
            return true;
        }
    }

    public static EntityTrackerInfo getEntityTrackerInfo(Entity entity, boolean flag)
    {
        for (Iterator iterator = ForgeHooks.entityTrackerMap.entrySet().iterator(); iterator.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

            if (((Class)entry.getKey()).isInstance(entity) && (!flag || entry.getKey() == entity.getClass()))
            {
                return (EntityTrackerInfo)entry.getValue();
            }
        }

        return null;
    }

    public static Class getEntityClass(int i, int j)
    {
        for (Iterator iterator = ForgeHooks.entityTrackerMap.entrySet().iterator(); iterator.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            EntityTrackerInfo entitytrackerinfo = (EntityTrackerInfo)entry.getValue();

            if (j == entitytrackerinfo.ID && i == getModID(entitytrackerinfo.Mod))
            {
                return (Class)entry.getKey();
            }
        }

        return null;
    }

    public static NetworkMod getModByID(int i)
    {
        return (NetworkMod)ForgeHooks.networkMods.get(Integer.valueOf(i));
    }

    public static int getModID(NetworkMod networkmod)
    {
        for (Iterator iterator = ForgeHooks.networkMods.entrySet().iterator(); iterator.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

            if (entry.getValue() == networkmod)
            {
                return ((Integer)entry.getKey()).intValue();
            }
        }

        return -1;
    }

    public static NetworkMod[] getNetworkMods()
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = ModLoader.getLoadedMods().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            BaseMod basemod = (BaseMod)iterator.next();

            if (basemod instanceof NetworkMod)
            {
                arraylist.add((NetworkMod)basemod);
            }
        }
        while (true);

        return (NetworkMod[])arraylist.toArray(new NetworkMod[0]);
    }

    public static void setGuiHandler(BaseMod basemod, IGuiHandler iguihandler)
    {
        ForgeHooks.guiHandlers.put(basemod, iguihandler);
    }

    public static IGuiHandler getGuiHandler(BaseMod basemod)
    {
        return (IGuiHandler)ForgeHooks.guiHandlers.get(basemod);
    }

    public static void registerArrowNockHandler(IArrowNockHandler iarrownockhandler)
    {
        ForgeHooks.arrowNockHandlers.add(iarrownockhandler);
    }

    public static void registerArrowLooseHandler(IArrowLooseHandler iarrowloosehandler)
    {
        ForgeHooks.arrowLooseHandlers.add(iarrowloosehandler);
    }

    public static void sendPacket(NetworkManager networkmanager, Packet packet)
    {
        ForgeHooks.getPacketHandler().sendPacket(networkmanager, packet);
    }

    public static void sendPacket(NetworkManager networkmanager, NetworkMod networkmod, short word0, byte abyte0[])
    {
        if (abyte0 == null)
        {
            abyte0 = new byte[0];
        }

        if (abyte0.length > 255)
        {
            throw new IllegalArgumentException(String.format("Data argument was to long, must not be longer then 255 bytes was %d", new Object[]
                    {
                        Integer.valueOf(abyte0.length)
                    }));
        }
        else
        {
            Packet131MapData packet131mapdata = new Packet131MapData();
            packet131mapdata.itemID = (short)getModID(networkmod);
            packet131mapdata.uniqueID = word0;
            packet131mapdata.itemData = abyte0;
            sendPacket(networkmanager, ((Packet)(packet131mapdata)));
            return;
        }
    }

    public static void sendTileEntityPacket(NetworkManager networkmanager, int i, short word0, int j, byte byte0, int k, int l, int i1)
    {
        Packet132TileEntityData packet132tileentitydata = new Packet132TileEntityData();
        packet132tileentitydata.xPosition = i;
        packet132tileentitydata.yPosition = word0;
        packet132tileentitydata.zPosition = j;
        packet132tileentitydata.actionType = byte0;
        packet132tileentitydata.customParam1 = k;
        packet132tileentitydata.customParam2 = l;
        packet132tileentitydata.customParam3 = i1;
        sendPacket(networkmanager, packet132tileentitydata);
    }

    public static boolean isClient()
    {
        if (isClient == -1)
        {
            try
            {
                Class.forName("net.minecraft.client.Minecraft", false, (forge.MinecraftForge.class).getClassLoader());
                isClient = 1;
            }
            catch (ClassNotFoundException classnotfoundexception)
            {
                isClient = 0;
            }
        }

        return isClient == 1;
    }

    static
    {
        addDungeonMob("Skeleton", 1.0F);
        addDungeonMob("Zombie", 2.0F);
        addDungeonMob("Spider", 1.0F);
        addDungeonLoot(new ItemStack(Item.saddle), 1.0F);
        addDungeonLoot(new ItemStack(Item.ingotIron), 1.0F, 1, 4);
        addDungeonLoot(new ItemStack(Item.bread), 1.0F);
        addDungeonLoot(new ItemStack(Item.wheat), 1.0F, 1, 4);
        addDungeonLoot(new ItemStack(Item.gunpowder), 1.0F, 1, 4);
        addDungeonLoot(new ItemStack(Item.silk), 1.0F, 1, 4);
        addDungeonLoot(new ItemStack(Item.bucketEmpty), 1.0F);
        addDungeonLoot(new ItemStack(Item.appleGold), 0.01F);
        addDungeonLoot(new ItemStack(Item.redstone), 0.5F, 1, 4);
        addDungeonLoot(new ItemStack(Item.record13), 0.05F);
        addDungeonLoot(new ItemStack(Item.recordCat), 0.05F);
        addDungeonLoot(new ItemStack(Item.dyePowder, 1, 3), 1.0F);
        registerMinecart(net.minecraft.src.EntityMinecart.class, 0, new ItemStack(Item.minecartEmpty));
        registerMinecart(net.minecraft.src.EntityMinecart.class, 1, new ItemStack(Item.minecartCrate));
        registerMinecart(net.minecraft.src.EntityMinecart.class, 2, new ItemStack(Item.minecartPowered));
    }
}

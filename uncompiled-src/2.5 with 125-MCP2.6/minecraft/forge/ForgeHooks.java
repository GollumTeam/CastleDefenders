package forge;

import forge.packets.PacketEntitySpawn;
import forge.packets.PacketHandlerBase;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.src.*;

public class ForgeHooks
{
    static class ProbableItem
    {
        int WeightStart;
        int WeightEnd;
        int ItemID;
        int Metadata;
        int Quantity;

        public ProbableItem(int i, int j, int k, int l, int i1)
        {
            WeightStart = l;
            WeightEnd = i1;
            ItemID = i;
            Metadata = j;
            Quantity = k;
        }
    }

    static LinkedList craftingHandlers = new LinkedList();
    static LinkedList destroyToolHandlers = new LinkedList();
    static LinkedList bonemealHandlers = new LinkedList();
    static LinkedList hoeHandlers = new LinkedList();
    static LinkedList sleepHandlers = new LinkedList();
    static LinkedList minecartHandlers = new LinkedList();
    static LinkedList connectionHandlers = new LinkedList();
    static LinkedList pickupHandlers = new LinkedList();
    static LinkedList chunkLoadHandlers = new LinkedList();
    static LinkedList entityInteractHandlers = new LinkedList();
    static LinkedList chatHandlers = new LinkedList();
    static LinkedList saveHandlers = new LinkedList();
    static LinkedList fuelHandlers = new LinkedList();
    static LinkedList specialMobSpawnHandlers = new LinkedList();
    static List plantGrassList;
    static int plantGrassWeight = 30;
    static List seedGrassList;
    static int seedGrassWeight = 10;
    public static HashMap entityTrackerMap = new HashMap();
    public static Hashtable networkMods = new Hashtable();
    public static Hashtable guiHandlers = new Hashtable();
    public static ArrayList arrowLooseHandlers = new ArrayList();
    public static ArrayList arrowNockHandlers = new ArrayList();
    public static final int majorVersion = 3;
    public static final int minorVersion = 3;
    public static final int revisionVersion = 7;
    public static final int buildVersion = 135;
    static boolean toolInit = false;
    static HashMap toolClasses = new HashMap();
    static HashMap toolHarvestLevels = new HashMap();
    static HashSet toolEffectiveness = new HashSet();
    private static PacketHandlerBase forgePacketHandler = null;

    public ForgeHooks()
    {
    }

    public static void onTakenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory)
    {
        ICraftingHandler icraftinghandler;

        for (Iterator iterator = craftingHandlers.iterator(); iterator.hasNext(); icraftinghandler.onTakenFromCrafting(entityplayer, itemstack, iinventory))
        {
            icraftinghandler = (ICraftingHandler)iterator.next();
        }
    }

    public static void onDestroyCurrentItem(EntityPlayer entityplayer, ItemStack itemstack)
    {
        IDestroyToolHandler idestroytoolhandler;

        for (Iterator iterator = destroyToolHandlers.iterator(); iterator.hasNext(); idestroytoolhandler.onDestroyCurrentItem(entityplayer, itemstack))
        {
            idestroytoolhandler = (IDestroyToolHandler)iterator.next();
        }
    }

    public static boolean onUseBonemeal(World world, int i, int j, int k, int l)
    {
        for (Iterator iterator = bonemealHandlers.iterator(); iterator.hasNext();)
        {
            IBonemealHandler ibonemealhandler = (IBonemealHandler)iterator.next();

            if (ibonemealhandler.onUseBonemeal(world, i, j, k, l))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean onUseHoe(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k)
    {
        for (Iterator iterator = hoeHandlers.iterator(); iterator.hasNext();)
        {
            IHoeHandler ihoehandler = (IHoeHandler)iterator.next();

            if (ihoehandler.onUseHoe(itemstack, entityplayer, world, i, j, k))
            {
                return true;
            }
        }

        return false;
    }

    public static EnumStatus sleepInBedAt(EntityPlayer entityplayer, int i, int j, int k)
    {
        for (Iterator iterator = sleepHandlers.iterator(); iterator.hasNext();)
        {
            ISleepHandler isleephandler = (ISleepHandler)iterator.next();
            EnumStatus enumstatus = isleephandler.sleepInBedAt(entityplayer, i, j, k);

            if (enumstatus != null)
            {
                return enumstatus;
            }
        }

        return null;
    }

    public static void onMinecartUpdate(EntityMinecart entityminecart, int i, int j, int k)
    {
        IMinecartHandler iminecarthandler;

        for (Iterator iterator = minecartHandlers.iterator(); iterator.hasNext(); iminecarthandler.onMinecartUpdate(entityminecart, i, j, k))
        {
            iminecarthandler = (IMinecartHandler)iterator.next();
        }
    }

    public static void onMinecartEntityCollision(EntityMinecart entityminecart, Entity entity)
    {
        IMinecartHandler iminecarthandler;

        for (Iterator iterator = minecartHandlers.iterator(); iterator.hasNext(); iminecarthandler.onMinecartEntityCollision(entityminecart, entity))
        {
            iminecarthandler = (IMinecartHandler)iterator.next();
        }
    }

    public static boolean onMinecartInteract(EntityMinecart entityminecart, EntityPlayer entityplayer)
    {
        boolean flag = true;

        for (Iterator iterator = minecartHandlers.iterator(); iterator.hasNext();)
        {
            IMinecartHandler iminecarthandler = (IMinecartHandler)iterator.next();
            boolean flag1 = iminecarthandler.onMinecartInteract(entityminecart, entityplayer, flag);
            flag = flag && flag1;
        }

        return flag;
    }

    public static void onConnect(NetworkManager networkmanager)
    {
        IConnectionHandler iconnectionhandler;

        for (Iterator iterator = connectionHandlers.iterator(); iterator.hasNext(); iconnectionhandler.onConnect(networkmanager))
        {
            iconnectionhandler = (IConnectionHandler)iterator.next();
        }
    }

    public static void onLogin(NetworkManager networkmanager, Packet1Login packet1login)
    {
        IConnectionHandler iconnectionhandler;

        for (Iterator iterator = connectionHandlers.iterator(); iterator.hasNext(); iconnectionhandler.onLogin(networkmanager, packet1login))
        {
            iconnectionhandler = (IConnectionHandler)iterator.next();
        }
    }

    public static void onDisconnect(NetworkManager networkmanager, String s, Object aobj[])
    {
        IConnectionHandler iconnectionhandler;

        for (Iterator iterator = connectionHandlers.iterator(); iterator.hasNext(); iconnectionhandler.onDisconnect(networkmanager, s, aobj))
        {
            iconnectionhandler = (IConnectionHandler)iterator.next();
        }
    }

    public static boolean onItemPickup(EntityPlayer entityplayer, EntityItem entityitem)
    {
        boolean flag = true;

        for (Iterator iterator = pickupHandlers.iterator(); iterator.hasNext();)
        {
            IPickupHandler ipickuphandler = (IPickupHandler)iterator.next();
            flag = flag && ipickuphandler.onItemPickup(entityplayer, entityitem);

            if (!flag || entityitem.item.stackSize <= 0)
            {
                return false;
            }
        }

        return flag;
    }

    public static void addActiveChunks(World world, Set set)
    {
        IChunkLoadHandler ichunkloadhandler;

        for (Iterator iterator = chunkLoadHandlers.iterator(); iterator.hasNext(); ichunkloadhandler.addActiveChunks(world, set))
        {
            ichunkloadhandler = (IChunkLoadHandler)iterator.next();
        }
    }

    public static boolean canUnloadChunk(Chunk chunk)
    {
        for (Iterator iterator = chunkLoadHandlers.iterator(); iterator.hasNext();)
        {
            IChunkLoadHandler ichunkloadhandler = (IChunkLoadHandler)iterator.next();

            if (!ichunkloadhandler.canUnloadChunk(chunk))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean canUpdateEntity(Entity entity)
    {
        for (Iterator iterator = chunkLoadHandlers.iterator(); iterator.hasNext();)
        {
            IChunkLoadHandler ichunkloadhandler = (IChunkLoadHandler)iterator.next();

            if (ichunkloadhandler.canUpdateEntity(entity))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean onEntityInteract(EntityPlayer entityplayer, Entity entity, boolean flag)
    {
        for (Iterator iterator = entityInteractHandlers.iterator(); iterator.hasNext();)
        {
            IEntityInteractHandler ientityinteracthandler = (IEntityInteractHandler)iterator.next();

            if (!ientityinteracthandler.onEntityInteract(entityplayer, entity, flag))
            {
                return false;
            }
        }

        return true;
    }

    public static String onServerChat(EntityPlayer entityplayer, String s)
    {
        for (Iterator iterator = chatHandlers.iterator(); iterator.hasNext();)
        {
            IChatHandler ichathandler = (IChatHandler)iterator.next();
            s = ichathandler.onServerChat(entityplayer, s);

            if (s == null)
            {
                return null;
            }
        }

        return s;
    }

    public static boolean onChatCommand(EntityPlayer entityplayer, boolean flag, String s)
    {
        for (Iterator iterator = chatHandlers.iterator(); iterator.hasNext();)
        {
            IChatHandler ichathandler = (IChatHandler)iterator.next();

            if (ichathandler.onChatCommand(entityplayer, flag, s))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean onServerCommand(Object obj, String s, String s1)
    {
        for (Iterator iterator = chatHandlers.iterator(); iterator.hasNext();)
        {
            IChatHandler ichathandler = (IChatHandler)iterator.next();

            if (ichathandler.onServerCommand(obj, s, s1))
            {
                return true;
            }
        }

        return false;
    }

    public static String onServerCommandSay(Object obj, String s, String s1)
    {
        for (Iterator iterator = chatHandlers.iterator(); iterator.hasNext();)
        {
            IChatHandler ichathandler = (IChatHandler)iterator.next();
            s1 = ichathandler.onServerCommandSay(obj, s, s1);

            if (s1 == null)
            {
                return null;
            }
        }

        return s1;
    }

    public static String onClientChatRecv(String s)
    {
        for (Iterator iterator = chatHandlers.iterator(); iterator.hasNext();)
        {
            IChatHandler ichathandler = (IChatHandler)iterator.next();
            s = ichathandler.onClientChatRecv(s);

            if (s == null)
            {
                return null;
            }
        }

        return s;
    }

    public static void onWorldLoad(World world)
    {
        ISaveEventHandler isaveeventhandler;

        for (Iterator iterator = saveHandlers.iterator(); iterator.hasNext(); isaveeventhandler.onWorldLoad(world))
        {
            isaveeventhandler = (ISaveEventHandler)iterator.next();
        }
    }

    public static void onWorldSave(World world)
    {
        ISaveEventHandler isaveeventhandler;

        for (Iterator iterator = saveHandlers.iterator(); iterator.hasNext(); isaveeventhandler.onWorldSave(world))
        {
            isaveeventhandler = (ISaveEventHandler)iterator.next();
        }
    }

    public static void onChunkLoad(World world, Chunk chunk)
    {
        ISaveEventHandler isaveeventhandler;

        for (Iterator iterator = saveHandlers.iterator(); iterator.hasNext(); isaveeventhandler.onChunkLoad(world, chunk))
        {
            isaveeventhandler = (ISaveEventHandler)iterator.next();
        }
    }

    public static void onChunkUnload(World world, Chunk chunk)
    {
        ISaveEventHandler isaveeventhandler;

        for (Iterator iterator = saveHandlers.iterator(); iterator.hasNext(); isaveeventhandler.onChunkUnload(world, chunk))
        {
            isaveeventhandler = (ISaveEventHandler)iterator.next();
        }
    }

    public static void onChunkLoadData(World world, Chunk chunk, NBTTagCompound nbttagcompound)
    {
        ISaveEventHandler isaveeventhandler;

        for (Iterator iterator = saveHandlers.iterator(); iterator.hasNext(); isaveeventhandler.onChunkLoadData(world, chunk, nbttagcompound))
        {
            isaveeventhandler = (ISaveEventHandler)iterator.next();
        }
    }

    public static void onChunkSaveData(World world, Chunk chunk, NBTTagCompound nbttagcompound)
    {
        ISaveEventHandler isaveeventhandler;

        for (Iterator iterator = saveHandlers.iterator(); iterator.hasNext(); isaveeventhandler.onChunkSaveData(world, chunk, nbttagcompound))
        {
            isaveeventhandler = (ISaveEventHandler)iterator.next();
        }
    }

    public static int getItemBurnTime(ItemStack itemstack)
    {
        for (Iterator iterator = fuelHandlers.iterator(); iterator.hasNext();)
        {
            IFuelHandler ifuelhandler = (IFuelHandler)iterator.next();
            int i = ifuelhandler.getItemBurnTime(itemstack);

            if (i > 0)
            {
                return i;
            }
        }

        return 0;
    }

    public static boolean onEntitySpawnSpecial(EntityLiving entityliving, World world, float f, float f1, float f2)
    {
        for (Iterator iterator = specialMobSpawnHandlers.iterator(); iterator.hasNext();)
        {
            ISpecialMobSpawnHandler ispecialmobspawnhandler = (ISpecialMobSpawnHandler)iterator.next();

            if (ispecialmobspawnhandler.onSpecialEntitySpawn(entityliving, world, f, f1, f2))
            {
                return true;
            }
        }

        return false;
    }

    static ProbableItem getRandomItem(List list, int i)
    {
        int j = Collections.binarySearch(list, Integer.valueOf(i), new Comparator()
        {
            public int compare(Object obj, Object obj1)
            {
                ProbableItem probableitem = (ProbableItem)obj;
                Integer integer = (Integer)obj1;

                if (integer.intValue() < probableitem.WeightStart)
                {
                    return 1;
                }

                return integer.intValue() < probableitem.WeightEnd ? 0 : -1;
            }
        }
                                        );

        if (j < 0)
        {
            return null;
        }
        else
        {
            return (ProbableItem)list.get(j);
        }
    }

    public static void plantGrassPlant(World world, int i, int j, int k)
    {
        int l = world.rand.nextInt(plantGrassWeight);
        ProbableItem probableitem = getRandomItem(plantGrassList, l);

        if (probableitem == null)
        {
            return;
        }
        else
        {
            world.setBlockAndMetadataWithNotify(i, j, k, probableitem.ItemID, probableitem.Metadata);
            return;
        }
    }

    public static void addPlantGrass(int i, int j, int k)
    {
        plantGrassList.add(new ProbableItem(i, j, 1, plantGrassWeight, plantGrassWeight + k));
        plantGrassWeight += k;
    }

    public static ItemStack getGrassSeed(World world)
    {
        int i = world.rand.nextInt(seedGrassWeight);
        ProbableItem probableitem = getRandomItem(seedGrassList, i);

        if (probableitem == null)
        {
            return null;
        }
        else
        {
            return new ItemStack(probableitem.ItemID, probableitem.Quantity, probableitem.Metadata);
        }
    }

    public static void addGrassSeed(int i, int j, int k, int l)
    {
        seedGrassList.add(new ProbableItem(i, j, k, seedGrassWeight, seedGrassWeight + l));
        seedGrassWeight += l;
    }

    public static boolean canHarvestBlock(Block block, EntityPlayer entityplayer, int i)
    {
        if (block.blockMaterial.isHarvestable())
        {
            return true;
        }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if (itemstack == null)
        {
            return entityplayer.canHarvestBlock(block);
        }

        List list = (List)toolClasses.get(Integer.valueOf(itemstack.itemID));

        if (list == null)
        {
            return entityplayer.canHarvestBlock(block);
        }

        Object aobj[] = list.toArray();
        String s = (String)aobj[0];
        int j = ((Integer)aobj[1]).intValue();
        Integer integer = (Integer)toolHarvestLevels.get(Arrays.asList(new Serializable[]
                {
                    Integer.valueOf(block.blockID), Integer.valueOf(i), s
                }));

        if (integer == null)
        {
            return entityplayer.canHarvestBlock(block);
        }

        return integer.intValue() <= j;
    }

    public static float blockStrength(Block block, EntityPlayer entityplayer, int i)
    {
        float f = block.getHardness(i);

        if (f < 0.0F)
        {
            return 0.0F;
        }

        if (!canHarvestBlock(block, entityplayer, i))
        {
            return 1.0F / f / 100F;
        }
        else
        {
            return entityplayer.getCurrentPlayerStrVsBlock(block, i) / f / 30F;
        }
    }

    public static boolean isToolEffective(ItemStack itemstack, Block block, int i)
    {
        List list = (List)toolClasses.get(Integer.valueOf(itemstack.itemID));

        if (list == null)
        {
            return false;
        }
        else
        {
            return toolEffectiveness.contains(Arrays.asList(new Serializable[]
                    {
                        Integer.valueOf(block.blockID), Integer.valueOf(i), (String)list.get(0)
                    }));
        }
    }

    static void initTools()
    {
        if (toolInit)
        {
            return;
        }

        toolInit = true;
        MinecraftForge.setToolClass(Item.pickaxeWood, "pickaxe", 0);
        MinecraftForge.setToolClass(Item.pickaxeStone, "pickaxe", 1);
        MinecraftForge.setToolClass(Item.pickaxeSteel, "pickaxe", 2);
        MinecraftForge.setToolClass(Item.pickaxeGold, "pickaxe", 0);
        MinecraftForge.setToolClass(Item.pickaxeDiamond, "pickaxe", 3);
        MinecraftForge.setToolClass(Item.axeWood, "axe", 0);
        MinecraftForge.setToolClass(Item.axeStone, "axe", 1);
        MinecraftForge.setToolClass(Item.axeSteel, "axe", 2);
        MinecraftForge.setToolClass(Item.axeGold, "axe", 0);
        MinecraftForge.setToolClass(Item.axeDiamond, "axe", 3);
        MinecraftForge.setToolClass(Item.shovelWood, "shovel", 0);
        MinecraftForge.setToolClass(Item.shovelStone, "shovel", 1);
        MinecraftForge.setToolClass(Item.shovelSteel, "shovel", 2);
        MinecraftForge.setToolClass(Item.shovelGold, "shovel", 0);
        MinecraftForge.setToolClass(Item.shovelDiamond, "shovel", 3);
        MinecraftForge.setBlockHarvestLevel(Block.obsidian, "pickaxe", 3);
        MinecraftForge.setBlockHarvestLevel(Block.oreDiamond, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(Block.blockDiamond, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(Block.oreGold, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(Block.blockGold, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(Block.oreIron, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(Block.blockSteel, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(Block.oreLapis, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(Block.blockLapis, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(Block.oreRedstone, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(Block.oreRedstoneGlowing, "pickaxe", 2);
        MinecraftForge.removeBlockEffectiveness(Block.oreRedstone, "pickaxe");
        MinecraftForge.removeBlockEffectiveness(Block.obsidian, "pickaxe");
        MinecraftForge.removeBlockEffectiveness(Block.oreRedstoneGlowing, "pickaxe");
        Block ablock[] =
        {
            Block.cobblestone, Block.stairDouble, Block.stairSingle, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreCoal, Block.ice, Block.netherrack, Block.oreLapis,
            Block.blockLapis
        };
        Block ablock1[] = ablock;
        int i = ablock1.length;

        for (int j = 0; j < i; j++)
        {
            Block block = ablock1[j];
            MinecraftForge.setBlockHarvestLevel(block, "pickaxe", 0);
        }

        ablock1 = (new Block[]
                {
                    Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay, Block.tilledField, Block.slowSand, Block.mycelium
                });
        Block ablock2[] = ablock1;
        int k = ablock2.length;

        for (int l = 0; l < k; l++)
        {
            Block block1 = ablock2[l];
            MinecraftForge.setBlockHarvestLevel(block1, "shovel", 0);
        }

        ablock2 = (new Block[]
                {
                    Block.planks, Block.bookShelf, Block.wood, Block.chest, Block.stairDouble, Block.stairSingle, Block.pumpkin, Block.pumpkinLantern
                });
        Block ablock3[] = ablock2;
        int i1 = ablock3.length;

        for (int j1 = 0; j1 < i1; j1++)
        {
            Block block2 = ablock3[j1];
            MinecraftForge.setBlockHarvestLevel(block2, "axe", 0);
        }
    }

    public static Packet getEntitySpawnPacket(Entity entity)
    {
        EntityTrackerInfo entitytrackerinfo = MinecraftForge.getEntityTrackerInfo(entity, false);

        if (entitytrackerinfo == null)
        {
            return null;
        }
        else
        {
            PacketEntitySpawn packetentityspawn = new PacketEntitySpawn(entity, entitytrackerinfo.Mod, entitytrackerinfo.ID);
            return packetentityspawn.getPacket();
        }
    }

    public static boolean onArrowLoose(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
    {
        for (Iterator iterator = arrowLooseHandlers.iterator(); iterator.hasNext();)
        {
            IArrowLooseHandler iarrowloosehandler = (IArrowLooseHandler)iterator.next();

            if (iarrowloosehandler.onArrowLoose(itemstack, world, entityplayer, i))
            {
                return true;
            }
        }

        return false;
    }

    public static ItemStack onArrowNock(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        for (Iterator iterator = arrowNockHandlers.iterator(); iterator.hasNext();)
        {
            IArrowNockHandler iarrownockhandler = (IArrowNockHandler)iterator.next();
            ItemStack itemstack1 = iarrownockhandler.onArrowNock(itemstack, world, entityplayer);

            if (itemstack1 != null)
            {
                return itemstack1;
            }
        }

        return null;
    }

    public static int getMajorVersion()
    {
        return 3;
    }

    public static int getMinorVersion()
    {
        return 3;
    }

    public static int getRevisionVersion()
    {
        return 7;
    }

    public static int getBuildVersion()
    {
        return 135;
    }

    public static void setPacketHandler(PacketHandlerBase packethandlerbase)
    {
        if (forgePacketHandler != null)
        {
            throw new RuntimeException("Attempted to set Forge's Internal packet handler after it was already set");
        }
        else
        {
            forgePacketHandler = packethandlerbase;
            return;
        }
    }

    public static PacketHandlerBase getPacketHandler()
    {
        return forgePacketHandler;
    }

    public static boolean onItemDataPacket(NetworkManager networkmanager, Packet131MapData packet131mapdata)
    {
        NetworkMod networkmod = MinecraftForge.getModByID(packet131mapdata.itemID);

        if (networkmod == null)
        {
            ModLoader.getLogger().log(Level.WARNING, String.format("Received Unknown MapData packet %d:%d", new Object[]
                    {
                        Short.valueOf(packet131mapdata.itemID), Short.valueOf(packet131mapdata.uniqueID)
                    }));
            return false;
        }
        else
        {
            networkmod.onPacketData(networkmanager, packet131mapdata.uniqueID, packet131mapdata.itemData);
            return true;
        }
    }

    static
    {
        plantGrassList = new ArrayList();
        plantGrassList.add(new ProbableItem(Block.plantYellow.blockID, 0, 1, 0, 20));
        plantGrassList.add(new ProbableItem(Block.plantRed.blockID, 0, 1, 20, 30));
        seedGrassList = new ArrayList();
        seedGrassList.add(new ProbableItem(Item.seeds.shiftedIndex, 0, 1, 0, 10));
        System.out.printf("MinecraftForge v%d.%d.%d.%d Initialized\n", new Object[]
                {
                    Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(7), Integer.valueOf(135)
                });
        ModLoader.getLogger().info(String.format("MinecraftForge v%d.%d.%d.%d Initialized", new Object[]
                {
                    Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(7), Integer.valueOf(135)
                }));
    }
}

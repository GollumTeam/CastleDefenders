package mods.CastleDef;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod$Init;
import cpw.mods.fml.common.Mod$PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import mods.CastleDef.common.CommonProxyCastleDef;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;

@Mod(
    modid = "CastleDef",
    name = "Castle Defenders",
    version = "1.0.0"
)
@NetworkMod(
    clientSideRequired = true,
    serverSideRequired = false
)
public class mod_castledef
{
    public static Block BlockKnight;
    public static Block BlockArcher;
    public static Block BlockMerc;
    public static Block BlockEKnight;
    public static Block BlockEArcher;
    public static Block BlockMage;
    public static Block BlockEMage;
    public static Block BlockArcherM;
    public static Item ItemMedallion;
    public static int defenderID;
    public static int knightID;
    public static int archerID;
    public static int mercID;
    public static int EknightID;
    public static int EarcherID;
    public static int mageID = -17;
    public static int EmageID = -18;
    public static int archerMID;
    public static int BlockKnightID;
    public static int BlockArcherID;
    public static int BlockMercID;
    public static int BlockEKnightID;
    public static int BlockEArcherID;
    public static int BlockMageID;
    public static int BlockEMageID;
    public static int BlockArcherMID;
    public static int MedallionID;
    public static int CastleSpawnRaste;
    public static int MercSpawnRate;
    @SidedProxy(
        clientSide = "mods.CastleDef.client.ClientProxyCastleDef",
        serverSide = "mods.CastleDef.common.CommonProxyCastleDef"
    )
    public static CommonProxyCastleDef proxy;
    static int startEntityId = 300;

    @Mod$PreInit
    public void PreLoad(FMLPreInitializationEvent var1)
    {
        Configuration var2 = new Configuration(var1.getSuggestedConfigurationFile());
        var2.load();
        BlockKnightID = var2.get("BlockKnightID", "block", 238).getInt();
        BlockArcherID = var2.get("BlockArcherID", "block", 239).getInt();
        BlockMercID = var2.get("BlockMercID", "block", 234).getInt();
        BlockEKnightID = var2.get("BlockEKnightID", "block", 237).getInt();
        BlockEArcherID = var2.get("BlockEArcherID", "block", 236).getInt();
        BlockMageID = var2.get("BlockMageID", "block", 235).getInt();
        BlockEMageID = var2.get("BlockEMageID", "block", 233).getInt();
        BlockArcherMID = var2.get("BlockArcherMID", "block", 232).getInt();
        MedallionID = var2.get("MedallionID", "item", 3001).getInt();
        defenderID = var2.get("defenderID", "general", -32).getInt();
        knightID = var2.get("knightID", "general", -31).getInt();
        archerID = var2.get("archerID", "general", -30).getInt();
        mercID = var2.get("mercID", "general", -29).getInt();
        EknightID = var2.get("EknightID", "general", -28).getInt();
        EarcherID = var2.get("EarcherID", "general", -27).getInt();
        EmageID = var2.get("EmageID", "general", -26).getInt();
        mageID = var2.get("mageID", "general", -13).getInt();
        archerMID = var2.get("archerMID", "general", -25).getInt();
        CastleSpawnRaste = var2.get("CastleSpawnRaste", "general", 7).getInt();
        MercSpawnRate = var2.get("MercSpawnRate", "general", 7).getInt();
        var2.save();
    }

    @Mod$Init
    public void load(FMLInitializationEvent var1)
    {
        proxy.registerRenderThings();
        ItemMedallion = (new ItemMedallion(MedallionID)).setUnlocalizedName("Medallion");
        LanguageRegistry.addName(ItemMedallion, "Medallion");
        BlockKnight = (new BlockKnight(BlockKnightID)).setUnlocalizedName("BlockKnight").setHardness(2.0F).setResistance(5.0F);
        GameRegistry.registerBlock(BlockKnight);
        LanguageRegistry.addName(BlockKnight, "Knight Spawner");
        GameRegistry.registerTileEntity(TileEntityBlockKnight.class, "Knight Block");
        BlockArcher = (new BlockArcher(BlockArcherID)).setUnlocalizedName("BlockArcher").setHardness(2.0F).setResistance(5.0F);
        GameRegistry.registerBlock(BlockArcher);
        LanguageRegistry.addName(BlockArcher, "Archer Spawner");
        GameRegistry.registerTileEntity(TileEntityBlockArcher.class, "BlockArcher");
        BlockMerc = (new BlockMerc(BlockMercID)).setUnlocalizedName("BlockMerc").setHardness(2.0F).setResistance(5.0F);
        GameRegistry.registerBlock(BlockMerc);
        LanguageRegistry.addName(BlockMerc, "Merc Spawner");
        GameRegistry.registerTileEntity(TileEntityBlockMerc.class, "Merc Block");
        BlockEKnight = (new BlockEKnight(BlockEKnightID)).setUnlocalizedName("BlockEKnight").setHardness(2.0F).setResistance(5.0F);
        GameRegistry.registerBlock(BlockEKnight);
        LanguageRegistry.addName(BlockEKnight, "Enemy Knight Spawner");
        GameRegistry.registerTileEntity(TileEntityBlockEKnight.class, "Enemy Knight Block");
        BlockEArcher = (new BlockEArcher(BlockEArcherID)).setUnlocalizedName("BlockEArcher").setHardness(2.0F).setResistance(5.0F);
        GameRegistry.registerBlock(BlockEArcher);
        LanguageRegistry.addName(BlockEArcher, "Enemy Archer Spawner");
        GameRegistry.registerTileEntity(TileEntityBlockEArcher.class, "Enemy Archer Block");
        BlockMage = (new BlockMage(BlockMageID)).setUnlocalizedName("BlockMage").setHardness(2.0F).setResistance(5.0F);
        GameRegistry.registerBlock(BlockMage);
        LanguageRegistry.addName(BlockMage, "Mage Spawner");
        GameRegistry.registerTileEntity(TileEntityBlockEArcher.class, "Mage Block");
        BlockEMage = (new BlockEMage(BlockEMageID)).setUnlocalizedName("BlockEMage").setHardness(2.0F).setResistance(5.0F);
        GameRegistry.registerBlock(BlockEMage);
        LanguageRegistry.addName(BlockEMage, "Enemy Mage Spawner");
        GameRegistry.registerTileEntity(TileEntityBlockEArcher.class, "Enemy Mage Block");
        BlockArcherM = (new BlockArcherM(BlockArcherMID)).setUnlocalizedName("BlockArcherM").setHardness(2.0F).setResistance(5.0F);
        GameRegistry.registerBlock(BlockArcherM);
        LanguageRegistry.addName(BlockArcherM, "Merc Archer Spawner");
        GameRegistry.registerTileEntity(TileEntityBlockArcherM.class, "BlockArcherM");
        GameRegistry.addRecipe(new ItemStack(BlockKnight, 1), new Object[] {" X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.swordIron});
        GameRegistry.addRecipe(new ItemStack(BlockArcher, 1), new Object[] {" X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.bow});
        GameRegistry.addRecipe(new ItemStack(BlockMage, 1), new Object[] {"   ", " X ", " Y ", 'X', ItemMedallion, 'Y', BlockEMage});
        EntityRegistry.registerGlobalEntityID(EntityDefender.class, "Defender", defenderID);
        EntityRegistry.registerGlobalEntityID(EntityKnight.class, "Knight", knightID);
        EntityRegistry.addSpawn(EntityKnight.class, 10, 0, 0, EnumCreatureType.creature, new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.hell, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.sky, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills});
        LanguageRegistry.instance().addStringLocalization("entity.Castle Defenders.Knight.name", "Knight");
        registerEntityEgg(EntityKnight.class, 16777215, 0);
        EntityRegistry.registerGlobalEntityID(EntityArcher.class, "Archer", archerID);
        EntityRegistry.addSpawn(EntityArcher.class, 10, 0, 0, EnumCreatureType.creature, new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.hell, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.sky, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills});
        LanguageRegistry.instance().addStringLocalization("entity.Castle Defenders.Archer.name", "Archer");
        registerEntityEgg(EntityArcher.class, 16777215, 5242880);
        EntityRegistry.registerGlobalEntityID(EntityMerc.class, "Merc", mercID);
        EntityRegistry.addSpawn(EntityMerc.class, 10, 0, 0, EnumCreatureType.creature, new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.hell, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.sky, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills});
        registerEntityEgg(EntityMerc.class, 16777215, 6304768);
        EntityRegistry.registerGlobalEntityID(EntityMage.class, "Mage", mageID);
        registerEntityEgg(EntityMage.class, 16777215, 13408);
        EntityRegistry.registerGlobalEntityID(EntityEMage.class, "Enemy Mage", EmageID);
        registerEntityEgg(EntityEMage.class, 16777215, 9450496);
        EntityRegistry.registerGlobalEntityID(EntityEKnight.class, "Enemy Knight", EknightID);
        EntityRegistry.addSpawn(EntityEKnight.class, 10, 0, 0, EnumCreatureType.creature, new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.hell, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.sky, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills});
        registerEntityEgg(EntityEKnight.class, 16777215, 36960);
        EntityRegistry.registerGlobalEntityID(EntityEArcher.class, "Enemy Archer", EarcherID);
        EntityRegistry.addSpawn(EntityEArcher.class, 10, 0, 0, EnumCreatureType.creature, new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.hell, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.sky, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills});
        registerEntityEgg(EntityEArcher.class, 16777215, 7704672);
        EntityRegistry.registerGlobalEntityID(EntityArcherM.class, "Merc Archer", archerMID);
        EntityRegistry.addSpawn(EntityArcherM.class, 10, 0, 0, EnumCreatureType.creature, new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.hell, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.sky, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills});
        registerEntityEgg(EntityArcherM.class, 16777215, 7706976);
        GameRegistry.registerWorldGenerator(new WorldGeneratorMercbase());
        GameRegistry.registerWorldGenerator(new WorldGeneratorCastle());
    }

    public static int getUniqueEntityId()
    {
        do
        {
            ++startEntityId;
        }
        while (EntityList.getStringFromID(startEntityId) != null);

        return startEntityId;
    }

    public static void registerEntityEgg(Class var0, int var1, int var2)
    {
        int var3 = getUniqueEntityId();
        EntityList.IDtoClassMapping.put(Integer.valueOf(var3), var0);
        EntityList.entityEggs.put(Integer.valueOf(var3), new EntityEggInfo(var3, var1, var2));
    }
}

package mods.castledefenders.common.config;

import mods.castledefenders.ModCastleDefenders;
import mods.gollum.core.config.Config;
import mods.gollum.core.config.ConfigProp;
import mods.gollum.core.config.container.ItemStackConfig;
import mods.gollum.core.config.container.MobCapacitiesConfig;
import net.minecraft.item.Item;

public class ConfigCastleDefender extends Config {
	
	// Config d'affichage
	@ConfigProp(group = "Display") public static boolean displayMercenaryMessage = true;
	@ConfigProp(group = "Display") public static boolean displayMercenaryLife = true;
	@ConfigProp(group = "Display") public static double mercenaryLifeTop = 80.D;
	@ConfigProp(group = "Display") public static double mercenaryLifeHeight = 3.D;
	@ConfigProp(group = "Display") public static double mercenaryLifeWidth = 40.D;
	
	// Liste des IDs
	@ConfigProp(group = "Blocks Ids") public static int blockKnightID     = 1238;
	@ConfigProp(group = "Blocks Ids") public static int blockKnight2ID    = 1240;
	@ConfigProp(group = "Blocks Ids") public static int blockArcherID     = 1239;
	@ConfigProp(group = "Blocks Ids") public static int blockArcher2ID    = 1241;
	@ConfigProp(group = "Blocks Ids") public static int blockMercID       = 1234;
	@ConfigProp(group = "Blocks Ids") public static int blockMercArcherID = 1232;
	@ConfigProp(group = "Blocks Ids") public static int blockMageID       = 1235;
	@ConfigProp(group = "Blocks Ids") public static int blockHealerID     = 1242;
	@ConfigProp(group = "Blocks Ids") public static int blockEKnightID    = 1237;
	@ConfigProp(group = "Blocks Ids") public static int blockEArcherID    = 1236;
	@ConfigProp(group = "Blocks Ids") public static int blockEMageID      = 1233;
	
	@ConfigProp(group = "Items Ids") public static int medallionID    = 13001;
	
	@ConfigProp(group = "Spawn count") public static int maxSpawnKnight     = 6;
	@ConfigProp(group = "Spawn count") public static int maxSpawnKnight2    = 6;
	@ConfigProp(group = "Spawn count") public static int maxSpawnEKnight    = 6;
	@ConfigProp(group = "Spawn count") public static int maxSpawnArcher     = 6;
	@ConfigProp(group = "Spawn count") public static int maxSpawnArcher2    = 6;
	@ConfigProp(group = "Spawn count") public static int maxSpawnEArcher    = 6;
	@ConfigProp(group = "Spawn count") public static int maxSpawnMage       = 6;
	@ConfigProp(group = "Spawn count") public static int maxSpawnEMage      = 6;
	@ConfigProp(group = "Spawn count") public static int maxSpawnMerc       = 6;
	@ConfigProp(group = "Spawn count") public static int maxSpawnMercArcher = 6;
	@ConfigProp(group = "Spawn count") public static int maxSpawnHealer     = 6;
	
	@ConfigProp(group = "Entity capacities") public static MobCapacitiesConfig knightCapacities     = new MobCapacitiesConfig(0.55D, 20.D, 4.D , 20.D);
	@ConfigProp(group = "Entity capacities") public static MobCapacitiesConfig knight2Capacities    = new MobCapacitiesConfig(0.6D , 30.D, 8.D , 25.D);
	@ConfigProp(group = "Entity capacities") public static MobCapacitiesConfig eKnightCapacities    = new MobCapacitiesConfig(0.55D, 25.D, 6.D , 16.D);
	@ConfigProp(group = "Entity capacities") public static MobCapacitiesConfig archerCapacities     = new MobCapacitiesConfig(0.1D , 15.D, 4.D , 25.D, 30.D);
	@ConfigProp(group = "Entity capacities") public static MobCapacitiesConfig archer2Capacities    = new MobCapacitiesConfig(0.1D , 25.D, 7.D , 30.D, 15.D);
	@ConfigProp(group = "Entity capacities") public static MobCapacitiesConfig eArcherCapacities    = new MobCapacitiesConfig(0.1D , 20.D, 6.D , 18.D, 20.D);
	@ConfigProp(group = "Entity capacities") public static MobCapacitiesConfig mageCapacities       = new MobCapacitiesConfig(0.1D , 25.D, 5.D , 10.D, 40.D);
	@ConfigProp(group = "Entity capacities") public static MobCapacitiesConfig eMageCapacities      = new MobCapacitiesConfig(0.1D , 30.D, 5.D , 10.D, 40.D);
	@ConfigProp(group = "Entity capacities") public static MobCapacitiesConfig mercCapacities       = new MobCapacitiesConfig(0.6D , 20.D, 5.D , 20.D);
	@ConfigProp(group = "Entity capacities") public static MobCapacitiesConfig mercArcherCapacities = new MobCapacitiesConfig(0.6D , 20.D, 5.D , 25.D, 17.D);
	@ConfigProp(group = "Entity capacities") public static MobCapacitiesConfig healerCapacities     = new MobCapacitiesConfig(0.6D , 15.D, 0.3D, 5.D , 100.D);
	
	// Config des mercenaire
	@ConfigProp(group = "Mercenary", info="[ItemID,metadata,number],...") 
	public static ItemStackConfig[] mercenaryCost  = {new ItemStackConfig(Item.ingotGold.itemID), new ItemStackConfig(Item.ingotIron.itemID, 10)};
	@ConfigProp(group = "Mercenary")
	public static ItemStackConfig[] mercArcherCost = {new ItemStackConfig(Item.ingotGold.itemID), new ItemStackConfig(Item.ingotIron.itemID, 10)};
	@ConfigProp(group = "Mercenary")
	public static ItemStackConfig[] healerCost     = {new ItemStackConfig(Item.ingotGold.itemID), new ItemStackConfig(Item.ingotIron.itemID, 10)};
	@ConfigProp(group = "Mercenary")
	public static float healPointByTimeRange = 1.5F;
	
	// Ratio de building de chaque type
	@ConfigProp(group = "Spawn rate group [0-10]") public static int castleSpawnRate    = 5;
	@ConfigProp(group = "Spawn rate group [0-10]") public static int mercenarySpawnRate = 5;
	
	// Ratio de building entre les batiments d'un meme type
	@ConfigProp(group = "Spawn rate between mercenary building") public static int mercenaryBuilding1SpawnRate = 7;
	@ConfigProp(group = "Spawn rate between mercenary building") public static int mercenaryBuilding2SpawnRate = 6;
	@ConfigProp(group = "Spawn rate between mercenary building") public static int mercenaryBuilding3SpawnRate = 1;
	@ConfigProp(group = "Spawn rate between mercenary building") public static int mercenaryBuilding4SpawnRate = 1;
	
	@ConfigProp(group = "Spawn rate between castle building") public static int castleBuilding1SpawnRate = 6;
	@ConfigProp(group = "Spawn rate between castle building") public static int castleBuilding2SpawnRate = 6;
	@ConfigProp(group = "Spawn rate between castle building") public static int castleBuilding3SpawnRate = 3;
	@ConfigProp(group = "Spawn rate between castle building") public static int castleBuilding4SpawnRate = 2;
	
}

package com.gollum.castledefenders.common.config;

import com.gollum.core.common.config.Config;
import com.gollum.core.common.config.ConfigProp;
import com.gollum.core.common.config.type.ItemStackConfigType;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class ConfigCastleDefender extends Config {
	
	@ConfigProp(mcRestart = true) public boolean compatibilityWithOldVersion = false;
	
	// Config d'affichage
	@ConfigProp(group = "Display") public boolean displayMercenaryMessage = true;
	@ConfigProp(group = "Display") public boolean displayMercenaryLife = true;
	@ConfigProp(group = "Display") public double mercenaryLifeTop = 80.D;
	@ConfigProp(group = "Display") public double mercenaryLifeHeight = 3.D;
	@ConfigProp(group = "Display") public double mercenaryLifeWidth = 40.D;
	
	// Liste des IDs
	@ConfigProp(group = "Blocks Ids") public int blockKnightID     = 1238;
	@ConfigProp(group = "Blocks Ids") public int blockKnight2ID    = 1240;
	@ConfigProp(group = "Blocks Ids") public int blockArcherID     = 1239;
	@ConfigProp(group = "Blocks Ids") public int blockArcher2ID    = 1241;
	@ConfigProp(group = "Blocks Ids") public int blockMercID       = 1234;
	@ConfigProp(group = "Blocks Ids") public int blockMercArcherID = 1232;
	@ConfigProp(group = "Blocks Ids") public int blockMageID       = 1235;
	@ConfigProp(group = "Blocks Ids") public int blockHealerID     = 1242;
	@ConfigProp(group = "Blocks Ids") public int blockEKnightID    = 1237;
	@ConfigProp(group = "Blocks Ids") public int blockEArcherID    = 1236;
	@ConfigProp(group = "Blocks Ids") public int blockEMageID      = 1233;
	
	@ConfigProp(group = "Items Ids") public int medallionID    = 13001;
	
	@ConfigProp(group = "Spawn count") public int maxSpawnKnight     = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnKnight2    = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnEKnight    = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnArcher     = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnArcher2    = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnEArcher    = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnMage       = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnEMage      = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnMerc       = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnMercArcher = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnHealer     = 6;
	
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType knightCapacities     = new MobCapacitiesConfigType(0.55D, 20.D, 4.D , 20.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType knight2Capacities    = new MobCapacitiesConfigType(0.6D , 30.D, 8.D , 25.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType eKnightCapacities    = new MobCapacitiesConfigType(0.55D, 25.D, 6.D , 16.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType archerCapacities     = new MobCapacitiesConfigType(0.1D , 15.D, 4.D , 25.D, 30.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType archer2Capacities    = new MobCapacitiesConfigType(0.1D , 25.D, 7.D , 30.D, 15.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType eArcherCapacities    = new MobCapacitiesConfigType(0.1D , 20.D, 6.D , 18.D, 20.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType mageCapacities       = new MobCapacitiesConfigType(0.1D , 25.D, 5.D , 10.D, 40.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType eMageCapacities      = new MobCapacitiesConfigType(0.1D , 30.D, 5.D , 10.D, 40.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType mercCapacities       = new MobCapacitiesConfigType(0.6D , 20.D, 5.D , 20.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType mercArcherCapacities = new MobCapacitiesConfigType(0.6D , 20.D, 5.D , 25.D, 17.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType healerCapacities     = new MobCapacitiesConfigType(0.6D , 15.D, 0.3D, 5.D , 100.D);
	
	// Config des mercenaire
	@ConfigProp(group = "Mercenary", info="[ItemID,metadata,number],...") 
	public ItemStackConfigType[] mercenaryCost  = {new ItemStackConfigType("minecraft:gold_ingot"), new ItemStackConfigType("minecraft:iron_ingot", 10)};
	@ConfigProp(group = "Mercenary")
	public ItemStackConfigType[] mercArcherCost = {new ItemStackConfigType("minecraft:gold_ingot"), new ItemStackConfigType("minecraft:iron_ingot", 10)};
	@ConfigProp(group = "Mercenary")
	public ItemStackConfigType[] healerCost     = {new ItemStackConfigType("minecraft:gold_ingot"), new ItemStackConfigType("minecraft:iron_ingot", 10)};
	@ConfigProp(group = "Mercenary")
	public float healPointByTimeRange = 1.5F;
	
}

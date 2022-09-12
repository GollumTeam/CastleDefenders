package com.gollum.castledefenders.common.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.gollum.core.common.config.Config;
import com.gollum.core.common.config.ConfigProp;
import com.gollum.core.common.config.ConfigProp.Type;
import com.gollum.core.common.config.type.ItemStackConfigType;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

public class ConfigCastleDefender extends Config {
	
	// Config d'affichage
	@ConfigProp(group = "Display") public boolean displayMercenaryMessage = true;
	@ConfigProp(group = "Display") public boolean displayMercenaryLife = true;
	@ConfigProp(group = "Display") public double mercenaryLifeTop = 80.D;
	@ConfigProp(group = "Display") public double mercenaryLifeHeight = 3.D;
	@ConfigProp(group = "Display") public double mercenaryLifeWidth = 40.D;
	
	@ConfigProp(group = "Spawn count") public int maxSpawnKnight     = 5;
	@ConfigProp(group = "Spawn count") public int maxSpawnKnight2    = 5;
	@ConfigProp(group = "Spawn count") public int maxSpawnEKnight    = 5;
	@ConfigProp(group = "Spawn count") public int maxSpawnArcher     = 5;
	@ConfigProp(group = "Spawn count") public int maxSpawnArcher2    = 5;
	@ConfigProp(group = "Spawn count") public int maxSpawnEArcher    = 5;
	@ConfigProp(group = "Spawn count") public int maxSpawnMage       = 5;
	@ConfigProp(group = "Spawn count") public int maxSpawnEMage      = 5;
	@ConfigProp(group = "Spawn count") public int maxSpawnMerc       = 1;
	@ConfigProp(group = "Spawn count") public int maxSpawnMercArcher = 1;
	@ConfigProp(group = "Spawn count") public int maxSpawnHealer     = 1;
	
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType knightCapacities     = new MobCapacitiesConfigType(0.55D, 20.D, 4.D , 20.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType knight2Capacities    = new MobCapacitiesConfigType(0.6D , 30.D, 8.D , 25.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType eKnightCapacities    = new MobCapacitiesConfigType(0.55D, 25.D, 6.D , 16.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType archerCapacities     = new MobCapacitiesConfigType(0.1D , 15.D, 2.D , 25.D, 30.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType archer2Capacities    = new MobCapacitiesConfigType(0.1D , 25.D, 4.D , 30.D, 15.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType eArcherCapacities    = new MobCapacitiesConfigType(0.1D , 20.D, 3.D , 18.D, 20.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType mageCapacities       = new MobCapacitiesConfigType(0.1D , 25.D, 5.D , 15.D, 30.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType eMageCapacities      = new MobCapacitiesConfigType(0.1D , 30.D, 8.D , 15.D, 30.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType mercCapacities       = new MobCapacitiesConfigType(0.6D , 30.D, 7.D , 20.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType mercArcherCapacities = new MobCapacitiesConfigType(0.6D , 30.D, 3.D , 25.D, 17.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType healerCapacities     = new MobCapacitiesConfigType(0.6D , 20.D, 0.3D, 5.D , 100.D);
	

	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String knightBiomes[];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int knightSpawnMin = 0;
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int knightSpawnMax = 0;
	
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String knight2Biomes   [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int knight2SpawnMin = 0;
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int knight2SpawnMax = 0;
	
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String eKnightBiomes   [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int eKnightSpawnMin = 0;
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int eKnightSpawnMax = 1;
	
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String archerBiomes    [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int archerSpawnMin = 0;
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int archerSpawnMax = 0;
	
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String archer2Biomes   [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int archer2SpawnMin = 0;
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int archer2SpawnMax = 0;
	
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String eArcherBiomes   [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int eArcherSpawnMin = 0;
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int eArcherSpawnMax = 1;
	
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String mageBiomes      [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int mageSpawnMin = 0;
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int mageSpawnMax = 1;
	
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String eMageBiomes     [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int eMageSpawnMin = 0;
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "30") public int eMageSpawnMax = 1;
	
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String mercBiomes      [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "3A0") public int mercSpawnMin = 0;
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "3A0") public int mercSpawnMax = 1;
	
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String mercArcherBiomes[];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "20") public int mercArcherSpawnMin = 0;
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "20") public int mercArcherSpawnMax = 1;
	
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String healerBiomes    [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "20") public int healerSpawnMin = 0;
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.SLIDER, minValue = "0", maxValue = "20") public int healerSpawnMax = 1;
	
	
	// Config des mercenaire
	@ConfigProp(group = "Mercenary", info="[ItemID,metadata,number],...") 
	public ItemStackConfigType[] mercenaryCost  = {new ItemStackConfigType("minecraft:gold_ingot"), new ItemStackConfigType("minecraft:iron_ingot", 10)};
	@ConfigProp(group = "Mercenary")
	public ItemStackConfigType[] mercArcherCost = {new ItemStackConfigType("minecraft:gold_ingot"), new ItemStackConfigType("minecraft:iron_ingot", 10)};
	@ConfigProp(group = "Mercenary")
	public ItemStackConfigType[] healerCost     = {new ItemStackConfigType("minecraft:gold_ingot"), new ItemStackConfigType("minecraft:iron_ingot", 10)};
	@ConfigProp(group = "Mercenary")
	public float healPointByTimeRange = 1.5F;
	

	public ConfigCastleDefender() {
		List<Biome> biomes =  Arrays.asList(
			Biomes.DESERT_HILLS,
			Biomes.EXTREME_HILLS,
			Biomes.EXTREME_HILLS_EDGE,
			Biomes.FOREST_HILLS,
			Biomes.FROZEN_OCEAN,
			Biomes.FROZEN_RIVER,
			Biomes.ICE_MOUNTAINS,
			Biomes.ICE_PLAINS,
			Biomes.JUNGLE_HILLS,
			Biomes.PLAINS,
			Biomes.BIRCH_FOREST_HILLS,
			Biomes.TAIGA_HILLS,
			Biomes.SAVANNA
		);
		
		List<String> biomesStr = biomes
			.stream()
			.map(b -> b.getRegistryName().toString())
			.collect(Collectors.toList())
		;
		
		this.knightBiomes     = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.knight2Biomes    = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.eKnightBiomes    = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.archerBiomes     = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.archer2Biomes    = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.eArcherBiomes    = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.mageBiomes       = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.eMageBiomes      = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.mercBiomes       = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.mercArcherBiomes = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.healerBiomes     = (String[]) biomesStr.toArray(new String[biomes.size()]);
	}
}

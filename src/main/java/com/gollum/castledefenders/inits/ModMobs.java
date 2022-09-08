package com.gollum.castledefenders.inits;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

import com.gollum.castledefenders.common.entities.EntityArcher;
import com.gollum.castledefenders.common.entities.EntityArcher2;
import com.gollum.castledefenders.common.entities.EntityEArcher;
import com.gollum.castledefenders.common.entities.EntityEKnight;
import com.gollum.castledefenders.common.entities.EntityEMage;
import com.gollum.castledefenders.common.entities.EntityHealer;
import com.gollum.castledefenders.common.entities.EntityKnight;
import com.gollum.castledefenders.common.entities.EntityKnight2;
import com.gollum.castledefenders.common.entities.EntityMage;
import com.gollum.castledefenders.common.entities.EntityMerc;
import com.gollum.castledefenders.common.entities.EntityMercArcher;
import com.gollum.core.common.factory.MobFactory;

import net.minecraftforge.fml.common.registry.EntityRegistry;


public class ModMobs {
	
	public static void init() {
		
		registerMob(EntityKnight.class    , "Knight"     , 0x000000);
		registerMob(EntityKnight2.class   , "Knight2"    , 0x00FFFC);
		registerMob(EntityArcher.class    , "Archer"     , 0x500000);
		registerMob(EntityArcher2.class   , "Archer2"    , 0x00FF88);
		registerMob(EntityMage.class      , "Mage"       , 0xE10000);
		registerMob(EntityEKnight.class   , "EnemyKnight", 0xFF00AA);
		registerMob(EntityEArcher.class   , "EnemyArcher", 0xE1AA00);
		registerMob(EntityEMage.class     , "EnemyMage"  , 0xE12AFF);
		registerMob(EntityMerc.class      , "Merc"       , 0x875600);
		registerMob(EntityMercArcher.class, "MercArcher" , 0xBF95FF);
		registerMob(EntityHealer.class    , "Healer"     , 0xFF84B4);
		
	}

	/**
	 * Enregistre un mob
	 * @param entityClass
	 * @param name
	 * @param id
	 * @param spawn
	 */
	protected static void registerMob (Class entityClass, String name, int color) {
		
		new MobFactory().register(entityClass, name, 0xFFFFFF, color);;
		
		// Pop dans les biomes
		EntityRegistry.addSpawn(entityClass, 10, 0, 0, EnumCreatureType.CREATURE, new Biome[] {
			Biomes.DESERT,
			Biomes.DESERT_HILLS,
			Biomes.EXTREME_HILLS,
			Biomes.EXTREME_HILLS_EDGE,
			Biomes.FOREST,
			Biomes.FOREST_HILLS,
			Biomes.FROZEN_OCEAN,
			Biomes.FROZEN_RIVER,
			Biomes.HELL,
			Biomes.ICE_MOUNTAINS,
			Biomes.ICE_PLAINS,
			Biomes.JUNGLE,
			Biomes.JUNGLE_HILLS,
			Biomes.MUSHROOM_ISLAND,
			Biomes.MUSHROOM_ISLAND_SHORE,
			Biomes.OCEAN,
			Biomes.PLAINS,
			Biomes.RIVER,
			Biomes.SKY,
			Biomes.BIRCH_FOREST,
			Biomes.BIRCH_FOREST_HILLS,
			Biomes.SWAMPLAND,
			Biomes.TAIGA,
			Biomes.TAIGA_HILLS,
			Biomes.COLD_BEACH,
			Biomes.COLD_TAIGA,
			Biomes.SAVANNA,
			Biomes.SAVANNA_PLATEAU,
		});
		
	}
}

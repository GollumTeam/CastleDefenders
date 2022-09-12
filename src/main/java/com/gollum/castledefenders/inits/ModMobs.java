package com.gollum.castledefenders.inits;

import static com.gollum.castledefenders.ModCastleDefenders.config;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gollum.castledefenders.common.config.ConfigCastleDefender;
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
import com.gollum.core.tools.registered.RegisteredObjects;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import scala.actors.threadpool.Arrays;


public class ModMobs {
	
	public static void init() {
		
		registerMob(EntityKnight.class    , "knight"     , 0x000000, config.knightSpawnMin    , config.knightSpawnMax    , config.knightBiomes    );
		registerMob(EntityKnight2.class   , "knight2"    , 0x00FFFC, config.knight2SpawnMin   , config.knight2SpawnMax   , config.knight2Biomes   );
		registerMob(EntityArcher.class    , "archer"     , 0x500000, config.eKnightSpawnMin   , config.eKnightSpawnMax   , config.eKnightBiomes   );
		registerMob(EntityArcher2.class   , "archer2"    , 0x00FF88, config.archerSpawnMin    , config.archerSpawnMax    , config.archerBiomes    );
		registerMob(EntityMage.class      , "mage"       , 0xE10000, config.archer2SpawnMin   , config.archer2SpawnMax   , config.archer2Biomes   );
		registerMob(EntityEKnight.class   , "enemyknight", 0xFF00AA, config.eArcherSpawnMin   , config.eArcherSpawnMax   , config.eArcherBiomes   );
		registerMob(EntityEArcher.class   , "enemyarcher", 0xE1AA00, config.mageSpawnMin      , config.mageSpawnMax      , config.mageBiomes      );
		registerMob(EntityEMage.class     , "enemymage"  , 0xE12AFF, config.eMageSpawnMin     , config.eMageSpawnMax     , config.eMageBiomes     );
		registerMob(EntityMerc.class      , "merc"       , 0x875600, config.mercSpawnMin      , config.mercSpawnMax      , config.mercBiomes      );
		registerMob(EntityMercArcher.class, "mercarcher" , 0xBF95FF, config.mercArcherSpawnMin, config.mercArcherSpawnMax, config.mercArcherBiomes);
		registerMob(EntityHealer.class    , "healer"     , 0xFF84B4, config.healerSpawnMin    , config.healerSpawnMax    , config.healerBiomes    );
		
	}

	/**
	 * Enregistre un mob
	 * @param entityClass
	 * @param name
	 * @param id
	 * @param spawn
	 */
	protected static void registerMob (Class entityClass, String name, int color, int spawMin, int spawMax, String[] biomes) {
		
		new MobFactory().register(entityClass, name, 0xFFFFFF, color);;

		List<String> list = Arrays.asList(biomes);

		// Pop dans les biomes
		EntityRegistry.addSpawn(entityClass, 0, spawMin, spawMax, EnumCreatureType.CREATURE, list
			.stream()
			.map(b -> RegisteredObjects.instance().getBiome(b))
			.filter(b -> b != null)
			.collect(Collectors.toList())
			.toArray(new Biome[list.size()])
		);
		
	}
}

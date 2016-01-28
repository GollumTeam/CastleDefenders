package com.gollum.castledefenders.inits;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

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
import com.gollum.core.common.facory.Mobactory;

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
		
		new Mobactory().register(entityClass, name, 0xFFFFFF, color);;
		
		// Pop dans les biomes
		EntityRegistry.addSpawn(entityClass, 10, 0, 0, EnumCreatureType.CREATURE, new BiomeGenBase[] {
			BiomeGenBase.desert, BiomeGenBase.desertHills,
			BiomeGenBase.extremeHills,
			BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest,
			BiomeGenBase.forestHills, BiomeGenBase.frozenOcean,
			BiomeGenBase.frozenRiver, BiomeGenBase.hell,
			BiomeGenBase.iceMountains, BiomeGenBase.icePlains,
			BiomeGenBase.jungle, BiomeGenBase.jungleHills,
			BiomeGenBase.mushroomIsland,
			BiomeGenBase.mushroomIslandShore, BiomeGenBase.ocean,
			BiomeGenBase.plains, BiomeGenBase.river,
			BiomeGenBase.sky, BiomeGenBase.swampland,
			BiomeGenBase.taiga, BiomeGenBase.taigaHills,
		});
		
	}
}

package com.gollum.castledefenders.inits;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.config.ConfigCastleDefender;
import com.gollum.castledefenders.common.entities.EntityArcher;
import com.gollum.castledefenders.common.entities.EntityArcher2;
import com.gollum.castledefenders.common.entities.EntityFireBall;
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

import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import scala.actors.threadpool.Arrays;


public class ModEntities {

	private static int ID = 0;
	
	public static EntityEntry FIRE_BALL = EntityEntryBuilder.create()
		.entity(EntityFireBall.class)
		.id(new ResourceLocation(ModCastleDefenders.MODID, "fire_ball"), ModEntities.ID++)
		.name("fire_ball")
		.tracker(128, 1, true)
		.build()
	;
	
	public static void init() {
		ForgeRegistries.ENTITIES.register(ModEntities.FIRE_BALL);
	}
}

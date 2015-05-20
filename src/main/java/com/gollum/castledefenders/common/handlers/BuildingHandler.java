package com.gollum.castledefenders.common.handlers;

import static com.gollum.core.ModGollumCoreLib.log;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import net.minecraft.entity.EntityTracker;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.ChunkDataEvent.Save;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Unload;

import com.gollum.core.common.building.Builder;
import com.gollum.core.common.building.Builder.BuilderRunnable;
import com.gollum.core.common.events.BuildingGenerateEvent;
import com.gollum.core.utils.reflection.Reflection;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;


public class BuildingHandler {
	
	boolean mustBeSave = false;
	
	@SubscribeEvent
	public void onBuildingGenerate (BuildingGenerateEvent event) {
		
		if (!event.world.isRemote) {
			log.debug ("COOOOOOLLLL EVENt BUILDING");
		}
	}
	
}

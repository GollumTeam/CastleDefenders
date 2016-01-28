package com.gollum.castledefenders.common.handlers;

import static com.gollum.core.ModGollumCoreLib.log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.inits.ModAchievements;
import com.gollum.core.common.events.BuildingGenerateEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class BuildingHandler {
	
	boolean mustBeSave = false;
	
	@SubscribeEvent
	public void onBuildingGenerate (BuildingGenerateEvent event) {
		
		if (!event.world.isRemote) {
			
			if (event.building.modId.equals(ModCastleDefenders.MODID)) {
				
				Achievement target = null;
				StatBase  counter = null;
				if(event.building.name.equals("castle1"   )) { target = ModAchievements.achievementCastle1   ; counter = ModAchievements.achievementCastle1Counter   ; }
				if(event.building.name.equals("castle2"   )) { target = ModAchievements.achievementCastle2   ; counter = ModAchievements.achievementCastle2Counter   ; }
				if(event.building.name.equals("castle3"   )) { target = ModAchievements.achievementCastle3   ; counter = ModAchievements.achievementCastle3Counter   ; }
				if(event.building.name.equals("castle4"   )) { target = ModAchievements.achievementCastle4   ; counter = ModAchievements.achievementCastle4Counter   ; }
				if(event.building.name.equals("mercenary1")) { target = ModAchievements.achievementMercenary1; counter = ModAchievements.achievementMercenary1Counter; }
				if(event.building.name.equals("mercenary2")) { target = ModAchievements.achievementMercenary2; counter = ModAchievements.achievementMercenary2Counter; }
				if(event.building.name.equals("mercenary3")) { target = ModAchievements.achievementMercenary3; counter = ModAchievements.achievementMercenary3Counter; }
				if(event.building.name.equals("mercenary4")) { target = ModAchievements.achievementMercenary4; counter = ModAchievements.achievementMercenary4Counter; }
				
				if (target != null && counter != null) {
					
					for (Object entity : event.world.playerEntities) {
						if (entity instanceof EntityPlayerMP) {
							EntityPlayerMP player = (EntityPlayerMP)entity;
							
							// Add Stat
							player.addStat(counter, 1);
							
							// Add Achievement
							player.addStat(ModAchievements.achievementCastleDefenders, 1);
							
							if (!player.getStatFile().hasAchievementUnlocked(target)) {
								player.addStat(target, 1);
								
								if (
									player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementCastle1) &&
									player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementCastle2) &&
									player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementCastle3) &&
									player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementCastle4) &&
									!player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementAllCastle)
								) {
									player.addStat(ModAchievements.achievementAllCastle, 1);
								}
								
								if (
									player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementMercenary1) &&
									player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementMercenary2) &&
									player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementMercenary3) &&
									player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementMercenary4) &&
									!player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementAllMercenary)
								) {
									player.addStat(ModAchievements.achievementAllMercenary, 1);
								}
								
								if (
									player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementAllCastle)    &&
									player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementAllMercenary) &&
									!player.getStatFile().hasAchievementUnlocked(ModAchievements.achievementAllBuilding)
								) {
									player.addStat(ModAchievements.achievementAllBuilding, 1);
								}
							}
						}
					}
				}
			}
			
		}
	}
	
}

package com.gollum.castledefenders.inits;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.core.common.stats.StatsPage;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBasic;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.AchievementPage;


public class ModAchievements {
	
	/////////////////
	// Achievement //
	/////////////////
	
	public static AchievementPage pageAchievement;
	
	public static Achievement achievementCastleDefenders;
	
	public static Achievement achievementCastle1;
	public static Achievement achievementCastle2;
	public static Achievement achievementCastle3;
	public static Achievement achievementCastle4;
	
	public static Achievement achievementMercenary1;
	public static Achievement achievementMercenary2;
	public static Achievement achievementMercenary3;
	public static Achievement achievementMercenary4;
	
	public static Achievement achievementAllCastleHidden1;
	public static Achievement achievementAllCastleHidden2;
	public static Achievement achievementAllCastleHidden3;
	public static Achievement achievementAllCastle;
	
	public static Achievement achievementAllMercenaryHidden1;
	public static Achievement achievementAllMercenaryHidden2;
	public static Achievement achievementAllMercenaryHidden3;
	public static Achievement achievementAllMercenary;
	
	///////////
	// Stats //
	///////////
	
	public static StatsPage       statsAchievement;
	
	public static StatBasic achievementCastle1Counter;
	public static StatBasic achievementCastle2Counter;
	public static StatBasic achievementCastle3Counter;
	public static StatBasic achievementCastle4Counter;
	
	public static StatBasic achievementMercenary1Counter;
	public static StatBasic achievementMercenary2Counter;
	public static StatBasic achievementMercenary3Counter;
	public static StatBasic achievementMercenary4Counter;
	
	public static void init() {
		
		/////////////////
		// Achievement //
		/////////////////
		
		ModAchievements.achievementCastleDefenders = new Achievement("achievement.castledefenders.foundCastleDefenders", "foundCastleDefenders", 1, 0, Blocks.cobblestone, null).registerStat().initIndependentStat();
		
		ModAchievements.achievementCastle1 = new Achievement("achievement.castledefenders.foundCastleBuilding1", "foundCastleBuilding1", 4, -7, ModBlocks.blockEArcher, ModAchievements.achievementCastleDefenders).registerStat();
		ModAchievements.achievementCastle2 = new Achievement("achievement.castledefenders.foundCastleBuilding2", "foundCastleBuilding2", 3, -5, ModBlocks.blockEKnight, ModAchievements.achievementCastleDefenders).registerStat();
		ModAchievements.achievementCastle3 = new Achievement("achievement.castledefenders.foundCastleBuilding3", "foundCastleBuilding3", 3, -3, ModBlocks.blockEKnight, ModAchievements.achievementCastleDefenders).registerStat();
		ModAchievements.achievementCastle4 = new Achievement("achievement.castledefenders.foundCastleBuilding4", "foundCastleBuilding4", 4, -1, ModBlocks.blockEMage  , ModAchievements.achievementCastleDefenders).registerStat();
		
		ModAchievements.achievementMercenary1 = new Achievement("achievement.castledefenders.foundMercenaryBuilding1", "foundMercenaryBuilding1", 4, 1, ModBlocks.blockKnight , ModAchievements.achievementCastleDefenders).registerStat();
		ModAchievements.achievementMercenary2 = new Achievement("achievement.castledefenders.foundMercenaryBuilding2", "foundMercenaryBuilding2", 3, 3, ModBlocks.blockArcher , ModAchievements.achievementCastleDefenders).registerStat();
		ModAchievements.achievementMercenary3 = new Achievement("achievement.castledefenders.foundMercenaryBuilding3", "foundMercenaryBuilding3", 3, 5, ModBlocks.blockKnight2, ModAchievements.achievementCastleDefenders).registerStat();
		ModAchievements.achievementMercenary4 = new Achievement("achievement.castledefenders.foundMercenaryBuilding4", "foundMercenaryBuilding4", 4, 7, ModBlocks.blockArcher2, ModAchievements.achievementCastleDefenders).registerStat();
		
		ModAchievements.achievementAllCastleHidden1 = new Achievement("achievement.castledefenders.foundAllCastleBuildingHidden1", "foundAllCastleBuilding", 7,-4, ModItems.itemMedallion, ModAchievements.achievementCastle1).registerStat();
		ModAchievements.achievementAllCastleHidden2 = new Achievement("achievement.castledefenders.foundAllCastleBuildingHidden2", "foundAllCastleBuilding", 7,-4, ModItems.itemMedallion, ModAchievements.achievementCastle2).registerStat();
		ModAchievements.achievementAllCastleHidden3 = new Achievement("achievement.castledefenders.foundAllCastleBuildingHidden3", "foundAllCastleBuilding", 7,-4, ModItems.itemMedallion, ModAchievements.achievementCastle3).registerStat();
		ModAchievements.achievementAllCastle        = new Achievement("achievement.castledefenders.foundAllCastleBuilding"       , "foundAllCastleBuilding", 7,-4, ModItems.itemMedallion, ModAchievements.achievementCastle4).registerStat();
		
		ModAchievements.achievementAllMercenaryHidden1 = new Achievement("achievement.castledefenders.foundAllMercenaryBuildingHidden1", "foundAllMercenaryBuilding", 7, 4, ModItems.itemMedallion, ModAchievements.achievementMercenary1).registerStat();
		ModAchievements.achievementAllMercenaryHidden2 = new Achievement("achievement.castledefenders.foundAllMercenaryBuildingHidden2", "foundAllMercenaryBuilding", 7, 4, ModItems.itemMedallion, ModAchievements.achievementMercenary2).registerStat();
		ModAchievements.achievementAllMercenaryHidden3 = new Achievement("achievement.castledefenders.foundAllMercenaryBuildingHidden3", "foundAllMercenaryBuilding", 7, 4, ModItems.itemMedallion, ModAchievements.achievementMercenary3).registerStat();
		ModAchievements.achievementAllMercenary        = new Achievement("achievement.castledefenders.foundAllMercenaryBuilding"       , "foundAllMercenaryBuilding", 7, 4, ModItems.itemMedallion, ModAchievements.achievementMercenary4).registerStat();
		
		ModAchievements.pageAchievement = new AchievementPage(ModCastleDefenders.MODNAME,
				
			ModAchievements.achievementCastleDefenders,
			
			ModAchievements.achievementCastle1,
			ModAchievements.achievementCastle2,
			ModAchievements.achievementCastle3,
			ModAchievements.achievementCastle4,
			
			ModAchievements.achievementMercenary1,
			ModAchievements.achievementMercenary2,
			ModAchievements.achievementMercenary3,
			ModAchievements.achievementMercenary4,

			ModAchievements.achievementAllMercenaryHidden1,
			ModAchievements.achievementAllMercenaryHidden2,
			ModAchievements.achievementAllMercenaryHidden3,
			ModAchievements.achievementAllMercenary,

			ModAchievements.achievementAllCastleHidden1,
			ModAchievements.achievementAllCastleHidden2,
			ModAchievements.achievementAllCastleHidden3,
			ModAchievements.achievementAllCastle
		);
		
		AchievementPage.registerAchievementPage(ModAchievements.pageAchievement);

		///////////
		// Stats //
		///////////
		
		ModAchievements.achievementCastle1Counter = (StatBasic) new StatBasic("stat.castledefenders.foundCastleBuilding1Counter", new ChatComponentTranslation("stats.foundCastleBuilding1Counter", new Object[0]), StatBasic.simpleStatType).registerStat();
		ModAchievements.achievementCastle2Counter = (StatBasic) new StatBasic("stat.castledefenders.foundCastleBuilding2Counter", new ChatComponentTranslation("stats.foundCastleBuilding2Counter", new Object[0]), StatBasic.simpleStatType).registerStat();
		ModAchievements.achievementCastle3Counter = (StatBasic) new StatBasic("stat.castledefenders.foundCastleBuilding3Counter", new ChatComponentTranslation("stats.foundCastleBuilding3Counter", new Object[0]), StatBasic.simpleStatType).registerStat();
		ModAchievements.achievementCastle4Counter = (StatBasic) new StatBasic("stat.castledefenders.foundCastleBuilding4Counter", new ChatComponentTranslation("stats.foundCastleBuilding4Counter", new Object[0]), StatBasic.simpleStatType).registerStat();
		
		ModAchievements.achievementMercenary1Counter = (StatBasic) new StatBasic("stat.castledefenders.foundMercenaryBuilding1Counter", new ChatComponentTranslation("stats.foundMercenaryBuilding1Counter", new Object[0]), StatBasic.simpleStatType).registerStat();
		ModAchievements.achievementMercenary2Counter = (StatBasic) new StatBasic("stat.castledefenders.foundMercenaryBuilding2Counter", new ChatComponentTranslation("stats.foundMercenaryBuilding2Counter", new Object[0]), StatBasic.simpleStatType).registerStat();
		ModAchievements.achievementMercenary3Counter = (StatBasic) new StatBasic("stat.castledefenders.foundMercenaryBuilding3Counter", new ChatComponentTranslation("stats.foundMercenaryBuilding3Counter", new Object[0]), StatBasic.simpleStatType).registerStat();
		ModAchievements.achievementMercenary4Counter = (StatBasic) new StatBasic("stat.castledefenders.foundMercenaryBuilding4Counter", new ChatComponentTranslation("stats.foundMercenaryBuilding4Counter", new Object[0]), StatBasic.simpleStatType).registerStat();
		
		ModAchievements.statsAchievement = new StatsPage(ModCastleDefenders.MODNAME, 
			ModAchievements.achievementCastle1Counter,
			ModAchievements.achievementCastle2Counter,
			ModAchievements.achievementCastle3Counter,
			ModAchievements.achievementCastle4Counter,
			ModAchievements.achievementMercenary1Counter,
			ModAchievements.achievementMercenary2Counter,
			ModAchievements.achievementMercenary3Counter,
			ModAchievements.achievementMercenary4Counter
		);
		
		StatsPage.registerStatsPage(ModAchievements.statsAchievement);
	}
}

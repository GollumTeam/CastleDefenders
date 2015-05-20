package com.gollum.castledefenders.inits;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;


public class ModAchievements {

	public static AchievementPage page;

	public static Achievement achievementTest1;
	public static Achievement achievementTest2;
//	public static Achievement achievementTest3;
//	public static Achievement achievementTest4;
	
	public static void init() {
		
		ModAchievements.achievementTest1 = new Achievement("achievement.castledefenders.foundMercenaryBuilding", "foundMercenaryBuilding", 1, -2, new ItemStack(ModBlocks.blockMerc, 1, 0)   , null).registerStat().initIndependentStat();
		ModAchievements.achievementTest2 = new Achievement("achievement.castledefenders.foundCastleBuilding"   , "foundCastleBuilding"   , 3, -1, new ItemStack(ModBlocks.blockEKnight, 1, 0), null).registerStat().initIndependentStat();
//		ModAchievements.achievementTest3 = new Achievement("achievement.castledefenders.test3", "test3", 5, -1, Items.golden_apple                               , ModAchievements.achievementTest2).registerStat();
//		ModAchievements.achievementTest4 = new Achievement("achievement.castledefenders.test4", "test4", 3, -3, Items.arrow                                      , ModAchievements.achievementTest1).registerStat();
		
		ModAchievements.page = new AchievementPage("Castle Defenders", 
			ModAchievements.achievementTest1,
			ModAchievements.achievementTest2//,
//			ModAchievements.achievementTest3,
//			ModAchievements.achievementTest4
		);
		
		AchievementPage.registerAchievementPage(ModAchievements.page);
	}
}

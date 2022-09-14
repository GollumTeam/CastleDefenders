package com.gollum.castledefenders.inits;

import static com.gollum.castledefenders.ModCastleDefenders.i18n;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.core.common.stats.StatsPage;

import net.minecraft.advancements.Advancement;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBasic;
import net.minecraft.util.text.TextComponentString;


public class ModAdvancement {
	
	public static StatsPage STATS_PAGE;
	
	public static StatBasic STAT_COUNTER_CASTLE1;
	public static StatBasic STAT_COUNTER_CASTLE2;
	public static StatBasic STAT_COUNTER_CASTLE3;
	public static StatBasic STAT_COUNTER_CASTLE4;
	
	public static StatBasic STAT_COUNTER_MERCENARY1;
	public static StatBasic STAT_COUNTER_MERCENARY2;
	public static StatBasic STAT_COUNTER_MERCENARY3;
	public static StatBasic STAT_COUNTER_MERCENARY4;
	
	public static void init() {
		
		ModAdvancement.STAT_COUNTER_CASTLE1 = (StatBasic) new StatBasic("stat.castledefenders.foundCastleBuilding1Counter", new TextComponentString(i18n.trans("stats.foundCastleBuilding1Counter")), StatBasic.simpleStatType).registerStat();
		ModAdvancement.STAT_COUNTER_CASTLE2 = (StatBasic) new StatBasic("stat.castledefenders.foundCastleBuilding2Counter", new TextComponentString(i18n.trans("stats.foundCastleBuilding2Counter")), StatBasic.simpleStatType).registerStat();
		ModAdvancement.STAT_COUNTER_CASTLE3 = (StatBasic) new StatBasic("stat.castledefenders.foundCastleBuilding3Counter", new TextComponentString(i18n.trans("stats.foundCastleBuilding3Counter")), StatBasic.simpleStatType).registerStat();
		ModAdvancement.STAT_COUNTER_CASTLE4 = (StatBasic) new StatBasic("stat.castledefenders.foundCastleBuilding4Counter", new TextComponentString(i18n.trans("stats.foundCastleBuilding4Counter")), StatBasic.simpleStatType).registerStat();
		
		ModAdvancement.STAT_COUNTER_MERCENARY1 = (StatBasic) new StatBasic("stat.castledefenders.foundMercenaryBuilding1Counter", new TextComponentString(i18n.trans("stats.foundMercenaryBuilding1Counter")), StatBasic.simpleStatType).registerStat();
		ModAdvancement.STAT_COUNTER_MERCENARY2 = (StatBasic) new StatBasic("stat.castledefenders.foundMercenaryBuilding2Counter", new TextComponentString(i18n.trans("stats.foundMercenaryBuilding2Counter")), StatBasic.simpleStatType).registerStat();
		ModAdvancement.STAT_COUNTER_MERCENARY3 = (StatBasic) new StatBasic("stat.castledefenders.foundMercenaryBuilding3Counter", new TextComponentString(i18n.trans("stats.foundMercenaryBuilding3Counter")), StatBasic.simpleStatType).registerStat();
		ModAdvancement.STAT_COUNTER_MERCENARY4 = (StatBasic) new StatBasic("stat.castledefenders.foundMercenaryBuilding4Counter", new TextComponentString(i18n.trans("stats.foundMercenaryBuilding4Counter")), StatBasic.simpleStatType).registerStat();
		
		ModAdvancement.STATS_PAGE = new StatsPage(ModCastleDefenders.MODNAME, 
			ModAdvancement.STAT_COUNTER_CASTLE1,
			ModAdvancement.STAT_COUNTER_CASTLE2,
			ModAdvancement.STAT_COUNTER_CASTLE3,
			ModAdvancement.STAT_COUNTER_CASTLE4,
			ModAdvancement.STAT_COUNTER_MERCENARY1,
			ModAdvancement.STAT_COUNTER_MERCENARY2,
			ModAdvancement.STAT_COUNTER_MERCENARY3,
			ModAdvancement.STAT_COUNTER_MERCENARY4
		);
		
		StatsPage.registerStatsPage(ModAdvancement.STATS_PAGE);
	}
}

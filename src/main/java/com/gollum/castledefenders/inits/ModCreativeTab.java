package com.gollum.castledefenders.inits;

import com.gollum.core.common.creativetab.GollumCreativeTabs;

public class ModCreativeTab {
	
	public static GollumCreativeTabs tabCastleDefenders = new GollumCreativeTabs("CastleDefender");
	
	public static void init() {
		ModCreativeTab.tabCastleDefenders.setIcon(ModBlocks.blockMercArcher);
	}

}

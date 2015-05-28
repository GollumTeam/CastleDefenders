package com.gollum.castledefenders.inits;

import static com.gollum.castledefenders.ModCastleDefenders.config;
import com.gollum.core.tools.helper.items.HItem;

import net.minecraft.item.Item;


public class ModItems {
	
	public static Item itemMedallion;
	
	public static void init() {
		ModItems.itemMedallion = new HItem (config.medallionID, "Medallion").setCreativeTab(ModCreativeTab.tabCastleDefenders);
	}
}

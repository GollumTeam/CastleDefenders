package com.gollum.castledefenders.inits;

import com.gollum.core.tools.helper.items.HItem;

import net.minecraft.item.Item;


public class ModItems {
	
	public static Item itemMedallion;
	
	public static void init() {
		ModItems.itemMedallion = new HItem ("Medallion").setCreativeTab(ModCreativeTab.tabCastleDefenders);
	}
}

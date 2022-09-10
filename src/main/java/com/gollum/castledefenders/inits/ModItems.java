package com.gollum.castledefenders.inits;

import com.gollum.core.tools.helper.items.HItem;

import net.minecraft.item.Item;


public class ModItems {
	
	public static Item MEDALLION;
	
	public static void init() {
		ModItems.MEDALLION = new HItem ("medallion").setCreativeTab(ModCreativeTab.CASTLE_DEFENDERS);
	}
}

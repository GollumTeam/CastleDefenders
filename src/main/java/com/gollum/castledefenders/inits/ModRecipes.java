package com.gollum.castledefenders.inits;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.gollum.core.common.tileentities.TileEntityBlockProximitySpawn;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {
	
	public static void init() {
		
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockKnight ,    1), new Object[] { " X ", "XYX", " X ", 'X', Items.iron_ingot, 'Y', Items.iron_sword });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockKnight2,    1), new Object[] { " X ", "XYX", " X ", 'X', Items.iron_ingot, 'Y', Items.diamond_sword });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockArcher ,    1), new Object[] { " X ", "XYX", " X ", 'X', Items.iron_ingot, 'Y', Items.bow });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockArcher2,    1), new Object[] { "ZXZ", "XYX", "ZXZ", 'X', Items.iron_ingot, 'Y', Items.bow,          'Z', Items.diamond });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockMage   ,    1), new Object[] { "YYY", "XXX", "XXX", 'X', Blocks.obsidian, 'Y', ModItems.itemMedallion });
		
	}
}

package com.gollum.castledefenders.inits;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.gollum.core.common.tileentities.TileEntityBlockProximitySpawn;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {
	
	public static void init() {
		
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockKnight ,    1), new Object[] { " X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.swordIron });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockKnight2,    1), new Object[] { " X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.swordDiamond });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockArcher ,    1), new Object[] { " X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.bow });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockArcher2,    1), new Object[] { "ZXZ", "XYX", "ZXZ", 'X', Item.ingotIron, 'Y', Item.bow,          'Z', Item.swordDiamond });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockMage   ,    1), new Object[] { "YYY", "XXX", "XXX", 'X', Block.obsidian, 'Y', ModItems.itemMedallion });
		
	}
}

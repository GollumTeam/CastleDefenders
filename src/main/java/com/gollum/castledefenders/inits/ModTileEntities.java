package com.gollum.castledefenders.inits;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockArcher;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockArcher2;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockEArcher;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockEKnight;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockEMage;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockHealer;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockKnight;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockKnight2;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockMage;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockMerc;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockMercArcher;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class ModTileEntities {
	
	public static void init() {
		GameRegistry.registerTileEntity(TileEntityBlockKnight.class    , new ResourceLocation(ModCastleDefenders.MODID+":knight"));
		GameRegistry.registerTileEntity(TileEntityBlockKnight2.class   , new ResourceLocation(ModCastleDefenders.MODID+":knight2"));
		GameRegistry.registerTileEntity(TileEntityBlockArcher.class    , new ResourceLocation(ModCastleDefenders.MODID+":archer"));
		GameRegistry.registerTileEntity(TileEntityBlockArcher2.class   , new ResourceLocation(ModCastleDefenders.MODID+":archer2"));
		GameRegistry.registerTileEntity(TileEntityBlockMerc.class      , new ResourceLocation(ModCastleDefenders.MODID+":merc"));
		GameRegistry.registerTileEntity(TileEntityBlockMercArcher.class, new ResourceLocation(ModCastleDefenders.MODID+":mercarcher"));
		GameRegistry.registerTileEntity(TileEntityBlockMage.class      , new ResourceLocation(ModCastleDefenders.MODID+":mage"));
		GameRegistry.registerTileEntity(TileEntityBlockHealer.class    , new ResourceLocation(ModCastleDefenders.MODID+":hearler"));
		GameRegistry.registerTileEntity(TileEntityBlockEKnight.class   , new ResourceLocation(ModCastleDefenders.MODID+":enemyknight"));
		GameRegistry.registerTileEntity(TileEntityBlockEArcher.class   , new ResourceLocation(ModCastleDefenders.MODID+":enemyarcher"));
		GameRegistry.registerTileEntity(TileEntityBlockEMage.class     , new ResourceLocation(ModCastleDefenders.MODID+":enemymageblock"));
	}
}

package com.gollum.castledefenders.inits;

import static com.gollum.castledefenders.ModCastleDefenders.config;

import com.gollum.castledefenders.common.blocks.BlockArcher;
import com.gollum.castledefenders.common.blocks.BlockArcher2;
import com.gollum.castledefenders.common.blocks.BlockEArcher;
import com.gollum.castledefenders.common.blocks.BlockEKnight;
import com.gollum.castledefenders.common.blocks.BlockEMage;
import com.gollum.castledefenders.common.blocks.BlockHealer;
import com.gollum.castledefenders.common.blocks.BlockKnight;
import com.gollum.castledefenders.common.blocks.BlockKnight2;
import com.gollum.castledefenders.common.blocks.BlockMage;
import com.gollum.castledefenders.common.blocks.BlockMerc;
import com.gollum.castledefenders.common.blocks.BlockMercArcher;

import net.minecraft.block.Block;


public class ModBlocks {
	
	public static Block blockKnight;
	public static Block blockKnight2;
	public static Block blockArcher;
	public static Block blockArcher2;
	public static Block blockMerc;
	public static Block blockMercArcher;
	public static Block blockMage;
	public static Block blockHealer;
	public static Block blockEKnight;
	public static Block blockEArcher;
	public static Block blockEMage;
	
	public static void init() {
		
		// Création des blocks
		ModBlocks.blockKnight     = new BlockKnight    (config.blockKnightID    , "BlockKnight"    ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockKnight2    = new BlockKnight2   (config.blockKnight2ID   , "BlockKnight2"   ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockArcher     = new BlockArcher    (config.blockArcherID    , "BlockArcher"    ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockArcher2    = new BlockArcher2   (config.blockArcher2ID   , "BlockArcher2"   ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockMerc       = new BlockMerc      (config.blockMercID      , "BlockMerc"      ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockMercArcher = new BlockMercArcher(config.blockMercArcherID, "BlockMercArcher").setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockMage       = new BlockMage      (config.blockMageID      , "BlockMage"      ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockHealer     = new BlockHealer    (config.blockHealerID    , "BlockHealer"    ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockEKnight    = new BlockEKnight   (config.blockEKnightID   , "BlockEKnight"   ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockEArcher    = new BlockEArcher   (config.blockEArcherID   , "BlockEArcher"   ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockEMage      = new BlockEMage     (config.blockEMageID     , "BlockEMage"     ).setHardness(2.0F).setResistance(5.0F);
		
	}

}

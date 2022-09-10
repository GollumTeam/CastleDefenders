package com.gollum.castledefenders.inits;

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
	
	public static Block KNIGHT;
	public static Block KNIGHT2;
	public static Block ARCHER;
	public static Block ARCHER2;
	public static Block MERC;
	public static Block MERC_ARCHER;
	public static Block MAGE;
	public static Block HEALER;
	public static Block EKNIGHT;
	public static Block EARCHER;
	public static Block EMAGE;
	
	public static void init() {
		
		// Création des blocks
		ModBlocks.KNIGHT     = new BlockKnight    ("knight"    ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.KNIGHT2    = new BlockKnight2   ("knight2"   ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.ARCHER     = new BlockArcher    ("archer"    ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.ARCHER2    = new BlockArcher2   ("archer2"   ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.MERC       = new BlockMerc      ("merc"      ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.MERC_ARCHER = new BlockMercArcher("mercarcher").setHardness(2.0F).setResistance(5.0F);
		ModBlocks.MAGE       = new BlockMage      ("mage"      ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.HEALER     = new BlockHealer    ("healer"    ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.EKNIGHT    = new BlockEKnight   ("eknight"   ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.EARCHER    = new BlockEArcher   ("earcher"   ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.EMAGE      = new BlockEMage     ("emage"     ).setHardness(2.0F).setResistance(5.0F);
		
	}

}

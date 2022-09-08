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
		ModBlocks.blockKnight     = new BlockKnight    ("knight"    ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockKnight2    = new BlockKnight2   ("knight2"   ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockArcher     = new BlockArcher    ("archer"    ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockArcher2    = new BlockArcher2   ("archer2"   ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockMerc       = new BlockMerc      ("merc"      ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockMercArcher = new BlockMercArcher("mercarcher").setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockMage       = new BlockMage      ("mage"      ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockHealer     = new BlockHealer    ("healer"    ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockEKnight    = new BlockEKnight   ("eknight"   ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockEArcher    = new BlockEArcher   ("earcher"   ).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.blockEMage      = new BlockEMage     ("emage"     ).setHardness(2.0F).setResistance(5.0F);
		
	}

}

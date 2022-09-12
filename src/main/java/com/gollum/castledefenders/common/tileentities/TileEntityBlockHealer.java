package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockHealer extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockHealer() {
		super(ModCastleDefenders.MODID+":healer", ModCastleDefenders.config.maxSpawnHealer);
	}
}

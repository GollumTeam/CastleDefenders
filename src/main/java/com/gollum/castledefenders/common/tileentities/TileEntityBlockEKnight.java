package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockEKnight extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockEKnight() {
		super(ModCastleDefenders.MODID+":enemyknight", ModCastleDefenders.config.maxSpawnEKnight);
	}
}

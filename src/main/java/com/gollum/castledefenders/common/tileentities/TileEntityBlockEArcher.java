package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockEArcher extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockEArcher() {
		super(ModCastleDefenders.MODID+":earcher");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnEArcher;
	}
}

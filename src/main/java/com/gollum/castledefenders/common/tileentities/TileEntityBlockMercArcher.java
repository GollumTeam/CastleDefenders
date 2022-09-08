package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockMercArcher extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockMercArcher() {
		super("MercArcher");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnMercArcher;
	}
}

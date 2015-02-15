package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockEArcher extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockEArcher() {
		super("EnemyArcher");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnEArcher;
	}
}

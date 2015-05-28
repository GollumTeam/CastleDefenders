package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockEMage extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockEMage() {
		super("EnemyMage");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnEMage;
	}
}

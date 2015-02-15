package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockHealer extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockHealer() {
		super("Healer");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnHealer;
	}
}

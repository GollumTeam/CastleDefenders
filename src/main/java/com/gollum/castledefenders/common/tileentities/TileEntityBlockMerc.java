package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockMerc extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockMerc() {
		super("Merc");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnMerc;
	}
}

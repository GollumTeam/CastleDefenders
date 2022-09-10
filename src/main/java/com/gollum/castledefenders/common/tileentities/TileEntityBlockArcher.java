package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockArcher extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockArcher() {
		super(ModCastleDefenders.MODID+":archer");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnArcher;
	}
}

package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockArcher2 extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockArcher2() {
		super(ModCastleDefenders.MODID+":archer2");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnArcher2;
	}
}

package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockMerc extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockMerc() {
		super(ModCastleDefenders.MODID+":merc");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnMerc;
	}
}

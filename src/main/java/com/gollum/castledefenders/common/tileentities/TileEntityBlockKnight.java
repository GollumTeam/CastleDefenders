package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockKnight extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockKnight() {
		super(ModCastleDefenders.MODID+":knight");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnKnight;
	}
}

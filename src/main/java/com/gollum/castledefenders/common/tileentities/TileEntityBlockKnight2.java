package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockKnight2 extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockKnight2() {
		super(ModCastleDefenders.MODID+":knight2");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnKnight2;
	}
}

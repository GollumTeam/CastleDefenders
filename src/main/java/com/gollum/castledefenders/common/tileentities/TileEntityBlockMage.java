package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockMage extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockMage() {
		super(ModCastleDefenders.MODID+":mage", ModCastleDefenders.config.maxSpawnMage);
	}
}

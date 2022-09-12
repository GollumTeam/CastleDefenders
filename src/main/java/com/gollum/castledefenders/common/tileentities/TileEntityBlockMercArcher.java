package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;

public class TileEntityBlockMercArcher extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockMercArcher() {
		super(ModCastleDefenders.MODID+":mercarcher", ModCastleDefenders.config.maxSpawnMercArcher);
	}
}

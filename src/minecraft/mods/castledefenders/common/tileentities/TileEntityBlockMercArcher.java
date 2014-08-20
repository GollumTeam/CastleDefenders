package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockMercArcher extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockMercArcher() {
		super("MercArcher");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnMercArcher;
	}
}

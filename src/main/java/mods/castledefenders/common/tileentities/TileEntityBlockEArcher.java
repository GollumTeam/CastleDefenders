package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockEArcher extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockEArcher() {
		super("EnemyArcher");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnEArcher;
	}
}

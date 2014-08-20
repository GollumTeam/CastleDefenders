package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockEArcher extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockEArcher() {
		super("Enemy Archer");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnEArcher;
	}
}

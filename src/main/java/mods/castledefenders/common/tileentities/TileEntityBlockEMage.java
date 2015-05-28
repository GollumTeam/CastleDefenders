package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockEMage extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockEMage() {
		super("EnemyMage");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnEMage;
	}
}

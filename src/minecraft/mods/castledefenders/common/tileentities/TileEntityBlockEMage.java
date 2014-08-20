package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockEMage extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockEMage() {
		super("Enemy Mage");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnEMage;
	}
}

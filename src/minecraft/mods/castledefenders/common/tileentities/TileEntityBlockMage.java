package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockMage extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockMage() {
		super("Mage");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnMage;
	}
}

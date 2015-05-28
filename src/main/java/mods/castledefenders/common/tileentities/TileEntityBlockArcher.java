package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockArcher extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockArcher() {
		super("Archer");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnArcher;
	}
}

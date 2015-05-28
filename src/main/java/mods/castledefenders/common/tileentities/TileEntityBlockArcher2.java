package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockArcher2 extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockArcher2() {
		super("Archer2");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnArcher2;
	}
}

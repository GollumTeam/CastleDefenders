package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockKnight2 extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockKnight2() {
		super("Knight2");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnKnight2;
	}
}

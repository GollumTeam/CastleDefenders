package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockKnight extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockKnight() {
		super("Knight");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnKnight;
	}
}

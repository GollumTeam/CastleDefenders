package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockEKnight extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockEKnight() {
		super("Enemy Knight");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnEKnight;
	}
}

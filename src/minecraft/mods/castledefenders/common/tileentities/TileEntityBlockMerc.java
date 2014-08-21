package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockMerc extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockMerc() {
		super("Merc");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnMerc;
	}
}

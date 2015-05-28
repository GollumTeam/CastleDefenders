package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;

public class TileEntityBlockHealer extends TileEntityBlockCastleDefenders {
	
	/**
	 * Constructeur
	 */
	public TileEntityBlockHealer() {
		super("Healer");
		this.maxSpawn = ModCastleDefenders.config.maxSpawnHealer;
	}
}

package mods.castledefender.common.blocks;

import mods.castledefender.common.tileentities.TileEntityBlockArcher;
import mods.castledefender.common.tileentities.TileEntityBlockArcherM;
import mods.castledefender.common.tileentities.TileEntityBlockMerc;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockArcherM extends BlockCastleDefender {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockArcherM (int id) {
		super(id, Material.rock);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockArcherM();
	}

}

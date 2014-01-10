package mods.castledefender.common;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEMage extends BlockCastleDefender {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockEMage (int id) {
		super(id, Material.circuits);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return null;
	}

}

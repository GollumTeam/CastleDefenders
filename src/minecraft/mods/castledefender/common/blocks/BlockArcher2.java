package mods.castledefender.common.blocks;

import mods.castledefender.common.tileentities.TileEntityBlockArcher2;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockArcher2 extends BlockCastleDefender {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockArcher2 (int id) {
		super(id, Material.rock);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockArcher2();
	}
}

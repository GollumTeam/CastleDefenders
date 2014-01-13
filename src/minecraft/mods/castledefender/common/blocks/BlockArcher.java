package mods.castledefender.common.blocks;

import java.util.Random;

import mods.castledefender.common.tileentities.TileEntityBlockArcher;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockArcher extends BlockCastleDefenders {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockArcher (int id) {
		super(id, Material.rock);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockArcher();
	}
}

package mods.castledefenders.common.blocks;

import java.util.Random;

import mods.castledefenders.common.tileentities.TileEntityBlockMercArcher;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMercArcher extends BlockCastleDefenders {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockMercArcher (int id, String registerName) {
		super(id, registerName, Material.rock);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockMercArcher();
	}
	
	@Override
	public int idDropped(int metadata, Random random, int j) {
		return 0;
	}

}

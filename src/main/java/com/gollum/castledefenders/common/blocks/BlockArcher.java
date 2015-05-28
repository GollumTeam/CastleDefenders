package com.gollum.castledefenders.common.blocks;

import com.gollum.castledefenders.common.tileentities.TileEntityBlockArcher;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockArcher extends BlockCastleDefenders {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockArcher (int id, String registerName) {
		super(id, registerName, Material.rock);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockArcher();
	}
}

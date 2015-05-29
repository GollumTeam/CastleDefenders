package com.gollum.castledefenders.common.blocks;

import java.util.Random;

import com.gollum.castledefenders.common.tileentities.TileEntityBlockHealer;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHealer extends BlockCastleDefenders {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockHealer (int id, String registerName) {
		super(id, registerName, Material.rock);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockHealer();
	}
	
	@Override
	public int idDropped(int metadata, Random random, int j) {
		return 0;
	}
	
}

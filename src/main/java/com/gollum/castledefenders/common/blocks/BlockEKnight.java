package com.gollum.castledefenders.common.blocks;

import java.util.Random;

import com.gollum.castledefenders.common.tileentities.TileEntityBlockEKnight;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockEKnight extends BlockCastleDefenders {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockEKnight (String registerName) {
		super(registerName, Material.circuits);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityBlockEKnight();
	}
	
	/**
	 * Enleve les collisions
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state) {
		return null;
	}
	
	/**
	 * Enleve les collisions
	 */
	@Override
	public boolean isBlockNormalCube() {
		return false;
	}
	
	/**
	 * The type of render function that is called for this block
	 * Affiche en mode baton
	 */
	@Override
	public int getRenderType() {
		return 1;
	}
	
	/**
	 * Drops the block items with a specified chance of dropping the specified
	 * items
	 */
	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
		super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
		int var8 = 15 + world.rand.nextInt(15) + world.rand.nextInt(15);
		this.dropXpOnBlockBreak(world, pos, var8);
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
}

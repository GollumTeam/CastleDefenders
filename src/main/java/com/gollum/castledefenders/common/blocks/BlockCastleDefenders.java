package com.gollum.castledefenders.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import com.gollum.castledefenders.inits.ModCreativeTab;
import com.gollum.core.tools.helper.blocks.HBlockContainer;

public abstract class BlockCastleDefenders extends HBlockContainer {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockCastleDefenders (String registerName, Material material) {
		super(registerName, material);
		this.setCreativeTab(ModCreativeTab.CASTLE_DEFENDERS);
	}
	

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
}

package com.gollum.castledefenders.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
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
		this.setCreativeTab(ModCreativeTab.tabCastleDefenders);
	}
	
	/**
	 * only called by clickMiddleMouseButton , and passed to
	 * inventory.setCurrentItem (along with isCreative)
	 */
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
		return null;
	}

	@Override
	public int getRenderType() {
		return 3;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
}

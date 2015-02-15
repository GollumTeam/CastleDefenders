package com.gollum.castledefenders.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.core.tools.helper.blocks.HBlockContainer;

public abstract class BlockCastleDefenders extends HBlockContainer {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockCastleDefenders (String registerName, Material material) {
		super(registerName, material);
		this.setCreativeTab(ModCastleDefenders.tabCastleDefenders);
	}
	
	/**
	 * only called by clickMiddleMouseButton , and passed to
	 * inventory.setCurrentItem (along with isCreative)
	 */
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return null;
	}
	
	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube() {
		return false;
	}
	
}

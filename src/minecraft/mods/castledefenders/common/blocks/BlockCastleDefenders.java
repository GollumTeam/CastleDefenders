package mods.castledefenders.common.blocks;

import mods.castledefenders.ModCastleDefenders;
import mods.gollum.core.helper.blocks.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockCastleDefenders extends BlockContainer {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockCastleDefenders (int id, String registerName, Material material) {
		super(id, registerName, material);
		this.setCreativeTab(ModCastleDefenders.tabCastleDefenders);
	}
	
	/**
	 * only called by clickMiddleMouseButton , and passed to
	 * inventory.setCurrentItem (along with isCreative)
	 */
	@Override
	public int idPicked(World var1, int var2, int var3, int var4) {
		return 0;
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

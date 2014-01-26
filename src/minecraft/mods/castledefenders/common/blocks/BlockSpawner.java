package mods.castledefenders.common.blocks;

import mods.castledefenders.common.ModCastleDefenders;
import mods.castledefenders.common.tileentities.TileEntityBlockSpawner;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockSpawner extends BlockContainer {

	public BlockSpawner(int id) {
		super(id, Material.air);
		this.setCreativeTab(ModCastleDefenders.tabCastleDefenders);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockSpawner();
	}
	
	@Override
	public boolean isAirBlock(World world, int x, int y, int z) {
		return true;
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
	 * Enleve les collisions
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return null;
	}
	
	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType() {
		return -1;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock() {
		return false;
	}
	
}

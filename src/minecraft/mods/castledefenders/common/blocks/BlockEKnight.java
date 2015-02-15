package mods.castledefenders.common.blocks;

import mods.castledefenders.common.tileentities.TileEntityBlockEKnight;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return null;
	}
	
	/**
	 * Enleve les collisions
	 */
	@Override
	public boolean renderAsNormalBlock() {
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
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return null;
	}
	
	/**
	 * Drops the block items with a specified chance of dropping the specified
	 * items
	 */
	@Override
	public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6, int var7) {
		super.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, var6, var7);
		int var8 = 15 + var1.rand.nextInt(15) + var1.rand.nextInt(15);
		this.dropXpOnBlockBreak(var1, var2, var3, var4, var8);
	}
}

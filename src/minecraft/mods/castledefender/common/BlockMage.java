package mods.castledefender.common;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockMage extends BlockCastleDefender {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockMage (int id) {
		super(id, Material.circuits);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return null;
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
	
}

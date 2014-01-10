package mods.castledefender.common.blocks;

import java.util.Random;

import mods.castledefender.common.tileentities.TileEntityBlockEMage;
import mods.castledefender.common.tileentities.TileEntityBlockMerc;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockEMage extends BlockCastleDefender {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockEMage (int id) {
		super(id, Material.circuits);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockEMage();
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

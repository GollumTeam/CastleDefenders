package mods.castledefenders.common.blocks;

import java.util.Random;

import mods.castledefenders.common.tileentities.TileEntityBlockEMage;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEMage extends BlockCastleDefenders {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockEMage (int id, String registerName) {
		super(id, registerName, Material.rock);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockEMage();
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
	
	@Override
	public int idDropped(int metadata, Random random, int j) {
		return 0;
	}
}

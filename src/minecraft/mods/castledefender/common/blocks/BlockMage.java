package mods.castledefender.common.blocks;

import mods.castledefender.common.tileentities.TileEntityBlockMage;
import mods.castledefender.common.tileentities.TileEntityBlockMerc;
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
		return new TileEntityBlockMage();
	}
	
}

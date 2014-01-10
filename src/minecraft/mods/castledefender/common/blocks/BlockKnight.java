package mods.castledefender.common.blocks;

import mods.castledefender.common.tileentities.TileEntityBlockKnight;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockKnight extends BlockCastleDefender {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockKnight (int id) {
		super(id, Material.rock);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockKnight();
	}

}

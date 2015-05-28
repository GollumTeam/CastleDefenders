package mods.castledefenders.common.blocks;

import mods.castledefenders.common.tileentities.TileEntityBlockKnight2;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockKnight2 extends BlockCastleDefenders {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockKnight2 (int id, String registerName) {
		super(id, registerName, Material.rock);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockKnight2();
	}

}

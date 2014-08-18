package mods.castledefenders.common.blocks;

import mods.castledefenders.common.tileentities.TileEntityBlockArcher;
import mods.castledefenders.common.tileentities.TileEntityBlockMercArcher;
import mods.castledefenders.common.tileentities.TileEntityBlockMerc;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMercArcher extends BlockCastleDefenders {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockMercArcher (int id) {
		super(id, Material.rock);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockMercArcher();
	}

}

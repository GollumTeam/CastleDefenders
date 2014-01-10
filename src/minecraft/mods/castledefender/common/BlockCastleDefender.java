package mods.castledefender.common;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockCastleDefender extends BlockContainer {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockCastleDefender (int id, Material material) {
		super(id, material);
		this.setCreativeTab(ModCastleDefender.tabsCastleDefender);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void registerIcons(IconRegister register) {
		this.blockIcon = register.registerIcon("castledefender:" + this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1));
	}
	
}

package mods.castledefender.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockMerc extends BlockCastleDefender {
	
	protected Icon sides;
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockMerc (int id) {
		super(id, Material.rock);
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
	 * When this method is called, your block should register all the icons it
	 * needs with the given IconRegister. This is the only chance you get to
	 * register icons.
	 */
	public void registerIcons(IconRegister register) {
		this.sides  = register.registerIcon("castledefender:BlockMerc-top-bottom");
		super.registerIcons (register);
	}
	
	@Override
	public Icon getIcon(int face, int medtadata) {
		return face == 0 ? this.blockIcon : (face == 1 ? this.blockIcon : this.sides);
	}
	
}

package com.gollum.castledefenders.common.blocks;

import com.gollum.castledefenders.common.tileentities.TileEntityBlockMerc;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockMerc extends BlockCastleDefenders {
	
	protected IIcon sides;
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockMerc (String registerName) {
		super(registerName, Material.rock);
	}
	
	/**
	 * Creation de l'entite
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityBlockMerc();
	}
	
	/**
	 * When this method is called, your block should register all the icons it
	 * needs with the given IconRegister. This is the only chance you get to
	 * register icons.
	 */
	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.sides  = this.helper.loadTexture(register, "_side"); 
		super.registerBlockIcons (register);
	}
	
	@Override
	public IIcon getIcon(int face, int medtadata) {
		return face == 0 ?  this.blockIcon : (face == 1 ? this.blockIcon : this.sides);
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return null;
	}
	
	
}

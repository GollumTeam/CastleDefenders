package com.gollum.castledefenders.common.blocks;

import java.util.Random;

import com.gollum.castledefenders.common.tileentities.TileEntityBlockMerc;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockMerc extends BlockCastleDefenders {
	
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
	
	// TODO
//	@Override
//	public void registerBlockIcons(IIconRegister register) {
//		this.sides  = this.helper.loadTexture(register, "_side"); 
//		super.registerBlockIcons (register);
//	}
	
	// TODO
//	@Override
//	public IIcon getIcon(int face, int medtadata) {
//		return face == 0 ?  this.blockIcon : (face == 1 ? this.blockIcon : this.sides);
//	}
	
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	
	
}

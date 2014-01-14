package mods.castledefenders.common.blocks;

import mods.castledefenders.common.ModCastleDefenders;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockCastleDefenders extends BlockContainer {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public BlockCastleDefenders (int id, Material material) {
		super(id, material);
		this.setCreativeTab(ModCastleDefenders.tabCastleDefenders);
	}
	
	/**
	 * Renregistrement des textures
	 */
	@Override
	public void registerIcons(IconRegister register) {
		String texturePath = "castledefenders:" + this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1);
		ModCastleDefenders.log.info ("Register block texture : "+ texturePath);
		this.blockIcon = register.registerIcon(texturePath);
	}
	
	/**
	 * only called by clickMiddleMouseButton , and passed to
	 * inventory.setCurrentItem (along with isCreative)
	 */
	@Override
	public int idPicked(World var1, int var2, int var3, int var4) {
		return 0;
	}
	
	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube() {
		return false;
	}
	
}

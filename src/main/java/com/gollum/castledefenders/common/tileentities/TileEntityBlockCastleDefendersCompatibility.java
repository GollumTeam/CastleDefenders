package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.blocks.BlockArcher;
import com.gollum.castledefenders.common.blocks.BlockEArcher;
import com.gollum.castledefenders.common.blocks.BlockEKnight;
import com.gollum.castledefenders.common.blocks.BlockEMage;
import com.gollum.castledefenders.common.blocks.BlockKnight;
import com.gollum.castledefenders.common.blocks.BlockMage;
import com.gollum.castledefenders.common.blocks.BlockMerc;
import com.gollum.castledefenders.common.blocks.BlockMercArcher;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ITickable;

public class TileEntityBlockCastleDefendersCompatibility extends TileEntity implements ITickable {

	/**
	 * Constructeur
	 */
	public TileEntityBlockCastleDefendersCompatibility() {
		super();
	}
	
	@Override
	public void update() {
		
		IBlockState state = this.getWorld().getBlockState(this.pos);
		
		if (state != null) {
			Block block = state.getBlock();
			
			if (block instanceof BlockKnight    ) { this.getWorld().setTileEntity(this.pos, new TileEntityBlockKnight    ()); }
			if (block instanceof BlockArcher    ) { this.getWorld().setTileEntity(this.pos, new TileEntityBlockArcher    ()); }
			if (block instanceof BlockMage      ) { this.getWorld().setTileEntity(this.pos, new TileEntityBlockMage      ()); }
			if (block instanceof BlockEKnight   ) { this.getWorld().setTileEntity(this.pos, new TileEntityBlockEKnight   ()); }
			if (block instanceof BlockEArcher   ) { this.getWorld().setTileEntity(this.pos, new TileEntityBlockEArcher   ()); }
			if (block instanceof BlockEMage     ) { this.getWorld().setTileEntity(this.pos, new TileEntityBlockEMage     ()); }
			if (block instanceof BlockMerc      ) { this.getWorld().setTileEntity(this.pos, new TileEntityBlockMerc      ()); }
			if (block instanceof BlockMercArcher) { this.getWorld().setTileEntity(this.pos, new TileEntityBlockMercArcher()); }
		
			this.getWorld().markBlockForUpdate(this.pos);
		
			ModCastleDefenders.log.debug ("Replace old TileEntity");
		}
	}
	
}

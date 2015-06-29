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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityBlockCastleDefendersCompatibility extends TileEntity {

	/**
	 * Constructeur
	 */
	public TileEntityBlockCastleDefendersCompatibility() {
		super();
	}
	
	public void updateEntity() {
		
		Block block = this.getWorldObj().getBlock(this.xCoord, this.yCoord, this.zCoord);
		
		if (block instanceof BlockKnight    ) { this.getWorldObj().setTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockKnight    ()); }
		if (block instanceof BlockArcher    ) { this.getWorldObj().setTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockArcher    ()); }
		if (block instanceof BlockMage      ) { this.getWorldObj().setTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockMage      ()); }
		if (block instanceof BlockEKnight   ) { this.getWorldObj().setTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockEKnight   ()); }
		if (block instanceof BlockEArcher   ) { this.getWorldObj().setTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockEArcher   ()); }
		if (block instanceof BlockEMage     ) { this.getWorldObj().setTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockEMage     ()); }
		if (block instanceof BlockMerc      ) { this.getWorldObj().setTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockMerc      ()); }
		if (block instanceof BlockMercArcher) { this.getWorldObj().setTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockMercArcher()); }
		
		this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		
		ModCastleDefenders.log.debug ("Replace old TileEntity");
	}
	
}

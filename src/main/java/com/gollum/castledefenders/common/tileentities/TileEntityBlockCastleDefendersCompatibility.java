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

public abstract class TileEntityBlockCastleDefendersCompatibility extends TileEntity {
	
	public void updateEntity() {
		
		Block block = Block.blocksList[this.getWorldObj().getBlockId(this.xCoord, this.yCoord, this.zCoord)];

		if (block instanceof BlockKnight    ) { this.getWorldObj().setBlockTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockKnight    ()); }
		if (block instanceof BlockArcher    ) { this.getWorldObj().setBlockTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockArcher    ()); }
		if (block instanceof BlockMage      ) { this.getWorldObj().setBlockTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockMage      ()); }
		if (block instanceof BlockEKnight   ) { this.getWorldObj().setBlockTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockEKnight   ()); }
		if (block instanceof BlockEArcher   ) { this.getWorldObj().setBlockTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockEArcher   ()); }
		if (block instanceof BlockEMage     ) { this.getWorldObj().setBlockTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockEMage     ()); }
		if (block instanceof BlockMerc      ) { this.getWorldObj().setBlockTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockMerc      ()); }
		if (block instanceof BlockMercArcher) { this.getWorldObj().setBlockTileEntity(this.xCoord, this.yCoord, this.zCoord, new TileEntityBlockMercArcher()); }
		
		this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
}

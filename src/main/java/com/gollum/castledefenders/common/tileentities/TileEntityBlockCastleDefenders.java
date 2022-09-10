package com.gollum.castledefenders.common.tileentities;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.core.tools.registered.RegisteredObjects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

public abstract class TileEntityBlockCastleDefenders extends TileEntity implements ITickable {

	// Les données de l'entité
	public int delay = 20;
	protected int minSpawnDelay = 200;
	protected int maxSpawnDelay = 800;
	protected int maxSpawn = 0;
	
	// Le mob
	private String mobID;
	
	/**
	 * Constructeur
	 * 
	 * @param MobId
	 */
	public TileEntityBlockCastleDefenders(String mobID) {
		super();
		this.mobID = mobID;
	}

	/**
	 * Getter
	 * @return
	 */
	public String getMobID() {
		return this.mobID;
	}
	
	public boolean anyPlayerInRange() {
		return this.world.getClosestPlayer(
			(double) this.pos.getX() + 0.5D,
			(double) this.pos.getY() + 0.5D,
			(double) this.pos.getZ() + 0.5D,
			16.0D,
			false
		) != null;
	}

	private void updateDelay() {
		this.delay = this.minSpawnDelay + this.world.rand.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
	}
	
	@Override
	public void update() {
		
		if (this.anyPlayerInRange()) {
			
			if (!this.world.isRemote) {
				
				// Lance un timeout
				if (this.delay == -1) {
					this.updateDelay();
				}
				if (this.delay > 0) {
					--this.delay;
					return;
				}
				
				Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(this.mobID), this.world);
				
				// L'entity n'existe pas
				if (entity == null) {
					ModCastleDefenders.logger.warning("This mob "+this.mobID+" isn't  register");
					return;
				}

				int nbEntityArround = this.world.getEntitiesWithinAABB(entity.getClass(), new AxisAlignedBB((double)this.pos.getX(), (double)this.pos.getY(), (double)this.pos.getZ(), (double)(this.pos.getX() + 1), (double)(this.pos.getY() + 1), (double)(this.pos.getZ() + 1)).expand(12.0D, 4.0D, 12.0D)).size();
				
				//Le nombre d'entity est supérieur à 6 autour du block
				if (nbEntityArround >= this.maxSpawn) {
					this.updateDelay();
					return;
				}
				
				double x = (double)this.pos.getX() + 0.5D;
				double y = (double)(this.pos.getY() + 1);
				double z = (double)this.pos.getZ() + 0.5D;
				EntityLiving entityLiving = entity instanceof EntityLiving ? (EntityLiving)entity : null;
				entity.setLocationAndAngles(x, y, z, this.world.rand.nextFloat() * 360.0F, this.world.rand.nextFloat() * 360.0F);
				
				if (entityLiving == null || entityLiving.getCanSpawnHere()) {
					
					this.world.spawnEntity(entity);
					this.world.playSound(
						(EntityPlayer)null,
						this.pos.getX()+0.5f,                      // x           
						this.pos.getY()+0.5f,                      // y           
						this.pos.getZ()+0.5f,                      // z           
						SoundEvents.BLOCK_STONE_PLACE,             // sound       
						SoundCategory.BLOCKS,                      // category    
						0.5F,                                      // volume             
						this.world.rand.nextFloat() * 0.25F + 0.6F // pitch
					);
					
					if (entityLiving != null) {
						entityLiving.spawnExplosionParticle();
					}
					
					this.updateDelay();
				}
			}
		}
	}
	
	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound var1) {
		super.readFromNBT(var1);
		this.delay = var1.getShort("Delay");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound var1) {
		super.writeToNBT(var1);
		var1.setShort("Delay", (short) this.delay);
		return var1;
	}

}

package mods.castledefenders.common.tileentities;

import mods.castledefenders.ModCastleDefenders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public abstract class TileEntityBlockCastleDefenders extends TileEntity {

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
		return this.worldObj.getClosestPlayer((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D, 16.0D) != null;
	}

	private void updateDelay() {
		this.delay = this.minSpawnDelay + this.worldObj.rand.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
	}
	
	/**
	 * Allows the entity to update its state. Overridden in most subclasses,
	 * e.g. the mob spawner uses this to count ticks and creates a new spawn
	 * inside its implementation.
	 */
	public void updateEntity() {
		
		
		if (this.anyPlayerInRange()) {
			
			if (!this.worldObj.isRemote) {
				
				// Lance un timeout
				if (this.delay == -1) {
					this.updateDelay();
				}
				if (this.delay > 0) {
					--this.delay;
					return;
				}
				
				Entity entity = EntityList.createEntityByName(this.mobID, this.worldObj);
				
				// L'entity n'existe pas
				if (entity == null) {
					ModCastleDefenders.log.warning("This mob "+this.mobID+" isn't  register");
					return;
				}

				int nbEntityArround = this.worldObj.getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 1), (double)(this.zCoord + 1)).expand(12.0D, 4.0D, 12.0D)).size();
				
				//Le nombre d'entity est supérieur à 6 autour du block
				if (nbEntityArround >= this.maxSpawn) {
					this.updateDelay();
					return;
				}
				
				double x = (double)this.xCoord + 0.5D;
				double y = (double)(this.yCoord + 1);
				double z = (double)this.zCoord + 0.5D;
				EntityLiving entityLiving = entity instanceof EntityLiving ? (EntityLiving)entity : null;
				entity.setLocationAndAngles(x, y, z, this.worldObj.rand.nextFloat() * 360.0F, this.worldObj.rand.nextFloat() * 360.0F);
				
				if (entityLiving == null || entityLiving.getCanSpawnHere()) {
					
					this.worldObj.spawnEntityInWorld(entity);
					this.worldObj.playSoundEffect (this.xCoord, this.yCoord, this.zCoord, "dig.stone", 0.5F, this.worldObj.rand.nextFloat() * 0.25F + 0.6F);
					
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
	public void writeToNBT(NBTTagCompound var1) {
		super.writeToNBT(var1);
		var1.setShort("Delay", (short) this.delay);
	}

}

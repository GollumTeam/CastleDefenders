package mods.castledefender.common.tileentities;

import java.util.Iterator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public abstract class TileEntityBlockCastleDefender extends TileEntity {

	// Les données de l'entité
	public int delay = -1;
	private int minSpawnDelay = 200;
	private int maxSpawnDelay = 800;
	private int spawnCount = 4;
	private NBTTagCompound spawnerTags = null;
	
	// Le mob
	private String mobID;
	private Entity spawnedMob;
	
	public double yaw;
	public double yaw2 = 0.0D;
	
	/**
	 * Constructeur
	 * 
	 * @param MobId
	 */
	public TileEntityBlockCastleDefender(String mobID) {
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
			
			if (this.worldObj.isRemote) {
				
				// Pk 3 je ne sais pas ???? Code d'origine. Surment forcer un vrai random
				double var10000 = (double)((float)this.xCoord + this.worldObj.rand.nextFloat());
				var10000 = (double)((float)this.yCoord + this.worldObj.rand.nextFloat());
				var10000 = (double)((float)this.zCoord + this.worldObj.rand.nextFloat());
				
				this.yaw2 = this.yaw % 360.0D;
				this.yaw += 4.545454502105713D;
				
			} else {
				
				// Lance un timeout
				if (this.delay == -1) {
					this.updateDelay();
				}
				if (this.delay > 0) {
					--this.delay;
					return;
				}
				
				for (int var1 = 0; var1 < this.spawnCount; ++var1) {
					Entity entity = EntityList.createEntityByName(this.mobID, this.worldObj);
					
					// L'entity n'existe pas
					if (entity == null) {
						return;
					}

					int var3 = this.worldObj.getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getAABBPool().getAABB((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 1), (double)(this.zCoord + 1)).expand(8.0D, 4.0D, 8.0D)).size();

					if (var3 >= 6) {
						this.updateDelay();
						return;
					}
					
					double var4 = (double)this.xCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0D;
					double var6 = (double)(this.yCoord + this.worldObj.rand.nextInt(3) - 1);
					double var8 = (double)this.zCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0D;
					EntityLiving var10 = entity instanceof EntityLiving ? (EntityLiving)entity : null;
					entity.setLocationAndAngles(var4, var6, var8, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
					
					if (var10 == null || var10.getCanSpawnHere())
					{
						this.writeNBTTagsTo(entity);
						this.worldObj.spawnEntityInWorld(entity);
						this.worldObj.playAuxSFX(2004, this.xCoord, this.yCoord, this.zCoord, 0);
						
						if (var10 != null)
						{
							var10.spawnExplosionParticle();
						}
						
						this.updateDelay();
					}
				}
			}
		}
	}
	
	public void writeNBTTagsTo(Entity entity) {
		if (this.spawnerTags != null) {
			NBTTagCompound nbtag = new NBTTagCompound();
			entity.readFromNBT(nbtag);
			
			Iterator var3 = this.spawnerTags.getTags().iterator();

			while (var3.hasNext()) {
				NBTBase var4 = (NBTBase) var3.next();
				nbtag.setTag(var4.getName(), var4.copy());
			}

			entity.readFromNBT(nbtag);
		}
	}
	
	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound var1) {
		super.readFromNBT(var1);
		this.mobID = var1.getString("EntityId");
		this.delay = var1.getShort("Delay");

		if (var1.hasKey("SpawnData")) {
			this.spawnerTags = var1.getCompoundTag("SpawnData");
		} else {
			this.spawnerTags = null;
		}

		if (var1.hasKey("MinSpawnDelay")) {
			this.minSpawnDelay = var1.getShort("MinSpawnDelay");
			this.maxSpawnDelay = var1.getShort("MaxSpawnDelay");
			this.spawnCount = var1.getShort("SpawnCount");
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound var1) {
		super.writeToNBT(var1);
		var1.setString("EntityId", this.mobID);
		var1.setShort("Delay", (short) this.delay);
		var1.setShort("MinSpawnDelay", (short) this.minSpawnDelay);
		var1.setShort("MaxSpawnDelay", (short) this.maxSpawnDelay);
		var1.setShort("SpawnCount", (short) this.spawnCount);

		if (this.spawnerTags != null) {
			var1.setCompoundTag("SpawnData", this.spawnerTags);
		}
	}

}

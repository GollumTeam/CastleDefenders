package mods.castledefenders.common.entities;

import java.util.List;

import mods.castledefenders.ModCastleDefenders;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.gollum.core.common.config.type.ItemStackConfigType;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class EntityHealer extends EntityMerc {
	
	private final static int maxCouldDownMusic = 300;
	
	int couldDown = 0;
	int couldDownMusic = maxCouldDownMusic;
	
	public EntityHealer(World world) {
		
		super(world);
		this.blockSpawnId       = ModCastleDefenders.blockHealer.blockID;
		this.defaultHeldItem    = new ItemStack(Item.book, 1);
		
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.healerCapacities; }
	
	/**
	 * @return les capacitées du mod
	 */
	protected ItemStackConfigType[] getCost () { return ModCastleDefenders.config.healerCost; }
	
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if (this.couldDown > this.getTimeRange()) {
			this.healEntitiesNearby();
		} else {
			this.couldDown++;
		}
		
		if (this.couldDownMusic <= maxCouldDownMusic) {
			this.couldDownMusic++;
		}
		
	}
	
	private void healEntitiesNearby() {
		
		List<EntityPlayer> entitiesNearby = this.worldObj.getEntitiesWithinAABB (
			EntityPlayer.class, AxisAlignedBB.getBoundingBox(
				this.posX - this.getFollowRange(), this.posY - this.getFollowRange(), this.posZ - this.getFollowRange(), 
				this.posX + this.getFollowRange(), this.posY + this.getFollowRange(), this.posZ + this.getFollowRange()
			)
		);
		
		for (EntityPlayer player: entitiesNearby) {
			if (this.isOwner(player)) {
				
				if (player.getHealth() != player.getMaxHealth()) {
					
					if (this.couldDownMusic > maxCouldDownMusic) {
						this.couldDownMusic = 0;
						this.worldObj.playSoundAtEntity (this, ModCastleDefenders.MODID.toLowerCase()+":monk", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.2F);
					}
					
					ModCastleDefenders.log.debug("Heal player");
					player.heal(ModCastleDefenders.config.healPointByTimeRange);
					this.couldDown = 0;
				}
			}
		}
	}
}

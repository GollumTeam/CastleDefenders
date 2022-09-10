package com.gollum.castledefenders.common.entities;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.core.common.config.type.ItemStackConfigType;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;
import com.gollum.core.tools.registered.RegisteredObjects;

public class EntityHealer extends EntityMerc {
	
	private final static int maxCouldDownMusic = 300;
	
	int couldDown = 0;
	int couldDownMusic = maxCouldDownMusic;
	
	public EntityHealer(World world) {
		
		super(world);
		this.blockSpawn         = ModBlocks.HEALER;
		this.defaultHeldItem    = new ItemStack(Items.BOOK, 1);
		
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
		
		List<EntityPlayer> entitiesNearby = this.world.getEntitiesWithinAABB (
			EntityPlayer.class, new AxisAlignedBB(
				this.posX - this.getFollowRange(), this.posY - this.getFollowRange(), this.posZ - this.getFollowRange(), 
				this.posX + this.getFollowRange(), this.posY + this.getFollowRange(), this.posZ + this.getFollowRange()
			)
		);
		
		for (EntityPlayer player: entitiesNearby) {
			if (this.isOwner(player)) {
				
				if (player.getHealth() != player.getMaxHealth()) {
					
					if (this.couldDownMusic > maxCouldDownMusic) {
						this.couldDownMusic = 0;
						this.world.playSound(
							this.posX, 
							this.posY, 
							this.posZ, 
							RegisteredObjects.instance().getSoundEvent(ModCastleDefenders.MODID.toLowerCase()+":monk"),
							SoundCategory.NEUTRAL,
							0.5F,
							this.world.rand.nextFloat() * 0.1F + 0.2F,
							false
						);
					}
					
					ModCastleDefenders.logger.debug("Heal player");
					player.heal(ModCastleDefenders.config.healPointByTimeRange);
					this.couldDown = 0;
				}
			}
		}
	}
}

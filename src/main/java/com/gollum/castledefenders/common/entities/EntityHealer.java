package com.gollum.castledefenders.common.entities;

import java.util.List;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.castledefenders.inits.ModSounds;
import com.gollum.core.common.config.type.ItemStackConfigType;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityHealer extends EntityMercenary {
	
	private final static int maxCouldDownMusic = 300;
	
	int couldDown = 0;
	int couldDownMusic = maxCouldDownMusic;
	
	public EntityHealer(World world) {
		
		super(world);
		this.blockSpawn         = ModBlocks.HEALER;
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOOK, 1));
		
	}
	
    protected void initEntityAI() {
    	super.initEntityAI();

		this.tasks.addTask(this.nextIdTask (), new EntityAIWander(this, this.getMaxSpeed()));
		this.tasks.addTask(this.nextIdTask (), new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(this.nextIdTask (), new EntityAILookIdle(this));
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
						this.playSound(
							ModSounds.MONK,
							0.1F,
							this.world.rand.nextFloat() * 0.1F + 0.3F
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

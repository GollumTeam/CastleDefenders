package com.gollum.castledefenders.common.entities;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.aientities.EntityAIDistanceAttack;
import com.gollum.castledefenders.common.aientities.EntityAIDistanceAttack.PROJECTILE_TYPE;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.castledefenders.inits.ModItems;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class EntityEMage extends EntityEnemy {
	
	public EntityEMage(World world) {
		super(world);
		this.blockSpawn = ModBlocks.EMAGE;
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModBlocks.EKNIGHT, 1));
	}
    protected void initEntityAI() {
    	super.initEntityAI();
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), PROJECTILE_TYPE.FIRE));
    }
	
	/**
     * Determines if an entity can be despawned, used on idle far away entities
     */
	protected boolean canDespawn() {
		return false;
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.eMageCapacities; }
}

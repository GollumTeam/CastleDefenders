package com.gollum.castledefenders.common.entities;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityKnight extends EntityDefender {
	
	public EntityKnight(World world) {
		super(world);
		this.blockSpawn = ModBlocks.KNIGHT;
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD, 1));
	}
	
    protected void initEntityAI() {
    	super.initEntityAI();
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackMelee(this, this.getMoveSpeed (), true));
    }
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.knightCapacities; }
	
	
}

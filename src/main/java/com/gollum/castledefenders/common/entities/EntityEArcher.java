package com.gollum.castledefenders.common.entities;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.aientities.EntityAIDistanceAttack;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class EntityEArcher extends EntityEnemy {
	
	public EntityEArcher(World world) {
		super(world);
		this.blockSpawn      = ModBlocks.EARCHER;
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW, 1));
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), EntityAIDistanceAttack.TYPE_ARROW));
	}
	
	@Override
	protected boolean canDespawn() {
		return false;
	}
	
	@Override
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.eArcherCapacities; }
}

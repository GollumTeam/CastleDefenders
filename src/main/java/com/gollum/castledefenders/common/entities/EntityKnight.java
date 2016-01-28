package com.gollum.castledefenders.common.entities;

import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class EntityKnight extends EntityDefender {
	
	public EntityKnight(World world) {
		
		super(world);
		this.blockSpawn      = ModBlocks.blockKnight;
		this.defaultHeldItem = new ItemStack(Items.iron_sword, 1);
		
		// TODO
//		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, IMob.class, this.getMoveSpeed (), true));
//		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityCreeper.class, this.getMoveSpeed (), true));
//		
//		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget(this, IMob.class         , 0, false, true));
//		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget(this, EntityCreeper.class, 0, false, true));
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.knightCapacities; }
	
	
}

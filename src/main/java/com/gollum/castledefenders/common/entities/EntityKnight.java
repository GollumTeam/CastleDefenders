package com.gollum.castledefenders.common.entities;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;
import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityKnight extends EntityDefender {
	
	public EntityKnight(World world) {
		
		super(world);
		this.blockSpawn      = ModBlocks.blockKnight;
		this.defaultHeldItem = new ItemStack(Items.iron_sword, 1);
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, this.getMoveSpeed (), true));
		
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget (this, EntityLiving.class, 0, false, true, new Predicate<Entity>() {
			public boolean apply(Entity entity) {
				return 
					entity instanceof EntityMob ||
					entity instanceof EntitySlime ||
					entity instanceof EntityGolem ||
					entity instanceof EntityGhast
				;
			}
		}));
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.knightCapacities; }
	
	
}

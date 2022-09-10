package com.gollum.castledefenders.common.entities;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;
import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityKnight extends EntityDefender {
	
	public EntityKnight(World world) {
		
		super(world);
		this.blockSpawn      = ModBlocks.KNIGHT;
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD, 1));
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackMelee(this, this.getMoveSpeed (), true));
		
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget (this, EntityLiving.class, 0, false, true, new Predicate<Entity>() {
			public boolean apply(Entity entity) {
				return 
					entity instanceof IMob
				;
			}
		}));
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.knightCapacities; }
	
	
}

package com.gollum.castledefenders.common.entities;

import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.core.common.config.type.ItemStackConfigType;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class EntityMerc extends EntityMercenary {
	
	public EntityMerc(World world) {
		
		super(world);
		this.blockSpawn      = ModBlocks.blockMerc;
		this.defaultHeldItem = new ItemStack(Items.iron_sword, 1);
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, this.getMaxSpeed(), true));
		this.tasks.addTask(this.nextIdTask (), new EntityAIWander(this, this.getMaxSpeed()));
		this.tasks.addTask(this.nextIdTask (), new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(this.nextIdTask (), new EntityAILookIdle(this));
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.mercCapacities; }
	/**
	 * @return les capacitées du mod
	 */
	protected ItemStackConfigType[] getCost () { return ModCastleDefenders.config.mercenaryCost; }
	
}

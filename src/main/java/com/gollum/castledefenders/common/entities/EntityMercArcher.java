package com.gollum.castledefenders.common.entities;

import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.aientities.EntityAIDistanceAttack;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.core.common.config.type.ItemStackConfigType;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class EntityMercArcher extends EntityMercenary {
	
	public EntityMercArcher(World world) {
		
		super(world);
		this.blockSpawnId    = ModBlocks.blockMercArcher.blockID;
		this.defaultHeldItem = new ItemStack(Item.bow, 1);
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), EntityAIDistanceAttack.TYPE_ARROW));
		this.tasks.addTask(this.nextIdTask (), new EntityAIWander(this, this.getMaxSpeed()));
		this.tasks.addTask(this.nextIdTask (), new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(this.nextIdTask (), new EntityAILookIdle(this));
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.mercArcherCapacities; }
	/**
	 * @return les capacitées du mod
	 */
	protected ItemStackConfigType[] getCost () { return ModCastleDefenders.config.mercArcherCost; }
	
}

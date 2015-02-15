package com.gollum.castledefenders.common.entities;

import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public class EntityEKnight extends EntityEnemy {
	
	public EntityEKnight(World world) {
		super(world);
		this.blockSpawn      = ModCastleDefenders.blockEKnight;
		this.defaultHeldItem = new ItemStack(Items.iron_sword, 1);
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityPlayer.class, this.getMoveSpeed(), true));
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityDefender.class, this.getMoveSpeed(), true));
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityMerc.class, this.getMoveSpeed(), true));
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityMercArcher.class, this.getMoveSpeed(), true));
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfigType getCapacities () { return ModCastleDefenders.config.eKnightCapacities; }
	
}

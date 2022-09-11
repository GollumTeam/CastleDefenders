package com.gollum.castledefenders.common.entities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.gollum.castledefenders.inits.ModItems;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

public interface ICastleEntity {
	
	/**
	 * @return Vitesse du mod
	 */
	double getMoveSpeed ();
	/**
	 * @return Point de vie du mod
	 */
	double getMaxHealt ();
	/**
	 * @return Point de vie du mod
	 */
	double getAttackStrength();
	/**
	 * @return Zone de detection du mod
	 */
	double getFollowRange();
	/**
	 * @return Vitesse de tir du mod
	 */
	double getTimeRange();
	
}

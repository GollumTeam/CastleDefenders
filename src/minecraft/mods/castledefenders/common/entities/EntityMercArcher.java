package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import mods.castledefenders.common.aientities.EntityAIDistanceAttack;
import mods.gollum.core.config.container.ItemStackConfig;
import mods.gollum.core.config.container.MobCapacitiesConfig;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMercArcher extends EntityMercenary {
	
	public EntityMercArcher(World world) {
		
		super(world);
		this.blockSpawnId    = ModCastleDefenders.blockMercArcherID;
		this.defaultHeldItem = new ItemStack(Item.bow, 1);
		
		this.tasks.addTask(this.nextIdTask (), new EntityAISwimming(this));
		this.tasks.addTask(this.nextIdTask (), this.aiSit);
		this.tasks.addTask(this.nextIdTask (), new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(this.nextIdTask (), new EntityAIFollowOwner(this, this.getMaxSpeed(), 10.0F, 2.0F));
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), EntityAIDistanceAttack.TYPE_ARROW));
		this.tasks.addTask(this.nextIdTask (), new EntityAIWander(this, this.getMaxSpeed()));
		this.tasks.addTask(this.nextIdTask (), new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(this.nextIdTask (), new EntityAILookIdle(this));
		
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAITargetNonTamed(this, IMob.class, 200, false));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget(this, IMob.class, 0, true));
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfig getCapacities () { return ModCastleDefenders.mercArcherCapacities; }
	/**
	 * @return les capacitées du mod
	 */
	protected ItemStackConfig[] getCost () { return ModCastleDefenders.mercArcherCost; }
	
}

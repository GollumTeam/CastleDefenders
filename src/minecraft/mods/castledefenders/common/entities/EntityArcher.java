package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import mods.castledefenders.common.aientities.EntityAIDistanceAttack;
import mods.gollum.core.config.container.ItemStackConfig;
import mods.gollum.core.config.container.MobCapacitiesConfig;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityArcher extends EntityDefender {

	public EntityArcher(World world) {

		super(world);
		this.blockSpawnId    = ModCastleDefenders.blockArcherID;
		this.defaultHeldItem = new ItemStack(Item.bow, 1);
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), EntityAIDistanceAttack.TYPE_ARROW));
		
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget (this, IMob.class, 0, true));
	}
	
	/**
     * Determines if an entity can be despawned, used on idle far away entities
     */
	protected boolean canDespawn() {
		return false;
	}
	
	/**
	 * @return les capacitées du mod
	 */
	protected MobCapacitiesConfig getCapacities () { return ModCastleDefenders.archerCapacities; }
	
}

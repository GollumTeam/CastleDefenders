package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import mods.castledefenders.common.aientities.EntityAIDistanceAttack;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;

public class EntityMage extends EntityDefender {

	public EntityMage(World world) {

		super(world);
		this.blockSpawnId = ModCastleDefenders.blockMageID;
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), EntityAIDistanceAttack.TYPE_FIRE));
		
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget (this, IMob.class, 0, true));
	}
	
	/**
	 * @return Vitesse de tir du mod
	 */
	public double getTimeRange() { return ModCastleDefenders.mageTimeRange; }
	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return ModCastleDefenders.mageFollowRange; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return ModCastleDefenders.mageMoveSpeed; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return ModCastleDefenders.mageHealt; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return ModCastleDefenders.mageAttackStrength; }
	
	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	protected boolean canDespawn() {
		return false;
	}
	
}

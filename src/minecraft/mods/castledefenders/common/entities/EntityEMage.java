package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import mods.castledefenders.common.aientities.EntityAIDistanceAttack;
import net.minecraft.world.World;

public class EntityEMage extends EntityEnemy {
	
	
	public EntityEMage(World world) {
		super(world);
		this.blockSpawnId = ModCastleDefenders.blockEMageID;
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), EntityAIDistanceAttack.TYPE_FIRE));
		
	}
	
	/**
	 * @return Vitesse de tir du mod
	 */
	public double getTimeRange() { return ModCastleDefenders.eMageTimeRange; }
	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return ModCastleDefenders.eMageFollowRange; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return ModCastleDefenders.eMageMoveSpeed; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return ModCastleDefenders.eMageHealt; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return ModCastleDefenders.eMageAttackStrength; }
	
	/**
     * Determines if an entity can be despawned, used on idle far away entities
     */
	protected boolean canDespawn() {
		return false;
	}
}

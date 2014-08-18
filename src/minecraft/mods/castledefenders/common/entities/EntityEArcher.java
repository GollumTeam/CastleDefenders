package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import mods.castledefenders.common.aientities.EntityAIDistanceAttack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityEArcher extends EntityEnemy {
	
	
	public EntityEArcher(World world) {
		super(world);
		this.blockSpawnId       = ModCastleDefenders.blockEArcherID;
		this.defaultHeldItem    = new ItemStack(Item.bow, 1);
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), EntityAIDistanceAttack.TYPE_ARROW));
	}
	
	/**
	 * @return Vitesse de tir du mod
	 */
	public double getTimeRange() { return ModCastleDefenders.eArcherTimeRange; }
	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return ModCastleDefenders.eArcherFollowRange; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return ModCastleDefenders.eArcherMoveSpeed; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return ModCastleDefenders.eArcherHealt; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return ModCastleDefenders.eArcherAttackStrength; }
	
	/**
     * Determines if an entity can be despawned, used on idle far away entities
     */
	protected boolean canDespawn() {
		return false;
	}
}

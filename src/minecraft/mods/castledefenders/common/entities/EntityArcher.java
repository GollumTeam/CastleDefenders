package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import mods.castledefenders.common.aientities.EntityAIDistanceAttack;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityArcher extends EntityDefender {

	public EntityArcher(World world) {

		super(world);
		this.blockSpawnId = ModCastleDefenders.blockArcherID;
		this.defaultHeldItem = new ItemStack(Item.bow, 1);
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIDistanceAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), EntityAIDistanceAttack.TYPE_ARROW));
		
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget (this, IMob.class, 0, true));
	}
	
	/**
	 * @return Vitesse de tir du mod
	 */
	public double getTimeRange() { return ModCastleDefenders.archerTimeRange; }
	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return ModCastleDefenders.archerFollowRange; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return ModCastleDefenders.archerMoveSpeed; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return ModCastleDefenders.archerHealt; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return ModCastleDefenders.archerAttackStrength; }
	
	/**
     * Determines if an entity can be despawned, used on idle far away entities
     */
	protected boolean canDespawn() {
		return false;
	}
	
}

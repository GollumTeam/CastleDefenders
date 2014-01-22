package mods.castledefenders.common.entities;

import mods.castledefenders.common.ModCastleDefenders;
import mods.castledefenders.common.aientities.EntityAIArcherArrowAttack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityEMage extends EntityEnemy {
	
	
	public EntityEMage(World world) {
		super(world);
		this.blockSpawnId       = ModCastleDefenders.blockEMageID;
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIArcherArrowAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), 1));
	}
	
	/**
	 * @return Vitesse de tir du mod
	 */
	public double getTimeRange() { return 6.D; }
	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return 20.D; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return 0.D; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return 20.0D; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return 6; }
	
	/**
     * Determines if an entity can be despawned, used on idle far away entities
     */
	protected boolean canDespawn() {
		return false;
	}
}

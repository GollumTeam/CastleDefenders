package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import net.minecraft.world.World;

public class EntityArcher2 extends EntityArcher {

	public EntityArcher2(World world) {
		super(world);
		this.blockSpawnId = ModCastleDefenders.blockArcher2ID;
	}
	
	/**
	 * @return Vitesse de tir du mod
	 */
	public double getTimeRange() { return ModCastleDefenders.archer2TimeRange; }
	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return ModCastleDefenders.archer2FollowRange; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return ModCastleDefenders.archer2MoveSpeed; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return ModCastleDefenders.archer2Healt; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return ModCastleDefenders.archer2AttackStrength; }
	
}

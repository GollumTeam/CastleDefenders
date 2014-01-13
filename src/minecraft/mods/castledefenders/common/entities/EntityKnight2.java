package mods.castledefenders.common.entities;

import net.minecraft.world.World;

public class EntityKnight2 extends EntityKnight {

	public EntityKnight2(World world) {
		super(world);
	}
	
	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return 20.D; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return 0.6D; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return 30.0D; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return 7; }
	
}

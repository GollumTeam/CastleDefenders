package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityKnight2 extends EntityKnight {

	public EntityKnight2(World world) {
		super(world);
		this.blockSpawnId       = ModCastleDefenders.blockKnight2ID;
		this.defaultHeldItem    = new ItemStack(Item.swordDiamond, 1);
	}
	
	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return ModCastleDefenders.knight2FollowRange; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return ModCastleDefenders.knight2MoveSpeed; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return ModCastleDefenders.knight2Healt; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return ModCastleDefenders.knight2AttackStrength; }
	
}

package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EArcher extends EntityEnemy {
	
	
	public EArcher(World world) {
		super(world);
		this.blockSpawnId       = ModCastleDefenders.blockEKnightID;
		this.defaultHeldItem    = new ItemStack(Item.swordIron, 1);
		
	}

	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return 16.D; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return 0.5D; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return 25.0D; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return 6; }
	
}

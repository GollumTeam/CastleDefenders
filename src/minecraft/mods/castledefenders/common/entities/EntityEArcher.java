package mods.castledefenders.common.entities;

import mods.castledefenders.common.ModCastleDefenders;
import mods.castledefenders.common.aientities.EntityAIArcherArrowAttack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityEArcher extends EntityEnemy {
	
	
	public EntityEArcher(World world) {
		super(world);
		this.blockSpawnId       = ModCastleDefenders.blockEArcherID;
		this.defaultHeldItem    = new ItemStack(Item.bow, 1);
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIArcherArrowAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), 1));
	}
	
	/**
	 * @return Vitesse de tir du mod
	 */
	public double getTimeRange() { return 6.D; }
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

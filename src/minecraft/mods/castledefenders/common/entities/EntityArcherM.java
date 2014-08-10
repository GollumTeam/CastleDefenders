package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import mods.castledefenders.common.aientities.EntityAIArcherArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityArcherM extends EntityMercenary {
	
	public EntityArcherM(World world) {
		
		super(world);
		this.blockSpawnId = ModCastleDefenders.blockArcherMID;
		this.defaultHeldItem = new ItemStack(Item.bow, 1);
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIArcherArrowAttack (this, this.getMoveSpeed (), this.getFollowRange (), this.getTimeRange (), 1));
		
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget (this, IMob.class, 0, true));
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
	public double getHealt () { return 15.0D; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return 4; }
	
	
}

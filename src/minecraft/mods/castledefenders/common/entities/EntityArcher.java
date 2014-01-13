package mods.castledefenders.common.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;

public class EntityArcher extends EntityDefender {
	
	public EntityArcher(World world) {
		
		super(world);
		
//		this.tasks.addTask(1, new EntityArcherArrowAttack(this, this.moveSpeed, 1, 30));
//		this.tasks.addTask(2, new EntityAIWander(this, this.moveSpeed));
//		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, IMob.class, 30.0F, 0, true));
	}
	

	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return 20.D; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return 0.3D; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return 25.0D; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return 6; }
	
}

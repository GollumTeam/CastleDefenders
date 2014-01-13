package mods.castledefender.common.entities;

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
		
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed) .setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)     .setAttribute(this.getHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)   .setAttribute(this.getFollowRange ());
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)  .setAttribute(this.getAttackStrength ());
		
//		this.tasks.addTask(1, new EntityArcherArrowAttack(this, this.moveSpeed, 1, 30));
//		this.tasks.addTask(2, new EntityAIWander(this, this.moveSpeed));
//		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, IMob.class, 30.0F, 0, true));
	}
	
	
	
}

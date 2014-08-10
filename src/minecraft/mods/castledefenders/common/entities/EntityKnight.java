package mods.castledefenders.common.entities;

import java.util.List;

import mods.castledefenders.ModCastleDefenders;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityKnight extends EntityDefender {
	
	public EntityKnight(World world) {
		
		super(world);
		this.blockSpawnId       = ModCastleDefenders.blockKnightID;
		this.defaultHeldItem    = new ItemStack(Item.swordIron, 1);
		
		this.getNavigator().setBreakDoors(true);
		
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, IMob.class, this.getMoveSpeed (), true));
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityCreeper.class, this.getMoveSpeed (), true));
		
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget(this, IMob.class         , 0, false, true));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget(this, EntityCreeper.class, 0, false, true));
	}
	
	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return 20.D; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return 0.55D; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return 15.0D; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return 4; }
	
	
}

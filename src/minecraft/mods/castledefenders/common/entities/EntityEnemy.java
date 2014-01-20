package mods.castledefenders.common.entities;

import java.util.List;

import mods.castledefenders.common.ModCastleDefenders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityEnemy extends EntityMob {
	
	public ItemStack defaultHeldItem = null;
	public int blockSpawnId;
	private int idTask = 0;
	private int idTargetTask = 0;


	public EntityEnemy(World world) {
		super(world);
		
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)    .setAttribute(this.getHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)  .setAttribute(this.getFollowRange ());
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage) .setAttribute(this.getAttackStrength ());
		
		this.getNavigator().setBreakDoors(true);
		this.tasks.addTask(this.nextIdTask (), new EntityAISwimming(this));
		this.tasks.addTask(this.nextIdTask (), new EntityAIWander(this, this.getMoveSpeed()));
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityPlayer.class, this.getMoveSpeed(), true));
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityDefender.class, this.getMoveSpeed(), true));
//		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityMerc.class, this.getMoveSpeed(), true));
//		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityArcherM.class, this.getMoveSpeed(), true));
		this.targetTasks.addTask(this.nextIdTask (), new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.targetTasks.addTask(this.nextIdTask (), new EntityAINearestAttackableTarget(this, EntityDefender.class, 0, true));
//		this.targetTasks.addTask(this.nextIdTask (), new EntityAINearestAttackableTarget(this, EntityArcherM.class,  0, true));
//		this.targetTasks.addTask(this.nextIdTask (), new EntityAINearestAttackableTarget(this, EntityMerc.class,  0, true));

	}

	/**
	 * Next Id Task
	 * @return
	 */
	public int nextIdTask () {
		return this.idTask++;
	}
	
	/**
	 * Next target Id Task
	 * @return
	 */
	public int nextIdTargetTask () {
		return this.idTargetTask++;
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
	
	
	/**
	 * Affecte les attributs de l'entity
	 */
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes ();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed) .setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)     .setAttribute(this.getHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)   .setAttribute(this.getFollowRange ());
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)  .setAttribute(this.getAttackStrength ());
	}
	
	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled() {
		return true;
	}
	
	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	protected int getDropItemId() {
		return 0;
	}
	
	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.boundingBox.minY);
		int z = MathHelper.floor_double(this.posZ);
		
		int idBlock = this.worldObj.getBlockId(x, y - 1, z);
		

		List entityListBlockArround = this.worldObj.getEntitiesWithinAABB(
			this.getClass(), 
			AxisAlignedBB.getBoundingBox(
				this.posX,        this.posY,        this.posZ,
				this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D
			).expand(2.0D, 2.0D, 2.0D)
		);
		
		return
			idBlock == this.blockSpawnId &&
			this.worldObj.checkNoEntityCollision(this.boundingBox) &&
			this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 &&
			entityListBlockArround.isEmpty();
	}
	
	/**
	 * Returns the item that this EntityLiving is holding, if any.
	 */
	@Override
	public ItemStack getHeldItem() {
		
		if (this.defaultHeldItem == null) {
			return super.getHeldItem ();
		}
		
		return this.defaultHeldItem;
	}
	
}

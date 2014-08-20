package mods.castledefenders.common.entities;

import java.util.List;

import mods.gollum.core.config.container.ItemStackConfig;
import mods.gollum.core.config.container.MobCapacitiesConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityEnemy extends EntityMob {
	
	protected ItemStack defaultHeldItem = null;
	protected int blockSpawnId;
	private int idTask = 0;
	private int idTargetTask = 0;


	public EntityEnemy(World world) {
		super(world);
		
		this.setSize(1.1F, 1.8F);
		
		this.getNavigator().setBreakDoors(false); // Permet d'ouvrir les port
		this.getNavigator().setAvoidsWater(true); // Evite l'eau
		
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)    .setAttribute(this.getMaxHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)  .setAttribute(this.getFollowRange ());
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage) .setAttribute(this.getAttackStrength ());
		
		this.getNavigator().setBreakDoors(true);
		this.tasks.addTask(this.nextIdTask (), new EntityAISwimming(this));
		this.tasks.addTask(this.nextIdTask (), new EntityAIWander(this, this.getMoveSpeed()));
		this.targetTasks.addTask(this.nextIdTask (), new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.targetTasks.addTask(this.nextIdTask (), new EntityAINearestAttackableTarget(this, EntityDefender.class, 0, true));
		this.targetTasks.addTask(this.nextIdTask (), new EntityAINearestAttackableTarget(this, EntityMercArcher.class,  0, true));
		this.targetTasks.addTask(this.nextIdTask (), new EntityAINearestAttackableTarget(this, EntityMerc.class,  0, true));

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
	 * @return Vitesse du mod
	 */
	protected double getMoveSpeed () { return this.getCapacities ().moveSpeed; }
	/**
	 * @return Point de vie du mod
	 */
	protected double getMaxHealt () { return this.getCapacities ().maxHealt; }
	/**
	 * @return Point de vie du mod
	 */
	protected double getAttackStrength () { return this.getCapacities ().attackStrength; }
	/**
	 * @return Zone de detection du mod
	 */
	protected double getFollowRange () { return this.getCapacities ().followRange; }
	/**
	 * @return Vitesse de tir du mod
	 */
	protected double getTimeRange() { return this.getCapacities ().timeRange; }
	
	/**
	 * @return les capacitées du mod
	 */
	protected abstract MobCapacitiesConfig getCapacities ();
	
	
	/**
	 * Affecte les attributs de l'entity
	 */
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes ();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed) .setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)     .setAttribute(this.getMaxHealt ());
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
		int up1 = this.worldObj.getBlockId(x, y, z);
		int up2 = this.worldObj.getBlockId(x, y + 1, z);
		

		List entityListBlockArround = this.worldObj.getEntitiesWithinAABB(
			this.getClass(), 
			AxisAlignedBB.getBoundingBox(
				this.posX,        this.posY,        this.posZ,
				this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D
			).expand(2.0D, 2.0D, 2.0D)
		);
		
		boolean found = false;
		for (Object arroundEntity : entityListBlockArround) {
			if (arroundEntity.getClass() == this.getClass()) {
				found = true;
			}
		}
		
		return
			idBlock == this.blockSpawnId &&
			(up1 == 0 || !Block.blocksList[up1].isCollidable()) &&
			(up2 == 0 || !Block.blocksList[up2].isCollidable()) &&
			!found;
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

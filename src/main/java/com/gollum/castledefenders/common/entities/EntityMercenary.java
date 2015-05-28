package com.gollum.castledefenders.common.entities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.core.common.config.type.ItemStackConfigType;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class EntityMercenary extends EntityTameable {
	
	protected ItemStack defaultHeldItem = null;
	protected int blockSpawnId;
	private int idTask = 0;
	private int idTargetTask = 0;
	
	private ArrayList<String> ownerList = new ArrayList<String>();
	private int eating = -1;
	
	public EntityMercenary(World world) {
		super(world);
		
		this.setSize(1.1F, 1.8F);
		
		this.getNavigator().setBreakDoors(true); // Permet d'ouvrir les port
		this.getNavigator().setAvoidsWater(false); // Evite l'eau
		
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)    .setAttribute(this.getMaxHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)  .setAttribute(this.getFollowRange ());
		
		float follow = Math.min((float)(this.getFollowRange()-1), 10F);
		if (follow < 1.0F) {
			follow = 1.0F;
		}
		
		this.tasks.addTask(this.nextIdTask (), new EntityAISwimming(this));
		this.tasks.addTask(this.nextIdTask (), this.aiSit);
		this.tasks.addTask(this.nextIdTask (), new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(this.nextIdTask (), new EntityAIFollowOwner(this, this.getMaxSpeed(), follow, 1.0F));
		
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAITargetNonTamed(this, IMob.class, 200, false));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAINearestAttackableTarget(this, IMob.class, 0, true));
		
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
	protected double getMinSpeed () { return 0.D; }
	/**
	 * @return Vitesse du mod
	 */
	protected double getMaxSpeed () { return this.getCapacities ().moveSpeed; }
	/**
	 * @return Vitesse du mod
	 */
	protected double getMoveSpeed () { return this.isTamed() ? this.getMaxSpeed() : this.getMinSpeed (); }
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
//	protected double getFollowRange () { return 11.0; }
	/**
	 * @return Vitesse de tir du mod
	 */
	protected double getTimeRange() { return this.getCapacities ().timeRange; }
	
	/**
	 * @return les capacitées du mod
	 */
	protected abstract MobCapacitiesConfigType getCapacities ();
	/**
	 * @return Coût du mod
	 */
	protected abstract ItemStackConfigType[] getCost ();

	@SideOnly(Side.CLIENT)
	public String getMessagePlayer () {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		ItemStack stack = null;
		if ((stack = this.hasBuyItemInHand(player)) != null) {
			return ModCastleDefenders.i18n.trans ("message.okfor", stack.stackSize, Item.itemsList[stack.itemID].getStatName ());
		}
		
		return ModCastleDefenders.i18n.trans ("message.buymercenary");
	}
	
	public ItemStack hasBuyItemInHand (EntityPlayer player) {
		ItemStack item = player.inventory.getCurrentItem();
		
		if (item != null) {
			
			if (this.getCapacities() == null) {
				return null;
			}
			
			for (ItemStackConfigType config : this.getCost ()) {
				ItemStack cStack = config.getItemStak();
				if (cStack.itemID == item.itemID) {
					return cStack;
				}
			}
		}
		return null;
	}
	
	/**
	 * Affecte les attributs de l'entity
	 */
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes ();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed) .setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)     .setAttribute(this.getMaxHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)   .setAttribute(this.getFollowRange ());
	}
	
	public void setTamed (boolean tamed) {
		
		super.setTamed(tamed);
		
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)    .setAttribute(this.getMaxHealt ());
	}
	
	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled() { 
		return true;
	}
	
	/**
	 * main AI tick function, replaces updateEntityActionState
	 */
	@Override
	protected void updateAITick() {
		this.dataWatcher.updateObject(18, new Integer ((int) this.getHealth()));
	}
	
	/**
	 * Play the taming effect, will either be hearts or smoke depending on
	 * status
	 */
	@Override
	protected void playTameEffect(boolean var1) { 
	}
	
	@Override
	protected void entityInit() { 
		super.entityInit();
		this.dataWatcher.addObject(18, new Integer((int) this.getHealth()));
	}
	
	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() { 
		return false;
	}
	
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) { 
		super.writeEntityToNBT(nbt);
		
		int i = 0;
		for (String owner : this.ownerList) {
			nbt.setString("owner"+i, owner);
			ModCastleDefenders.log.debug ("Write owner"+ i +" : "+ owner);
			i++;
		}
		
	}
	
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) { 
		super.readEntityFromNBT(nbt);
		
		int i = 0;
		this.ownerList.clear();
		String owner = null;
		
		do {
			owner = null;
			try {
				owner = nbt.getString("owner"+i);
				
			} catch (Exception e) {
				owner = null;
			}
			if (owner != null && !owner.equals("") && !this.ownerList.contains(owner)) {
				ModCastleDefenders.log.debug("Read NBT merc owner: "+owner);
				this.ownerList.add(owner);
			}
			i++;
		} while (owner != null && !owner.equals(""));
	}

	/**
	 * Returns true if this entity can attack entities of the specified class.
	 */
	@Override
	public boolean canAttackClass(Class var1) { 
		return EntityGhast.class != var1 && EntityMercenary.class != var1;
	}
	
	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() { 
		return false;
	}
	
	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	public int getMaxSpawnedInChunk () { 
		return 8;
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
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() { 
		return 0.4F;
	}
	
	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId() { 
		return -1;
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if (this.worldObj.isRemote) {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
//			ModCastleDefenders.log.debug("this.inOwnered : "+this.inOwnered);
		}
		
		if (this.eating >= 0) {
			
			if (this.eating % 4 == 0) {
				this.worldObj.playSoundAtEntity (this, "random.eat", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			this.eating++;
			if (this.eating == 10) {
				this.eating = -1;
			}
		}
	}
	
	@Override
	public float getEyeHeight() {
		return this.height * 0.8F;
	}
	
	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden
	 * by each mob to define their attack.
	 */
	@Override
	protected void attackEntity(Entity entity, float var2) {
		if (
				this.attackTime <= 0 && var2 < 2.0F && 
				entity.boundingBox.maxY > this.boundingBox.minY &&
				entity.boundingBox.minY < this.boundingBox.maxY
			) {
			
			this.attackTime = 10;
			this.attackEntityAsMob(entity);
		}
	}
	
	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float strength) {
		
		if (super.attackEntityFrom(damageSource, strength)) {
			Entity entity = damageSource.getEntity();

			if (this.riddenByEntity != entity && this.ridingEntity != entity) {
				if (entity != this) {
					this.entityToAttack = entity;
				}

				return true;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity) {
		double strength = this.getAttackStrength();

		if (this.isPotionActive(Potion.damageBoost)) {
			strength += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
		}

		if (this.isPotionActive(Potion.weakness)) {
			strength -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
		}

		return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)strength);
	}
	
	
	private boolean buy (EntityPlayer player, Class itemClass, int nb) {
		return this.buy(player, 0, itemClass, nb);
	}
	private boolean buy (EntityPlayer player, int itemId, int nb) {
		return this.buy(player, itemId, null, nb);
	}
	/**
	 * Buy item
	 */
	private boolean buy (EntityPlayer player, int itemId, Class itemClass, int nb) {
		
		ItemStack item = player.inventory.getCurrentItem();
		
		if (
			item != null && 
			item.stackSize >= nb && (
				(itemClass != null && itemClass.isInstance (Item.itemsList[item.itemID])) ||
				(itemId != 0 && item.itemID == itemId)
			)
		) {
			
			// Enleve l'item
			if (!player.capabilities.isCreativeMode) {
				item.stackSize -= nb;
			}
			if (item.stackSize <= 0) {
				player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
			}
			
			return true;
			
		}
		
		return false;
	}
	
	private void linkOwner (EntityPlayer player) {
		
		this.setPathToEntity((PathEntity) null);
		this.setAttackTarget((EntityLivingBase) null);
		this.isJumping = false;
		
		if (player !=  null) {
			this.setTamed(true);
			this.setOwner(player.username);
			
			if (!this.ownerList.contains (player.username)) {
				this.ownerList.add (player.username);
			}
		} else {
			this.setTamed(false);
			this.setOwner("");
		}
		
		this.worldObj.setEntityState(this, (byte) 7);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(this.getMoveSpeed ());
	}
	
	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	public boolean interact(EntityPlayer player) {
		
		ItemStack item = player.inventory.getCurrentItem();

		if (!this.worldObj.isRemote) {
			if (!this.isTamed()) {
				
				boolean buy = this.ownerList.contains (player.username);
				
				if (!buy) {
					for (ItemStackConfigType stackConfig: this.getCost ()) {
						ItemStack stack = stackConfig.getItemStak();
						if (buy = this.buy (player, stack.itemID, stack.stackSize)) {
							break;
						}
					}
				}
				
				if (buy) {
					
					this.linkOwner(player);
					return true;
				}
				
			} else if (this.getOwnerName().equals(player.username)) {
				
				ModCastleDefenders.log.debug("Interract with owner: "+player.username);
				
				if (this.buy (player, ItemFood.class, 1)) {
					
					ItemFood food = (ItemFood) Item.itemsList[item.itemID];
					this.heal(food.getHealAmount());
					
					this.worldObj.playSoundAtEntity (this, "random.eat", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
					this.eating = 0;
					
					return true;
				} else {

					this.linkOwner(null);
					
					return true;
				}
			}
		}

		return false;
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
	
	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}

	
	@SideOnly(Side.CLIENT)
	public boolean isOwner() {
		return this.isOwner(Minecraft.getMinecraft().thePlayer);
	}
	
	public boolean isOwner(EntityPlayer player) {
		return this.getOwnerName().equals(player.username);
	}
}

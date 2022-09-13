package com.gollum.castledefenders.common.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.core.common.config.type.ItemStackConfigType;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;
import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityMercenary extends EntityTameable implements ICastleEntity {

    private static final DataParameter<String> OWNERLIST = EntityDataManager.<String>createKey(EntityTameable.class, DataSerializers.STRING);
    
	protected Block blockSpawn;
	private int idTask = 0;
	private int idTargetTask = 0;

	private int eating = -1;
	private int autoHeal = 0;
	
	public EntityMercenary(World world) {
		super(world);
		
		this.setSize(1.1F, 1.8F);
		
		((PathNavigateGround)this.getNavigator()).setBreakDoors(true); // Permet d'ouvrir les port
		((PathNavigateGround)this.getNavigator()).setEnterDoors(true); // Permet d'ouvrir les port
		((PathNavigateGround)this.getNavigator()).setCanSwim(true); // Peux nager l'eau

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.getMoveSpeed());
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)    .setBaseValue(this.getMaxHealt());
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE)  .setBaseValue(this.getFollowRange());
	}


	@Override
	protected void entityInit() { 
        super.entityInit();
        this.dataManager.register(EntityMercenary.OWNERLIST, "");
    }
    
    protected void initEntityAI() {
		float follow = Math.min((float)(this.getFollowRange()-1), 10F);
		if (follow < 1.0F) {
			follow = 1.0F;
		}
        this.aiSit = new EntityAISit(this);
		this.tasks.addTask(this.nextIdTask (), new EntityAISwimming(this));
		this.tasks.addTask(this.nextIdTask (), this.aiSit);
		this.tasks.addTask(this.nextIdTask (), new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(this.nextIdTask (), new EntityAIFollowOwner(this, this.getMaxSpeed(), follow, 1.0F));
		
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(this.nextIdTargetTask (), new EntityAIHurtByTarget(this, true));
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
	public double getMoveSpeed () { return this.isTamed() ? this.getMaxSpeed() : this.getMinSpeed (); }
	/**
	 * @return Point de vie du mod
	 */
	public double getMaxHealt () { return this.getCapacities ().maxHealt; }
	/**
	 * @return Point de vie du mod
	 */
	public double getAttackStrength () { return this.getCapacities ().attackStrength; }
	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return this.getCapacities ().followRange; }
	
	/**
	 * @return Vitesse de tir du mod
	 */
	public double getTimeRange() { return this.getCapacities ().timeRange; }
	
	/**
	 * @return les capacitées du mod
	 */
	protected abstract MobCapacitiesConfigType getCapacities ();
	/**
	 * @return Coût du mod
	 */
	protected abstract ItemStackConfigType[] getCost ();


    public void onUpdate() {
    	super.onUpdate();
        if (!this.world.isRemote) {
        	if (this.autoHeal % 50 == 0) {
        		this.heal(0.5F);
        		this.autoHeal = 0;
        	}
    		this.autoHeal++;
        }
    }
	
	private void setOwnerListString(String ownerList) {
		this.dataManager.set(EntityMercenary.OWNERLIST, ownerList);
	}

	private String getOwnerListString() {
		return this.dataManager.get(EntityMercenary.OWNERLIST);
	}
	
    private ArrayList<String> getOwnerList() {
    	String strOwnerList = this.getOwnerListString();
    	ArrayList<String> list = new ArrayList<String>();
        Collections.addAll(list, strOwnerList.split(","));
    	return list;
    }
    
    private void addInOwnerList(String ownerId) {
    	 List<String> list = getOwnerList();
    	 list.add(ownerId);
    	 this.setOwnerListString(String.join(",", list));
    }
	
	@SideOnly(Side.CLIENT)
	public String getMessagePlayer () {
		EntityPlayer player = Minecraft.getMinecraft().player;

		if (player !=  null) {
			if (player.getUniqueID() != null && this.getOwnerList().contains(player.getUniqueID().toString())) {
				return ModCastleDefenders.i18n.trans ("message.wait_here");
			}
			
			
			ItemStack stack = null;
			if ((stack = this.hasBuyItemInHand(player)) != null) {
				return ModCastleDefenders.i18n.trans ("message.okfor", stack.getCount(), stack.getDisplayName());
			}
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
				if (cStack.getItem() == item.getItem()) {
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
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) .setBaseValue(this.getMoveSpeed());
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)     .setBaseValue(this.getMaxHealt());
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE)   .setBaseValue(this.getFollowRange());
	}
	
	public void setTamed (boolean tamed) {
		
		super.setTamed(tamed);

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.getMoveSpeed());
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)    .setBaseValue(this.getMaxHealt());
	}
	
	/**
	 * main AI tick function, replaces updateEntityActionState
	 */
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
	}
	
	/**
	 * Play the taming effect, will either be hearts or smoke depending on
	 * status
	 */
	@Override
	protected void playTameEffect(boolean var1) { 
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
	 * Returns true if this entity can attack entities of the specified class.
	 */
	@Override
	public boolean canAttackClass(Class<? extends EntityLivingBase> var1) { 
		return EntityGhast.class != var1 && !EntityMercenary.class.isAssignableFrom(var1);
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
		return 6;
	}
	
	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		
		int x = MathHelper.floor(this.posX);
		int y = MathHelper.floor(this.getEntityBoundingBox().minY);
		int z = MathHelper.floor(this.posZ);
		
		BlockPos pos = new BlockPos(x, y, z);
		
		IBlockState stateBlock = this.world.getBlockState(pos.down());
		IBlockState stateUp1 = this.world.getBlockState(pos);
		IBlockState stateUp2 = this.world.getBlockState(pos.up());
		

		List<? extends EntityMercenary> entityListBlockArround = this.world.getEntitiesWithinAABB(
			this.getClass(), 
			new AxisAlignedBB(
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
			stateBlock.getBlock() == this.blockSpawn &&
			(stateUp1 == null || stateUp1.getBlock() instanceof BlockAir || !stateUp1.getBlock().isCollidable()) &&
			(stateUp2 == null || stateUp2.getBlock() instanceof BlockAir || !stateUp2.getBlock().isCollidable()) &&
			!found
		;
	}
	
	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() { 
		return 0.4F;
	}
	
	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if (this.eating >= 0) {
			
			if (this.eating % 4 == 0) {
				this.playSound(
					SoundEvents.ENTITY_PLAYER_BURP,
					0.5F,
					this.world.rand.nextFloat() * 0.1F + 0.9F
				);
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
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float strength) {
		if (super.attackEntityFrom(damageSource, strength)) {
			
			Entity entity = damageSource.getImmediateSource();
			
			
			if (this.isPassenger(entity) && this.getRidingEntity().equals(entity)) {
				if (entity != this && entity instanceof EntityLivingBase) {
					this.setAttackTarget((EntityLivingBase)entity);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity) {
		double strength = this.getAttackStrength();

		if (this.isPotionActive(MobEffects.STRENGTH)) {
			strength += 3 << this.getActivePotionEffect(MobEffects.STRENGTH).getAmplifier();
		}

		if (this.isPotionActive(MobEffects.WEAKNESS)) {
			strength -= 2 << this.getActivePotionEffect(MobEffects.WEAKNESS).getAmplifier();
		}

		return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)strength);
	}
	
	
	private boolean buy (EntityPlayer player, Class<ItemFood> itemClass, int nb) {
		return this.buy(player, null, itemClass, nb);
	}
	private boolean buy (EntityPlayer player, Item item, int nb) {
		return this.buy(player, item, null, nb);
	}
	/**
	 * Buy item
	 */
	private boolean buy (EntityPlayer player, Item item, Class<ItemFood> itemClass, int nb) {
		
		ItemStack is = player.inventory.getCurrentItem();
		
		if (
			is != null && 
			is.getCount() >= nb && (
				(itemClass != null && itemClass.isInstance (is.getItem())) ||
				(item != null && is.getItem() == item)
			)
		) {
			
			// Enleve l'item
			if (!player.capabilities.isCreativeMode) {
				is.setCount(is.getCount() - nb);
			}
			if (is.getCount() <= 0) {
				player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
			}
			
			return true;
			
		}
		
		return false;
	}
	
	private void linkOwner (EntityPlayer player) {
		
		this.navigator.clearPath();
		this.setAttackTarget((EntityLivingBase) null);
		this.isJumping = false;
		
		if (player !=  null) {
			this.setTamed(true);
			this.setOwnerId(player.getUniqueID());
			
			if (!this.getOwnerList().contains(player.getUniqueID().toString())) {
				this.addInOwnerList(player.getUniqueID().toString());
			}
		} else {
			this.setTamed(false);
			this.setOwnerId(null);
		}
		
		this.world.setEntityState(this, (byte) 7);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.getMoveSpeed ());
	}
	
	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
		
		ItemStack is = player.inventory.getCurrentItem();


		if (is != null && is.getItem() instanceof ItemSplashPotion) {
			return false;
		}
		
		if (!this.world.isRemote && hand == EnumHand.MAIN_HAND) {
			if (!this.isTamed()) {
				
				boolean buy = this.isAlraidyBuy(player);
				
				if (!buy) {
					for (ItemStackConfigType stackConfig: this.getCost ()) {
						ItemStack stack = stackConfig.getItemStak();
						if (buy = this.buy (player, stack.getItem(), stack.getCount())) {
							break;
						}
					}
				}
				
				if (buy) {
					
					this.linkOwner(player);
					return true;
				}
				
			} else if (this.isOwner(player)) {
				
				if (this.buy (player, ItemFood.class, 1)) {
					
					ItemFood food = (ItemFood) is.getItem();
					this.heal(food.getHealAmount(is));
					
					this.playSound(
						SoundEvents.ENTITY_PLAYER_BURP,
						0.5F,
						this.world.rand.nextFloat() * 0.1F + 0.9F
					);
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
	
	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}
	
	public boolean isAlraidyBuy (EntityPlayer player) {
		return this.getOwnerList().contains(player.getUniqueID().toString());
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isOwner() {
		return this.isOwner(Minecraft.getMinecraft().player);
	}
	
	@Override
	public boolean isOwner(EntityLivingBase player) {
		return this.getOwnerId() != null && this.getOwnerId().equals(player.getUniqueID());
	}


	
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) { 
		super.writeEntityToNBT(nbt);
		nbt.setString("ownerList", this.getOwnerListString());
	}
	
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) { 
		super.readEntityFromNBT(nbt);
		this.setOwnerListString(nbt.getString("ownerList"));
	}
}

package mods.castledefender.common.entities;

import java.util.List;

import mods.castledefender.common.ModCastleDefender;
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
	
	private static final ItemStack defaultHeldItem = new ItemStack(Item.swordIron, 1);
	
	public EntityKnight(World world) {
		
		super(world);
		
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed) .setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)     .setAttribute(this.getHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)   .setAttribute(this.getFollowRange ());
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)   .setAttribute(this.getAttackStrength ());
		
		this.setSize(1.1F, 1.8F);
		this.getNavigator().setBreakDoors(true);
		this.tasks.addTask(1, new EntityAIAttackOnCollide(this, IMob.class, this.getMoveSpeed (), true));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityCreeper.class, this.getMoveSpeed (), true));
		this.tasks.addTask(3, new EntityAIWander(this, this.getMoveSpeed ()));
		
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, IMob.class         , 0, false, true)); // (this, , 16.0F, 0, false, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityCreeper.class, 0, false, true));
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
	
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes ();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed) .setAttribute(this.getMoveSpeed ());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)     .setAttribute(this.getHealt ());
		this.getEntityAttribute(SharedMonsterAttributes.followRange)   .setAttribute(this.getFollowRange ());
//		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)  .setAttribute(this.getAttackStrength ());
	}
	
	/**
	 * Returns the item that this EntityLiving is holding, if any.
	 */
	@Override
	public ItemStack getHeldItem() {
		return defaultHeldItem;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		
		if (this.worldObj.countEntities(this.getClass()) >= 15) {
			return false;
		} else {
			
			int var1 = MathHelper.floor_double(this.posX);
			int var2 = MathHelper.floor_double(this.boundingBox.minY);
			int var3 = MathHelper.floor_double(this.posZ);
			this.worldObj.getBlockId(var1, var2 - 1, var3);
			
			List var5 = this.worldObj.getEntitiesWithinAABB(
				EntityKnight.class,
				AxisAlignedBB.getBoundingBox(
					this.posX       , this.posY       , this.posZ,
					this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D
				).expand(
					2.0D, 2.0D, 2.0D
				)
			);
			
			return 
				this.worldObj.getBlockId(var1, var2 - 1, var3) == ModCastleDefender.BlockKnightID && 
				this.worldObj.checkNoEntityCollision(this.boundingBox) && 
				this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && var5.isEmpty()
			;
		}
	}
	
}

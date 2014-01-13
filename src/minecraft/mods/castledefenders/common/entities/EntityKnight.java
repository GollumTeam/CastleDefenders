package mods.castledefenders.common.entities;

import java.util.List;
import mods.castledefenders.common.ModCastleDefenders;
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
	public double getHealt () { return 15.0D; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return 4; }
	
	
	/**
	 * Returns the item that this EntityLiving is holding, if any.
	 */
	@Override
	public ItemStack getHeldItem() {
		return defaultHeldItem;
	}
	
}

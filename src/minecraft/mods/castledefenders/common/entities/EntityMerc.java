package mods.castledefenders.common.entities;

import mods.castledefenders.common.ModCastleDefenders;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMerc extends EntityMercenary {
	
	public EntityMerc(World world) {
		
		super(world);
		this.blockSpawnId       = ModCastleDefenders.blockMercID;
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

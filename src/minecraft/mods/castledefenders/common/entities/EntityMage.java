package mods.castledefenders.common.entities;

import java.util.List;

import mods.castledefenders.common.ModCastleDefenders;
import mods.castledefenders.common.aientities.EntityAIMageAttack;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMage extends EntityDefender {

	public EntityMage(World world) {

		super(world);
		this.blockSpawnId = ModCastleDefenders.blockMageID;
		
		this.setSize(1.1F, 1.8F);

		this.tasks.addTask(1, new EntityAIMageAttack(this, IMob.class, this.getMoveSpeed(), true));
		this.tasks.addTask(2, new EntityAIWander(this, this.getMoveSpeed ()));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget (this, IMob.class, 0, true));
	}

	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange() { return 100.D; }

	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed() { return 0.0D; }

	/**
	 * @return Point de vie du mod
	 */
	public double getHealt() { return 20.0D; }

	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength() { return 0; }
	
	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	protected boolean canDespawn() {
		return false;
	}
	
}

package mods.castledefenders.common.entities;

import mods.castledefenders.ModCastleDefenders;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityEKnight extends EntityEnemy {
	
	
	public EntityEKnight(World world) {
		super(world);
		this.blockSpawnId       = ModCastleDefenders.blockEKnightID;
		this.defaultHeldItem    = new ItemStack(Item.swordIron, 1);

		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityPlayer.class, this.getMoveSpeed(), true));
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityDefender.class, this.getMoveSpeed(), true));
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityMerc.class, this.getMoveSpeed(), true));
		this.tasks.addTask(this.nextIdTask (), new EntityAIAttackOnCollide(this, EntityMercArcher.class, this.getMoveSpeed(), true));
	}
	
	/**
	 * @return Zone de detection du mod
	 */
	public double getFollowRange () { return ModCastleDefenders.eKnightFollowRange; }
	/**
	 * @return Vitesse du mod
	 */
	public double getMoveSpeed () { return ModCastleDefenders.eKnightMoveSpeed; }
	/**
	 * @return Point de vie du mod
	 */
	public double getHealt () { return ModCastleDefenders.eKnightHealt; }
	/**
	 * @return Point de vie du mod
	 */
	public int getAttackStrength () { return ModCastleDefenders.eKnightAttackStrength; }
	
}

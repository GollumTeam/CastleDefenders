package com.gollum.castledefenders.common.entities;

import static com.gollum.castledefenders.ModCastleDefenders.logger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFireBall extends Entity {

	public EntityLivingBase shooter = null;
	public double oriX = 0D;
	public double oriY = 0D;
	public double oriZ = 0D;
	public double directionX = 0D;
	public double directionY = 0D;
	public double directionZ = 0D;
	public float damage = 2.0F;
	
	public EntityFireBall(World worldIn) {
		super(worldIn);
	}


	public EntityFireBall(World worldIn, EntityLivingBase shooter, Entity target) {
		this(
			worldIn,
			shooter,
			target.posX,
			target.posY,
			target.posZ
		);
	}
	
	public EntityFireBall(World worldIn, EntityLivingBase shooter, double x, double y, double z) {
		super(worldIn);
        this.width = 0.4F;
        this.height = 0.4F;
        this.setPosition(shooter.posX, shooter.posY + (double)shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ);
		this.shooter = shooter;
        this.oriX = this.posX;
        this.oriY = this.posY;
        this.oriZ = this.posZ;
        
        Vec3d vec3 = (new Vec3d(x - this.posX, y - this.posY, z - this.posZ)).normalize().scale(0.5);
        this.directionX += vec3.x;
        this.directionY += vec3.y;
        this.directionZ += vec3.z;
        
    	if (this.directionX == 0 && this.directionY == 0 && this.directionZ == 0) {
    		logger.debug("Fireball not move");
            this.setDead();
    	}
	}

	@Override
	protected void entityInit() {
	}


    public void onUpdate()
    {
    	
        if (this.world.isRemote || (this.shooter == null || !this.shooter.isDead) && this.world.isBlockLoaded(new BlockPos(this)))
        {
            super.onUpdate();

        	if (!this.world.isRemote) {
            	if (this.distanceRunning() > 20) {
    	            this.setDead();
    	            return;
    	    	}
        	}
    		
            RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast(this, true, false, this.shooter);
            if (raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult))
            {
                this.onImpact(raytraceresult);
                return;
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            
            
            float motionFactor = 1F;

            if (this.isInWater())
            {
                for (int i = 0; i < 4; ++i)
                {
                    this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
                }

                motionFactor = 0.7F;
            }

            this.motionX = this.directionX * motionFactor;
            this.motionY = this.directionY * motionFactor;
            this.motionZ = this.directionZ * motionFactor;

            this.world.spawnParticle(this.getParticleType(), this.posX, this.posY + 0.2D, this.posZ, 0.0D, 0.0D, 0.0D);
            this.setPosition(this.posX, this.posY, this.posZ);
        }
        else
        {
            this.setDead();
        }
    }
    
    protected double distanceRunning() {
    	return (new Vec3d(this.oriX, this.oriY, this.oriZ)).distanceTo(new Vec3d(this.posX, this.posY, this.posZ));
    }

    protected void onImpact(RayTraceResult result) {
    	if (!this.world.isRemote) {
    		
	        this.setDead();
	        
	        if (result.entityHit != null)
            {
	        	if (!result.entityHit.isImmuneToFire())
                {
	        		DamageSource damageSource = (new EntityDamageSourceIndirect("onFire", this, this.shooter != null ? this.shooter : this)).setFireDamage().setProjectile();
                    this.applyEnchantments(this.shooter, result.entityHit);
                    result.entityHit.setFire(1);
                    
                    result.entityHit.attackEntityFrom(damageSource, this.damage);
                	result.entityHit.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 0.6F + this.world.rand.nextFloat() * 0.2F);
                }
            }
	        else
            {
                boolean flag1 = true;

                if (this.shooter != null && this.shooter instanceof EntityLiving)
                {
                    flag1 = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.shooter);
                }

                if (flag1)
                {
                    BlockPos blockpos = result.getBlockPos().offset(result.sideHit);

                    if (this.world.isAirBlock(blockpos))
                    {
                        this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
                    }
                }
            }
    	}
    }
	
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    public boolean canBeAttackedWithItem() {
        return false;
    }

    public float getEyeHeight() {
        return 0.0F;
    }

    public float getBrightness() {
        return 1.0F;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 0xF000F0;
    }

    public float getCollisionBorderSize() {
        return 1.0F;
    }
    
    public boolean canBeCollidedWith() {
        return false;
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }
    
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.SMOKE_NORMAL;
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
        this.oriX = compound.getDouble("oriX");
        this.oriY = compound.getDouble("oriY");
        this.oriZ = compound.getDouble("oriZ");
        this.directionX = compound.getDouble("directionX");
        this.directionY = compound.getDouble("directionY");
        this.directionZ = compound.getDouble("directionZ");
        this.damage = compound.getFloat("damage");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setDouble("oriX", this.oriX);
        compound.setDouble("oriY", this.oriY);
        compound.setDouble("oriZ", this.oriZ);
        compound.setDouble("directionX", this.directionX);
        compound.setDouble("directionY", this.directionY);
        compound.setDouble("directionZ", this.directionZ);
        compound.setFloat("damage", this.damage);
	}
}

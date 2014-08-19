package net.minecraft.src;

import forge.ForgeHooks;
import java.lang.reflect.Constructor;
import java.util.*;

public final class SpawnerAnimals
{
    /** The 17x17 area around the player where mobs can spawn */
    private static HashMap eligibleChunksForSpawning = new HashMap();
    protected static final Class nightSpawnEntities[];

    public SpawnerAnimals()
    {
    }

    /**
     * Given a chunk, find a random position in it.
     */
    protected static ChunkPosition getRandomSpawningPointInChunk(World par0World, int par1, int par2)
    {
        Chunk chunk = par0World.getChunkFromChunkCoords(par1, par2);
        int i = par1 * 16 + par0World.rand.nextInt(16);
        int j = par0World.rand.nextInt(chunk != null ? Math.max(128, chunk.getTopFilledSegment() + 15) : 128);
        int k = par2 * 16 + par0World.rand.nextInt(16);
        return new ChunkPosition(i, j, k);
    }

    /**
     * The main spawning algorithm, spawns three random creatures of types in the subclass array
     */
    public static final int performSpawning(World par0World, boolean par1, boolean par2)
    {
        int j;
        int l;
        ChunkCoordinates chunkcoordinates;
        EnumCreatureType aenumcreaturetype[];
        int j1;

        if (!par1 && !par2)
        {
            return 0;
        }

        eligibleChunksForSpawning.clear();

        for (int i = 0; i < par0World.playerEntities.size(); i++)
        {
            EntityPlayer entityplayer = (EntityPlayer)par0World.playerEntities.get(i);
            int i1 = MathHelper.floor_double(entityplayer.posX / 16D);
            int k = MathHelper.floor_double(entityplayer.posZ / 16D);
            byte byte0 = 8;

            for (int k1 = -byte0; k1 <= byte0; k1++)
            {
                for (int l1 = -byte0; l1 <= byte0; l1++)
                {
                    boolean flag = k1 == -byte0 || k1 == byte0 || l1 == -byte0 || l1 == byte0;
                    ChunkCoordIntPair chunkcoordintpair1 = new ChunkCoordIntPair(k1 + i1, l1 + k);

                    if (!flag)
                    {
                        eligibleChunksForSpawning.put(chunkcoordintpair1, Boolean.valueOf(false));
                        continue;
                    }

                    if (!eligibleChunksForSpawning.containsKey(chunkcoordintpair1))
                    {
                        eligibleChunksForSpawning.put(chunkcoordintpair1, Boolean.valueOf(true));
                    }
                }
            }
        }

        j = 0;
        chunkcoordinates = par0World.getSpawnPoint();
        aenumcreaturetype = EnumCreatureType.values();
        l = aenumcreaturetype.length;
        j1 = 0;
        _L10:

        if (!(j1 >= l))
## JADFIX _L1 _L2
            _L1:
            EnumCreatureType enumcreaturetype;

        Iterator iterator;
        enumcreaturetype = aenumcreaturetype[j1];

        if (enumcreaturetype.getPeacefulCreature() && !par2 || !enumcreaturetype.getPeacefulCreature() && !par1 || par0World.countEntities(enumcreaturetype.getCreatureClass()) > (enumcreaturetype.getMaxNumberOfCreature() * eligibleChunksForSpawning.size()) / 256)
        {
            continue;
        }

        iterator = eligibleChunksForSpawning.keySet().iterator();
        _L4:
        int i2;
        int j2;
        int k2;
        int l2;
        int i3;

        do
        {
            ChunkCoordIntPair chunkcoordintpair;

            do
            {
                if (!iterator.hasNext())
                {
                    continue;
                }

                chunkcoordintpair = (ChunkCoordIntPair)iterator.next();
            }
            while (((Boolean)eligibleChunksForSpawning.get(chunkcoordintpair)).booleanValue());

            ChunkPosition chunkposition = getRandomSpawningPointInChunk(par0World, chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
            i2 = chunkposition.x;
            j2 = chunkposition.y;
            k2 = chunkposition.z;
        }
        while (par0World.isBlockNormalCube(i2, j2, k2) || par0World.getBlockMaterial(i2, j2, k2) != enumcreaturetype.getCreatureMaterial());

        l2 = 0;
        i3 = 0;
        _L9:

        if (!(i3 >= 3))
## JADFIX _L3 _L4
            _L3:
            int j3;

        int k3;
        int l3;
        byte byte1;
        SpawnListEntry spawnlistentry;
        int i4;
        j3 = i2;
        k3 = j2;
        l3 = k2;
        byte1 = 6;
        spawnlistentry = null;
        i4 = 0;
        _L8:

        if (!(i4 >= 4))
## JADFIX _L5 _L6
            _L5:
            EntityLiving entityliving;

        j3 += par0World.rand.nextInt(byte1) - par0World.rand.nextInt(byte1);
        k3 += par0World.rand.nextInt(1) - par0World.rand.nextInt(1);
        l3 += par0World.rand.nextInt(byte1) - par0World.rand.nextInt(byte1);

        if (!canCreatureTypeSpawnAtLocation(enumcreaturetype, par0World, j3, k3, l3))
        {
            continue;
        }

        float f = (float)j3 + 0.5F;
        float f1 = k3;
        float f2 = (float)l3 + 0.5F;

        if (par0World.getClosestPlayer(f, f1, f2, 24D) != null)
        {
            continue;
        }

        float f3 = f - (float)chunkcoordinates.posX;
        float f4 = f1 - (float)chunkcoordinates.posY;
        float f5 = f2 - (float)chunkcoordinates.posZ;
        float f6 = f3 * f3 + f4 * f4 + f5 * f5;

        if (f6 < 576F)
        {
            continue;
        }

        if (spawnlistentry == null)
        {
            spawnlistentry = par0World.getRandomMob(enumcreaturetype, j3, k3, l3);

            if (spawnlistentry == null)
            {
                break;
            }
        }

        try
        {
            entityliving = (EntityLiving)spawnlistentry.entityClass.getConstructor(new Class[]
                    {
                        net.minecraft.src.World.class
                    }).newInstance(new Object[]
                            {
                                par0World
                            });
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return j;
        }

        entityliving.setLocationAndAngles(f, f1, f2, par0World.rand.nextFloat() * 360F, 0.0F);

        if (!entityliving.getCanSpawnHere())
        {
            break;
        }

        l2++;
        par0World.spawnEntityInWorld(entityliving);
        creatureSpecificInit(entityliving, par0World, f, f1, f2);

        if (l2 < entityliving.getMaxSpawnedInChunk())
## JADFIX _L7 _L4
            _L7:
            j += l2;

        i4++;
        goto _L8
        _L6:
        i3++;
        goto _L9
        j1++;
        goto _L10
        _L2:
        return j;
    }

    /**
     * Returns whether or not the specified creature type can spawn at the specified location.
     */
    public static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType par0EnumCreatureType, World par1World, int par2, int par3, int par4)
    {
        if (par0EnumCreatureType.getCreatureMaterial() == Material.water)
        {
            return par1World.getBlockMaterial(par2, par3, par4).isLiquid() && !par1World.isBlockNormalCube(par2, par3 + 1, par4);
        }
        else
        {
            int i = par1World.getBlockId(par2, par3 - 1, par4);
            boolean flag = Block.blocksList[i] != null && Block.blocksList[i].canCreatureSpawn(par0EnumCreatureType, par1World, par2, par3 - 1, par4);
            return flag && i != Block.bedrock.blockID && !par1World.isBlockNormalCube(par2, par3, par4) && !par1World.getBlockMaterial(par2, par3, par4).isLiquid() && !par1World.isBlockNormalCube(par2, par3 + 1, par4);
        }
    }

    /**
     * determines if a skeleton spawns on a spider, and if a sheep is a different color
     */
    private static void creatureSpecificInit(EntityLiving par0EntityLiving, World par1World, float par2, float par3, float par4)
    {
        if (ForgeHooks.onEntitySpawnSpecial(par0EntityLiving, par1World, par2, par3, par4))
        {
            return;
        }

        if ((par0EntityLiving instanceof EntitySpider) && par1World.rand.nextInt(100) == 0)
        {
            EntitySkeleton entityskeleton = new EntitySkeleton(par1World);
            entityskeleton.setLocationAndAngles(par2, par3, par4, par0EntityLiving.rotationYaw, 0.0F);
            par1World.spawnEntityInWorld(entityskeleton);
            entityskeleton.mountEntity(par0EntityLiving);
        }
        else if (par0EntityLiving instanceof EntitySheep)
        {
            ((EntitySheep)par0EntityLiving).setFleeceColor(EntitySheep.getRandomFleeceColor(par1World.rand));
        }
        else if ((par0EntityLiving instanceof EntityOcelot) && par1World.rand.nextInt(7) == 0)
        {
            for (int i = 0; i < 2; i++)
            {
                EntityOcelot entityocelot = new EntityOcelot(par1World);
                entityocelot.setLocationAndAngles(par2, par3, par4, par0EntityLiving.rotationYaw, 0.0F);
                entityocelot.setGrowingAge(-24000);
                par1World.spawnEntityInWorld(entityocelot);
            }
        }
    }

    /**
     * Called during chunk generation to spawn initial creatures.
     */
    public static void performWorldGenSpawning(World par0World, BiomeGenBase par1BiomeGenBase, int par2, int par3, int par4, int par5, Random par6Random)
    {
        List list = par1BiomeGenBase.getSpawnableList(EnumCreatureType.creature);

        if (!list.isEmpty())
        {
            while (par6Random.nextFloat() < par1BiomeGenBase.getSpawningChance())
            {
                SpawnListEntry spawnlistentry = (SpawnListEntry)WeightedRandom.getRandomItem(par0World.rand, list);
                int i = spawnlistentry.minGroupCount + par6Random.nextInt((1 + spawnlistentry.maxGroupCount) - spawnlistentry.minGroupCount);
                int j = par2 + par6Random.nextInt(par4);
                int k = par3 + par6Random.nextInt(par5);
                int l = j;
                int i1 = k;
                int j1 = 0;

                while (j1 < i)
                {
                    boolean flag = false;

                    for (int k1 = 0; !flag && k1 < 4; k1++)
                    {
                        int l1 = par0World.getTopSolidOrLiquidBlock(j, k);

                        if (canCreatureTypeSpawnAtLocation(EnumCreatureType.creature, par0World, j, l1, k))
                        {
                            float f = (float)j + 0.5F;
                            float f1 = l1;
                            float f2 = (float)k + 0.5F;
                            EntityLiving entityliving;

                            try
                            {
                                entityliving = (EntityLiving)spawnlistentry.entityClass.getConstructor(new Class[]
                                        {
                                            net.minecraft.src.World.class
                                        }).newInstance(new Object[]
                                                {
                                                    par0World
                                                });
                            }
                            catch (Exception exception)
                            {
                                exception.printStackTrace();
                                continue;
                            }

                            entityliving.setLocationAndAngles(f, f1, f2, par6Random.nextFloat() * 360F, 0.0F);
                            par0World.spawnEntityInWorld(entityliving);
                            creatureSpecificInit(entityliving, par0World, f, f1, f2);
                            flag = true;
                        }

                        j += par6Random.nextInt(5) - par6Random.nextInt(5);

                        for (k += par6Random.nextInt(5) - par6Random.nextInt(5); j < par2 || j >= par2 + par4 || k < par3 || k >= par3 + par4; k = (i1 + par6Random.nextInt(5)) - par6Random.nextInt(5))
                        {
                            j = (l + par6Random.nextInt(5)) - par6Random.nextInt(5);
                        }
                    }

                    j1++;
                }
            }
        }
    }

    static
    {
        nightSpawnEntities = (new Class[]
                {
                    net.minecraft.src.EntitySpider.class, net.minecraft.src.EntityZombie.class, net.minecraft.src.EntitySkeleton.class
                });
    }
}

package net.minecraft.src;

import forge.ForgeHooksClient;
import java.util.*;
import org.lwjgl.opengl.GL11;

public class EffectRenderer
{
    /** Reference to the World object. */
    protected World worldObj;
    private List fxLayers[];
    private RenderEngine renderer;

    /** RNG. */
    private Random rand;
    private Hashtable effectList;

    public EffectRenderer(World par1World, RenderEngine par2RenderEngine)
    {
        fxLayers = new List[4];
        rand = new Random();
        effectList = new Hashtable();

        if (par1World != null)
        {
            worldObj = par1World;
        }

        renderer = par2RenderEngine;

        for (int i = 0; i < 4; i++)
        {
            fxLayers[i] = new ArrayList();
        }
    }

    public void addEffect(EntityFX par1EntityFX)
    {
        int i = par1EntityFX.getFXLayer();

        if (fxLayers[i].size() >= 4000)
        {
            fxLayers[i].remove(0);
        }

        fxLayers[i].add(par1EntityFX);
    }

    public void updateEffects()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < fxLayers[i].size(); j++)
            {
                EntityFX entityfx = (EntityFX)fxLayers[i].get(j);
                entityfx.onUpdate();

                if (entityfx.isDead)
                {
                    fxLayers[i].remove(j--);
                }
            }
        }

        Iterator iterator = effectList.keySet().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            String s = (String)iterator.next();
            ArrayList arraylist = (ArrayList)effectList.get(s);

            for (int k = 0; k < arraylist.size(); k++)
            {
                EntityFX entityfx1 = (EntityFX)arraylist.get(k);
                entityfx1.onUpdate();

                if (entityfx1.isDead)
                {
                    arraylist.remove(k--);
                }
            }

            if (effectList.size() == 0)
            {
                effectList.remove(s);
            }
        }
        while (true);
    }

    /**
     * Renders all current particles. Args player, partialTickTime
     */
    public void renderParticles(Entity par1Entity, float par2)
    {
        float f = ActiveRenderInfo.rotationX;
        float f1 = ActiveRenderInfo.rotationZ;
        float f2 = ActiveRenderInfo.rotationYZ;
        float f3 = ActiveRenderInfo.rotationXY;
        float f4 = ActiveRenderInfo.rotationXZ;
        EntityFX.interpPosX = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * (double)par2;
        EntityFX.interpPosY = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * (double)par2;
        EntityFX.interpPosZ = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * (double)par2;

        for (int i = 0; i < 3; i++)
        {
            if (fxLayers[i].size() == 0)
            {
                continue;
            }

            int j = 0;

            if (i == 0)
            {
                j = renderer.getTexture("/particles.png");
            }

            if (i == 1)
            {
                j = renderer.getTexture("/terrain.png");
            }

            if (i == 2)
            {
                j = renderer.getTexture("/gui/items.png");
            }

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, j);
            Tessellator tessellator1 = Tessellator.instance;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            tessellator1.startDrawingQuads();

            for (int k = 0; k < fxLayers[i].size(); k++)
            {
                EntityFX entityfx = (EntityFX)fxLayers[i].get(k);
                tessellator1.setBrightness(entityfx.getBrightnessForRender(par2));
                entityfx.renderParticle(tessellator1, par2, f, f4, f1, f2, f3);
            }

            tessellator1.draw();
        }

        Tessellator tessellator = Tessellator.instance;
        label0:

        for (Iterator iterator = effectList.entrySet().iterator(); iterator.hasNext(); tessellator.draw())
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderer.getTexture((String)entry.getKey()));
            tessellator.startDrawingQuads();
            Iterator iterator1 = ((ArrayList)entry.getValue()).iterator();

            do
            {
                if (!iterator1.hasNext())
                {
                    continue label0;
                }

                EntityFX entityfx1 = (EntityFX)iterator1.next();

                if (entityfx1.getFXLayer() != 3)
                {
                    tessellator.setBrightness(entityfx1.getBrightnessForRender(par2));
                    entityfx1.renderParticle(tessellator, par2, f, f4, f1, f2, f3);
                }
            }
            while (true);
        }
    }

    public void func_1187_b(Entity par1Entity, float par2)
    {
        float f = MathHelper.cos(par1Entity.rotationYaw * 0.01745329F);
        float f1 = MathHelper.sin(par1Entity.rotationYaw * 0.01745329F);
        float f2 = -f1 * MathHelper.sin(par1Entity.rotationPitch * 0.01745329F);
        float f3 = f * MathHelper.sin(par1Entity.rotationPitch * 0.01745329F);
        float f4 = MathHelper.cos(par1Entity.rotationPitch * 0.01745329F);
        byte byte0 = 3;

        if (fxLayers[byte0].size() != 0)
        {
            Tessellator tessellator = Tessellator.instance;

            for (int i = 0; i < fxLayers[byte0].size(); i++)
            {
                EntityFX entityfx = (EntityFX)fxLayers[byte0].get(i);
                tessellator.setBrightness(entityfx.getBrightnessForRender(par2));
                entityfx.renderParticle(tessellator, par2, f, f4, f1, f2, f3);
            }
        }
    }

    public void clearEffects(World par1World)
    {
        worldObj = par1World;

        for (int i = 0; i < 4; i++)
        {
            fxLayers[i].clear();
        }

        ArrayList arraylist;

        for (Iterator iterator = effectList.values().iterator(); iterator.hasNext(); arraylist.clear())
        {
            arraylist = (ArrayList)iterator.next();
        }

        effectList.clear();
    }

    public void addBlockDestroyEffects(int par1, int par2, int par3, int par4, int par5)
    {
        if (par4 != 0)
        {
            Block block = Block.blocksList[par4];
            int i = 4;

            for (int j = 0; j < i; j++)
            {
                for (int k = 0; k < i; k++)
                {
                    for (int l = 0; l < i; l++)
                    {
                        double d = (double)par1 + ((double)j + 0.5D) / (double)i;
                        double d1 = (double)par2 + ((double)k + 0.5D) / (double)i;
                        double d2 = (double)par3 + ((double)l + 0.5D) / (double)i;
                        int i1 = rand.nextInt(6);
                        addEffect((new EntityDiggingFX(worldObj, d, d1, d2, d - (double)par1 - 0.5D, d1 - (double)par2 - 0.5D, d2 - (double)par3 - 0.5D, block, i1, par5)).func_4041_a(par1, par2, par3), block);
                    }
                }
            }
        }
    }

    /**
     * Adds block hit particles for the specified block. Args: x, y, z, sideHit
     */
    public void addBlockHitEffects(int par1, int par2, int par3, int par4)
    {
        int i = worldObj.getBlockId(par1, par2, par3);

        if (i != 0)
        {
            Block block = Block.blocksList[i];
            float f = 0.1F;
            double d = (double)par1 + rand.nextDouble() * (block.maxX - block.minX - (double)(f * 2.0F)) + (double)f + block.minX;
            double d1 = (double)par2 + rand.nextDouble() * (block.maxY - block.minY - (double)(f * 2.0F)) + (double)f + block.minY;
            double d2 = (double)par3 + rand.nextDouble() * (block.maxZ - block.minZ - (double)(f * 2.0F)) + (double)f + block.minZ;

            if (par4 == 0)
            {
                d1 = ((double)par2 + block.minY) - (double)f;
            }

            if (par4 == 1)
            {
                d1 = (double)par2 + block.maxY + (double)f;
            }

            if (par4 == 2)
            {
                d2 = ((double)par3 + block.minZ) - (double)f;
            }

            if (par4 == 3)
            {
                d2 = (double)par3 + block.maxZ + (double)f;
            }

            if (par4 == 4)
            {
                d = ((double)par1 + block.minX) - (double)f;
            }

            if (par4 == 5)
            {
                d = (double)par1 + block.maxX + (double)f;
            }

            addEffect((new EntityDiggingFX(worldObj, d, d1, d2, 0.0D, 0.0D, 0.0D, block, par4, worldObj.getBlockMetadata(par1, par2, par3))).func_4041_a(par1, par2, par3).multiplyVelocity(0.2F).func_405_d(0.6F), block);
        }
    }

    public String getStatistics()
    {
        int i = 0;
        List alist[] = fxLayers;
        int j = alist.length;

        for (int k = 0; k < j; k++)
        {
            List list = alist[k];
            i += list.size();
        }

        for (Iterator iterator = effectList.values().iterator(); iterator.hasNext();)
        {
            ArrayList arraylist = (ArrayList)iterator.next();
            i += arraylist.size();
        }

        return Integer.toString(i);
    }

    public void addEffect(EntityFX entityfx, Object obj)
    {
        if (obj == null || !(entityfx instanceof EntityDiggingFX) && !(entityfx instanceof EntityBreakingFX))
        {
            addEffect(entityfx);
            return;
        }

        String s = "/terrain.png";

        if (entityfx.getFXLayer() == 0)
        {
            s = "/particles.png";
        }
        else if (entityfx.getFXLayer() == 2)
        {
            s = "/gui/items.png";
        }

        s = ForgeHooksClient.getTexture(s, obj);
        ArrayList arraylist = (ArrayList)effectList.get(s);

        if (arraylist == null)
        {
            arraylist = new ArrayList();
            effectList.put(s, arraylist);
        }

        arraylist.add(entityfx);
    }
}

package net.minecraft.src;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.FMLTextureFX;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class ModTextureStatic extends FMLTextureFX
{
    private boolean oldanaglyph;
    private int pixels[];
    private String targetTex;
    private int storedSize;
    private BufferedImage overrideData;
    private int needApply;

    public ModTextureStatic(int i, int j, BufferedImage bufferedimage)
    {
        this(i, 1, j, bufferedimage);
    }

    public ModTextureStatic(int i, int j, int k, BufferedImage bufferedimage)
    {
        this(i, j, k != 0 ? "/gui/items.png" : "/terrain.png", bufferedimage);
    }

    public ModTextureStatic(int i, int j, String s, BufferedImage bufferedimage)
    {
        super(i);
        oldanaglyph = false;
        pixels = null;
        targetTex = null;
        overrideData = null;
        needApply = 2;
        RenderEngine renderengine = FMLClientHandler.instance().getClient().renderEngine;
        targetTex = s;
        storedSize = j;
        tileSize = j;
        tileImage = renderengine.getTexture(s);
        overrideData = bufferedimage;
    }

    public void setup()
    {
        super.setup();
        int i = overrideData.getWidth();
        int j = overrideData.getHeight();
        pixels = new int[tileSizeSquare];

        if (tileSizeBase == i && tileSizeBase == j)
        {
            overrideData.getRGB(0, 0, i, j, pixels, 0, i);
        }
        else
        {
            BufferedImage bufferedimage = new BufferedImage(tileSizeBase, tileSizeBase, 6);
            Graphics2D graphics2d = bufferedimage.createGraphics();
            graphics2d.drawImage(overrideData, 0, 0, tileSizeBase, tileSizeBase, 0, 0, i, j, (ImageObserver)null);
            bufferedimage.getRGB(0, 0, tileSizeBase, tileSizeBase, pixels, 0, tileSizeBase);
            graphics2d.dispose();
        }

        update();
    }

    public void onTick()
    {
        if (oldanaglyph != anaglyphEnabled)
        {
            update();
        }

        tileSize = needApply != 0 ? storedSize : 0;

        if (needApply > 0)
        {
            needApply--;
        }
    }

    public void bindImage(RenderEngine renderengine)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderengine.getTexture(targetTex));
    }

    public void update()
    {
        needApply = 2;

        for (int i = 0; i < pixels.length; i++)
        {
            int j = i * 4;
            int k = pixels[i] >> 24 & 0xff;
            int l = pixels[i] >> 16 & 0xff;
            int i1 = pixels[i] >> 8 & 0xff;
            int j1 = pixels[i] >> 0 & 0xff;

            if (anaglyphEnabled)
            {
                l = i1 = j1 = (l + i1 + j1) / 3;
            }

            imageData[j + 0] = (byte)l;
            imageData[j + 1] = (byte)i1;
            imageData[j + 2] = (byte)j1;
            imageData[j + 3] = (byte)k;
        }

        oldanaglyph = anaglyphEnabled;
    }

    public static BufferedImage scale2x(BufferedImage bufferedimage)
    {
        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        BufferedImage bufferedimage1 = new BufferedImage(i * 2, j * 2, 2);

        for (int k = 0; k < j; k++)
        {
            int l = k * 2;

            for (int i1 = 0; i1 < i; i1++)
            {
                int j1 = i1 * 2;
                int k1 = bufferedimage.getRGB(i1, k);
                int l1 = k != 0 ? bufferedimage.getRGB(i1, k - 1) : k1;
                int i2 = i1 != 0 ? bufferedimage.getRGB(i1 - 1, k) : k1;
                int j2 = i1 < i - 1 ? bufferedimage.getRGB(i1 + 1, k) : k1;
                int k2 = k < j - 1 ? bufferedimage.getRGB(i1, k + 1) : k1;
                int l2;
                int i3;
                int j3;
                int k3;

                if (i2 != j2 && l1 != k2)
                {
                    l2 = l1 != i2 ? k1 : l1;
                    i3 = i2 != k2 ? k1 : k2;
                    j3 = l1 != j2 ? k1 : l1;
                    k3 = j2 != k2 ? k1 : k2;
                }
                else
                {
                    l2 = i3 = j3 = k3 = k1;
                }

                bufferedimage1.setRGB(j1, l, l2);
                bufferedimage1.setRGB(j1 + 1, l, i3);
                bufferedimage1.setRGB(j1, l + 1, j3);
                bufferedimage1.setRGB(j1 + 1, l + 1, k3);
            }
        }

        return bufferedimage1;
    }

    public String toString()
    {
        return String.format("ModTextureStatic %s @ %d", new Object[]
                {
                    targetTex, Integer.valueOf(iconIndex)
                });
    }
}

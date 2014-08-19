package net.minecraft.src;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.FMLTextureFX;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import net.minecraft.client.Minecraft;

public class ModTextureAnimation extends FMLTextureFX
{
    private final int tickRate;
    private byte images[][];
    private int index;
    private int ticks;
    private String targetTex;
    private BufferedImage imgData;

    public ModTextureAnimation(int i, int j, BufferedImage bufferedimage, int k)
    {
        this(i, 1, j, bufferedimage, k);
    }

    public ModTextureAnimation(int i, int j, int k, BufferedImage bufferedimage, int l)
    {
        this(i, j, k != 0 ? "/gui/items.png" : "/terrain.png", bufferedimage, l);
    }

    public ModTextureAnimation(int i, int j, String s, BufferedImage bufferedimage, int k)
    {
        super(i);
        index = 0;
        ticks = 0;
        targetTex = null;
        imgData = null;
        RenderEngine renderengine = FMLClientHandler.instance().getClient().renderEngine;
        targetTex = s;
        tileSize = j;
        tileImage = renderengine.getTexture(s);
        tickRate = k;
        ticks = k;
        imgData = bufferedimage;
    }

    public void setup()
    {
        super.setup();
        int i = imgData.getWidth();
        int j = imgData.getHeight();
        int k = tileSizeBase;
        int l = tileSizeBase;
        int i1 = (int)Math.floor(j / i);

        if (i1 < 1)
        {
            throw new IllegalArgumentException(String.format("Attempted to create a TextureAnimation with no complete frames: %dx%d", new Object[]
                    {
                        Integer.valueOf(i), Integer.valueOf(j)
                    }));
        }

        images = new byte[i1][];
        BufferedImage bufferedimage = imgData;

        if (i != k)
        {
            BufferedImage bufferedimage1 = new BufferedImage(k, l * i1, 6);
            Graphics2D graphics2d = bufferedimage1.createGraphics();
            graphics2d.drawImage(imgData, 0, 0, k, l * i1, 0, 0, i, j, (ImageObserver)null);
            graphics2d.dispose();
            bufferedimage = bufferedimage1;
        }

        for (int j1 = 0; j1 < i1; j1++)
        {
            int ai[] = new int[tileSizeSquare];
            bufferedimage.getRGB(0, l * j1, k, l, ai, 0, k);
            images[j1] = new byte[tileSizeSquare << 2];

            for (int k1 = 0; k1 < ai.length; k1++)
            {
                int l1 = k1 * 4;
                images[j1][l1 + 0] = (byte)(ai[k1] >> 16 & 0xff);
                images[j1][l1 + 1] = (byte)(ai[k1] >> 8 & 0xff);
                images[j1][l1 + 2] = (byte)(ai[k1] >> 0 & 0xff);
                images[j1][l1 + 3] = (byte)(ai[k1] >> 24 & 0xff);
            }
        }
    }

    public void onTick()
    {
        if (++ticks >= tickRate)
        {
            if (++index >= images.length)
            {
                index = 0;
            }

            imageData = images[index];
            ticks = 0;
        }
    }
}

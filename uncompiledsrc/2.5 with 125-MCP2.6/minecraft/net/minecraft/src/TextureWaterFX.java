package net.minecraft.src;

import cpw.mods.fml.client.FMLTextureFX;

public class TextureWaterFX extends FMLTextureFX
{
    protected float red[];
    protected float green[];
    protected float blue[];
    protected float alpha[];
    private int tickCounter;

    public TextureWaterFX()
    {
        super(Block.waterMoving.blockIndexInTexture);
        red = new float[256];
        green = new float[256];
        blue = new float[256];
        alpha = new float[256];
        tickCounter = 0;
        setup();
    }

    public void setup()
    {
        super.setup();
        red = new float[tileSizeSquare];
        green = new float[tileSizeSquare];
        blue = new float[tileSizeSquare];
        alpha = new float[tileSizeSquare];
        tickCounter = 0;
    }

    public void onTick()
    {
        tickCounter++;

        for (int i = 0; i < tileSizeBase; i++)
        {
            for (int k = 0; k < tileSizeBase; k++)
            {
                float f = 0.0F;

                for (int j2 = i - 1; j2 <= i + 1; j2++)
                {
                    int j1 = j2 & tileSizeMask;
                    int l1 = k & tileSizeMask;
                    f += red[j1 + l1 * tileSizeBase];
                }

                green[i + k * tileSizeBase] = f / 3.3F + blue[i + k * tileSizeBase] * 0.8F;
            }
        }

        for (int j = 0; j < tileSizeBase; j++)
        {
            for (int l = 0; l < tileSizeBase; l++)
            {
                blue[j + l * tileSizeBase] += alpha[j + l * tileSizeBase] * 0.05F;

                if (blue[j + l * tileSizeBase] < 0.0F)
                {
                    blue[j + l * tileSizeBase] = 0.0F;
                }

                alpha[j + l * tileSizeBase] -= 0.1F;

                if (Math.random() < 0.050000000000000003D)
                {
                    alpha[j + l * tileSizeBase] = 0.5F;
                }
            }
        }

        float af[] = green;
        green = red;
        red = af;

        for (int i1 = 0; i1 < tileSizeSquare; i1++)
        {
            float f1 = red[i1];

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }

            if (f1 < 0.0F)
            {
                f1 = 0.0F;
            }

            float f2 = f1 * f1;
            int k1 = (int)(32F + f2 * 32F);
            int i2 = (int)(50F + f2 * 64F);
            int k2 = 255;
            int l2 = (int)(146F + f2 * 50F);

            if (anaglyphEnabled)
            {
                int i3 = (k1 * 30 + i2 * 59 + k2 * 11) / 100;
                int j3 = (k1 * 30 + i2 * 70) / 100;
                int k3 = (k1 * 30 + k2 * 70) / 100;
                k1 = i3;
                i2 = j3;
                k2 = k3;
            }

            imageData[i1 * 4 + 0] = (byte)k1;
            imageData[i1 * 4 + 1] = (byte)i2;
            imageData[i1 * 4 + 2] = (byte)k2;
            imageData[i1 * 4 + 3] = (byte)l2;
        }
    }
}

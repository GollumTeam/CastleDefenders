package net.minecraft.src;

import cpw.mods.fml.client.FMLTextureFX;

public class TextureWaterFlowFX extends FMLTextureFX
{
    protected float field_1138_g[];
    protected float field_1137_h[];
    protected float field_1136_i[];
    protected float field_1135_j[];
    private int tickCounter;

    public TextureWaterFlowFX()
    {
        super(Block.waterMoving.blockIndexInTexture + 1);
        field_1138_g = new float[256];
        field_1137_h = new float[256];
        field_1136_i = new float[256];
        field_1135_j = new float[256];
        tickCounter = 0;
        tileSize = 2;
        setup();
    }

    public void setup()
    {
        super.setup();
        field_1138_g = new float[tileSizeSquare];
        field_1137_h = new float[tileSizeSquare];
        field_1136_i = new float[tileSizeSquare];
        field_1135_j = new float[tileSizeSquare];
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

                for (int j2 = k - 2; j2 <= k; j2++)
                {
                    int j1 = i & tileSizeMask;
                    int l1 = j2 & tileSizeMask;
                    f += field_1138_g[j1 + l1 * tileSizeBase];
                }

                field_1137_h[i + k * tileSizeBase] = f / 3.2F + field_1136_i[i + k * tileSizeBase] * 0.8F;
            }
        }

        for (int j = 0; j < tileSizeBase; j++)
        {
            for (int l = 0; l < tileSizeBase; l++)
            {
                field_1136_i[j + l * tileSizeBase] += field_1135_j[j + l * tileSizeBase] * 0.05F;

                if (field_1136_i[j + l * tileSizeBase] < 0.0F)
                {
                    field_1136_i[j + l * tileSizeBase] = 0.0F;
                }

                field_1135_j[j + l * tileSizeBase] -= 0.3F;

                if (Math.random() < 0.20000000000000001D)
                {
                    field_1135_j[j + l * tileSizeBase] = 0.5F;
                }
            }
        }

        float af[] = field_1137_h;
        field_1137_h = field_1138_g;
        field_1138_g = af;

        for (int i1 = 0; i1 < tileSizeSquare; i1++)
        {
            float f1 = field_1138_g[i1 - tickCounter * tileSizeBase & tileSizeSquareMask];

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

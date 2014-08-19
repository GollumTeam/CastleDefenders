package net.minecraft.src;

import cpw.mods.fml.client.FMLTextureFX;

public class TextureFlamesFX extends FMLTextureFX
{
    protected float field_1133_g[];
    protected float field_1132_h[];
    private int fireTileSize;
    private int fireGridSize;

    public TextureFlamesFX(int par1)
    {
        super(Block.fire.blockIndexInTexture + par1 * 16);
        field_1133_g = new float[320];
        field_1132_h = new float[320];
        fireTileSize = 20;
        fireGridSize = 320;
        setup();
    }

    public void setup()
    {
        super.setup();
        fireTileSize = tileSizeBase + (tileSizeBase >> 2);
        fireGridSize = fireTileSize * tileSizeBase;
        field_1133_g = new float[fireGridSize];
        field_1132_h = new float[fireGridSize];
    }

    public void onTick()
    {
        float f2 = 3F + (float)(tileSizeBase >> 4);
        float f3 = 1.01F + 0.8F / (float)tileSizeBase;

        for (int i1 = 0; i1 < tileSizeBase; i1++)
        {
            for (int j1 = 0; j1 < fireTileSize; j1++)
            {
                int i = fireTileSize - (tileSizeBase >> 3);
                float f = field_1133_g[i1 + ((j1 + 1) % fireTileSize) * tileSizeBase] * (float)i;

                for (int l1 = i1 - 1; l1 <= i1 + 1; l1++)
                {
                    for (int k = j1; k <= j1 + 1; k++)
                    {
                        if (l1 >= 0 && k >= 0 && l1 < tileSizeBase && k < fireTileSize)
                        {
                            f += field_1133_g[l1 + k * tileSizeBase];
                        }

                        i++;
                    }
                }

                field_1132_h[i1 + j1 * tileSizeBase] = f / ((float)i * f3);

                if (j1 >= fireTileSize - (tileSizeBase >> 4))
                {
                    field_1132_h[i1 + j1 * tileSizeBase] = (float)(Math.random() * Math.random() * Math.random() * (double)f2 + Math.random() * 0.10000000149011612D + 0.20000000298023224D);
                }
            }
        }

        float af[] = field_1132_h;
        field_1132_h = field_1133_g;
        field_1133_g = af;

        for (int j = 0; j < tileSizeSquare; j++)
        {
            float f1 = field_1133_g[j] * 1.8F;

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }

            if (f1 < 0.0F)
            {
                f1 = 0.0F;
            }

            int l = (int)(f1 * 155F + 100F);
            int k1 = (int)(f1 * f1 * 255F);
            int i2 = (int)(f1 * f1 * f1 * f1 * f1 * f1 * f1 * f1 * f1 * f1 * 255F);
            char c = '\377';

            if (f1 < 0.5F)
            {
                c = '\0';
            }

            float f4 = (f1 - 0.5F) * 2.0F;

            if (anaglyphEnabled)
            {
                int j2 = (l * 30 + k1 * 59 + i2 * 11) / 100;
                int k2 = (l * 30 + k1 * 70) / 100;
                int l2 = (l * 30 + i2 * 70) / 100;
                l = j2;
                k1 = k2;
                i2 = l2;
            }

            imageData[j * 4 + 0] = (byte)l;
            imageData[j * 4 + 1] = (byte)k1;
            imageData[j * 4 + 2] = (byte)i2;
            imageData[j * 4 + 3] = (byte)c;
        }
    }
}

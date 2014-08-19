package net.minecraft.src;

import cpw.mods.fml.client.FMLTextureFX;

public class TextureLavaFlowFX extends FMLTextureFX
{
    protected float field_1143_g[];
    protected float field_1142_h[];
    protected float field_1141_i[];
    protected float field_1140_j[];
    int field_1139_k;

    public TextureLavaFlowFX()
    {
        super(Block.lavaMoving.blockIndexInTexture + 1);
        field_1143_g = new float[256];
        field_1142_h = new float[256];
        field_1141_i = new float[256];
        field_1140_j = new float[256];
        field_1139_k = 0;
        tileSize = 2;
        setup();
    }

    public void setup()
    {
        super.setup();
        field_1143_g = new float[tileSizeSquare];
        field_1142_h = new float[tileSizeSquare];
        field_1141_i = new float[tileSizeSquare];
        field_1140_j = new float[tileSizeSquare];
        field_1139_k = 0;
    }

    public void onTick()
    {
        field_1139_k++;

        for (int i3 = 0; i3 < tileSizeBase; i3++)
        {
            for (int i = 0; i < tileSizeBase; i++)
            {
                float f = 0.0F;
                int j3 = (int)(MathHelper.sin(((float)i * (float)Math.PI * 2.0F) / 16F) * 1.2F);
                int k = (int)(MathHelper.sin(((float)i3 * (float)Math.PI * 2.0F) / 16F) * 1.2F);

                for (int i1 = i3 - 1; i1 <= i3 + 1; i1++)
                {
                    for (int k1 = i - 1; k1 <= i + 1; k1++)
                    {
                        int i2 = i1 + j3 & tileSizeMask;
                        int k2 = k1 + k & tileSizeMask;
                        f += field_1143_g[i2 + k2 * tileSizeBase];
                    }
                }

                field_1142_h[i3 + i * tileSizeBase] = f / 10F + ((field_1141_i[(i3 + 0 & tileSizeMask) + (i + 0 & tileSizeMask) * tileSizeBase] + field_1141_i[(i3 + 1 & tileSizeMask) + (i + 0 & tileSizeMask) * tileSizeBase] + field_1141_i[(i3 + 1 & tileSizeMask) + (i + 1 & tileSizeMask) * tileSizeBase] + field_1141_i[(i3 + 0 & tileSizeMask) + (i + 1 & tileSizeMask) * tileSizeBase]) / 4F) * 0.8F;
                field_1141_i[i3 + i * tileSizeBase] += field_1140_j[i3 + i * tileSizeBase] * 0.01F;

                if (field_1141_i[i3 + i * tileSizeBase] < 0.0F)
                {
                    field_1141_i[i3 + i * tileSizeBase] = 0.0F;
                }

                field_1140_j[i3 + i * tileSizeBase] -= 0.06F;

                if (Math.random() < 0.0050000000000000001D)
                {
                    field_1140_j[i3 + i * tileSizeBase] = 1.5F;
                }
            }
        }

        float af[] = field_1142_h;
        field_1142_h = field_1143_g;
        field_1143_g = af;

        for (int j = 0; j < tileSizeSquare; j++)
        {
            float f1 = field_1143_g[j - (field_1139_k / 3) * tileSizeBase & tileSizeSquareMask] * 2.0F;

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }

            if (f1 < 0.0F)
            {
                f1 = 0.0F;
            }

            int l = (int)(f1 * 100F + 155F);
            int j1 = (int)(f1 * f1 * 255F);
            int l1 = (int)(f1 * f1 * f1 * f1 * 128F);

            if (anaglyphEnabled)
            {
                int j2 = (l * 30 + j1 * 59 + l1 * 11) / 100;
                int l2 = (l * 30 + j1 * 70) / 100;
                int k3 = (l * 30 + l1 * 70) / 100;
                l = j2;
                j1 = l2;
                l1 = k3;
            }

            imageData[j * 4 + 0] = (byte)l;
            imageData[j * 4 + 1] = (byte)j1;
            imageData[j * 4 + 2] = (byte)l1;
            imageData[j * 4 + 3] = -1;
        }
    }
}

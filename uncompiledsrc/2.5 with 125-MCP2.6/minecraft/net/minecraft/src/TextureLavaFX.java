package net.minecraft.src;

import cpw.mods.fml.client.FMLTextureFX;

public class TextureLavaFX extends FMLTextureFX
{
    protected float field_1147_g[];
    protected float field_1146_h[];
    protected float field_1145_i[];
    protected float field_1144_j[];

    public TextureLavaFX()
    {
        super(Block.lavaMoving.blockIndexInTexture);
        setup();
    }

    public void setup()
    {
        super.setup();
        field_1147_g = new float[tileSizeSquare];
        field_1146_h = new float[tileSizeSquare];
        field_1145_i = new float[tileSizeSquare];
        field_1144_j = new float[tileSizeSquare];
    }

    public void onTick()
    {
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
                        f += field_1147_g[i2 + k2 * tileSizeBase];
                    }
                }

                field_1146_h[i3 + i * tileSizeBase] = f / 10F + ((field_1145_i[(i3 + 0 & 0xf) + (i + 0 & 0xf) * tileSizeBase] + field_1145_i[(i3 + 1 & 0xf) + (i + 0 & 0xf) * tileSizeBase] + field_1145_i[(i3 + 1 & 0xf) + (i + 1 & 0xf) * tileSizeBase] + field_1145_i[(i3 + 0 & 0xf) + (i + 1 & 0xf) * tileSizeBase]) / 4F) * 0.8F;
                field_1145_i[i3 + i * tileSizeBase] += field_1144_j[i3 + i * tileSizeBase] * 0.01F;

                if (field_1145_i[i3 + i * tileSizeBase] < 0.0F)
                {
                    field_1145_i[i3 + i * tileSizeBase] = 0.0F;
                }

                field_1144_j[i3 + i * tileSizeBase] -= 0.06F;

                if (Math.random() < 0.0050000000000000001D)
                {
                    field_1144_j[i3 + i * tileSizeBase] = 1.5F;
                }
            }
        }

        float af[] = field_1146_h;
        field_1146_h = field_1147_g;
        field_1147_g = af;

        for (int j = 0; j < tileSizeSquare; j++)
        {
            float f1 = field_1147_g[j] * 2.0F;

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

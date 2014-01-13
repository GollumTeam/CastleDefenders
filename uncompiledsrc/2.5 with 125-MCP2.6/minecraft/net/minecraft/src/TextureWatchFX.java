package net.minecraft.src;

import cpw.mods.fml.client.FMLTextureFX;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;

public class TextureWatchFX extends FMLTextureFX
{
    /**
     * Holds the game instance to retrieve information like world provider and time.
     */
    private Minecraft mc;
    private int watchIconImageData[];
    private int dialImageData[];
    private double field_4222_j;
    private double field_4221_k;

    public TextureWatchFX(Minecraft par1Minecraft)
    {
        super(Item.pocketSundial.getIconFromDamage(0));
        watchIconImageData = new int[256];
        dialImageData = new int[256];
        mc = par1Minecraft;
        tileImage = 1;
        setup();
    }

    public void setup()
    {
        super.setup();
        watchIconImageData = new int[tileSizeSquare];
        dialImageData = new int[tileSizeSquare];

        try
        {
            BufferedImage bufferedimage = ImageIO.read(mc.texturePackList.selectedTexturePack.getResourceAsStream("/gui/items.png"));
            int i = (iconIndex % 16) * tileSizeBase;
            int j = (iconIndex / 16) * tileSizeBase;
            bufferedimage.getRGB(i, j, tileSizeBase, tileSizeBase, watchIconImageData, 0, tileSizeBase);
            bufferedimage = ImageIO.read(mc.texturePackList.selectedTexturePack.getResourceAsStream("/misc/dial.png"));

            if (bufferedimage.getWidth() != tileSizeBase)
            {
                BufferedImage bufferedimage1 = new BufferedImage(tileSizeBase, tileSizeBase, 6);
                Graphics2D graphics2d = bufferedimage1.createGraphics();
                graphics2d.drawImage(bufferedimage, 0, 0, tileSizeBase, tileSizeBase, 0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), (ImageObserver)null);
                graphics2d.dispose();
                bufferedimage = bufferedimage1;
            }

            bufferedimage.getRGB(0, 0, tileSizeBase, tileSizeBase, dialImageData, 0, tileSizeBase);
        }
        catch (Exception exception)
        {
            log.log(Level.WARNING, String.format("A problem occurred with the watch texture: animation will be disabled", new Object[0]), exception);
            setErrored(true);
        }
    }

    public void onTick()
    {
        double d = 0.0D;

        if (mc.theWorld != null && mc.thePlayer != null)
        {
            float f = mc.theWorld.getCelestialAngle(1.0F);
            d = -f * (float)Math.PI * 2.0F;

            if (!mc.theWorld.worldProvider.func_48217_e())
            {
                d = Math.random() * Math.PI * 2D;
            }
        }

        double d1;

        for (d1 = d - field_4222_j; d1 < -Math.PI; d1 += (Math.PI * 2D)) { }

        for (; d1 >= Math.PI; d1 -= (Math.PI * 2D)) { }

        if (d1 < -1D)
        {
            d1 = -1D;
        }

        if (d1 > 1.0D)
        {
            d1 = 1.0D;
        }

        field_4221_k += d1 * 0.10000000000000001D;
        field_4221_k *= 0.80000000000000004D;
        field_4222_j += field_4221_k;
        double d2 = Math.sin(field_4222_j);
        double d3 = Math.cos(field_4222_j);

        for (int i = 0; i < tileSizeSquare; i++)
        {
            int j = watchIconImageData[i] >> 24 & 0xff;
            int k = watchIconImageData[i] >> 16 & 0xff;
            int l = watchIconImageData[i] >> 8 & 0xff;
            int i1 = watchIconImageData[i] >> 0 & 0xff;

            if (k == i1 && l == 0 && i1 > 0)
            {
                double d4 = -((double)(i % tileSizeBase) / (double)tileSizeMask - 0.5D);
                double d5 = (double)(i / tileSizeBase) / (double)tileSizeMask - 0.5D;
                int i2 = k;
                int j2 = (int)((d4 * d3 + d5 * d2 + 0.5D) * (double)tileSizeBase);
                int k2 = (int)(((d5 * d3 - d4 * d2) + 0.5D) * (double)tileSizeBase);
                int l2 = (j2 & tileSizeMask) + (k2 & tileSizeMask) * tileSizeBase;
                j = dialImageData[l2] >> 24 & 0xff;
                k = ((dialImageData[l2] >> 16 & 0xff) * k) / 255;
                l = ((dialImageData[l2] >> 8 & 0xff) * i2) / 255;
                i1 = ((dialImageData[l2] >> 0 & 0xff) * i2) / 255;
            }

            if (anaglyphEnabled)
            {
                int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
                int k1 = (k * 30 + l * 70) / 100;
                int l1 = (k * 30 + i1 * 70) / 100;
                k = j1;
                l = k1;
                i1 = l1;
            }

            imageData[i * 4 + 0] = (byte)k;
            imageData[i * 4 + 1] = (byte)l;
            imageData[i * 4 + 2] = (byte)i1;
            imageData[i * 4 + 3] = (byte)j;
        }
    }
}

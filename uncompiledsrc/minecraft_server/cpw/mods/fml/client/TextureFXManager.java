package cpw.mods.fml.client;

import com.google.common.collect.Maps;
import cpw.mods.fml.client.TextureFXManager$1;
import cpw.mods.fml.client.TextureFXManager$TextureHolder;
import cpw.mods.fml.common.FMLLog;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public class TextureFXManager
{
    private static final TextureFXManager INSTANCE = new TextureFXManager();
    private Minecraft client;
    private Map texturesById = Maps.newHashMap();
    private Map texturesByName = Maps.newHashMap();
    private TextureHelper helper;

    void setClient(Minecraft var1)
    {
        this.client = var1;
    }

    public BufferedImage loadImageFromTexturePack(bge var1, String var2) throws IOException
    {
        InputStream var3 = this.client.D.e().a(var2);

        if (var3 == null)
        {
            throw new RuntimeException(String.format("The requested image path %s is not found", new Object[] {var2}));
        }
        else
        {
            BufferedImage var4 = ImageIO.read(var3);

            if (var4 == null)
            {
                throw new RuntimeException(String.format("The requested image path %s appears to be corrupted", new Object[] {var2}));
            }
            else
            {
                return var4;
            }
        }
    }

    public static TextureFXManager instance()
    {
        return INSTANCE;
    }

    public void fixTransparency(BufferedImage var1, String var2)
    {
        if (var2.matches("^/mob/.*_eyes.*.png$"))
        {
            for (int var3 = 0; var3 < var1.getWidth(); ++var3)
            {
                for (int var4 = 0; var4 < var1.getHeight(); ++var4)
                {
                    int var5 = var1.getRGB(var3, var4);

                    if ((var5 & -16777216) == 0 && var5 != 0)
                    {
                        var1.setRGB(var3, var4, 0);
                    }
                }
            }
        }
    }

    public void bindTextureToName(String var1, int var2)
    {
        TextureFXManager$TextureHolder var3 = new TextureFXManager$TextureHolder(this, (TextureFXManager$1)null);
        TextureFXManager$TextureHolder.access$102(var3, var2);
        TextureFXManager$TextureHolder.access$202(var3, var1);
        this.texturesById.put(Integer.valueOf(var2), var3);
        this.texturesByName.put(var1, var3);
    }

    public void setTextureDimensions(int var1, int var2, int var3)
    {
        TextureFXManager$TextureHolder var4 = (TextureFXManager$TextureHolder)this.texturesById.get(Integer.valueOf(var1));

        if (var4 != null)
        {
            TextureFXManager$TextureHolder.access$302(var4, var2);
            TextureFXManager$TextureHolder.access$402(var4, var3);
        }
    }

    public Dimension getTextureDimensions(String var1)
    {
        return this.texturesByName.containsKey(var1) ? new Dimension(TextureFXManager$TextureHolder.access$300((TextureFXManager$TextureHolder)this.texturesByName.get(var1)), TextureFXManager$TextureHolder.access$400((TextureFXManager$TextureHolder)this.texturesByName.get(var1))) : new Dimension(1, 1);
    }

    public TextureHelper getHelper()
    {
        if (this.helper == null)
        {
            ContextCapabilities var1 = GLContext.getCapabilities();
            boolean var2 = false;

            try
            {
                var2 = var1.getClass().getField("GL_ARB_copy_image").getBoolean(var1);
            }
            catch (Exception var4)
            {
                FMLLog.info("Forge Mod Loader has detected an older LWJGL version, new advanced texture animation features are disabled", new Object[0]);
            }

            FMLLog.info("Not using advanced OpenGL 4.3 advanced capability for animations : OpenGL 4.3 is %s", new Object[] {var2 ? "available" : "not available"});
            this.helper = new CopySubimageTextureHelper();
        }

        return this.helper;
    }
}

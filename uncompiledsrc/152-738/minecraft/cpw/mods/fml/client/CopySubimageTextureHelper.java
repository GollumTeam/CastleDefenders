package cpw.mods.fml.client;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureStitched;
import org.lwjgl.opengl.GL11;

public class CopySubimageTextureHelper extends TextureHelper
{
    public void doTextureCopy(Texture var1, Texture var2, int var3, int var4)
    {
        if (var1.getGlTextureId() != -1)
        {
            var1.bindTexture(0);
            ByteBuffer var5 = var2.getTextureData();
            var5.position(0);
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, var3, var4, var2.getWidth(), var2.getHeight(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, var5);
        }
    }

    public void doTextureUpload(TextureStitched var1) {}
}

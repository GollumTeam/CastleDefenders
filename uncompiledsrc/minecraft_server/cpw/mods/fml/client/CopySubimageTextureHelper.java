package cpw.mods.fml.client;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;

public class CopySubimageTextureHelper extends TextureHelper
{
    public void doTextureCopy(bio var1, bio var2, int var3, int var4)
    {
        if (var1.c() != -1)
        {
            var1.a(0);
            ByteBuffer var5 = var2.h();
            var5.position(0);
            GL11.glTexSubImage2D(3553, 0, var3, var4, var2.d(), var2.e(), 6408, 5121, var5);
        }
    }

    public void doTextureUpload(bil var1) {}
}

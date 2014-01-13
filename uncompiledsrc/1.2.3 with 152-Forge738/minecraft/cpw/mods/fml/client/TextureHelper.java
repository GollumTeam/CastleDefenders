package cpw.mods.fml.client;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureStitched;

public abstract class TextureHelper
{
    public abstract void doTextureCopy(Texture var1, Texture var2, int var3, int var4);

    public abstract void doTextureUpload(TextureStitched var1);

    public void rotateTexture(Texture var1, ByteBuffer var2)
    {
        ByteBuffer var3 = var2;
        var2.position(0);
        ByteBuffer var4 = ByteBuffer.allocateDirect(var2.capacity());
        var4.position(0);
        int var5 = var1.getHeight();
        int var6 = var1.getWidth();

        for (int var7 = 0; var7 < var5; ++var7)
        {
            int var8 = var5 - var7 - 1;
            int var9 = var7 * var6;

            for (int var10 = 0; var10 < var6; ++var10)
            {
                int var11 = var10 * var5 + var8;
                int var12 = var9 + var10;
                var12 <<= 2;
                var11 <<= 2;
                var4.put(var11 + 0, var3.get(var12 + 0));
                var4.put(var11 + 1, var3.get(var12 + 1));
                var4.put(var11 + 2, var3.get(var12 + 2));
                var4.put(var11 + 3, var3.get(var12 + 3));
            }
        }

        var2.position(0);
        var2.put(var4);
    }
}

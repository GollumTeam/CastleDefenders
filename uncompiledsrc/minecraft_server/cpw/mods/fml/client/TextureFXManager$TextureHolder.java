package cpw.mods.fml.client;

import cpw.mods.fml.client.TextureFXManager$1;

class TextureFXManager$TextureHolder
{
    private int textureId;
    private String textureName;
    private int x;
    private int y;

    final TextureFXManager this$0;

    private TextureFXManager$TextureHolder(TextureFXManager var1)
    {
        this.this$0 = var1;
    }

    TextureFXManager$TextureHolder(TextureFXManager var1, TextureFXManager$1 var2)
    {
        this(var1);
    }

    static int access$102(TextureFXManager$TextureHolder var0, int var1)
    {
        return var0.textureId = var1;
    }

    static String access$202(TextureFXManager$TextureHolder var0, String var1)
    {
        return var0.textureName = var1;
    }

    static int access$302(TextureFXManager$TextureHolder var0, int var1)
    {
        return var0.x = var1;
    }

    static int access$402(TextureFXManager$TextureHolder var0, int var1)
    {
        return var0.y = var1;
    }

    static int access$300(TextureFXManager$TextureHolder var0)
    {
        return var0.x;
    }

    static int access$400(TextureFXManager$TextureHolder var0)
    {
        return var0.y;
    }
}

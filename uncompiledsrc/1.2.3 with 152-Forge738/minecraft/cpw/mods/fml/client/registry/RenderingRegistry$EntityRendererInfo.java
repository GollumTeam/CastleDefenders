package cpw.mods.fml.client.registry;

import net.minecraft.client.renderer.entity.Render;

class RenderingRegistry$EntityRendererInfo
{
    private Class target;
    private Render renderer;

    public RenderingRegistry$EntityRendererInfo(Class var1, Render var2)
    {
        this.target = var1;
        this.renderer = var2;
    }

    static Class access$000(RenderingRegistry$EntityRendererInfo var0)
    {
        return var0.target;
    }

    static Render access$100(RenderingRegistry$EntityRendererInfo var0)
    {
        return var0.renderer;
    }
}

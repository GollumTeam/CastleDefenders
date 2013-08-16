package cpw.mods.fml.client.registry;

class RenderingRegistry$EntityRendererInfo
{
    private Class target;
    private bgz renderer;

    public RenderingRegistry$EntityRendererInfo(Class var1, bgz var2)
    {
        this.target = var1;
        this.renderer = var2;
    }

    static Class access$000(RenderingRegistry$EntityRendererInfo var0)
    {
        return var0.target;
    }

    static bgz access$100(RenderingRegistry$EntityRendererInfo var0)
    {
        return var0.renderer;
    }
}

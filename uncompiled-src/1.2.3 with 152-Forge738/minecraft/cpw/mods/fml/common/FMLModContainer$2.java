package cpw.mods.fml.common;

import com.google.common.base.Function;

class FMLModContainer$2 implements Function
{
    final FMLModContainer this$0;

    FMLModContainer$2(FMLModContainer var1)
    {
        this.this$0 = var1;
    }

    public Object apply(ModContainer var1)
    {
        return var1.getMetadata();
    }

    public Object apply(Object var1)
    {
        return this.apply((ModContainer)var1);
    }
}

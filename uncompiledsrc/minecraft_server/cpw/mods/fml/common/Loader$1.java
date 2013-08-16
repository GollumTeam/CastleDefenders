package cpw.mods.fml.common;

import com.google.common.base.Function;

class Loader$1 implements Function
{
    final Loader this$0;

    Loader$1(Loader var1)
    {
        this.this$0 = var1;
    }

    public Boolean apply(String var1)
    {
        return Boolean.valueOf(Boolean.parseBoolean(var1));
    }

    public Object apply(Object var1)
    {
        return this.apply((String)var1);
    }
}

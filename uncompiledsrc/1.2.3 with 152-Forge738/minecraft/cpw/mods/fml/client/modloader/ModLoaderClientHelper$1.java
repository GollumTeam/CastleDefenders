package cpw.mods.fml.client.modloader;

import com.google.common.base.Supplier;
import java.util.Collection;
import java.util.Collections;

class ModLoaderClientHelper$1 implements Supplier
{
    final ModLoaderClientHelper this$0;

    ModLoaderClientHelper$1(ModLoaderClientHelper var1)
    {
        this.this$0 = var1;
    }

    public Collection get()
    {
        return Collections.singleton(new ModLoaderKeyBindingHandler());
    }

    public Object get()
    {
        return this.get();
    }
}

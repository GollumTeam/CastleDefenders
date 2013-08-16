package cpw.mods.fml.client.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import cpw.mods.fml.client.registry.KeyBindingRegistry$KeyHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class KeyBindingRegistry
{
    private static final KeyBindingRegistry INSTANCE = new KeyBindingRegistry();
    private Set keyHandlers = Sets.newLinkedHashSet();

    public static void registerKeyBinding(KeyBindingRegistry$KeyHandler var0)
    {
        instance().keyHandlers.add(var0);

        if (!KeyBindingRegistry$KeyHandler.access$000(var0))
        {
            TickRegistry.registerTickHandler(var0, Side.CLIENT);
        }
    }

    @Deprecated
    public static KeyBindingRegistry instance()
    {
        return INSTANCE;
    }

    public void uploadKeyBindingsToGame(avy var1)
    {
        ArrayList var2 = Lists.newArrayList();
        Iterator var3 = this.keyHandlers.iterator();

        while (var3.hasNext())
        {
            KeyBindingRegistry$KeyHandler var4 = (KeyBindingRegistry$KeyHandler)var3.next();
            ava[] var5 = var4.keyBindings;
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7)
            {
                ava var8 = var5[var7];
                var2.add(var8);
            }
        }

        ava[] var9 = (ava[])var2.toArray(new ava[var2.size()]);
        ava[] var10 = new ava[var1.W.length + var9.length];
        System.arraycopy(var1.W, 0, var10, 0, var1.W.length);
        System.arraycopy(var9, 0, var10, var1.W.length, var9.length);
        var1.W = var10;
        var1.a();
    }
}

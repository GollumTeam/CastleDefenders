package cpw.mods.fml.client.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import cpw.mods.fml.client.registry.KeyBindingRegistry$KeyHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

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

    public void uploadKeyBindingsToGame(GameSettings var1)
    {
        ArrayList var2 = Lists.newArrayList();
        Iterator var3 = this.keyHandlers.iterator();

        while (var3.hasNext())
        {
            KeyBindingRegistry$KeyHandler var4 = (KeyBindingRegistry$KeyHandler)var3.next();
            KeyBinding[] var5 = var4.keyBindings;
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7)
            {
                KeyBinding var8 = var5[var7];
                var2.add(var8);
            }
        }

        KeyBinding[] var9 = (KeyBinding[])var2.toArray(new KeyBinding[var2.size()]);
        KeyBinding[] var10 = new KeyBinding[var1.keyBindings.length + var9.length];
        System.arraycopy(var1.keyBindings, 0, var10, 0, var1.keyBindings.length);
        System.arraycopy(var9, 0, var10, var1.keyBindings.length, var9.length);
        var1.keyBindings = var10;
        var1.loadOptions();
    }
}

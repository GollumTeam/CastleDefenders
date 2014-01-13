package cpw.mods.fml.client.registry;

import cpw.mods.fml.common.ITickHandler;
import java.util.EnumSet;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public abstract class KeyBindingRegistry$KeyHandler implements ITickHandler
{
    protected KeyBinding[] keyBindings;
    protected boolean[] keyDown;
    protected boolean[] repeatings;
    private boolean isDummy;

    static final boolean $assertionsDisabled = !KeyBindingRegistry.class.desiredAssertionStatus();

    public KeyBindingRegistry$KeyHandler(KeyBinding[] var1, boolean[] var2)
    {
        if (!$assertionsDisabled && var1.length != var2.length)
        {
            throw new AssertionError("You need to pass two arrays of identical length");
        }
        else
        {
            this.keyBindings = var1;
            this.repeatings = var2;
            this.keyDown = new boolean[var1.length];
        }
    }

    public KeyBindingRegistry$KeyHandler(KeyBinding[] var1)
    {
        this.keyBindings = var1;
        this.isDummy = true;
    }

    public KeyBinding[] getKeyBindings()
    {
        return this.keyBindings;
    }

    public final void tickStart(EnumSet var1, Object ... var2)
    {
        this.keyTick(var1, false);
    }

    public final void tickEnd(EnumSet var1, Object ... var2)
    {
        this.keyTick(var1, true);
    }

    private void keyTick(EnumSet var1, boolean var2)
    {
        for (int var3 = 0; var3 < this.keyBindings.length; ++var3)
        {
            KeyBinding var4 = this.keyBindings[var3];
            int var5 = var4.keyCode;
            boolean var6 = var5 < 0 ? Mouse.isButtonDown(var5 + 100) : Keyboard.isKeyDown(var5);

            if (var6 != this.keyDown[var3] || var6 && this.repeatings[var3])
            {
                if (var6)
                {
                    this.keyDown(var1, var4, var2, var6 != this.keyDown[var3]);
                }
                else
                {
                    this.keyUp(var1, var4, var2);
                }

                if (var2)
                {
                    this.keyDown[var3] = var6;
                }
            }
        }
    }

    public abstract void keyDown(EnumSet var1, KeyBinding var2, boolean var3, boolean var4);

    public abstract void keyUp(EnumSet var1, KeyBinding var2, boolean var3);

    public abstract EnumSet ticks();

    static boolean access$000(KeyBindingRegistry$KeyHandler var0)
    {
        return var0.isDummy;
    }
}

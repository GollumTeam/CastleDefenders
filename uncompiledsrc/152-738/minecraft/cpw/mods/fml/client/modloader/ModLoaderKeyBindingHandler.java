package cpw.mods.fml.client.modloader;

import com.google.common.collect.ObjectArrays;
import com.google.common.primitives.Booleans;
import cpw.mods.fml.client.registry.KeyBindingRegistry$KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.modloader.ModLoaderModContainer;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.client.settings.KeyBinding;

public class ModLoaderKeyBindingHandler extends KeyBindingRegistry$KeyHandler
{
    private ModLoaderModContainer modContainer;
    private List helper;
    private boolean[] active = new boolean[0];
    private boolean[] mlRepeats = new boolean[0];
    private boolean[] armed = new boolean[0];

    public ModLoaderKeyBindingHandler()
    {
        super(new KeyBinding[0], new boolean[0]);
    }

    void setModContainer(ModLoaderModContainer var1)
    {
        this.modContainer = var1;
    }

    public void fireKeyEvent(KeyBinding var1)
    {
        ((BaseMod)this.modContainer.getMod()).keyboardEvent(var1);
    }

    public void keyDown(EnumSet var1, KeyBinding var2, boolean var3, boolean var4)
    {
        if (var3)
        {
            int var5 = this.helper.indexOf(var2);

            if (var1.contains(TickType.CLIENT))
            {
                this.armed[var5] = true;
            }

            if (this.armed[var5] && var1.contains(TickType.RENDER) && (!this.active[var5] || this.mlRepeats[var5]))
            {
                this.fireKeyEvent(var2);
                this.active[var5] = true;
                this.armed[var5] = false;
            }
        }
    }

    public void keyUp(EnumSet var1, KeyBinding var2, boolean var3)
    {
        if (var3)
        {
            int var4 = this.helper.indexOf(var2);
            this.active[var4] = false;
        }
    }

    public EnumSet ticks()
    {
        return EnumSet.of(TickType.CLIENT, TickType.RENDER);
    }

    public String getLabel()
    {
        return this.modContainer.getModId() + " KB " + this.keyBindings[0].keyCode;
    }

    void addKeyBinding(KeyBinding var1, boolean var2)
    {
        this.keyBindings = (KeyBinding[])ObjectArrays.concat(this.keyBindings, var1);
        this.repeatings = new boolean[this.keyBindings.length];
        Arrays.fill(this.repeatings, true);
        this.active = new boolean[this.keyBindings.length];
        this.armed = new boolean[this.keyBindings.length];
        this.mlRepeats = Booleans.concat(new boolean[][] {this.mlRepeats, {var2}});
        this.keyDown = new boolean[this.keyBindings.length];
        this.helper = Arrays.asList(this.keyBindings);
    }
}

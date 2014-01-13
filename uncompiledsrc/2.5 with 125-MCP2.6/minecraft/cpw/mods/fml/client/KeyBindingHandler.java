package cpw.mods.fml.client;

import cpw.mods.fml.common.IKeyHandler;
import cpw.mods.fml.common.ModContainer;
import net.minecraft.src.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyBindingHandler implements IKeyHandler
{
    private boolean shouldRepeat;
    private KeyBinding keyBinding;
    private ModContainer modContainer;
    private boolean lastState;

    public KeyBindingHandler(KeyBinding keybinding, boolean flag, ModContainer modcontainer)
    {
        lastState = false;
        keyBinding = keybinding;
        shouldRepeat = flag;
        modContainer = modcontainer;
    }

    public Object getKeyBinding()
    {
        return keyBinding;
    }

    public ModContainer getOwningContainer()
    {
        return modContainer;
    }

    public void onEndTick()
    {
        int i = keyBinding.keyCode;
        boolean flag = i >= 0 ? Keyboard.isKeyDown(i) : Mouse.isButtonDown(i + 100);

        if (flag && (!lastState || lastState && shouldRepeat))
        {
            modContainer.keyBindEvent(keyBinding);
        }

        lastState = flag;
    }
}

package cpw.mods.fml.common.event;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;

public class FMLInterModComms$IMCEvent extends FMLEvent
{
    private ModContainer activeContainer;
    private ImmutableList currentList;

    public void applyModContainer(ModContainer var1)
    {
        this.activeContainer = var1;
        this.currentList = null;
        FMLLog.finest("Attempting to deliver %d IMC messages to mod %s", new Object[] {Integer.valueOf(FMLInterModComms.access$000().get(var1.getModId()).size()), var1.getModId()});
    }

    public ImmutableList getMessages()
    {
        if (this.currentList == null)
        {
            this.currentList = ImmutableList.copyOf(FMLInterModComms.access$000().removeAll(this.activeContainer.getModId()));
        }

        return this.currentList;
    }
}

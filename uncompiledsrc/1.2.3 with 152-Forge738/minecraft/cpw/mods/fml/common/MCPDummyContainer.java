package cpw.mods.fml.common;

import com.google.common.eventbus.EventBus;

public class MCPDummyContainer extends DummyModContainer
{
    public MCPDummyContainer(ModMetadata var1)
    {
        super(var1);
    }

    public boolean registerBus(EventBus var1, LoadController var2)
    {
        return true;
    }
}

package cpw.mods.fml.common.event;

import cpw.mods.fml.common.LoaderState$ModState;
import cpw.mods.fml.common.ModClassLoader;
import cpw.mods.fml.common.discovery.ASMDataTable;

public class FMLConstructionEvent extends FMLStateEvent
{
    private ModClassLoader modClassLoader;
    private ASMDataTable asmData;

    public FMLConstructionEvent(Object ... var1)
    {
        super(new Object[0]);
        this.modClassLoader = (ModClassLoader)var1[0];
        this.asmData = (ASMDataTable)var1[1];
    }

    public ModClassLoader getModClassLoader()
    {
        return this.modClassLoader;
    }

    public LoaderState$ModState getModState()
    {
        return LoaderState$ModState.CONSTRUCTED;
    }

    public ASMDataTable getASMHarvestedData()
    {
        return this.asmData;
    }
}

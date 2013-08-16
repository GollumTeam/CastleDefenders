package cpw.mods.fml.common;

import com.google.common.base.Throwables;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.event.FMLStateEvent;

public enum LoaderState
{
    NOINIT("Uninitialized", (Class)null),
    LOADING("Loading", (Class)null),
    CONSTRUCTING("Constructing mods", FMLConstructionEvent.class),
    PREINITIALIZATION("Pre-initializing mods", FMLPreInitializationEvent.class),
    INITIALIZATION("Initializing mods", FMLInitializationEvent.class),
    POSTINITIALIZATION("Post-initializing mods", FMLPostInitializationEvent.class),
    AVAILABLE("Mod loading complete", FMLLoadCompleteEvent.class),
    SERVER_ABOUT_TO_START("Server about to start", FMLServerAboutToStartEvent.class),
    SERVER_STARTING("Server starting", FMLServerStartingEvent.class),
    SERVER_STARTED("Server started", FMLServerStartedEvent.class),
    SERVER_STOPPING("Server stopping", FMLServerStoppingEvent.class),
    SERVER_STOPPED("Server stopped", FMLServerStoppedEvent.class),
    ERRORED("Mod Loading errored", (Class)null);
    private Class eventClass;
    private String name;

    private LoaderState(String var3, Class var4)
    {
        this.name = var3;
        this.eventClass = var4;
    }

    public LoaderState transition(boolean var1)
    {
        return var1 ? ERRORED : (this == SERVER_STOPPED ? AVAILABLE : values()[this.ordinal() < values().length ? this.ordinal() + 1 : this.ordinal()]);
    }

    public boolean hasEvent()
    {
        return this.eventClass != null;
    }

    public FMLStateEvent getEvent(Object ... var1)
    {
        try
        {
            return (FMLStateEvent)this.eventClass.getConstructor(new Class[] {Object[].class}).newInstance(new Object[] {var1});
        }
        catch (Exception var3)
        {
            throw Throwables.propagate(var3);
        }
    }

    public LoaderState requiredState()
    {
        return this == NOINIT ? NOINIT : values()[this.ordinal() - 1];
    }
}

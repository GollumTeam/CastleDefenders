package cpw.mods.fml.common;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ModContainer
{
    public static final class SourceType extends Enum
    {
        public static final SourceType JAR;
        public static final SourceType CLASSPATH;
        public static final SourceType DIR;
        private static final SourceType $VALUES[];

        public static SourceType[] values()
        {
            return (SourceType[])$VALUES.clone();
        }

        public static SourceType valueOf(String s)
        {
            return (SourceType)Enum.valueOf(cpw.mods.fml.common.ModContainer$SourceType.class, s);
        }

        static
        {
            JAR = new SourceType("JAR", 0);
            CLASSPATH = new SourceType("CLASSPATH", 1);
            DIR = new SourceType("DIR", 2);
            $VALUES = (new SourceType[]
                    {
                        JAR, CLASSPATH, DIR
                    });
        }

        private SourceType(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class ModState extends Enum
    {
        public static final ModState UNLOADED;
        public static final ModState LOADED;
        public static final ModState PREINITIALIZED;
        public static final ModState INITIALIZED;
        public static final ModState POSTINITIALIZED;
        public static final ModState AVAILABLE;
        private String label;
        private static final ModState $VALUES[];

        public static ModState[] values()
        {
            return (ModState[])$VALUES.clone();
        }

        public static ModState valueOf(String s)
        {
            return (ModState)Enum.valueOf(cpw.mods.fml.common.ModContainer$ModState.class, s);
        }

        public String toString()
        {
            return label;
        }

        static
        {
            UNLOADED = new ModState("UNLOADED", 0, "Unloaded");
            LOADED = new ModState("LOADED", 1, "Loaded");
            PREINITIALIZED = new ModState("PREINITIALIZED", 2, "Pre-initialized");
            INITIALIZED = new ModState("INITIALIZED", 3, "Initialized");
            POSTINITIALIZED = new ModState("POSTINITIALIZED", 4, "Post-initialized");
            AVAILABLE = new ModState("AVAILABLE", 5, "Available");
            $VALUES = (new ModState[]
                    {
                        UNLOADED, LOADED, PREINITIALIZED, INITIALIZED, POSTINITIALIZED, AVAILABLE
                    });
        }

        private ModState(String s, int i, String s1)
        {
            super(s, i);
            label = s1;
        }
    }

    public abstract boolean wantsPreInit();

    public abstract boolean wantsPostInit();

    public abstract void preInit();

    public abstract void init();

    public abstract void postInit();

    public abstract String getName();

    public abstract ModState getModState();

    public abstract void nextState();

    public abstract boolean matches(Object obj);

    public abstract File getSource();

    public abstract String getSortingRules();

    public abstract Object getMod();

    public abstract int lookupFuelValue(int i, int j);

    public abstract boolean wantsPickupNotification();

    public abstract IPickupNotifier getPickupNotifier();

    public abstract boolean wantsToDispense();

    public abstract IDispenseHandler getDispenseHandler();

    public abstract boolean wantsCraftingNotification();

    public abstract ICraftingHandler getCraftingHandler();

    public abstract List getDependencies();

    public abstract List getPreDepends();

    public abstract List getPostDepends();

    public abstract boolean wantsNetworkPackets();

    public abstract INetworkHandler getNetworkHandler();

    public abstract boolean ownsNetworkChannel(String s);

    public abstract boolean wantsConsoleCommands();

    public abstract IConsoleHandler getConsoleHandler();

    public abstract boolean wantsPlayerTracking();

    public abstract IPlayerTracker getPlayerTracker();

    public abstract List getKeys();

    public abstract SourceType getSourceType();

    public abstract void setSourceType(SourceType sourcetype);

    public abstract ModMetadata getMetadata();

    public abstract void setMetadata(ModMetadata modmetadata);

    public abstract void gatherRenderers(Map map);

    public abstract void requestAnimations();

    public abstract String getVersion();

    public abstract ProxyInjector findSidedProxy();

    public abstract void keyBindEvent(Object obj);
}

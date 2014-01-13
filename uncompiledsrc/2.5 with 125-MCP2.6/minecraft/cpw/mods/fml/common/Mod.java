package cpw.mods.fml.common;

import java.lang.annotation.Annotation;

public @interface Mod
{
    public static interface PostInit
        extends Annotation
    {
    }

    public static interface Init
        extends Annotation
    {
    }

    public static interface PreInit
        extends Annotation
    {
    }

    public abstract String name();

    public abstract String version();

    public abstract boolean wantsPreInit();

    public abstract boolean wantsPostInit();
}

package net.minecraft.src;

import java.lang.annotation.Annotation;

public @interface SidedProxy
{
    public abstract String clientSide();

    public abstract String serverSide();

    public abstract String bukkitSide();
}

package cpw.mods.fml.client;

import java.awt.Dimension;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.TexturePackBase;

public interface ITextureFX
{
    public abstract void onTexturePackChanged(RenderEngine renderengine, TexturePackBase texturepackbase, Dimension dimension);

    public abstract void onTextureDimensionsUpdate(int i, int j);

    public abstract void setErrored(boolean flag);

    public abstract boolean getErrored();
}

package cpw.mods.fml.client;

import java.awt.Dimension;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.texturepacks.ITexturePack;

public interface ITextureFX
{
    void onTexturePackChanged(RenderEngine var1, ITexturePack var2, Dimension var3);

    void onTextureDimensionsUpdate(int var1, int var2);

    void setErrored(boolean var1);

    boolean getErrored();
}

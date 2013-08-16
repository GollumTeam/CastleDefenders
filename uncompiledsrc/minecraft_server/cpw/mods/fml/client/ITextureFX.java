package cpw.mods.fml.client;

import java.awt.Dimension;

public interface ITextureFX
{
    void onTexturePackChanged(bge var1, bjt var2, Dimension var3);

    void onTextureDimensionsUpdate(int var1, int var2);

    void setErrored(boolean var1);

    boolean getErrored();
}

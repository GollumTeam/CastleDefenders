package cpw.mods.fml.client;

import cpw.mods.fml.common.FMLCommonHandler;
import java.awt.Dimension;
import java.util.logging.Logger;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.TextureFX;
import net.minecraft.src.TexturePackBase;

public class FMLTextureFX extends TextureFX implements ITextureFX
{
    public int tileSizeBase;
    public int tileSizeSquare;
    public int tileSizeMask;
    public int tileSizeSquareMask;
    public boolean errored;
    protected Logger log;

    public FMLTextureFX(int i)
    {
        super(i);
        tileSizeBase = 16;
        tileSizeSquare = 256;
        tileSizeMask = 15;
        tileSizeSquareMask = 255;
        errored = false;
        log = FMLCommonHandler.instance().getFMLLogger();
    }

    public void setErrored(boolean flag)
    {
        errored = flag;
    }

    public boolean getErrored()
    {
        return errored;
    }

    public void onTexturePackChanged(RenderEngine renderengine, TexturePackBase texturepackbase, Dimension dimension)
    {
        onTextureDimensionsUpdate(dimension.width, dimension.height);
    }

    public void onTextureDimensionsUpdate(int i, int j)
    {
        tileSizeBase = i >> 4;
        tileSizeSquare = tileSizeBase * tileSizeBase;
        tileSizeMask = tileSizeBase - 1;
        tileSizeSquareMask = tileSizeSquare - 1;
        setErrored(false);
        setup();
    }

    protected void setup()
    {
        imageData = new byte[tileSizeSquare << 2];
    }

    public boolean unregister(RenderEngine renderengine, java.util.List list)
    {
        list.remove(this);
        return true;
    }
}

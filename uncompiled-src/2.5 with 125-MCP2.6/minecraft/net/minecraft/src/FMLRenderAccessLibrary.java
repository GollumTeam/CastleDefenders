package net.minecraft.src;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FMLRenderAccessLibrary
{
    public FMLRenderAccessLibrary()
    {
    }

    public static Logger getLogger()
    {
        return FMLCommonHandler.instance().getFMLLogger();
    }

    public static void log(Level level, String s)
    {
        getLogger().log(level, s);
    }

    public static void log(Level level, String s, Throwable throwable)
    {
        getLogger().log(level, s, throwable);
    }

    public static void setTextureDimensions(int i, int j, int k, List list)
    {
        FMLClientHandler.instance().setTextureDimensions(i, j, k, list);
    }

    public static void preRegisterEffect(TextureFX texturefx)
    {
        FMLClientHandler.instance().onPreRegisterEffect(texturefx);
    }

    public static boolean onUpdateTextureEffect(TextureFX texturefx)
    {
        return FMLClientHandler.instance().onUpdateTextureEffect(texturefx);
    }

    public static java.awt.Dimension getTextureDimensions(TextureFX texturefx)
    {
        return FMLClientHandler.instance().getTextureDimensions(texturefx);
    }

    public static void onTexturePackChange(RenderEngine renderengine, TexturePackBase texturepackbase, List list)
    {
        FMLClientHandler.instance().onTexturePackChange(renderengine, texturepackbase, list);
    }

    public static boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
    {
        return FMLClientHandler.instance().renderWorldBlock(renderblocks, iblockaccess, i, j, k, block, l);
    }

    public static void renderInventoryBlock(RenderBlocks renderblocks, Block block, int i, int j)
    {
        FMLClientHandler.instance().renderInventoryBlock(renderblocks, block, i, j);
    }

    public static boolean renderItemAsFull3DBlock(int i)
    {
        return FMLClientHandler.instance().renderItemAsFull3DBlock(i);
    }
}

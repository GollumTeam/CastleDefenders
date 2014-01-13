package net.minecraft.client.texturepacks;

import java.io.File;
import net.minecraft.util.IDownloadSuccess;

class TexturePackDownloadSuccess implements IDownloadSuccess
{
    final TexturePackList texturePacks;

    TexturePackDownloadSuccess(TexturePackList par1TexturePackList)
    {
        this.texturePacks = par1TexturePackList;
    }

    public void onSuccess(File par1File)
    {
        if (TexturePackList.isDownloading(this.texturePacks))
        {
            TexturePackList.setSelectedTexturePack(this.texturePacks, new TexturePackCustom(TexturePackList.generateTexturePackID(this.texturePacks, par1File), par1File, TexturePackList.func_98143_h()));
            TexturePackList.getMinecraft(this.texturePacks).scheduleTexturePackRefresh();
        }
    }
}

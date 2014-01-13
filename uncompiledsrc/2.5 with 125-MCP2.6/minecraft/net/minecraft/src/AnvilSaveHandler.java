package net.minecraft.src;

import java.io.File;
import java.util.List;

public class AnvilSaveHandler extends SaveHandler
{
    public AnvilSaveHandler(File par1File, String par2Str, boolean par3)
    {
        super(par1File, par2Str, par3);
    }

    /**
     * Returns the chunk loader with the provided world provider
     */
    public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider)
    {
        File file = getSaveDirectory();

        if (par1WorldProvider.getSaveFolder() != null)
        {
            File file1 = new File(file, par1WorldProvider.getSaveFolder());
            file1.mkdirs();
            return new AnvilChunkLoader(file1);
        }
        else
        {
            return new AnvilChunkLoader(file);
        }
    }

    /**
     * saves level.dat and backs up the existing one to level.dat_old
     */
    public void saveWorldInfoAndPlayer(WorldInfo par1WorldInfo, List par2List)
    {
        par1WorldInfo.setSaveVersion(19133);
        super.saveWorldInfoAndPlayer(par1WorldInfo, par2List);
    }
}

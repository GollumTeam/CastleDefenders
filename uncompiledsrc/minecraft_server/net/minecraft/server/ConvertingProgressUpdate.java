package net.minecraft.server;

import net.minecraft.util.IProgressUpdate;

public class ConvertingProgressUpdate implements IProgressUpdate
{
    private long field_96245_b;

    /** Reference to the MinecraftServer object. */
    final MinecraftServer mcServer;

    public ConvertingProgressUpdate(MinecraftServer par1)
    {
        this.mcServer = par1;
        this.field_96245_b = System.currentTimeMillis();
    }

    /**
     * "Saving level", or the loading,or downloading equivelent
     */
    public void displayProgressMessage(String par1Str) {}

    /**
     * Updates the progress bar on the loading screen to the specified amount. Args: loadProgress
     */
    public void setLoadingProgress(int par1)
    {
        if (System.currentTimeMillis() - this.field_96245_b >= 1000L)
        {
            this.field_96245_b = System.currentTimeMillis();
            this.mcServer.getLogAgent().logInfo("Converting... " + par1 + "%");
        }
    }

    /**
     * This is called with "Working..." by resetProgressAndMessage
     */
    public void resetProgresAndWorkingMessage(String par1Str) {}
}

package net.minecraft.client;

public final class ThreadShutdown extends Thread
{
    public void run()
    {
        Minecraft.stopIntegratedServer();
    }
}

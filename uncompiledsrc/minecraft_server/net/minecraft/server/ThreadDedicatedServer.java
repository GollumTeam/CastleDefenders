package net.minecraft.server;

import net.minecraft.server.dedicated.DedicatedServer;

public final class ThreadDedicatedServer extends Thread
{
    final DedicatedServer field_96244_a;

    public ThreadDedicatedServer(DedicatedServer par1)
    {
        this.field_96244_a = par1;
    }

    public void run()
    {
        this.field_96244_a.stopServer();
    }
}

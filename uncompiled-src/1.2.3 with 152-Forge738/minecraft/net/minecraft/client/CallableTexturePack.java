package net.minecraft.client;

import java.util.concurrent.Callable;

public class CallableTexturePack implements Callable
{
    final Minecraft theMinecraft;

    public CallableTexturePack(Minecraft par1Minecraft)
    {
        this.theMinecraft = par1Minecraft;
    }

    public String callTexturePack()
    {
        return this.theMinecraft.gameSettings.skin;
    }

    public Object call()
    {
        return this.callTexturePack();
    }
}

package forge;

import net.minecraft.src.EntityPlayer;

public interface IChatHandler
{
    public abstract String onServerChat(EntityPlayer entityplayer, String s);

    public abstract boolean onChatCommand(EntityPlayer entityplayer, boolean flag, String s);

    public abstract boolean onServerCommand(Object obj, String s, String s1);

    public abstract String onServerCommandSay(Object obj, String s, String s1);

    public abstract String onClientChatRecv(String s);
}

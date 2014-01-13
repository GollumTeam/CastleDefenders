package cpw.mods.fml.common;

import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerTracker
{
    void onPlayerLogin(EntityPlayer var1);

    void onPlayerLogout(EntityPlayer var1);

    void onPlayerChangedDimension(EntityPlayer var1);

    void onPlayerRespawn(EntityPlayer var1);
}

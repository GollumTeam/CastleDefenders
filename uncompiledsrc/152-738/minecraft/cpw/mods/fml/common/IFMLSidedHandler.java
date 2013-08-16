package cpw.mods.fml.common;

import com.google.common.collect.MapDifference;
import cpw.mods.fml.common.network.EntitySpawnAdjustmentPacket;
import cpw.mods.fml.common.network.EntitySpawnPacket;
import cpw.mods.fml.common.network.ModMissingPacket;
import cpw.mods.fml.common.registry.EntityRegistry$EntityRegistration;
import cpw.mods.fml.relauncher.Side;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.server.MinecraftServer;

public interface IFMLSidedHandler
{
    List getAdditionalBrandingInformation();

    Side getSide();

    void haltGame(String var1, Throwable var2);

    void showGuiScreen(Object var1);

    Entity spawnEntityIntoClientWorld(EntityRegistry$EntityRegistration var1, EntitySpawnPacket var2);

    void adjustEntityLocationOnClient(EntitySpawnAdjustmentPacket var1);

    void beginServerLoading(MinecraftServer var1);

    void finishServerLoading();

    MinecraftServer getServer();

    void sendPacket(Packet var1);

    void displayMissingMods(ModMissingPacket var1);

    void handleTinyPacket(NetHandler var1, Packet131MapData var2);

    void setClientCompatibilityLevel(byte var1);

    byte getClientCompatibilityLevel();

    boolean shouldServerShouldBeKilledQuietly();

    void disconnectIDMismatch(MapDifference var1, NetHandler var2, INetworkManager var3);
}

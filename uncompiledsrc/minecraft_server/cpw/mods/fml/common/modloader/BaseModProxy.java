package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.TickType;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

public interface BaseModProxy
{
    void modsLoaded();

    void load();

    String getName();

    String getPriorities();

    String getVersion();

    boolean doTickInGUI(TickType var1, boolean var2, Object ... var3);

    boolean doTickInGame(TickType var1, boolean var2, Object ... var3);

    void generateSurface(World var1, Random var2, int var3, int var4);

    void generateNether(World var1, Random var2, int var3, int var4);

    int addFuel(int var1, int var2);

    void takenFromCrafting(EntityPlayer var1, ItemStack var2, IInventory var3);

    void takenFromFurnace(EntityPlayer var1, ItemStack var2);

    void onClientLogout(INetworkManager var1);

    void onClientLogin(EntityPlayer var1);

    void serverDisconnect();

    void serverConnect(NetHandler var1);

    void receiveCustomPacket(Packet250CustomPayload var1);

    void clientChat(String var1);

    void onItemPickup(EntityPlayer var1, ItemStack var2);

    void serverCustomPayload(NetServerHandler var1, Packet250CustomPayload var2);

    void serverChat(NetServerHandler var1, String var2);
}

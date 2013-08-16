package cpw.mods.fml.common.modloader;

import com.google.common.collect.Sets;
import cpw.mods.fml.common.network.IGuiHandler;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class ModLoaderGuiHelper implements IGuiHandler
{
    private BaseModProxy mod;
    private Set ids;
    private Container container;
    private int currentID;

    ModLoaderGuiHelper(BaseModProxy var1)
    {
        this.mod = var1;
        this.ids = Sets.newHashSet();
    }

    public Object getServerGuiElement(int var1, EntityPlayer var2, World var3, int var4, int var5, int var6)
    {
        return this.container;
    }

    public Object getClientGuiElement(int var1, EntityPlayer var2, World var3, int var4, int var5, int var6)
    {
        return ModLoaderHelper.getClientSideGui(this.mod, var2, var1, var4, var5, var6);
    }

    public void injectContainerAndID(Container var1, int var2)
    {
        this.container = var1;
        this.currentID = var2;
    }

    public Object getMod()
    {
        return this.mod;
    }

    public void associateId(int var1)
    {
        this.ids.add(Integer.valueOf(var1));
    }
}

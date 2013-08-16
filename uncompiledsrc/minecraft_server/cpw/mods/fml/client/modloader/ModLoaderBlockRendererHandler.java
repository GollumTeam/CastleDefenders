package cpw.mods.fml.client.modloader;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public class ModLoaderBlockRendererHandler implements ISimpleBlockRenderingHandler
{
    private int renderId;
    private boolean render3dInInventory;
    private BaseMod mod;

    public ModLoaderBlockRendererHandler(int var1, boolean var2, BaseMod var3)
    {
        this.renderId = var1;
        this.render3dInInventory = var2;
        this.mod = var3;
    }

    public int getRenderId()
    {
        return this.renderId;
    }

    public boolean shouldRender3DInInventory()
    {
        return this.render3dInInventory;
    }

    public boolean renderWorldBlock(IBlockAccess var1, int var2, int var3, int var4, Block var5, int var6, bgf var7)
    {
        return this.mod.renderWorldBlock(var7, var1, var2, var3, var4, var5, var6);
    }

    public void renderInventoryBlock(Block var1, int var2, int var3, bgf var4)
    {
        this.mod.renderInvBlock(var4, var1, var2, var3);
    }
}

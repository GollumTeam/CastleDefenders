package cpw.mods.fml.client;

import cpw.mods.fml.common.ModContainer;
import net.minecraft.src.*;

public class BlockRenderInfo
{
    private int renderId;
    private boolean render3dInInventory;
    private ModContainer modContainer;

    public BlockRenderInfo(int i, boolean flag, ModContainer modcontainer)
    {
        renderId = i;
        render3dInInventory = flag;
        modContainer = modcontainer;
    }

    public int getRenderId()
    {
        return renderId;
    }

    public boolean shouldRender3DInInventory()
    {
        return render3dInInventory;
    }

    public boolean renderWorldBlock(IBlockAccess iblockaccess, int i, int j, int k, Block block, int l, RenderBlocks renderblocks)
    {
        return ((BaseMod)modContainer.getMod()).renderWorldBlock(renderblocks, iblockaccess, i, j, k, block, l);
    }

    public void renderInventoryBlock(Block block, int i, int j, RenderBlocks renderblocks)
    {
        ((BaseMod)modContainer.getMod()).renderInvBlock(renderblocks, block, i, j);
    }
}

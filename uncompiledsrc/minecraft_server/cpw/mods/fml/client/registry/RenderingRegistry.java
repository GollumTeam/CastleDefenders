package cpw.mods.fml.client.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.ObjectArrays;
import cpw.mods.fml.client.registry.RenderingRegistry$EntityRendererInfo;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public class RenderingRegistry
{
    private static final RenderingRegistry INSTANCE = new RenderingRegistry();
    private int nextRenderId = 40;
    private Map blockRenderers = Maps.newHashMap();
    private List entityRenderers = Lists.newArrayList();

    public static int addNewArmourRendererPrefix(String var0)
    {
        bht.h = (String[])ObjectArrays.concat(bht.h, var0);
        bhg.k = bht.h;
        return bht.h.length - 1;
    }

    public static void registerEntityRenderingHandler(Class var0, bgz var1)
    {
        instance().entityRenderers.add(new RenderingRegistry$EntityRendererInfo(var0, var1));
    }

    public static void registerBlockHandler(ISimpleBlockRenderingHandler var0)
    {
        instance().blockRenderers.put(Integer.valueOf(var0.getRenderId()), var0);
    }

    public static void registerBlockHandler(int var0, ISimpleBlockRenderingHandler var1)
    {
        instance().blockRenderers.put(Integer.valueOf(var0), var1);
    }

    public static int getNextAvailableRenderId()
    {
        RenderingRegistry var10000 = instance();
        int var10002 = var10000.nextRenderId;
        RenderingRegistry var10001 = var10000;
        int var0 = var10000.nextRenderId;
        var10001.nextRenderId = var10002 + 1;
        return var0;
    }

    @Deprecated
    public static int addTextureOverride(String var0, String var1)
    {
        return -1;
    }

    public static void addTextureOverride(String var0, String var1, int var2) {}

    @Deprecated
    public static int getUniqueTextureIndex(String var0)
    {
        return -1;
    }

    @Deprecated
    public static RenderingRegistry instance()
    {
        return INSTANCE;
    }

    public boolean renderWorldBlock(bgf var1, IBlockAccess var2, int var3, int var4, int var5, Block var6, int var7)
    {
        if (!this.blockRenderers.containsKey(Integer.valueOf(var7)))
        {
            return false;
        }
        else
        {
            ISimpleBlockRenderingHandler var8 = (ISimpleBlockRenderingHandler)this.blockRenderers.get(Integer.valueOf(var7));
            return var8.renderWorldBlock(var2, var3, var4, var5, var6, var7, var1);
        }
    }

    public void renderInventoryBlock(bgf var1, Block var2, int var3, int var4)
    {
        if (this.blockRenderers.containsKey(Integer.valueOf(var4)))
        {
            ISimpleBlockRenderingHandler var5 = (ISimpleBlockRenderingHandler)this.blockRenderers.get(Integer.valueOf(var4));
            var5.renderInventoryBlock(var2, var3, var4, var1);
        }
    }

    public boolean renderItemAsFull3DBlock(int var1)
    {
        ISimpleBlockRenderingHandler var2 = (ISimpleBlockRenderingHandler)this.blockRenderers.get(Integer.valueOf(var1));
        return var2 != null && var2.shouldRender3DInInventory();
    }

    public void loadEntityRenderers(Map var1)
    {
        Iterator var2 = this.entityRenderers.iterator();

        while (var2.hasNext())
        {
            RenderingRegistry$EntityRendererInfo var3 = (RenderingRegistry$EntityRendererInfo)var2.next();
            var1.put(RenderingRegistry$EntityRendererInfo.access$000(var3), RenderingRegistry$EntityRendererInfo.access$100(var3));
            RenderingRegistry$EntityRendererInfo.access$100(var3).a(bgy.a);
        }
    }
}

package forge;

import java.util.LinkedList;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.opengl.Display;

public class MinecraftForgeClient
{
    private static IItemRenderer customItemRenderers[];
    private static boolean hasInit = false;

    public MinecraftForgeClient()
    {
    }

    public static void registerHighlightHandler(IHighlightHandler ihighlighthandler)
    {
        ForgeHooksClient.highlightHandlers.add(ihighlighthandler);
    }

    public static void registerRenderContextHandler(String s, int i, IRenderContextHandler irendercontexthandler)
    {
        ForgeHooksClient.registerRenderContextHandler(s, i, irendercontexthandler);
    }

    public static void registerTextureLoadHandler(ITextureLoadHandler itextureloadhandler)
    {
        ForgeHooksClient.textureLoadHandlers.add(itextureloadhandler);
    }

    public static void registerRenderLastHandler(IRenderWorldLastHandler irenderworldlasthandler)
    {
        ForgeHooksClient.renderWorldLastHandlers.add(irenderworldlasthandler);
    }

    public static void registerSoundHandler(ISoundHandler isoundhandler)
    {
        ForgeHooksClient.soundHandlers.add(isoundhandler);
        checkMinecraftVersion("Minecraft Minecraft 1.2.5", "Interface check in registerSoundHandler, remove it Mods should be updated");

        try
        {
            if (isoundhandler.getClass().getDeclaredMethod("onPlaySoundAtEntity", new Class[]
                    {
                        net.minecraft.src.Entity.class, java.lang.String.class, Float.TYPE, Float.TYPE
                    }) != null)
            {
                ForgeHooksClient.soundHandlers2.add(isoundhandler);
            }
        }
        catch (Exception exception)
        {
            if ((forge.MinecraftForgeClient.class).getPackage().getName().equals("net.minecraft.src.forge"))
            {
                exception.printStackTrace();
            }
        }
    }

    public static void bindTexture(String s, int i)
    {
        ForgeHooksClient.bindTexture(s, i);
    }

    public static void bindTexture(String s)
    {
        ForgeHooksClient.bindTexture(s, 0);
    }

    public static void unbindTexture()
    {
        ForgeHooksClient.unbindTexture();
    }

    public static void preloadTexture(String s)
    {
        ModLoader.getMinecraftInstance().renderEngine.getTexture(s);
    }

    public static void renderBlock(RenderBlocks renderblocks, Block block, int i, int j, int k)
    {
        ForgeHooksClient.beforeBlockRender(block, renderblocks);
        renderblocks.renderBlockByRenderType(block, i, j, k);
        ForgeHooksClient.afterBlockRender(block, renderblocks);
    }

    public static int getRenderPass()
    {
        return ForgeHooksClient.renderPass;
    }

    public static void registerItemRenderer(int i, IItemRenderer iitemrenderer)
    {
        customItemRenderers[i] = iitemrenderer;
    }

    public static IItemRenderer getItemRenderer(ItemStack itemstack, IItemRenderer.ItemRenderType itemrendertype)
    {
        IItemRenderer iitemrenderer = customItemRenderers[itemstack.itemID];

        if (iitemrenderer != null && iitemrenderer.handleRenderType(itemstack, itemrendertype))
        {
            return customItemRenderers[itemstack.itemID];
        }
        else
        {
            return null;
        }
    }

    public static void init()
    {
        if (hasInit)
        {
            return;
        }
        else
        {
            hasInit = true;
            ForgeHooks.setPacketHandler(new PacketHandlerClient());
            return;
        }
    }

    public static void checkMinecraftVersion(String s, String s1)
    {
        if (!Display.getTitle().equals(s))
        {
            MinecraftForge.killMinecraft("Minecraft Forge", s1.replaceAll("%version%", Display.getTitle()));
        }
    }

    static
    {
        customItemRenderers = new IItemRenderer[Item.itemsList.length];
        init();
    }
}

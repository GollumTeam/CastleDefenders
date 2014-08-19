package forge;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ForgeHooksClient
{
    private static class TesKey
        implements Comparable
    {
        public int tex;
        public int sub;

        public int compareTo(TesKey teskey)
        {
            if (sub == teskey.sub)
            {
                return tex - teskey.tex;
            }
            else
            {
                return sub - teskey.sub;
            }
        }

        public boolean equals(Object obj)
        {
            return compareTo((TesKey)obj) == 0;
        }

        public int hashCode()
        {
            int i = Integer.valueOf(tex).hashCode();
            int j = Integer.valueOf(sub).hashCode();
            return i + 31 * j;
        }

        public int compareTo(Object obj)
        {
            return compareTo((TesKey)obj);
        }

        public TesKey(int i, int j)
        {
            tex = i;
            sub = j;
        }
    }

    private static Field textureID = null;
    private static boolean textureIDChecked = false;
    public static LinkedList highlightHandlers = new LinkedList();
    public static LinkedList renderWorldLastHandlers = new LinkedList();
    public static LinkedList textureLoadHandlers = new LinkedList();
    public static HashMap tessellators = new HashMap();
    public static HashMap textures = new HashMap();
    public static boolean inWorld = false;
    public static TreeSet renderTextures = new TreeSet();
    public static Tessellator defaultTessellator = null;
    public static HashMap renderHandlers = new HashMap();
    public static IRenderContextHandler unbindContext = null;
    static int renderPass = -1;
    public static LinkedList soundHandlers = new LinkedList();
    public static LinkedList soundHandlers2 = new LinkedList();

    public ForgeHooksClient()
    {
    }

    public static boolean onBlockHighlight(RenderGlobal renderglobal, EntityPlayer entityplayer, MovingObjectPosition movingobjectposition, int i, ItemStack itemstack, float f)
    {
        for (Iterator iterator = highlightHandlers.iterator(); iterator.hasNext();)
        {
            IHighlightHandler ihighlighthandler = (IHighlightHandler)iterator.next();

            if (ihighlighthandler.onBlockHighlight(renderglobal, entityplayer, movingobjectposition, i, itemstack, f))
            {
                return true;
            }
        }

        return false;
    }

    public static void onRenderWorldLast(RenderGlobal renderglobal, float f)
    {
        IRenderWorldLastHandler irenderworldlasthandler;

        for (Iterator iterator = renderWorldLastHandlers.iterator(); iterator.hasNext(); irenderworldlasthandler.onRenderWorldLast(renderglobal, f))
        {
            irenderworldlasthandler = (IRenderWorldLastHandler)iterator.next();
        }
    }

    public static void onTextureLoad(String s, int i)
    {
        ITextureLoadHandler itextureloadhandler;

        for (Iterator iterator = textureLoadHandlers.iterator(); iterator.hasNext(); itextureloadhandler.onTextureLoad(s, i))
        {
            itextureloadhandler = (ITextureLoadHandler)iterator.next();
        }
    }

    public static boolean canRenderInPass(Block block, int i)
    {
        if (block instanceof IMultipassRender)
        {
            return ((IMultipassRender)block).canRenderInPass(i);
        }

        return i == block.getRenderBlockPass();
    }

    protected static void registerRenderContextHandler(String s, int i, IRenderContextHandler irendercontexthandler)
    {
        Integer integer = (Integer)textures.get(s);

        if (integer == null)
        {
            integer = Integer.valueOf(ModLoader.getMinecraftInstance().renderEngine.getTexture(s));
            textures.put(s, integer);
        }

        renderHandlers.put(new TesKey(integer.intValue(), i), irendercontexthandler);
    }

    protected static void bindTessellator(int i, int j)
    {
        TesKey teskey = new TesKey(i, j);
        Tessellator tessellator = (Tessellator)tessellators.get(teskey);

        if (tessellator == null)
        {
            tessellator = new Tessellator();

            if (!textureIDChecked && textureID == null)
            {
                textureIDChecked = true;

                try
                {
                    textureID = (net.minecraft.src.Tessellator.class).getField("textureID");
                }
                catch (NoSuchFieldException nosuchfieldexception) { }
            }

            if (textureID != null)
            {
                tessellator.textureID = i;
            }

            tessellators.put(teskey, tessellator);
        }

        if (inWorld && !renderTextures.contains(teskey))
        {
            renderTextures.add(teskey);
            tessellator.startDrawingQuads();
            tessellator.setTranslation(defaultTessellator.xOffset, defaultTessellator.yOffset, defaultTessellator.zOffset);
        }

        Tessellator.instance = tessellator;
    }

    protected static void bindTexture(String s, int i)
    {
        Integer integer = (Integer)textures.get(s);

        if (integer == null)
        {
            integer = Integer.valueOf(ModLoader.getMinecraftInstance().renderEngine.getTexture(s));
            textures.put(s, integer);
        }

        if (!inWorld)
        {
            if (unbindContext != null)
            {
                unbindContext.afterRenderContext();
                unbindContext = null;
            }

            if (Tessellator.instance.isDrawing)
            {
                int j = Tessellator.instance.drawMode;
                Tessellator.instance.draw();
                Tessellator.instance.startDrawing(j);
            }

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, integer.intValue());
            unbindContext = (IRenderContextHandler)renderHandlers.get(new TesKey(integer.intValue(), i));

            if (unbindContext != null)
            {
                unbindContext.beforeRenderContext();
            }

            return;
        }
        else
        {
            bindTessellator(integer.intValue(), i);
            return;
        }
    }

    protected static void unbindTexture()
    {
        if (inWorld)
        {
            Tessellator.instance = defaultTessellator;
        }
        else
        {
            if (Tessellator.instance.isDrawing)
            {
                int i = Tessellator.instance.drawMode;
                Tessellator.instance.draw();

                if (unbindContext != null)
                {
                    unbindContext.afterRenderContext();
                    unbindContext = null;
                }

                Tessellator.instance.startDrawing(i);
            }

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, ModLoader.getMinecraftInstance().renderEngine.getTexture("/terrain.png"));
            return;
        }
    }

    public static void beforeRenderPass(int i)
    {
        renderPass = i;
        defaultTessellator = Tessellator.instance;
        Tessellator.renderingWorldRenderer = true;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ModLoader.getMinecraftInstance().renderEngine.getTexture("/terrain.png"));
        renderTextures.clear();
        inWorld = true;
    }

    public static void afterRenderPass(int i)
    {
        renderPass = -1;
        inWorld = false;

        for (Iterator iterator = renderTextures.iterator(); iterator.hasNext();)
        {
            TesKey teskey = (TesKey)iterator.next();
            IRenderContextHandler irendercontexthandler = (IRenderContextHandler)renderHandlers.get(teskey);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, teskey.tex);
            Tessellator tessellator = (Tessellator)tessellators.get(teskey);

            if (irendercontexthandler == null)
            {
                tessellator.draw();
            }
            else
            {
                Tessellator.instance = tessellator;
                irendercontexthandler.beforeRenderContext();
                tessellator.draw();
                irendercontexthandler.afterRenderContext();
            }
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ModLoader.getMinecraftInstance().renderEngine.getTexture("/terrain.png"));
        Tessellator.renderingWorldRenderer = false;
        Tessellator.instance = defaultTessellator;
    }

    public static void beforeBlockRender(Block block, RenderBlocks renderblocks)
    {
        if (!block.isDefaultTexture && renderblocks.overrideBlockTexture == -1)
        {
            bindTexture(block.getTextureFile(), 0);
        }
    }

    public static void afterBlockRender(Block block, RenderBlocks renderblocks)
    {
        if (!block.isDefaultTexture && renderblocks.overrideBlockTexture == -1)
        {
            unbindTexture();
        }
    }

    public static void overrideTexture(Object obj)
    {
        if (obj instanceof ITextureProvider)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, ModLoader.getMinecraftInstance().renderEngine.getTexture(((ITextureProvider)obj).getTextureFile()));
        }
    }

    public static String getTexture(String s, Object obj)
    {
        if (obj instanceof ITextureProvider)
        {
            return ((ITextureProvider)obj).getTextureFile();
        }
        else
        {
            return s;
        }
    }

    public static void renderEquippedItem(IItemRenderer iitemrenderer, RenderBlocks renderblocks, EntityLiving entityliving, ItemStack itemstack)
    {
        if (iitemrenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, IItemRenderer.ItemRendererHelper.EQUIPPED_BLOCK))
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            iitemrenderer.renderItem(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, new Object[]
                    {
                        renderblocks, entityliving
                    });
            GL11.glPopMatrix();
        }
        else
        {
            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glTranslatef(0.0F, -0.3F, 0.0F);
            GL11.glScalef(1.5F, 1.5F, 1.5F);
            GL11.glRotatef(50F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(335F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
            iitemrenderer.renderItem(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, new Object[]
                    {
                        renderblocks, entityliving
                    });
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    public static boolean renderEntityItem(EntityItem entityitem, ItemStack itemstack, float f, float f1, Random random, RenderEngine renderengine, RenderBlocks renderblocks)
    {
        IItemRenderer iitemrenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.ENTITY);

        if (iitemrenderer == null)
        {
            return false;
        }

        if (iitemrenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.ENTITY, itemstack, IItemRenderer.ItemRendererHelper.ENTITY_ROTATION))
        {
            GL11.glRotatef(f1, 0.0F, 1.0F, 0.0F);
        }

        if (!iitemrenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.ENTITY, itemstack, IItemRenderer.ItemRendererHelper.ENTITY_BOBBING))
        {
            GL11.glTranslatef(0.0F, -f, 0.0F);
        }

        boolean flag = iitemrenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.ENTITY, itemstack, IItemRenderer.ItemRendererHelper.BLOCK_3D);

        if (itemstack.itemID < 256 && (flag || RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType())))
        {
            renderengine.bindTexture(renderengine.getTexture(itemstack.getItem().getTextureFile()));
            int i = Block.blocksList[itemstack.itemID].getRenderType();
            float f2 = i != 1 && i != 19 && i != 12 && i != 2 ? 0.25F : 0.5F;
            GL11.glScalef(f2, f2, f2);
            int j = entityitem.item.stackSize;
            byte byte0 = j <= 20 ? j <= 5 ? ((byte)(j <= 1 ? 1 : 2)) : 3 : 4;

            for (int k = 0; k < j; k++)
            {
                GL11.glPushMatrix();

                if (k > 0)
                {
                    GL11.glTranslatef(((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / 0.5F, ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / 0.5F, ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / 0.5F);
                }

                iitemrenderer.renderItem(IItemRenderer.ItemRenderType.ENTITY, itemstack, new Object[]
                        {
                            renderblocks, entityitem
                        });
                GL11.glPopMatrix();
            }
        }
        else
        {
            renderengine.bindTexture(renderengine.getTexture(itemstack.getItem().getTextureFile()));
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            iitemrenderer.renderItem(IItemRenderer.ItemRenderType.ENTITY, itemstack, new Object[]
                    {
                        renderblocks, entityitem
                    });
        }

        return true;
    }

    public static boolean renderInventoryItem(RenderBlocks renderblocks, RenderEngine renderengine, ItemStack itemstack, boolean flag, float f, float f1, float f2)
    {
        IItemRenderer iitemrenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.INVENTORY);

        if (iitemrenderer == null)
        {
            return false;
        }

        renderengine.bindTexture(renderengine.getTexture(Item.itemsList[itemstack.itemID].getTextureFile()));

        if (iitemrenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.INVENTORY, itemstack, IItemRenderer.ItemRendererHelper.INVENTORY_BLOCK))
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(f1 - 2.0F, f2 + 3F, -3F + f);
            GL11.glScalef(10F, 10F, 10F);
            GL11.glTranslatef(1.0F, 0.5F, 1.0F);
            GL11.glScalef(1.0F, 1.0F, -1F);
            GL11.glRotatef(210F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);

            if (flag)
            {
                int i = Item.itemsList[itemstack.itemID].getColorFromDamage(itemstack.getItemDamage(), 0);
                float f3 = (float)(i >> 16 & 0xff) / 255F;
                float f5 = (float)(i >> 8 & 0xff) / 255F;
                float f7 = (float)(i & 0xff) / 255F;
                GL11.glColor4f(f3, f5, f7, 1.0F);
            }

            GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
            renderblocks.useInventoryTint = flag;
            iitemrenderer.renderItem(IItemRenderer.ItemRenderType.INVENTORY, itemstack, new Object[]
                    {
                        renderblocks
                    });
            renderblocks.useInventoryTint = true;
            GL11.glPopMatrix();
        }
        else
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPushMatrix();
            GL11.glTranslatef(f1, f2, -3F + f);

            if (flag)
            {
                int j = Item.itemsList[itemstack.itemID].getColorFromDamage(itemstack.getItemDamage(), 0);
                float f4 = (float)(j >> 16 & 0xff) / 255F;
                float f6 = (float)(j >> 8 & 0xff) / 255F;
                float f8 = (float)(j & 0xff) / 255F;
                GL11.glColor4f(f4, f6, f8, 1.0F);
            }

            iitemrenderer.renderItem(IItemRenderer.ItemRenderType.INVENTORY, itemstack, new Object[]
                    {
                        renderblocks
                    });
            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_LIGHTING);
        }

        return true;
    }

    private static Class getClass(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch (Exception exception) { }

        try
        {
            return Class.forName((new StringBuilder()).append("net.minecraft.src.").append(s).toString());
        }
        catch (Exception exception1)
        {
            return null;
        }
    }

    public static void onSetupAudio(SoundManager soundmanager)
    {
        ISoundHandler isoundhandler;

        for (Iterator iterator = soundHandlers.iterator(); iterator.hasNext(); isoundhandler.onSetupAudio(soundmanager))
        {
            isoundhandler = (ISoundHandler)iterator.next();
        }
    }

    public static void onLoadSoundSettings(SoundManager soundmanager)
    {
        ISoundHandler isoundhandler;

        for (Iterator iterator = soundHandlers.iterator(); iterator.hasNext(); isoundhandler.onLoadSoundSettings(soundmanager))
        {
            isoundhandler = (ISoundHandler)iterator.next();
        }
    }

    public static SoundPoolEntry onPlayBackgroundMusic(SoundManager soundmanager, SoundPoolEntry soundpoolentry)
    {
        for (Iterator iterator = soundHandlers.iterator(); iterator.hasNext();)
        {
            ISoundHandler isoundhandler = (ISoundHandler)iterator.next();
            soundpoolentry = isoundhandler.onPlayBackgroundMusic(soundmanager, soundpoolentry);

            if (soundpoolentry == null)
            {
                return null;
            }
        }

        return soundpoolentry;
    }

    public static SoundPoolEntry onPlayStreaming(SoundManager soundmanager, SoundPoolEntry soundpoolentry, String s, float f, float f1, float f2)
    {
        for (Iterator iterator = soundHandlers.iterator(); iterator.hasNext();)
        {
            ISoundHandler isoundhandler = (ISoundHandler)iterator.next();
            soundpoolentry = isoundhandler.onPlayStreaming(soundmanager, soundpoolentry, s, f, f1, f2);

            if (soundpoolentry == null)
            {
                return null;
            }
        }

        return soundpoolentry;
    }

    public static SoundPoolEntry onPlaySound(SoundManager soundmanager, SoundPoolEntry soundpoolentry, String s, float f, float f1, float f2, float f3, float f4)
    {
        for (Iterator iterator = soundHandlers.iterator(); iterator.hasNext();)
        {
            ISoundHandler isoundhandler = (ISoundHandler)iterator.next();
            soundpoolentry = isoundhandler.onPlaySound(soundmanager, soundpoolentry, s, f, f1, f2, f3, f4);

            if (soundpoolentry == null)
            {
                return null;
            }
        }

        return soundpoolentry;
    }

    public static SoundPoolEntry onPlaySoundEffect(SoundManager soundmanager, SoundPoolEntry soundpoolentry, String s, float f, float f1)
    {
        for (Iterator iterator = soundHandlers.iterator(); iterator.hasNext();)
        {
            ISoundHandler isoundhandler = (ISoundHandler)iterator.next();
            soundpoolentry = isoundhandler.onPlaySoundEffect(soundmanager, soundpoolentry, s, f, f1);

            if (soundpoolentry == null)
            {
                return null;
            }
        }

        return soundpoolentry;
    }

    public static String onPlaySoundAtEntity(Entity entity, String s, float f, float f1)
    {
        MinecraftForgeClient.checkMinecraftVersion("Minecraft Minecraft 1.2.5", "Interface check in onPlaySoundAtEntity, remove it Mods should be updated");

        for (Iterator iterator = soundHandlers2.iterator(); iterator.hasNext();)
        {
            ISoundHandler isoundhandler = (ISoundHandler)iterator.next();
            s = isoundhandler.onPlaySoundAtEntity(entity, s, f, f1);

            if (s == null)
            {
                return null;
            }
        }

        return s;
    }

    public static void onLogin(Packet1Login packet1login, NetClientHandler netclienthandler, NetworkManager networkmanager)
    {
        ForgeHooks.onLogin(networkmanager, packet1login);
        String as[] = MessageManager.getInstance().getRegisteredChannels(networkmanager);
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("Forge");
        String as1[] = as;
        int i = as1.length;

        for (int j = 0; j < i; j++)
        {
            String s = as1[j];
            stringbuilder.append("\0");
            stringbuilder.append(s);
        }

        Packet250CustomPayload packet250custompayload = new Packet250CustomPayload();
        packet250custompayload.channel = "REGISTER";

        try
        {
            packet250custompayload.data = stringbuilder.toString().getBytes("UTF8");
        }
        catch (UnsupportedEncodingException unsupportedencodingexception)
        {
            unsupportedencodingexception.printStackTrace();
        }

        packet250custompayload.length = packet250custompayload.data.length;
        netclienthandler.addToSendQueue(packet250custompayload);
    }

    public static Packet onSendLogin(Packet1Login packet1login)
    {
        packet1login.serverMode = 0x40e9b47;
        packet1login.field_48170_e = 135;
        return packet1login;
    }

    public static void onCustomPayload(Packet250CustomPayload packet250custompayload, NetworkManager networkmanager)
    {
        MessageManager messagemanager = MessageManager.getInstance();

        if (packet250custompayload.channel.equals("REGISTER"))
        {
            try
            {
                String s = new String(packet250custompayload.data, "UTF8");
                String as[] = s.split("\0");
                int i = as.length;

                for (int k = 0; k < i; k++)
                {
                    String s2 = as[k];
                    messagemanager.addActiveChannel(networkmanager, s2);
                }
            }
            catch (UnsupportedEncodingException unsupportedencodingexception)
            {
                ModLoader.throwException("ForgeHooksClient.onCustomPayload", unsupportedencodingexception);
            }
        }
        else if (packet250custompayload.channel.equals("UNREGISTER"))
        {
            try
            {
                String s1 = new String(packet250custompayload.data, "UTF8");
                String as1[] = s1.split("\0");
                int j = as1.length;

                for (int l = 0; l < j; l++)
                {
                    String s3 = as1[l];
                    messagemanager.removeActiveChannel(networkmanager, s3);
                }
            }
            catch (UnsupportedEncodingException unsupportedencodingexception1)
            {
                ModLoader.throwException("ForgeHooksClient.onCustomPayload", unsupportedencodingexception1);
            }
        }
        else
        {
            messagemanager.dispatchIncomingMessage(networkmanager, packet250custompayload.channel, packet250custompayload.data);
        }
    }

    public static void onTextureLoadPre(String s)
    {
        if (Tessellator.renderingWorldRenderer)
        {
            String s1 = String.format("Warning: Texture %s not preloaded, will cause render glitches!", new Object[]
                    {
                        s
                    });
            System.out.println(s1);

            if ((net.minecraft.src.Tessellator.class).getPackage() != null && (net.minecraft.src.Tessellator.class).getPackage().equals("net.minecraft.src"))
            {
                Minecraft minecraft = ModLoader.getMinecraftInstance();

                if (minecraft.ingameGUI != null)
                {
                    minecraft.ingameGUI.addChatMessage(s1);
                }
            }
        }
    }
}

package cpw.mods.fml.client;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiScrollingList
{
    private final Minecraft client;
    protected final int listWidth;
    protected final int listHeight;
    protected final int top;
    protected final int bottom;
    private final int right;
    protected final int left;
    protected final int slotHeight;
    private int scrollUpActionId;
    private int scrollDownActionId;
    protected int mouseX;
    protected int mouseY;
    private float initialMouseClickY;
    private float scrollFactor;
    private float scrollDistance;
    private int selectedIndex;
    private long lastClickTime;
    private boolean field_25123_p;
    private boolean field_27262_q;
    private int field_27261_r;

    public GuiScrollingList(Minecraft minecraft, int i, int j, int k, int l, int i1, int j1)
    {
        initialMouseClickY = -2F;
        selectedIndex = -1;
        lastClickTime = 0L;
        field_25123_p = true;
        client = minecraft;
        listWidth = i;
        listHeight = j;
        top = k;
        bottom = l;
        slotHeight = j1;
        left = i1;
        right = i + left;
    }

    public void func_27258_a(boolean flag)
    {
        field_25123_p = flag;
    }

    protected void func_27259_a(boolean flag, int i)
    {
        field_27262_q = flag;
        field_27261_r = i;

        if (!flag)
        {
            field_27261_r = 0;
        }
    }

    protected abstract int getSize();

    protected abstract void elementClicked(int i, boolean flag);

    protected abstract boolean isSelected(int i);

    protected int getContentHeight()
    {
        return getSize() * slotHeight + field_27261_r;
    }

    protected abstract void drawBackground();

    protected abstract void drawSlot(int i, int j, int k, int l, Tessellator tessellator);

    protected void func_27260_a(int i, int j, Tessellator tessellator)
    {
    }

    protected void func_27255_a(int i, int j)
    {
    }

    protected void func_27257_b(int i, int j)
    {
    }

    public int func_27256_c(int i, int j)
    {
        int k = listWidth / 2 - 110;
        int l = listWidth / 2 + 110;
        int i1 = ((j - top - field_27261_r) + (int)scrollDistance) - 4;
        int j1 = i1 / slotHeight;
        return i < k || i > l || j1 < 0 || i1 < 0 || j1 >= getSize() ? -1 : j1;
    }

    public void registerScrollButtons(List list, int i, int j)
    {
        scrollUpActionId = i;
        scrollDownActionId = j;
    }

    private void applyScrollLimits()
    {
        int i = getContentHeight() - (bottom - top - 4);

        if (i < 0)
        {
            i /= 2;
        }

        if (scrollDistance < 0.0F)
        {
            scrollDistance = 0.0F;
        }

        if (scrollDistance > (float)i)
        {
            scrollDistance = i;
        }
    }

    public void actionPerformed(GuiButton guibutton)
    {
        if (guibutton.enabled)
        {
            if (guibutton.id == scrollUpActionId)
            {
                scrollDistance -= (slotHeight * 2) / 3;
                initialMouseClickY = -2F;
                applyScrollLimits();
            }
            else if (guibutton.id == scrollDownActionId)
            {
                scrollDistance += (slotHeight * 2) / 3;
                initialMouseClickY = -2F;
                applyScrollLimits();
            }
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        mouseX = i;
        mouseY = j;
        drawBackground();
        int k = getSize();
        int l = (left + listWidth) - 6;
        int i1 = l + 6;
        int j1 = left;
        int k1 = l - 1;

        if (Mouse.isButtonDown(0))
        {
            if (initialMouseClickY == -1F)
            {
                boolean flag = true;

                if (j >= top && j <= bottom)
                {
                    int l1 = ((j - top - field_27261_r) + (int)scrollDistance) - 4;
                    int j2 = l1 / slotHeight;

                    if (i >= j1 && i <= k1 && j2 >= 0 && l1 >= 0 && j2 < k)
                    {
                        boolean flag1 = j2 == selectedIndex && System.currentTimeMillis() - lastClickTime < 250L;
                        elementClicked(j2, flag1);
                        selectedIndex = j2;
                        lastClickTime = System.currentTimeMillis();
                    }
                    else if (i >= j1 && i <= k1 && l1 < 0)
                    {
                        func_27255_a(i - j1, ((j - top) + (int)scrollDistance) - 4);
                        flag = false;
                    }

                    if (i >= l && i <= i1)
                    {
                        scrollFactor = -1F;
                        int k3 = getContentHeight() - (bottom - top - 4);

                        if (k3 < 1)
                        {
                            k3 = 1;
                        }

                        int l2 = (int)((float)((bottom - top) * (bottom - top)) / (float)getContentHeight());

                        if (l2 < 32)
                        {
                            l2 = 32;
                        }

                        if (l2 > bottom - top - 8)
                        {
                            l2 = bottom - top - 8;
                        }

                        scrollFactor /= (float)(bottom - top - l2) / (float)k3;
                    }
                    else
                    {
                        scrollFactor = 1.0F;
                    }

                    if (flag)
                    {
                        initialMouseClickY = j;
                    }
                    else
                    {
                        initialMouseClickY = -2F;
                    }
                }
                else
                {
                    initialMouseClickY = -2F;
                }
            }
            else if (initialMouseClickY >= 0.0F)
            {
                scrollDistance -= ((float)j - initialMouseClickY) * scrollFactor;
                initialMouseClickY = j;
            }
        }
        else
        {
            do
            {
                if (!Mouse.next())
                {
                    break;
                }

                int j4 = Mouse.getEventDWheel();

                if (j4 != 0)
                {
                    if (j4 > 0)
                    {
                        j4 = -1;
                    }
                    else if (j4 < 0)
                    {
                        j4 = 1;
                    }

                    scrollDistance += (j4 * slotHeight) / 2;
                }
            }
            while (true);

            initialMouseClickY = -1F;
        }

        applyScrollLimits();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        Tessellator tessellator = Tessellator.instance;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, client.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f1 = 32F;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(0x202020);
        tessellator.addVertexWithUV(left, bottom, 0.0D, (float)left / f1, (float)(bottom + (int)scrollDistance) / f1);
        tessellator.addVertexWithUV(right, bottom, 0.0D, (float)right / f1, (float)(bottom + (int)scrollDistance) / f1);
        tessellator.addVertexWithUV(right, top, 0.0D, (float)right / f1, (float)(top + (int)scrollDistance) / f1);
        tessellator.addVertexWithUV(left, top, 0.0D, (float)left / f1, (float)(top + (int)scrollDistance) / f1);
        tessellator.draw();
        int i2 = (top + 4) - (int)scrollDistance;

        if (field_27262_q)
        {
            func_27260_a(k1, i2, tessellator);
        }

        for (int k2 = 0; k2 < k; k2++)
        {
            int l3 = i2 + k2 * slotHeight + field_27261_r;
            int i3 = slotHeight - 4;

            if (l3 > bottom || l3 + i3 < top)
            {
                continue;
            }

            if (field_25123_p && isSelected(k2))
            {
                int k4 = j1;
                int i5 = k1;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                tessellator.startDrawingQuads();
                tessellator.setColorOpaque_I(0x808080);
                tessellator.addVertexWithUV(k4, l3 + i3 + 2, 0.0D, 0.0D, 1.0D);
                tessellator.addVertexWithUV(i5, l3 + i3 + 2, 0.0D, 1.0D, 1.0D);
                tessellator.addVertexWithUV(i5, l3 - 2, 0.0D, 1.0D, 0.0D);
                tessellator.addVertexWithUV(k4, l3 - 2, 0.0D, 0.0D, 0.0D);
                tessellator.setColorOpaque_I(0);
                tessellator.addVertexWithUV(k4 + 1, l3 + i3 + 1, 0.0D, 0.0D, 1.0D);
                tessellator.addVertexWithUV(i5 - 1, l3 + i3 + 1, 0.0D, 1.0D, 1.0D);
                tessellator.addVertexWithUV(i5 - 1, l3 - 1, 0.0D, 1.0D, 0.0D);
                tessellator.addVertexWithUV(k4 + 1, l3 - 1, 0.0D, 0.0D, 0.0D);
                tessellator.draw();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            drawSlot(k2, k1, l3, i3, tessellator);
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        byte byte0 = 4;
        overlayBackground(0, top, 255, 255);
        overlayBackground(bottom, listHeight, 255, 255);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0, 0);
        tessellator.addVertexWithUV(left, top + byte0, 0.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(right, top + byte0, 0.0D, 1.0D, 1.0D);
        tessellator.setColorRGBA_I(0, 255);
        tessellator.addVertexWithUV(right, top, 0.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(left, top, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0, 255);
        tessellator.addVertexWithUV(left, bottom, 0.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(right, bottom, 0.0D, 1.0D, 1.0D);
        tessellator.setColorRGBA_I(0, 0);
        tessellator.addVertexWithUV(right, bottom - byte0, 0.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(left, bottom - byte0, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        int i4 = getContentHeight() - (bottom - top - 4);

        if (i4 > 0)
        {
            int j3 = ((bottom - top) * (bottom - top)) / getContentHeight();

            if (j3 < 32)
            {
                j3 = 32;
            }

            if (j3 > bottom - top - 8)
            {
                j3 = bottom - top - 8;
            }

            int l4 = ((int)scrollDistance * (bottom - top - j3)) / i4 + top;

            if (l4 < top)
            {
                l4 = top;
            }

            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_I(0, 255);
            tessellator.addVertexWithUV(l, bottom, 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV(i1, bottom, 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV(i1, top, 0.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(l, top, 0.0D, 0.0D, 0.0D);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_I(0x808080, 255);
            tessellator.addVertexWithUV(l, l4 + j3, 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV(i1, l4 + j3, 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV(i1, l4, 0.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(l, l4, 0.0D, 0.0D, 0.0D);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_I(0xc0c0c0, 255);
            tessellator.addVertexWithUV(l, (l4 + j3) - 1, 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV(i1 - 1, (l4 + j3) - 1, 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV(i1 - 1, l4, 0.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(l, l4, 0.0D, 0.0D, 0.0D);
            tessellator.draw();
        }

        func_27257_b(i, j);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void overlayBackground(int i, int j, int k, int l)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, client.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32F;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0x404040, l);
        tessellator.addVertexWithUV(0.0D, j, 0.0D, 0.0D, (float)j / f);
        tessellator.addVertexWithUV((double)listWidth + 30D, j, 0.0D, (float)(listWidth + 30) / f, (float)j / f);
        tessellator.setColorRGBA_I(0x404040, k);
        tessellator.addVertexWithUV((double)listWidth + 30D, i, 0.0D, (float)(listWidth + 30) / f, (float)i / f);
        tessellator.addVertexWithUV(0.0D, i, 0.0D, 0.0D, (float)i / f);
        tessellator.draw();
    }
}

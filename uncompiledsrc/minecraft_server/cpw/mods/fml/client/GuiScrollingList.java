package cpw.mods.fml.client;

import java.util.List;
import net.minecraft.client.Minecraft;
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
    private float initialMouseClickY = -2.0F;
    private float scrollFactor;
    private float scrollDistance;
    private int selectedIndex = -1;
    private long lastClickTime = 0L;
    private boolean field_25123_p = true;
    private boolean field_27262_q;
    private int field_27261_r;

    public GuiScrollingList(Minecraft var1, int var2, int var3, int var4, int var5, int var6, int var7)
    {
        this.client = var1;
        this.listWidth = var2;
        this.listHeight = var3;
        this.top = var4;
        this.bottom = var5;
        this.slotHeight = var7;
        this.left = var6;
        this.right = var2 + this.left;
    }

    public void func_27258_a(boolean var1)
    {
        this.field_25123_p = var1;
    }

    protected void func_27259_a(boolean var1, int var2)
    {
        this.field_27262_q = var1;
        this.field_27261_r = var2;

        if (!var1)
        {
            this.field_27261_r = 0;
        }
    }

    protected abstract int getSize();

    protected abstract void elementClicked(int var1, boolean var2);

    protected abstract boolean isSelected(int var1);

    protected int getContentHeight()
    {
        return this.getSize() * this.slotHeight + this.field_27261_r;
    }

    protected abstract void drawBackground();

    protected abstract void drawSlot(int var1, int var2, int var3, int var4, bgd var5);

    protected void func_27260_a(int var1, int var2, bgd var3) {}

    protected void func_27255_a(int var1, int var2) {}

    protected void func_27257_b(int var1, int var2) {}

    public int func_27256_c(int var1, int var2)
    {
        int var3 = this.left + 1;
        int var4 = this.left + this.listWidth - 7;
        int var5 = var2 - this.top - this.field_27261_r + (int)this.scrollDistance - 4;
        int var6 = var5 / this.slotHeight;
        return var1 >= var3 && var1 <= var4 && var6 >= 0 && var5 >= 0 && var6 < this.getSize() ? var6 : -1;
    }

    public void registerScrollButtons(List var1, int var2, int var3)
    {
        this.scrollUpActionId = var2;
        this.scrollDownActionId = var3;
    }

    private void applyScrollLimits()
    {
        int var1 = this.getContentHeight() - (this.bottom - this.top - 4);

        if (var1 < 0)
        {
            var1 /= 2;
        }

        if (this.scrollDistance < 0.0F)
        {
            this.scrollDistance = 0.0F;
        }

        if (this.scrollDistance > (float)var1)
        {
            this.scrollDistance = (float)var1;
        }
    }

    public void actionPerformed(awg var1)
    {
        if (var1.g)
        {
            if (var1.f == this.scrollUpActionId)
            {
                this.scrollDistance -= (float)(this.slotHeight * 2 / 3);
                this.initialMouseClickY = -2.0F;
                this.applyScrollLimits();
            }
            else if (var1.f == this.scrollDownActionId)
            {
                this.scrollDistance += (float)(this.slotHeight * 2 / 3);
                this.initialMouseClickY = -2.0F;
                this.applyScrollLimits();
            }
        }
    }

    public void drawScreen(int var1, int var2, float var3)
    {
        this.mouseX = var1;
        this.mouseY = var2;
        this.drawBackground();
        int var4 = this.getSize();
        int var5 = this.left + this.listWidth - 6;
        int var6 = var5 + 6;
        int var7 = this.left;
        int var8 = var5 - 1;
        int var9;
        int var10;
        int var11;
        int var12;

        if (Mouse.isButtonDown(0))
        {
            if (this.initialMouseClickY == -1.0F)
            {
                boolean var13 = true;

                if (var2 >= this.top && var2 <= this.bottom)
                {
                    var9 = var2 - this.top - this.field_27261_r + (int)this.scrollDistance - 4;
                    var10 = var9 / this.slotHeight;

                    if (var1 >= var7 && var1 <= var8 && var10 >= 0 && var9 >= 0 && var10 < var4)
                    {
                        boolean var14 = var10 == this.selectedIndex && System.currentTimeMillis() - this.lastClickTime < 250L;
                        this.elementClicked(var10, var14);
                        this.selectedIndex = var10;
                        this.lastClickTime = System.currentTimeMillis();
                    }
                    else if (var1 >= var7 && var1 <= var8 && var9 < 0)
                    {
                        this.func_27255_a(var1 - var7, var2 - this.top + (int)this.scrollDistance - 4);
                        var13 = false;
                    }

                    if (var1 >= var5 && var1 <= var6)
                    {
                        this.scrollFactor = -1.0F;
                        var12 = this.getContentHeight() - (this.bottom - this.top - 4);

                        if (var12 < 1)
                        {
                            var12 = 1;
                        }

                        var11 = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getContentHeight());

                        if (var11 < 32)
                        {
                            var11 = 32;
                        }

                        if (var11 > this.bottom - this.top - 8)
                        {
                            var11 = this.bottom - this.top - 8;
                        }

                        this.scrollFactor /= (float)(this.bottom - this.top - var11) / (float)var12;
                    }
                    else
                    {
                        this.scrollFactor = 1.0F;
                    }

                    if (var13)
                    {
                        this.initialMouseClickY = (float)var2;
                    }
                    else
                    {
                        this.initialMouseClickY = -2.0F;
                    }
                }
                else
                {
                    this.initialMouseClickY = -2.0F;
                }
            }
            else if (this.initialMouseClickY >= 0.0F)
            {
                this.scrollDistance -= ((float)var2 - this.initialMouseClickY) * this.scrollFactor;
                this.initialMouseClickY = (float)var2;
            }
        }
        else
        {
            while (Mouse.next())
            {
                int var17 = Mouse.getEventDWheel();

                if (var17 != 0)
                {
                    if (var17 > 0)
                    {
                        var17 = -1;
                    }
                    else if (var17 < 0)
                    {
                        var17 = 1;
                    }

                    this.scrollDistance += (float)(var17 * this.slotHeight / 2);
                }
            }

            this.initialMouseClickY = -1.0F;
        }

        this.applyScrollLimits();
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        bgd var18 = bgd.a;
        this.client.p.b("/gui/background.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var19 = 32.0F;
        var18.b();
        var18.d(2105376);
        var18.a((double)this.left, (double)this.bottom, 0.0D, (double)((float)this.left / var19), (double)((float)(this.bottom + (int)this.scrollDistance) / var19));
        var18.a((double)this.right, (double)this.bottom, 0.0D, (double)((float)this.right / var19), (double)((float)(this.bottom + (int)this.scrollDistance) / var19));
        var18.a((double)this.right, (double)this.top, 0.0D, (double)((float)this.right / var19), (double)((float)(this.top + (int)this.scrollDistance) / var19));
        var18.a((double)this.left, (double)this.top, 0.0D, (double)((float)this.left / var19), (double)((float)(this.top + (int)this.scrollDistance) / var19));
        var18.a();
        var9 = this.top + 4 - (int)this.scrollDistance;

        if (this.field_27262_q)
        {
            this.func_27260_a(var8, var9, var18);
        }

        for (var10 = 0; var10 < var4; ++var10)
        {
            var12 = var9 + var10 * this.slotHeight + this.field_27261_r;
            var11 = this.slotHeight - 4;

            if (var12 <= this.bottom && var12 + var11 >= this.top)
            {
                if (this.field_25123_p && this.isSelected(var10))
                {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glDisable(3553);
                    var18.b();
                    var18.d(8421504);
                    var18.a((double)var7, (double)(var12 + var11 + 2), 0.0D, 0.0D, 1.0D);
                    var18.a((double)var8, (double)(var12 + var11 + 2), 0.0D, 1.0D, 1.0D);
                    var18.a((double)var8, (double)(var12 - 2), 0.0D, 1.0D, 0.0D);
                    var18.a((double)var7, (double)(var12 - 2), 0.0D, 0.0D, 0.0D);
                    var18.d(0);
                    var18.a((double)(var7 + 1), (double)(var12 + var11 + 1), 0.0D, 0.0D, 1.0D);
                    var18.a((double)(var8 - 1), (double)(var12 + var11 + 1), 0.0D, 1.0D, 1.0D);
                    var18.a((double)(var8 - 1), (double)(var12 - 1), 0.0D, 1.0D, 0.0D);
                    var18.a((double)(var7 + 1), (double)(var12 - 1), 0.0D, 0.0D, 0.0D);
                    var18.a();
                    GL11.glEnable(3553);
                }

                this.drawSlot(var10, var8, var12, var11, var18);
            }
        }

        GL11.glDisable(2929);
        byte var16 = 4;
        this.overlayBackground(0, this.top, 255, 255);
        this.overlayBackground(this.bottom, this.listHeight, 255, 255);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        var18.b();
        var18.a(0, 0);
        var18.a((double)this.left, (double)(this.top + var16), 0.0D, 0.0D, 1.0D);
        var18.a((double)this.right, (double)(this.top + var16), 0.0D, 1.0D, 1.0D);
        var18.a(0, 255);
        var18.a((double)this.right, (double)this.top, 0.0D, 1.0D, 0.0D);
        var18.a((double)this.left, (double)this.top, 0.0D, 0.0D, 0.0D);
        var18.a();
        var18.b();
        var18.a(0, 255);
        var18.a((double)this.left, (double)this.bottom, 0.0D, 0.0D, 1.0D);
        var18.a((double)this.right, (double)this.bottom, 0.0D, 1.0D, 1.0D);
        var18.a(0, 0);
        var18.a((double)this.right, (double)(this.bottom - var16), 0.0D, 1.0D, 0.0D);
        var18.a((double)this.left, (double)(this.bottom - var16), 0.0D, 0.0D, 0.0D);
        var18.a();
        var12 = this.getContentHeight() - (this.bottom - this.top - 4);

        if (var12 > 0)
        {
            var11 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();

            if (var11 < 32)
            {
                var11 = 32;
            }

            if (var11 > this.bottom - this.top - 8)
            {
                var11 = this.bottom - this.top - 8;
            }

            int var15 = (int)this.scrollDistance * (this.bottom - this.top - var11) / var12 + this.top;

            if (var15 < this.top)
            {
                var15 = this.top;
            }

            var18.b();
            var18.a(0, 255);
            var18.a((double)var5, (double)this.bottom, 0.0D, 0.0D, 1.0D);
            var18.a((double)var6, (double)this.bottom, 0.0D, 1.0D, 1.0D);
            var18.a((double)var6, (double)this.top, 0.0D, 1.0D, 0.0D);
            var18.a((double)var5, (double)this.top, 0.0D, 0.0D, 0.0D);
            var18.a();
            var18.b();
            var18.a(8421504, 255);
            var18.a((double)var5, (double)(var15 + var11), 0.0D, 0.0D, 1.0D);
            var18.a((double)var6, (double)(var15 + var11), 0.0D, 1.0D, 1.0D);
            var18.a((double)var6, (double)var15, 0.0D, 1.0D, 0.0D);
            var18.a((double)var5, (double)var15, 0.0D, 0.0D, 0.0D);
            var18.a();
            var18.b();
            var18.a(12632256, 255);
            var18.a((double)var5, (double)(var15 + var11 - 1), 0.0D, 0.0D, 1.0D);
            var18.a((double)(var6 - 1), (double)(var15 + var11 - 1), 0.0D, 1.0D, 1.0D);
            var18.a((double)(var6 - 1), (double)var15, 0.0D, 1.0D, 0.0D);
            var18.a((double)var5, (double)var15, 0.0D, 0.0D, 0.0D);
            var18.a();
        }

        this.func_27257_b(var1, var2);
        GL11.glEnable(3553);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GL11.glDisable(3042);
    }

    private void overlayBackground(int var1, int var2, int var3, int var4)
    {
        bgd var5 = bgd.a;
        this.client.p.b("/gui/background.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var6 = 32.0F;
        var5.b();
        var5.a(4210752, var4);
        var5.a(0.0D, (double)var2, 0.0D, 0.0D, (double)((float)var2 / var6));
        var5.a((double)this.listWidth + 30.0D, (double)var2, 0.0D, (double)((float)(this.listWidth + 30) / var6), (double)((float)var2 / var6));
        var5.a(4210752, var3);
        var5.a((double)this.listWidth + 30.0D, (double)var1, 0.0D, (double)((float)(this.listWidth + 30) / var6), (double)((float)var1 / var6));
        var5.a(0.0D, (double)var1, 0.0D, 0.0D, (double)((float)var1 / var6));
        var5.a();
    }
}

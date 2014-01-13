package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import java.util.Iterator;

public class BaseModTicker implements ITickHandler
{
    private BaseModProxy mod;
    private EnumSet ticks;
    private boolean clockTickTrigger;
    private boolean sendGuiTicks;

    BaseModTicker(BaseModProxy var1, boolean var2)
    {
        this.mod = var1;
        this.ticks = EnumSet.of(TickType.WORLDLOAD);
        this.sendGuiTicks = var2;
    }

    BaseModTicker(EnumSet var1, boolean var2)
    {
        this.ticks = var1;
        this.sendGuiTicks = var2;
    }

    public void tickStart(EnumSet var1, Object ... var2)
    {
        this.tickBaseMod(var1, false, var2);
    }

    public void tickEnd(EnumSet var1, Object ... var2)
    {
        this.tickBaseMod(var1, true, var2);
    }

    private void tickBaseMod(EnumSet var1, boolean var2, Object ... var3)
    {
        if (FMLCommonHandler.instance().getSide().isClient() && (this.ticks.contains(TickType.CLIENT) || this.ticks.contains(TickType.WORLDLOAD)))
        {
            EnumSet var4 = EnumSet.copyOf(var1);

            if (var2 && var1.contains(TickType.CLIENT) || var1.contains(TickType.WORLDLOAD))
            {
                this.clockTickTrigger = true;
                var4.remove(TickType.CLIENT);
                var4.remove(TickType.WORLDLOAD);
            }

            if (var2 && this.clockTickTrigger && var1.contains(TickType.RENDER))
            {
                this.clockTickTrigger = false;
                var4.remove(TickType.RENDER);
                var4.add(TickType.CLIENT);
            }

            this.sendTick(var4, var2, var3);
        }
        else
        {
            this.sendTick(var1, var2, var3);
        }
    }

    private void sendTick(EnumSet var1, boolean var2, Object ... var3)
    {
        Iterator var4 = var1.iterator();

        while (var4.hasNext())
        {
            TickType var5 = (TickType)var4.next();

            if (this.ticks.contains(var5))
            {
                boolean var6 = true;

                if (this.sendGuiTicks)
                {
                    var6 = this.mod.doTickInGUI(var5, var2, var3);
                }
                else
                {
                    var6 = this.mod.doTickInGame(var5, var2, var3);
                }

                if (!var6)
                {
                    this.ticks.remove(var5);
                    this.ticks.removeAll(var5.partnerTicks());
                }
            }
        }
    }

    public EnumSet ticks()
    {
        return this.clockTickTrigger ? EnumSet.of(TickType.RENDER) : this.ticks;
    }

    public String getLabel()
    {
        return this.mod.getClass().getSimpleName();
    }

    public void setMod(BaseModProxy var1)
    {
        this.mod = var1;
    }
}

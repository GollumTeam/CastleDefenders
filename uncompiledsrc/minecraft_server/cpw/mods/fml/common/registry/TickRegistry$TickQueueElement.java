package cpw.mods.fml.common.registry;

import cpw.mods.fml.common.IScheduledTickHandler;

public class TickRegistry$TickQueueElement implements Comparable
{
    private long next;
    public IScheduledTickHandler ticker;

    public TickRegistry$TickQueueElement(IScheduledTickHandler var1, long var2)
    {
        this.ticker = var1;
        this.update(var2);
    }

    public int compareTo(TickRegistry$TickQueueElement var1)
    {
        return (int)(this.next - var1.next);
    }

    public void update(long var1)
    {
        this.next = var1 + (long)Math.max(this.ticker.nextTickSpacing(), 1);
    }

    public boolean scheduledNow(long var1)
    {
        return var1 >= this.next;
    }

    public int compareTo(Object var1)
    {
        return this.compareTo((TickRegistry$TickQueueElement)var1);
    }
}

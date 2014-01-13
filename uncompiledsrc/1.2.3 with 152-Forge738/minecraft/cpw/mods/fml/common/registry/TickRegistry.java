package cpw.mods.fml.common.registry;

import com.google.common.collect.Queues;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.SingleIntervalHandler;
import cpw.mods.fml.common.registry.TickRegistry$TickQueueElement;
import cpw.mods.fml.relauncher.Side;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;

public class TickRegistry
{
    private static PriorityQueue clientTickHandlers = Queues.newPriorityQueue();
    private static PriorityQueue serverTickHandlers = Queues.newPriorityQueue();
    private static AtomicLong clientTickCounter = new AtomicLong();
    private static AtomicLong serverTickCounter = new AtomicLong();

    public static void registerScheduledTickHandler(IScheduledTickHandler var0, Side var1)
    {
        getQueue(var1).add(new TickRegistry$TickQueueElement(var0, getCounter(var1).get()));
    }

    private static PriorityQueue getQueue(Side var0)
    {
        return var0.isClient() ? clientTickHandlers : serverTickHandlers;
    }

    private static AtomicLong getCounter(Side var0)
    {
        return var0.isClient() ? clientTickCounter : serverTickCounter;
    }

    public static void registerTickHandler(ITickHandler var0, Side var1)
    {
        registerScheduledTickHandler(new SingleIntervalHandler(var0), var1);
    }

    public static void updateTickQueue(List var0, Side var1)
    {
        synchronized (var0)
        {
            var0.clear();
            long var3 = getCounter(var1).incrementAndGet();
            PriorityQueue var5 = getQueue(var1);

            while (var5.size() != 0 && ((TickRegistry$TickQueueElement)var5.peek()).scheduledNow(var3))
            {
                TickRegistry$TickQueueElement var6 = (TickRegistry$TickQueueElement)var5.poll();
                var6.update(var3);
                var5.offer(var6);
                var0.add(var6.ticker);
            }
        }
    }
}

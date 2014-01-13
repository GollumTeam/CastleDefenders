package cpw.mods.fml.relauncher;

import cpw.mods.fml.relauncher.FMLRelaunchLog$1;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

class FMLRelaunchLog$ConsoleLogThread implements Runnable
{
    static ConsoleHandler wrappedHandler = new ConsoleHandler();
    static LinkedBlockingQueue recordQueue = new LinkedBlockingQueue();

    private FMLRelaunchLog$ConsoleLogThread() {}

    public void run()
    {
        while (true)
        {
            try
            {
                while (true)
                {
                    LogRecord var1 = (LogRecord)recordQueue.take();
                    wrappedHandler.publish(var1);
                }
            }
            catch (InterruptedException var3)
            {
                var3.printStackTrace(FMLRelaunchLog.access$000());
                Thread.interrupted();
            }
        }
    }

    FMLRelaunchLog$ConsoleLogThread(FMLRelaunchLog$1 var1)
    {
        this();
    }
}

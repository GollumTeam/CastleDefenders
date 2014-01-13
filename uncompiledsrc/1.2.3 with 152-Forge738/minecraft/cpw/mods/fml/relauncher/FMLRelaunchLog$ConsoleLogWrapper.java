package cpw.mods.fml.relauncher;

import cpw.mods.fml.relauncher.FMLRelaunchLog$1;
import cpw.mods.fml.relauncher.FMLRelaunchLog$ConsoleLogThread;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

class FMLRelaunchLog$ConsoleLogWrapper extends Handler
{
    private FMLRelaunchLog$ConsoleLogWrapper() {}

    public void publish(LogRecord var1)
    {
        boolean var2 = Thread.interrupted();

        try
        {
            FMLRelaunchLog$ConsoleLogThread.recordQueue.put(var1);
        }
        catch (InterruptedException var4)
        {
            var4.printStackTrace(FMLRelaunchLog.access$000());
        }

        if (var2)
        {
            Thread.currentThread().interrupt();
        }
    }

    public void flush() {}

    public void close() throws SecurityException {}

    FMLRelaunchLog$ConsoleLogWrapper(FMLRelaunchLog$1 var1)
    {
        this();
    }
}

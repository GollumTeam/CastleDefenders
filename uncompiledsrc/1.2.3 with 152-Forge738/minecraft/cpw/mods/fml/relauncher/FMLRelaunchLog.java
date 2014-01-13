package cpw.mods.fml.relauncher;

import cpw.mods.fml.relauncher.FMLRelaunchLog$1;
import cpw.mods.fml.relauncher.FMLRelaunchLog$ConsoleLogThread;
import cpw.mods.fml.relauncher.FMLRelaunchLog$ConsoleLogWrapper;
import cpw.mods.fml.relauncher.FMLRelaunchLog$LoggingOutStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FMLRelaunchLog
{
    public static FMLRelaunchLog log = new FMLRelaunchLog();
    static File minecraftHome;
    private static boolean configured;
    private static Thread consoleLogThread;
    private static PrintStream errCache;
    private Logger myLog;
    private static FileHandler fileHandler;
    private static FMLLogFormatter formatter;

    private static void configureLogging()
    {
        LogManager.getLogManager().reset();
        Logger var0 = Logger.getLogger("global");
        var0.setLevel(Level.OFF);
        log.myLog = Logger.getLogger("ForgeModLoader");
        Logger var1 = Logger.getLogger("STDOUT");
        var1.setParent(log.myLog);
        Logger var2 = Logger.getLogger("STDERR");
        var2.setParent(log.myLog);
        log.myLog.setLevel(Level.ALL);
        log.myLog.setUseParentHandlers(false);
        consoleLogThread = new Thread(new FMLRelaunchLog$ConsoleLogThread((FMLRelaunchLog$1)null));
        consoleLogThread.setDaemon(true);
        consoleLogThread.start();
        formatter = new FMLLogFormatter();

        try
        {
            File var3 = new File(minecraftHome, FMLRelauncher.logFileNamePattern);
            fileHandler = new FMLRelaunchLog$1(var3.getPath(), 0, 3);
        }
        catch (Exception var4)
        {
            ;
        }

        resetLoggingHandlers();
        errCache = System.err;
        System.setOut(new PrintStream(new FMLRelaunchLog$LoggingOutStream(var1), true));
        System.setErr(new PrintStream(new FMLRelaunchLog$LoggingOutStream(var2), true));
        configured = true;
    }

    private static void resetLoggingHandlers()
    {
        FMLRelaunchLog$ConsoleLogThread.wrappedHandler.setLevel(Level.parse(System.getProperty("fml.log.level", "INFO")));
        log.myLog.addHandler(new FMLRelaunchLog$ConsoleLogWrapper((FMLRelaunchLog$1)null));
        FMLRelaunchLog$ConsoleLogThread.wrappedHandler.setFormatter(formatter);
        fileHandler.setLevel(Level.ALL);
        fileHandler.setFormatter(formatter);
        log.myLog.addHandler(fileHandler);
    }

    public static void loadLogConfiguration(File var0)
    {
        if (var0 != null && var0.exists() && var0.canRead())
        {
            try
            {
                LogManager.getLogManager().readConfiguration(new FileInputStream(var0));
                resetLoggingHandlers();
            }
            catch (Exception var2)
            {
                log(Level.SEVERE, (Throwable)var2, "Error reading logging configuration file %s", new Object[] {var0.getName()});
            }
        }
    }

    public static void log(String var0, Level var1, String var2, Object ... var3)
    {
        makeLog(var0);
        Logger.getLogger(var0).log(var1, String.format(var2, var3));
    }

    public static void log(Level var0, String var1, Object ... var2)
    {
        if (!configured)
        {
            configureLogging();
        }

        log.myLog.log(var0, String.format(var1, var2));
    }

    public static void log(String var0, Level var1, Throwable var2, String var3, Object ... var4)
    {
        makeLog(var0);
        Logger.getLogger(var0).log(var1, String.format(var3, var4), var2);
    }

    public static void log(Level var0, Throwable var1, String var2, Object ... var3)
    {
        if (!configured)
        {
            configureLogging();
        }

        log.myLog.log(var0, String.format(var2, var3), var1);
    }

    public static void severe(String var0, Object ... var1)
    {
        log(Level.SEVERE, var0, var1);
    }

    public static void warning(String var0, Object ... var1)
    {
        log(Level.WARNING, var0, var1);
    }

    public static void info(String var0, Object ... var1)
    {
        log(Level.INFO, var0, var1);
    }

    public static void fine(String var0, Object ... var1)
    {
        log(Level.FINE, var0, var1);
    }

    public static void finer(String var0, Object ... var1)
    {
        log(Level.FINER, var0, var1);
    }

    public static void finest(String var0, Object ... var1)
    {
        log(Level.FINEST, var0, var1);
    }

    public Logger getLogger()
    {
        return this.myLog;
    }

    public static void makeLog(String var0)
    {
        Logger var1 = Logger.getLogger(var0);
        var1.setParent(log.myLog);
    }

    static PrintStream access$000()
    {
        return errCache;
    }
}

package cpw.mods.fml.relauncher;

import java.applet.Applet;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class FMLRelauncher
{
    private static FMLRelauncher INSTANCE;
    public static String logFileNamePattern;
    private static String side;
    private RelaunchClassLoader classLoader;
    private Object newApplet;
    private Class appletClass;
    JDialog popupWindow;

    public static void handleClientRelaunch(ArgsWrapper var0)
    {
        logFileNamePattern = "ForgeModLoader-client-%g.log";
        side = "CLIENT";
        instance().relaunchClient(var0);
    }

    public static void handleServerRelaunch(ArgsWrapper var0)
    {
        logFileNamePattern = "ForgeModLoader-server-%g.log";
        side = "SERVER";
        instance().relaunchServer(var0);
    }

    static FMLRelauncher instance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new FMLRelauncher();
        }

        return INSTANCE;
    }

    private FMLRelauncher()
    {
        URLClassLoader var1 = (URLClassLoader)this.getClass().getClassLoader();
        this.classLoader = new RelaunchClassLoader(var1.getURLs());
    }

    private void showWindow(boolean var1)
    {
        if (RelaunchLibraryManager.downloadMonitor == null)
        {
            try
            {
                if (var1)
                {
                    RelaunchLibraryManager.downloadMonitor = new Downloader();
                    this.popupWindow = (JDialog)RelaunchLibraryManager.downloadMonitor.makeDialog();
                }
                else
                {
                    RelaunchLibraryManager.downloadMonitor = new DummyDownloader();
                }
            }
            catch (Throwable var3)
            {
                if (RelaunchLibraryManager.downloadMonitor == null)
                {
                    RelaunchLibraryManager.downloadMonitor = new DummyDownloader();
                    var3.printStackTrace();
                }
                else
                {
                    RelaunchLibraryManager.downloadMonitor.makeHeadless();
                }

                this.popupWindow = null;
            }
        }
    }

    private void relaunchClient(ArgsWrapper var1)
    {
        this.showWindow(true);
        Class var2;

        try
        {
            File var3 = this.computeExistingClientHome();
            this.setupHome(var3);
            var2 = this.setupNewClientHome(var3);
        }
        finally
        {
            if (this.popupWindow != null)
            {
                this.popupWindow.setVisible(false);
                this.popupWindow.dispose();
            }
        }

        if (RelaunchLibraryManager.downloadMonitor.shouldStopIt())
        {
            System.exit(1);
        }

        try
        {
            ReflectionHelper.findMethod(var2, (Object)null, new String[] {"fmlReentry"}, new Class[] {ArgsWrapper.class}).invoke((Object)null, new Object[] {var1});
        }
        catch (Exception var7)
        {
            var7.printStackTrace();
        }
    }

    private Class setupNewClientHome(File var1)
    {
        Class var2 = ReflectionHelper.getClass(this.classLoader, new String[] {"net.minecraft.client.Minecraft"});
        ReflectionHelper.setPrivateValue(var2, (Object)null, var1, new String[] {"minecraftDir", "an", "minecraftDir"});
        return var2;
    }

    private void relaunchServer(ArgsWrapper var1)
    {
        this.showWindow(false);
        File var3 = new File(".");
        this.setupHome(var3);
        Class var2 = ReflectionHelper.getClass(this.classLoader, new String[] {"net.minecraft.server.MinecraftServer"});

        try
        {
            ReflectionHelper.findMethod(var2, (Object)null, new String[] {"fmlReentry"}, new Class[] {ArgsWrapper.class}).invoke((Object)null, new Object[] {var1});
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
        }
    }

    private void setupHome(File var1)
    {
        FMLInjectionData.build(var1, this.classLoader);
        FMLRelaunchLog.minecraftHome = var1;
        FMLRelaunchLog.info("Forge Mod Loader version %s.%s.%s.%s for Minecraft %s loading", new Object[] {FMLInjectionData.major, FMLInjectionData.minor, FMLInjectionData.rev, FMLInjectionData.build, FMLInjectionData.mccversion, FMLInjectionData.mcpversion});
        FMLRelaunchLog.info("Java is %s, version %s, running on %s:%s:%s, installed at %s", new Object[] {System.getProperty("java.vm.name"), System.getProperty("java.version"), System.getProperty("os.name"), System.getProperty("os.arch"), System.getProperty("os.version"), System.getProperty("java.home")});
        FMLRelaunchLog.fine("Java classpath at launch is %s", new Object[] {System.getProperty("java.class.path")});
        FMLRelaunchLog.fine("Java library path at launch is %s", new Object[] {System.getProperty("java.library.path")});

        try
        {
            RelaunchLibraryManager.handleLaunch(var1, this.classLoader);
        }
        catch (Throwable var5)
        {
            if (this.popupWindow != null)
            {
                try
                {
                    String var3 = (new File(var1, "ForgeModLoader-client-0.log")).getCanonicalPath();
                    JOptionPane.showMessageDialog(this.popupWindow, String.format("<html><div align=\"center\"><font size=\"+1\">There was a fatal error starting up minecraft and FML</font></div><br/>Minecraft cannot launch in it\'s current configuration<br/>Please consult the file <i><a href=\"file:///%s\">%s</a></i> for further information</html>", new Object[] {var3, var3}), "Fatal FML error", 0);
                }
                catch (Exception var4)
                {
                    ;
                }
            }

            throw new RuntimeException(var5);
        }
    }

    private File computeExistingClientHome()
    {
        Class var1 = ReflectionHelper.getClass(this.getClass().getClassLoader(), new String[] {"net.minecraft.client.Minecraft"});
        String var2 = System.getProperty("minecraft.applet.TargetDirectory");

        if (var2 != null)
        {
            var2 = var2.replace('/', File.separatorChar);
            ReflectionHelper.setPrivateValue(var1, (Object)null, new File(var2), new String[] {"minecraftDir", "an", "minecraftDir"});
        }

        Method var3 = ReflectionHelper.findMethod(var1, (Object)null, new String[] {"getMinecraftDir", "getMinecraftDir", "b"}, new Class[0]);

        try
        {
            var3.invoke((Object)null, new Object[0]);
        }
        catch (Exception var5)
        {
            ;
        }

        File var4 = (File)ReflectionHelper.getPrivateValue(var1, (Object)null, new String[] {"minecraftDir", "an", "minecraftDir"});
        return var4;
    }

    public static void appletEntry(Applet var0)
    {
        side = "CLIENT";
        logFileNamePattern = "ForgeModLoader-client-%g.log";
        instance().relaunchApplet(var0);
    }

    private void relaunchApplet(Applet var1)
    {
        this.showWindow(true);

        if (var1.getClass().getClassLoader() == this.classLoader)
        {
            if (this.popupWindow != null)
            {
                this.popupWindow.setVisible(false);
                this.popupWindow.dispose();
            }

            try
            {
                this.newApplet = var1;
                this.appletClass = ReflectionHelper.getClass(this.classLoader, new String[] {"net.minecraft.client.MinecraftApplet"});
                ReflectionHelper.findMethod(this.appletClass, this.newApplet, new String[] {"fmlInitReentry"}, new Class[0]).invoke(this.newApplet, new Object[0]);
            }
            catch (Exception var11)
            {
                System.out.println("FMLRelauncher.relaunchApplet");
                var11.printStackTrace();
                throw new RuntimeException(var11);
            }
        }
        else
        {
            File var2 = this.computeExistingClientHome();
            this.setupHome(var2);
            this.setupNewClientHome(var2);
            Class var3 = ReflectionHelper.getClass(this.getClass().getClassLoader(), new String[] {"java.applet.Applet"});

            try
            {
                this.appletClass = ReflectionHelper.getClass(this.classLoader, new String[] {"net.minecraft.client.MinecraftApplet"});
                this.newApplet = this.appletClass.newInstance();
                Object var4 = ReflectionHelper.getPrivateValue(ReflectionHelper.getClass(this.getClass().getClassLoader(), new String[] {"java.awt.Component"}), var1, new String[] {"parent"});
                String var5 = System.getProperty("minecraft.applet.WrapperClass", "net.minecraft.Launcher");
                Class var6 = ReflectionHelper.getClass(this.getClass().getClassLoader(), new String[] {var5});

                if (!var6.isInstance(var4))
                {
                    FMLRelaunchLog.severe("Found unknown applet parent %s, unable to inject!\n", new Object[] {var4.getClass().getName()});
                    throw new RuntimeException();
                }

                ReflectionHelper.findMethod(ReflectionHelper.getClass(this.getClass().getClassLoader(), new String[] {"java.awt.Container"}), var1, new String[] {"removeAll"}, new Class[0]).invoke(var4, new Object[0]);
                ReflectionHelper.findMethod(var6, var4, new String[] {"replace"}, new Class[] {var3}).invoke(var4, new Object[] {this.newApplet});
            }
            catch (Exception var12)
            {
                throw new RuntimeException(var12);
            }
            finally
            {
                if (this.popupWindow != null)
                {
                    this.popupWindow.setVisible(false);
                    this.popupWindow.dispose();
                }
            }
        }
    }

    public static void appletStart(Applet var0)
    {
        instance().startApplet(var0);
    }

    private void startApplet(Applet var1)
    {
        if (var1.getClass().getClassLoader() == this.classLoader)
        {
            if (this.popupWindow != null)
            {
                this.popupWindow.setVisible(false);
                this.popupWindow.dispose();
            }

            if (RelaunchLibraryManager.downloadMonitor.shouldStopIt())
            {
                System.exit(1);
            }

            try
            {
                ReflectionHelper.findMethod(this.appletClass, this.newApplet, new String[] {"fmlStartReentry"}, new Class[0]).invoke(this.newApplet, new Object[0]);
            }
            catch (Exception var3)
            {
                System.out.println("FMLRelauncher.startApplet");
                var3.printStackTrace();
                throw new RuntimeException(var3);
            }
        }
    }

    public static String side()
    {
        return side;
    }
}

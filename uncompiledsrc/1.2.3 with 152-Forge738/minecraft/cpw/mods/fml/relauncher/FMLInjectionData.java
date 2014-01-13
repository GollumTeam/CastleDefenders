package cpw.mods.fml.relauncher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

public class FMLInjectionData
{
    static File minecraftHome;
    static String major;
    static String minor;
    static String rev;
    static String build;
    static String mccversion;
    static String mcpversion;
    static String deobfuscationDataHash;
    public static List containers = new ArrayList();

    static void build(File var0, RelaunchClassLoader var1)
    {
        minecraftHome = var0;
        InputStream var2 = var1.getResourceAsStream("fmlversion.properties");
        Properties var3 = new Properties();

        if (var2 != null)
        {
            try
            {
                var3.load(var2);
            }
            catch (IOException var5)
            {
                FMLRelaunchLog.log(Level.SEVERE, (Throwable)var5, "Could not get FML version information - corrupted installation detected!", new Object[0]);
            }
        }

        major = var3.getProperty("fmlbuild.major.number", "missing");
        minor = var3.getProperty("fmlbuild.minor.number", "missing");
        rev = var3.getProperty("fmlbuild.revision.number", "missing");
        build = var3.getProperty("fmlbuild.build.number", "missing");
        mccversion = var3.getProperty("fmlbuild.mcversion", "missing");
        mcpversion = var3.getProperty("fmlbuild.mcpversion", "missing");
        deobfuscationDataHash = var3.getProperty("fmlbuild.deobfuscation.hash", "deadbeef");
    }

    static String debfuscationDataName()
    {
        return "deobfuscation_data_" + mccversion + ".zip";
    }

    public static Object[] data()
    {
        return new Object[] {major, minor, rev, build, mccversion, mcpversion, minecraftHome, containers};
    }
}

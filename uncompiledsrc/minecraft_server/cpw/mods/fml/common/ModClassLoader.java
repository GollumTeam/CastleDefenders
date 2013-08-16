package cpw.mods.fml.common;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.asm.transformers.AccessTransformer;
import cpw.mods.fml.relauncher.RelaunchClassLoader;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.logging.Level;

public class ModClassLoader extends URLClassLoader
{
    private static final List STANDARD_LIBRARIES = ImmutableList.of("jinput.jar", "lwjgl.jar", "lwjgl_util.jar");
    private RelaunchClassLoader mainClassLoader;

    public ModClassLoader(ClassLoader var1)
    {
        super(new URL[0], (ClassLoader)null);
        this.mainClassLoader = (RelaunchClassLoader)var1;
    }

    public void addFile(File var1) throws MalformedURLException
    {
        URL var2 = var1.toURI().toURL();
        this.mainClassLoader.addURL(var2);
    }

    public Class loadClass(String var1) throws ClassNotFoundException
    {
        return this.mainClassLoader.loadClass(var1);
    }

    public File[] getParentSources()
    {
        List var1 = this.mainClassLoader.getSources();
        File[] var2 = new File[var1.size()];

        try
        {
            for (int var3 = 0; var3 < var1.size(); ++var3)
            {
                var2[var3] = new File(((URL)var1.get(var3)).toURI());
            }

            return var2;
        }
        catch (URISyntaxException var4)
        {
            FMLLog.log(Level.SEVERE, (Throwable)var4, "Unable to process our input to locate the minecraft code", new Object[0]);
            throw new LoaderException(var4);
        }
    }

    public List getDefaultLibraries()
    {
        return STANDARD_LIBRARIES;
    }

    public Class loadBaseModClass(String var1) throws Exception
    {
        AccessTransformer var2 = (AccessTransformer)this.mainClassLoader.getTransformers().get(0);
        var2.ensurePublicAccessFor(var1);
        return Class.forName(var1, true, this);
    }
}

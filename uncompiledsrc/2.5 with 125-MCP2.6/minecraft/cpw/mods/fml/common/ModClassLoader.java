package cpw.mods.fml.common;

import java.io.File;
import java.lang.reflect.Method;
import java.net.*;
import java.util.logging.Logger;

public class ModClassLoader extends URLClassLoader
{
    public ModClassLoader()
    {
        super(new URL[0], (cpw.mods.fml.common.ModClassLoader.class).getClassLoader());
    }

    public ModClassLoader(ClassLoader classloader)
    {
        super(new URL[0], null);
    }

    public void addFile(File file) throws MalformedURLException
    {
        ClassLoader classloader = getParent();

        if (classloader instanceof URLClassLoader)
        {
            URLClassLoader urlclassloader = (URLClassLoader)classloader;
            URL url = file.toURI().toURL();

            try
            {
                Method method = (java.net.URLClassLoader.class).getDeclaredMethod("addURL", new Class[]
                        {
                            java.net.URL.class
                        });
                method.setAccessible(true);
                method.invoke(urlclassloader, new Object[]
                        {
                            url
                        });
            }
            catch (Exception exception)
            {
                Loader.log.severe("A fatal error occured attempting to load a file into the classloader");
                throw new LoaderException(exception);
            }
        }
    }

    public File[] getParentSources()
    {
        ClassLoader classloader = getParent();

        if (classloader instanceof URLClassLoader)
        {
            URLClassLoader urlclassloader = (URLClassLoader)classloader;
            URL aurl[] = urlclassloader.getURLs();
            File afile[] = new File[aurl.length];

            try
            {
                for (int i = 0; i < aurl.length; i++)
                {
                    afile[i] = new File(aurl[i].toURI());
                }

                return afile;
            }
            catch (URISyntaxException urisyntaxexception)
            {
                Loader.log.throwing("ModClassLoader", "getParentSources", urisyntaxexception);
            }
        }

        Loader.log.severe("Unable to process our input to locate the minecraft code");
        throw new LoaderException();
    }
}

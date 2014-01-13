package cpw.mods.fml.common;

import com.google.common.base.Strings;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.discovery.ASMDataTable$ASMData;
import cpw.mods.fml.relauncher.Side;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class ProxyInjector
{
    public static void inject(ModContainer var0, ASMDataTable var1, Side var2, ILanguageAdapter var3)
    {
        FMLLog.fine("Attempting to inject @SidedProxy classes into %s", new Object[] {var0.getModId()});
        Set var4 = var1.getAnnotationsFor(var0).get(SidedProxy.class.getName());
        ClassLoader var5 = Loader.instance().getModClassLoader();
        Iterator var6 = var4.iterator();

        while (var6.hasNext())
        {
            ASMDataTable$ASMData var7 = (ASMDataTable$ASMData)var6.next();

            try
            {
                Class var8 = Class.forName(var7.getClassName(), true, var5);
                Field var9 = var8.getDeclaredField(var7.getObjectName());

                if (var9 == null)
                {
                    FMLLog.severe("Attempted to load a proxy type into %s.%s but the field was not found", new Object[] {var7.getClassName(), var7.getObjectName()});
                    throw new LoaderException();
                }

                SidedProxy var10 = (SidedProxy)var9.getAnnotation(SidedProxy.class);

                if (!Strings.isNullOrEmpty(var10.modId()) && !var10.modId().equals(var0.getModId()))
                {
                    FMLLog.fine("Skipping proxy injection for %s.%s since it is not for mod %s", new Object[] {var7.getClassName(), var7.getObjectName(), var0.getModId()});
                }
                else
                {
                    String var11 = var2.isClient() ? var10.clientSide() : var10.serverSide();
                    Object var12 = Class.forName(var11, true, var5).newInstance();

                    if (var3.supportsStatics() && (var9.getModifiers() & 8) == 0)
                    {
                        FMLLog.severe("Attempted to load a proxy type %s into %s.%s, but the field is not static", new Object[] {var11, var7.getClassName(), var7.getObjectName()});
                        throw new LoaderException();
                    }

                    if (!var9.getType().isAssignableFrom(var12.getClass()))
                    {
                        FMLLog.severe("Attempted to load a proxy type %s into %s.%s, but the types don\'t match", new Object[] {var11, var7.getClassName(), var7.getObjectName()});
                        throw new LoaderException();
                    }

                    var3.setProxy(var9, var8, var12);
                }
            }
            catch (Exception var13)
            {
                FMLLog.log(Level.SEVERE, (Throwable)var13, "An error occured trying to load a proxy into %s.%s", new Object[] {var7.getAnnotationInfo(), var7.getClassName(), var7.getObjectName()});
                throw new LoaderException(var13);
            }
        }

        var3.setInternalProxies(var0, var2, var5);
    }
}

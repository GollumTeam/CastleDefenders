package cpw.mods.fml.common;

import cpw.mods.fml.common.modloader.BaseMod;
import cpw.mods.fml.common.modloader.ModProperty;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public interface IFMLSidedHandler
{
    public abstract Logger getMinecraftLogger();

    public abstract File getMinecraftRootDirectory();

    public abstract boolean isModLoaderMod(Class class1);

    public abstract ModContainer loadBaseModMod(Class class1, File file);

    public abstract Object getMinecraftInstance();

    public abstract String getCurrentLanguage();

    public abstract Properties getCurrentLanguageTable();

    public abstract String getObjectName(Object obj);

    public abstract ModMetadata readMetadataFrom(InputStream inputstream, ModContainer modcontainer) throws Exception;

    public abstract void profileStart(String s);

    public abstract void profileEnd();

    public abstract ModProperty getModLoaderPropertyFor(Field field);

    public abstract List getAdditionalBrandingInformation();

    public abstract Side getSide();

    public abstract ProxyInjector findSidedProxyOn(BaseMod basemod);
}

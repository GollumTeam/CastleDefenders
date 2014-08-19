package cpw.mods.fml.common;

import java.util.Arrays;

public class FMLModLoaderContainer extends FMLModContainer
{
    public FMLModLoaderContainer()
    {
        super("Forge Mod Loader");
    }

    public ModMetadata getMetadata()
    {
        if (super.getMetadata() == null)
        {
            ModMetadata modmetadata = new ModMetadata(this);
            setMetadata(modmetadata);
            modmetadata.name = "Forge Mod Loader";
            modmetadata.version = Loader.instance().getFMLVersionString();
            modmetadata.credits = "Made possible with help from many people";
            modmetadata.authorList = Arrays.asList(new String[]
                    {
                        "cpw, LexManos"
                    });
            modmetadata.description = "The Forge Mod Loader provides the ability for systems to load mods from the file system. It also provides key capabilities for mods to be able to cooperate and provide a good modding environment. The mod loading system is compatible with ModLoader, all your ModLoader mods should work.";
            modmetadata.url = "https://github.com/cpw/FML/wiki";
            modmetadata.updateUrl = "https://github.com/cpw/FML/wiki";
            modmetadata.screenshots = new String[0];
            modmetadata.logoFile = "";
        }

        return super.getMetadata();
    }

    public String getName()
    {
        return "Forge Mod Loader";
    }

    public String getVersion()
    {
        return Loader.instance().getFMLVersionString();
    }
}

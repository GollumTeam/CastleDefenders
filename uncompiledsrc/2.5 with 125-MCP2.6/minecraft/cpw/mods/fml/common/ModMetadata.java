package cpw.mods.fml.common;

import java.util.*;

public class ModMetadata
{
    public static final class ModType extends Enum
    {
        public static final ModType MODLOADER;
        public static final ModType FML;
        private static final ModType $VALUES[];

        public static ModType[] values()
        {
            return (ModType[])$VALUES.clone();
        }

        public static ModType valueOf(String s)
        {
            return (ModType)Enum.valueOf(cpw.mods.fml.common.ModMetadata$ModType.class, s);
        }

        static
        {
            MODLOADER = new ModType("MODLOADER", 0);
            FML = new ModType("FML", 1);
            $VALUES = (new ModType[]
                    {
                        MODLOADER, FML
                    });
        }

        private ModType(String s, int i)
        {
            super(s, i);
        }
    }

    public ModContainer mod;
    public ModType type;
    public String name;
    public String description;
    public String url;
    public String updateUrl;
    public String logoFile;
    public String version;
    public List authorList;
    public String credits;
    public String parent;
    public String screenshots[];
    public ModContainer parentMod;
    public List childMods;

    public ModMetadata(ModContainer modcontainer)
    {
        url = "";
        updateUrl = "";
        logoFile = "";
        version = "";
        authorList = new ArrayList(1);
        credits = "";
        parent = "";
        childMods = new ArrayList(1);
        mod = modcontainer;
        type = (modcontainer instanceof FMLModContainer) ? ModType.FML : ModType.MODLOADER;
    }

    public void associate(Map map)
    {
        if (parent != null && parent.length() > 0)
        {
            ModContainer modcontainer = (ModContainer)map.get(parent);

            if (modcontainer != null && modcontainer.getMetadata() != null)
            {
                modcontainer.getMetadata().childMods.add(mod);
                parentMod = modcontainer;
            }
        }
    }

    public String getChildModCountString()
    {
        return String.format("%d child mod%s", new Object[]
                {
                    Integer.valueOf(childMods.size()), childMods.size() == 1 ? "" : "s"
                });
    }

    public String getAuthorList()
    {
        StringBuilder stringbuilder = new StringBuilder();

        for (int i = 0; i < authorList.size(); i++)
        {
            stringbuilder.append((String)authorList.get(i));

            if (i < authorList.size() - 1)
            {
                stringbuilder.append(", ");
            }
        }

        return stringbuilder.toString();
    }

    public String getChildModList()
    {
        StringBuilder stringbuilder = new StringBuilder();

        for (int i = 0; i < childMods.size(); i++)
        {
            stringbuilder.append(((ModContainer)childMods.get(i)).getMetadata().name);

            if (i < childMods.size() - 1)
            {
                stringbuilder.append(", ");
            }
        }

        return stringbuilder.toString();
    }
}

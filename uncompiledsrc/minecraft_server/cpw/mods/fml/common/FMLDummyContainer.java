package cpw.mods.fml.common;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.registry.GameData;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class FMLDummyContainer extends DummyModContainer implements WorldAccessContainer
{
    public FMLDummyContainer()
    {
        super(new ModMetadata());
        ModMetadata var1 = this.getMetadata();
        var1.modId = "FML";
        var1.name = "Forge Mod Loader";
        var1.version = Loader.instance().getFMLVersionString();
        var1.credits = "Made possible with help from many people";
        var1.authorList = Arrays.asList(new String[] {"cpw, LexManos"});
        var1.description = "The Forge Mod Loader provides the ability for systems to load mods from the file system. It also provides key capabilities for mods to be able to cooperate and provide a good modding environment. The mod loading system is compatible with ModLoader, all your ModLoader mods should work.";
        var1.url = "https://github.com/MinecraftForge/FML/wiki";
        var1.updateUrl = "https://github.com/MinecraftForge/FML/wiki";
        var1.screenshots = new String[0];
        var1.logoFile = "";
    }

    public boolean registerBus(EventBus var1, LoadController var2)
    {
        return true;
    }

    public NBTTagCompound getDataForWriting(SaveHandler var1, WorldInfo var2)
    {
        NBTTagCompound var3 = new NBTTagCompound();
        NBTTagList var4 = new NBTTagList();
        Iterator var5 = Loader.instance().getActiveModList().iterator();

        while (var5.hasNext())
        {
            ModContainer var6 = (ModContainer)var5.next();
            NBTTagCompound var7 = new NBTTagCompound();
            var7.setString("ModId", var6.getModId());
            var7.setString("ModVersion", var6.getVersion());
            var4.appendTag(var7);
        }

        var3.setTag("ModList", var4);
        NBTTagList var8 = new NBTTagList();
        GameData.writeItemData(var8);
        var3.setTag("ModItemData", var8);
        return var3;
    }

    public void readData(SaveHandler var1, WorldInfo var2, Map var3, NBTTagCompound var4)
    {
        NBTTagList var5;

        if (var4.hasKey("ModList"))
        {
            var5 = var4.getTagList("ModList");

            for (int var6 = 0; var6 < var5.tagCount(); ++var6)
            {
                NBTTagCompound var7 = (NBTTagCompound)var5.tagAt(var6);
                String var8 = var7.getString("ModId");
                String var9 = var7.getString("ModVersion");
                ModContainer var10 = (ModContainer)Loader.instance().getIndexedModList().get(var8);

                if (var10 == null)
                {
                    FMLLog.log("fml.ModTracker", Level.SEVERE, "This world was saved with mod %s which appears to be missing, things may not work well", new Object[] {var8});
                }
                else if (!var9.equals(var10.getVersion()))
                {
                    FMLLog.log("fml.ModTracker", Level.INFO, "This world was saved with mod %s version %s and it is now at version %s, things may not work well", new Object[] {var8, var9, var10.getVersion()});
                }
            }
        }

        if (var4.hasKey("ModItemData"))
        {
            var5 = var4.getTagList("ModItemData");
            Set var11 = GameData.buildWorldItemData(var5);
            GameData.validateWorldSave(var11);
        }
        else
        {
            GameData.validateWorldSave((Set)null);
        }
    }

    public Certificate getSigningCertificate()
    {
        Certificate[] var1 = this.getClass().getProtectionDomain().getCodeSource().getCertificates();
        return var1 != null ? var1[0] : null;
    }
}

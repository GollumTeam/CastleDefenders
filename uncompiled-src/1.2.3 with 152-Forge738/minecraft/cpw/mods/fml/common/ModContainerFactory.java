package cpw.mods.fml.common;

import cpw.mods.fml.common.discovery.ModCandidate;
import cpw.mods.fml.common.discovery.asm.ASMModParser;
import cpw.mods.fml.common.discovery.asm.ModAnnotation;
import cpw.mods.fml.common.modloader.ModLoaderModContainer;
import java.io.File;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.objectweb.asm.Type;

public class ModContainerFactory
{
    private static Pattern modClass = Pattern.compile(".*(\\.|)(mod\\_[^\\s$]+)$");
    private static ModContainerFactory INSTANCE = new ModContainerFactory();

    public static ModContainerFactory instance()
    {
        return INSTANCE;
    }

    public ModContainer build(ASMModParser var1, File var2, ModCandidate var3)
    {
        String var4 = var1.getASMType().getClassName();

        if (var1.isBaseMod(var3.getRememberedBaseMods()) && modClass.matcher(var4).find())
        {
            FMLLog.fine("Identified a BaseMod type mod %s", new Object[] {var4});
            return new ModLoaderModContainer(var4, var2, var1.getBaseModProperties());
        }
        else
        {
            if (modClass.matcher(var4).find())
            {
                FMLLog.fine("Identified a class %s following modloader naming convention but not directly a BaseMod or currently seen subclass", new Object[] {var4});
                var3.rememberModCandidateType(var1);
            }
            else if (var1.isBaseMod(var3.getRememberedBaseMods()))
            {
                FMLLog.fine("Found a basemod %s of non-standard naming format", new Object[] {var4});
                var3.rememberBaseModType(var4);
            }

            if (var4.startsWith("net.minecraft.src.") && var3.isClasspath() && !var3.isMinecraftJar())
            {
                FMLLog.severe("FML has detected a mod that is using a package name based on \'net.minecraft.src\' : %s. This is generally a severe programming error.  There should be no mod code in the minecraft namespace. MOVE YOUR MOD! If you\'re in eclipse, select your source code and \'refactor\' it into a new package. Go on. DO IT NOW!", new Object[] {var4});
            }

            Iterator var5 = var1.getAnnotations().iterator();
            ModAnnotation var6;

            do
            {
                if (!var5.hasNext())
                {
                    return null;
                }

                var6 = (ModAnnotation)var5.next();
            }
            while (!var6.getASMType().equals(Type.getType(Mod.class)));

            FMLLog.fine("Identified an FMLMod type mod %s", new Object[] {var4});
            return new FMLModContainer(var4, var2, var6.getValues());
        }
    }
}

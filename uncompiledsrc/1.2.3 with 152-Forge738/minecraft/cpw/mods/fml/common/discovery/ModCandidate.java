package cpw.mods.fml.common.discovery;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.discovery.asm.ASMModParser;
import java.io.File;
import java.util.List;

public class ModCandidate
{
    private File classPathRoot;
    private File modContainer;
    private ContainerType sourceType;
    private boolean classpath;
    private List baseModTypes;
    private boolean isMinecraft;
    private List baseModCandidateTypes;

    public ModCandidate(File var1, File var2, ContainerType var3)
    {
        this(var1, var2, var3, false, false);
    }

    public ModCandidate(File var1, File var2, ContainerType var3, boolean var4, boolean var5)
    {
        this.baseModTypes = Lists.newArrayList();
        this.baseModCandidateTypes = Lists.newArrayListWithCapacity(1);
        this.classPathRoot = var1;
        this.modContainer = var2;
        this.sourceType = var3;
        this.isMinecraft = var4;
        this.classpath = var5;
    }

    public File getClassPathRoot()
    {
        return this.classPathRoot;
    }

    public File getModContainer()
    {
        return this.modContainer;
    }

    public ContainerType getSourceType()
    {
        return this.sourceType;
    }

    public List explore(ASMDataTable var1)
    {
        List var2 = this.sourceType.findMods(this, var1);

        if (!this.baseModCandidateTypes.isEmpty())
        {
            FMLLog.info("Attempting to reparse the mod container %s", new Object[] {this.getModContainer().getName()});
            return this.sourceType.findMods(this, var1);
        }
        else
        {
            return var2;
        }
    }

    public boolean isClasspath()
    {
        return this.classpath;
    }

    public void rememberBaseModType(String var1)
    {
        this.baseModTypes.add(var1);
    }

    public List getRememberedBaseMods()
    {
        return this.baseModTypes;
    }

    public boolean isMinecraftJar()
    {
        return this.isMinecraft;
    }

    public void rememberModCandidateType(ASMModParser var1)
    {
        this.baseModCandidateTypes.add(var1);
    }
}

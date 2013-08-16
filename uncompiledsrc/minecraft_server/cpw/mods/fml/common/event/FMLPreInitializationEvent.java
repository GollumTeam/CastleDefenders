package cpw.mods.fml.common.event;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.LoaderState$ModState;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.discovery.ASMDataTable;
import java.io.File;
import java.security.CodeSource;
import java.security.cert.Certificate;
import java.util.Properties;
import java.util.logging.Logger;

public class FMLPreInitializationEvent extends FMLStateEvent
{
    private ModMetadata modMetadata;
    private File sourceFile;
    private File configurationDir;
    private File suggestedConfigFile;
    private ASMDataTable asmData;
    private ModContainer modContainer;

    public FMLPreInitializationEvent(Object ... var1)
    {
        super(var1);
        this.asmData = (ASMDataTable)var1[0];
        this.configurationDir = (File)var1[1];
    }

    public LoaderState$ModState getModState()
    {
        return LoaderState$ModState.PREINITIALIZED;
    }

    public void applyModContainer(ModContainer var1)
    {
        this.modContainer = var1;
        this.modMetadata = var1.getMetadata();
        this.sourceFile = var1.getSource();
        this.suggestedConfigFile = new File(this.configurationDir, var1.getModId() + ".cfg");
    }

    public File getSourceFile()
    {
        return this.sourceFile;
    }

    public ModMetadata getModMetadata()
    {
        return this.modMetadata;
    }

    public File getModConfigurationDirectory()
    {
        return this.configurationDir;
    }

    public File getSuggestedConfigurationFile()
    {
        return this.suggestedConfigFile;
    }

    public ASMDataTable getAsmData()
    {
        return this.asmData;
    }

    public Properties getVersionProperties()
    {
        return this.modContainer instanceof FMLModContainer ? ((FMLModContainer)this.modContainer).searchForVersionProperties() : null;
    }

    public Logger getModLog()
    {
        Logger var1 = Logger.getLogger(this.modContainer.getModId());
        var1.setParent(FMLLog.getLogger());
        return var1;
    }

    @Deprecated
    public Certificate[] getFMLSigningCertificates()
    {
        CodeSource var1 = this.getClass().getClassLoader().getParent().getClass().getProtectionDomain().getCodeSource();
        Certificate[] var2 = var1.getCertificates();
        return var2 == null ? new Certificate[0] : var2;
    }
}

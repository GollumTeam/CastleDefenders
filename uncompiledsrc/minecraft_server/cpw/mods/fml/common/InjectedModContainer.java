package cpw.mods.fml.common;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.VersionRange;
import java.io.File;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Set;

public class InjectedModContainer implements ModContainer
{
    private File source;
    public final ModContainer wrappedContainer;

    public InjectedModContainer(ModContainer var1, File var2)
    {
        this.source = var2;
        this.wrappedContainer = var1;
    }

    public String getModId()
    {
        return this.wrappedContainer.getModId();
    }

    public String getName()
    {
        return this.wrappedContainer.getName();
    }

    public String getVersion()
    {
        return this.wrappedContainer.getVersion();
    }

    public File getSource()
    {
        return this.source;
    }

    public ModMetadata getMetadata()
    {
        return this.wrappedContainer.getMetadata();
    }

    public void bindMetadata(MetadataCollection var1)
    {
        this.wrappedContainer.bindMetadata(var1);
    }

    public void setEnabledState(boolean var1)
    {
        this.wrappedContainer.setEnabledState(var1);
    }

    public Set getRequirements()
    {
        return this.wrappedContainer.getRequirements();
    }

    public List getDependencies()
    {
        return this.wrappedContainer.getDependencies();
    }

    public List getDependants()
    {
        return this.wrappedContainer.getDependants();
    }

    public String getSortingRules()
    {
        return this.wrappedContainer.getSortingRules();
    }

    public boolean registerBus(EventBus var1, LoadController var2)
    {
        return this.wrappedContainer.registerBus(var1, var2);
    }

    public boolean matches(Object var1)
    {
        return this.wrappedContainer.matches(var1);
    }

    public Object getMod()
    {
        return this.wrappedContainer.getMod();
    }

    public ArtifactVersion getProcessedVersion()
    {
        return this.wrappedContainer.getProcessedVersion();
    }

    public boolean isNetworkMod()
    {
        return this.wrappedContainer.isNetworkMod();
    }

    public boolean isImmutable()
    {
        return true;
    }

    public String getDisplayVersion()
    {
        return this.wrappedContainer.getDisplayVersion();
    }

    public VersionRange acceptableMinecraftVersionRange()
    {
        return this.wrappedContainer.acceptableMinecraftVersionRange();
    }

    public WorldAccessContainer getWrappedWorldAccessContainer()
    {
        return this.wrappedContainer instanceof WorldAccessContainer ? (WorldAccessContainer)this.wrappedContainer : null;
    }

    public Certificate getSigningCertificate()
    {
        return this.wrappedContainer.getSigningCertificate();
    }

    public String toString()
    {
        return "Wrapped{" + this.wrappedContainer.toString() + "}";
    }
}

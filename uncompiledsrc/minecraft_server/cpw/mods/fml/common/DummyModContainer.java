package cpw.mods.fml.common;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.VersionRange;
import java.io.File;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DummyModContainer implements ModContainer
{
    private ModMetadata md;
    private ArtifactVersion processedVersion;
    private String label;

    public DummyModContainer(ModMetadata var1)
    {
        this.md = var1;
    }

    public DummyModContainer(String var1)
    {
        this.label = var1;
    }

    public DummyModContainer() {}

    public void bindMetadata(MetadataCollection var1) {}

    public List getDependants()
    {
        return Collections.emptyList();
    }

    public List getDependencies()
    {
        return Collections.emptyList();
    }

    public Set getRequirements()
    {
        return Collections.emptySet();
    }

    public ModMetadata getMetadata()
    {
        return this.md;
    }

    public Object getMod()
    {
        return null;
    }

    public String getModId()
    {
        return this.md.modId;
    }

    public String getName()
    {
        return this.md.name;
    }

    public String getSortingRules()
    {
        return "";
    }

    public File getSource()
    {
        return null;
    }

    public String getVersion()
    {
        return this.md.version;
    }

    public boolean matches(Object var1)
    {
        return false;
    }

    public void setEnabledState(boolean var1) {}

    public boolean registerBus(EventBus var1, LoadController var2)
    {
        return false;
    }

    public ArtifactVersion getProcessedVersion()
    {
        if (this.processedVersion == null)
        {
            this.processedVersion = new DefaultArtifactVersion(this.getModId(), this.getVersion());
        }

        return this.processedVersion;
    }

    public boolean isImmutable()
    {
        return false;
    }

    public boolean isNetworkMod()
    {
        return false;
    }

    public String getDisplayVersion()
    {
        return this.md.version;
    }

    public VersionRange acceptableMinecraftVersionRange()
    {
        return Loader.instance().getMinecraftModContainer().getStaticVersionRange();
    }

    public Certificate getSigningCertificate()
    {
        return null;
    }

    public String toString()
    {
        return this.md != null ? this.getModId() : "Dummy Container (" + this.label + ") @" + System.identityHashCode(this);
    }
}

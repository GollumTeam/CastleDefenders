package cpw.mods.fml.common.versioning;

public class DefaultArtifactVersion implements ArtifactVersion
{
    private ComparableVersion comparableVersion;
    private String label;
    private boolean unbounded;
    private VersionRange range;

    public DefaultArtifactVersion(String var1)
    {
        this.comparableVersion = new ComparableVersion(var1);
        this.range = VersionRange.createFromVersion(var1, this);
    }

    public DefaultArtifactVersion(String var1, VersionRange var2)
    {
        this.label = var1;
        this.range = var2;
    }

    public DefaultArtifactVersion(String var1, String var2)
    {
        this(var2);
        this.label = var1;
    }

    public DefaultArtifactVersion(String var1, boolean var2)
    {
        this.label = var1;
        this.unbounded = true;
    }

    public boolean equals(Object var1)
    {
        return ((DefaultArtifactVersion)var1).containsVersion(this);
    }

    public int compareTo(ArtifactVersion var1)
    {
        return this.unbounded ? 0 : this.comparableVersion.compareTo(((DefaultArtifactVersion)var1).comparableVersion);
    }

    public String getLabel()
    {
        return this.label;
    }

    public boolean containsVersion(ArtifactVersion var1)
    {
        return !var1.getLabel().equals(this.getLabel()) ? false : (this.unbounded ? true : (this.range != null ? this.range.containsVersion(var1) : false));
    }

    public String getVersionString()
    {
        return this.comparableVersion == null ? "unknown" : this.comparableVersion.toString();
    }

    public String getRangeString()
    {
        return this.range == null ? "any" : this.range.toString();
    }

    public String toString()
    {
        return this.label == null ? this.comparableVersion.toString() : this.label + (this.unbounded ? "" : "@" + this.range);
    }

    public VersionRange getRange()
    {
        return this.range;
    }

    public int compareTo(Object var1)
    {
        return this.compareTo((ArtifactVersion)var1);
    }
}

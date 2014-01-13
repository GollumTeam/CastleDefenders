package cpw.mods.fml.common.versioning;

public interface ArtifactVersion extends Comparable
{
    String getLabel();

    String getVersionString();

    boolean containsVersion(ArtifactVersion var1);

    String getRangeString();
}

package cpw.mods.fml.common.functions;

import com.google.common.base.Function;
import cpw.mods.fml.common.versioning.ArtifactVersion;

public class ArtifactVersionNameFunction implements Function
{
    public String apply(ArtifactVersion var1)
    {
        return var1.getLabel();
    }

    public Object apply(Object var1)
    {
        return this.apply((ArtifactVersion)var1);
    }
}

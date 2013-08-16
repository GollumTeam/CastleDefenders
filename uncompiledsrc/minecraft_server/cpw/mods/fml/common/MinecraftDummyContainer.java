package cpw.mods.fml.common;

import cpw.mods.fml.common.versioning.VersionParser;
import cpw.mods.fml.common.versioning.VersionRange;

public class MinecraftDummyContainer extends DummyModContainer
{
    private VersionRange staticRange;

    public MinecraftDummyContainer(String var1)
    {
        super(new ModMetadata());
        this.getMetadata().modId = "Minecraft";
        this.getMetadata().name = "Minecraft";
        this.getMetadata().version = var1;
        this.staticRange = VersionParser.parseRange("[" + var1 + "]");
    }

    public VersionRange getStaticVersionRange()
    {
        return this.staticRange;
    }
}

package cpw.mods.fml.client;

import net.minecraft.src.TextureFX;

class OverrideInfo
{
    public String texture;
    public String override;
    public int index;
    public int imageIndex;
    public TextureFX textureFX;
    public boolean added;

    OverrideInfo()
    {
    }

    public boolean equals(Object obj)
    {
        try
        {
            OverrideInfo overrideinfo = (OverrideInfo)obj;
            return index == overrideinfo.index && imageIndex == overrideinfo.imageIndex;
        }
        catch (Exception exception)
        {
            return false;
        }
    }

    public int hashCode()
    {
        return index + imageIndex;
    }
}

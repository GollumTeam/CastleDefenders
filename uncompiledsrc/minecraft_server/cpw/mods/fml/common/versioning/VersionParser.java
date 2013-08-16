package cpw.mods.fml.common.versioning;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.LoaderException;
import java.util.ArrayList;
import java.util.logging.Level;

public class VersionParser
{
    private static final Splitter SEPARATOR = Splitter.on('@').omitEmptyStrings().trimResults();

    public static ArtifactVersion parseVersionReference(String var0)
    {
        if (Strings.isNullOrEmpty(var0))
        {
            throw new RuntimeException(String.format("Empty reference %s", new Object[] {var0}));
        }
        else
        {
            ArrayList var1 = Lists.newArrayList(SEPARATOR.split(var0));

            if (var1.size() > 2)
            {
                throw new RuntimeException(String.format("Invalid versioned reference %s", new Object[] {var0}));
            }
            else
            {
                return var1.size() == 1 ? new DefaultArtifactVersion((String)var1.get(0), true) : new DefaultArtifactVersion((String)var1.get(0), parseRange((String)var1.get(1)));
            }
        }
    }

    public static boolean satisfies(ArtifactVersion var0, ArtifactVersion var1)
    {
        return var0.containsVersion(var1);
    }

    public static VersionRange parseRange(String var0)
    {
        try
        {
            return VersionRange.createFromVersionSpec(var0);
        }
        catch (InvalidVersionSpecificationException var2)
        {
            FMLLog.log(Level.SEVERE, (Throwable)var2, "Unable to parse a version range specification successfully %s", new Object[] {var0});
            throw new LoaderException(var2);
        }
    }
}

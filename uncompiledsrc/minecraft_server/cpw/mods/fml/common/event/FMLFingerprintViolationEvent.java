package cpw.mods.fml.common.event;

import com.google.common.collect.ImmutableSet;
import java.io.File;
import java.util.Set;

public class FMLFingerprintViolationEvent extends FMLEvent
{
    public final boolean isDirectory;
    public final Set fingerprints;
    public final File source;
    public final String expectedFingerprint;

    public FMLFingerprintViolationEvent(boolean var1, File var2, ImmutableSet var3, String var4)
    {
        this.isDirectory = var1;
        this.source = var2;
        this.fingerprints = var3;
        this.expectedFingerprint = var4;
    }
}

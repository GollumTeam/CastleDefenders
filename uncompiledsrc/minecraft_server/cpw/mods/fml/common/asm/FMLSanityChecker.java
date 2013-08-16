package cpw.mods.fml.common.asm;

import cpw.mods.fml.common.CertificateHelper;
import cpw.mods.fml.common.asm.FMLSanityChecker$1;
import cpw.mods.fml.common.asm.FMLSanityChecker$MLDetectorClassVisitor;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.RelaunchClassLoader;
import java.awt.Component;
import java.io.File;
import java.security.CodeSource;
import java.security.cert.Certificate;
import java.util.Map;
import javax.swing.JOptionPane;
import org.objectweb.asm.ClassReader;

public class FMLSanityChecker implements IFMLCallHook
{
    private static final String FMLFINGERPRINT = "AE:F6:54:79:96:E9:1B:D1:59:70:6C:B4:6B:F5:4A:89:C5:CE:08:1D".toLowerCase().replace(":", "");
    private static final String FORGEFINGERPRINT = "DE:4C:F8:A3:F3:BC:15:63:58:10:04:4C:39:24:0B:F9:68:04:EA:7D".toLowerCase().replace(":", "");
    private RelaunchClassLoader cl;

    public Void call() throws Exception
    {
        CodeSource var1 = this.getClass().getProtectionDomain().getCodeSource();
        boolean var2 = false;

        if (var1.getLocation().getProtocol().equals("jar"))
        {
            Certificate[] var3 = var1.getCertificates();

            if (var3 != null)
            {
                Certificate[] var4 = var3;
                int var5 = var3.length;

                for (int var6 = 0; var6 < var5; ++var6)
                {
                    Certificate var7 = var4[var6];
                    String var8 = CertificateHelper.getFingerprint(var7);

                    if (var8.equals(FMLFINGERPRINT))
                    {
                        FMLRelaunchLog.info("Found valid fingerprint for FML. Certificate fingerprint %s", new Object[] {var8});
                        var2 = true;
                    }
                    else if (var8.equals(FORGEFINGERPRINT))
                    {
                        FMLRelaunchLog.info("Found valid fingerprint for Minecraft Forge. Certificate fingerprint %s", new Object[] {var8});
                        var2 = true;
                    }
                    else
                    {
                        FMLRelaunchLog.severe("Found invalid fingerprint for FML: %s", new Object[] {var8});
                    }
                }
            }
        }
        else
        {
            var2 = true;
        }

        if (!var2)
        {
            FMLRelaunchLog.severe("FML appears to be missing any signature data. This is not a good thing", new Object[0]);
        }

        byte[] var9 = this.cl.getClassBytes("ModLoader");

        if (var9 == null)
        {
            return null;
        }
        else
        {
            FMLSanityChecker$MLDetectorClassVisitor var10 = new FMLSanityChecker$MLDetectorClassVisitor((FMLSanityChecker$1)null);
            ClassReader var11 = new ClassReader(var9);
            var11.accept(var10, 1);

            if (!FMLSanityChecker$MLDetectorClassVisitor.access$100(var10))
            {
                JOptionPane.showMessageDialog((Component)null, "<html>CRITICAL ERROR<br/>ModLoader was detected in this environment<br/>ForgeModLoader cannot be installed alongside ModLoader<br/>All mods should work without ModLoader being installed<br/>Because ForgeModLoader is 100% compatible with ModLoader<br/>Re-install Minecraft Forge or Forge ModLoader into a clean<br/>jar and try again.", "ForgeModLoader critical error", 0);
                throw new RuntimeException("Invalid ModLoader class detected");
            }
            else
            {
                return null;
            }
        }
    }

    public void injectData(Map var1)
    {
        this.cl = (RelaunchClassLoader)var1.get("classLoader");
        FMLDeobfuscatingRemapper.INSTANCE.setup((File)var1.get("mcLocation"), this.cl, (String)var1.get("deobfuscationFileName"));
    }

    public Object call() throws Exception
    {
        return this.call();
    }
}

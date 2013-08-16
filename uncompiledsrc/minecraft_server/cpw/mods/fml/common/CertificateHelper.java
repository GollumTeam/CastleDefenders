package cpw.mods.fml.common;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.cert.Certificate;

public class CertificateHelper
{
    private static final String HEXES = "0123456789abcdef";

    public static String getFingerprint(Certificate var0)
    {
        if (var0 == null)
        {
            return "NO VALID CERTIFICATE FOUND";
        }
        else
        {
            try
            {
                MessageDigest var1 = MessageDigest.getInstance("SHA-1");
                byte[] var2 = var0.getEncoded();
                var1.update(var2);
                byte[] var3 = var1.digest();
                return hexify(var3);
            }
            catch (Exception var4)
            {
                return null;
            }
        }
    }

    public static String getFingerprint(ByteBuffer var0)
    {
        try
        {
            MessageDigest var1 = MessageDigest.getInstance("SHA-1");
            var1.update(var0);
            byte[] var2 = var1.digest();
            return hexify(var2);
        }
        catch (Exception var3)
        {
            return null;
        }
    }

    private static String hexify(byte[] var0)
    {
        StringBuilder var1 = new StringBuilder(2 * var0.length);
        byte[] var2 = var0;
        int var3 = var0.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            byte var5 = var2[var4];
            var1.append("0123456789abcdef".charAt((var5 & 240) >> 4)).append("0123456789abcdef".charAt(var5 & 15));
        }

        return var1.toString();
    }
}

package cpw.mods.fml.common.asm.transformers.deobf;

import com.google.common.io.InputSupplier;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipInputSupplier implements InputSupplier
{
    private ZipFile zipFile;
    private ZipEntry zipEntry;

    public ZipInputSupplier(ZipFile var1, ZipEntry var2)
    {
        this.zipFile = var1;
        this.zipEntry = var2;
    }

    public InputStream getInput() throws IOException
    {
        return this.zipFile.getInputStream(this.zipEntry);
    }

    public Object getInput() throws IOException
    {
        return this.getInput();
    }
}

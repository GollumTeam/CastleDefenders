package cpw.mods.fml.relauncher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class FMLRelaunchLog$LoggingOutStream extends ByteArrayOutputStream
{
    private Logger log;
    private StringBuilder currentMessage;

    public FMLRelaunchLog$LoggingOutStream(Logger var1)
    {
        this.log = var1;
        this.currentMessage = new StringBuilder();
    }

    public void flush() throws IOException
    {
        Class var2 = FMLRelaunchLog.class;

        synchronized (FMLRelaunchLog.class)
        {
            super.flush();
            String var1 = this.toString();
            super.reset();
            this.currentMessage.append(var1.replace(FMLLogFormatter.LINE_SEPARATOR, "\n"));
            int var3 = -1;

            for (int var4 = this.currentMessage.indexOf("\n", var3 + 1); var4 >= 0; var4 = this.currentMessage.indexOf("\n", var4 + 1))
            {
                this.log.log(Level.INFO, this.currentMessage.substring(var3 + 1, var4));
                var3 = var4;
            }

            if (var3 >= 0)
            {
                String var5 = this.currentMessage.substring(var3 + 1);
                this.currentMessage.setLength(0);
                this.currentMessage.append(var5);
            }
        }
    }
}

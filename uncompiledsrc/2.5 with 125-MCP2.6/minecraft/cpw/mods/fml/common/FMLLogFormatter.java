package cpw.mods.fml.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.*;

final class FMLLogFormatter extends Formatter
{
    private SimpleDateFormat dateFormat;

    FMLLogFormatter()
    {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public String format(LogRecord logrecord)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(dateFormat.format(Long.valueOf(logrecord.getMillis())));
        Level level = logrecord.getLevel();

        if (level == Level.FINEST)
        {
            stringbuilder.append(" [FINEST] ");
        }
        else if (level == Level.FINER)
        {
            stringbuilder.append(" [FINER] ");
        }
        else if (level == Level.FINE)
        {
            stringbuilder.append(" [FINE] ");
        }
        else if (level == Level.INFO)
        {
            stringbuilder.append(" [INFO] ");
        }
        else if (level == Level.WARNING)
        {
            stringbuilder.append(" [WARNING] ");
        }
        else if (level == Level.SEVERE)
        {
            stringbuilder.append(" [SEVERE] ");
        }
        else if (level == Level.SEVERE)
        {
            stringbuilder.append((new StringBuilder()).append(" [").append(level.getLocalizedName()).append("] ").toString());
        }

        stringbuilder.append(logrecord.getMessage());
        stringbuilder.append('\n');
        Throwable throwable = logrecord.getThrown();

        if (throwable != null)
        {
            StringWriter stringwriter = new StringWriter();
            throwable.printStackTrace(new PrintWriter(stringwriter));
            stringbuilder.append(stringwriter.toString());
        }

        return stringbuilder.toString();
    }
}

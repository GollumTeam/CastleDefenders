package net.minecraft.src;

import cpw.mods.fml.common.FMLCommonHandler;
import java.io.*;
import java.util.*;

public class StringTranslate
{
    /** Is the private singleton instance of StringTranslate. */
    private static StringTranslate instance = new StringTranslate();

    /**
     * Contains all key/value pairs to be translated - is loaded from '/lang/en_US.lang' when the StringTranslate is
     * created.
     */
    private Properties translateTable;
    private TreeMap languageList;
    private String currentLanguage;
    private boolean isUnicode;

    private StringTranslate()
    {
        translateTable = new Properties();
        loadLanguageList();
        setLanguage("en_US");
    }

    /**
     * Return the StringTranslate singleton instance
     */
    public static StringTranslate getInstance()
    {
        return instance;
    }

    private void loadLanguageList()
    {
        TreeMap treemap = new TreeMap();

        try
        {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((net.minecraft.src.StringTranslate.class).getResourceAsStream("/lang/languages.txt"), "UTF-8"));

            for (String s = bufferedreader.readLine(); s != null; s = bufferedreader.readLine())
            {
                String as[] = s.split("=");

                if (as != null && as.length == 2)
                {
                    treemap.put(as[0], as[1]);
                }
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
            return;
        }

        languageList = treemap;
    }

    public TreeMap getLanguageList()
    {
        return languageList;
    }

    private void loadLanguage(Properties par1Properties, String par2Str) throws IOException
    {
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((net.minecraft.src.StringTranslate.class).getResourceAsStream((new StringBuilder()).append("/lang/").append(par2Str).append(".lang").toString()), "UTF-8"));

        for (String s = bufferedreader.readLine(); s != null; s = bufferedreader.readLine())
        {
            s = s.trim();

            if (s.startsWith("#"))
            {
                continue;
            }

            String as[] = s.split("=");

            if (as != null && as.length == 2)
            {
                par1Properties.setProperty(as[0], as[1]);
            }
        }

        FMLCommonHandler.instance().handleLanguageLoad(par1Properties, par2Str);
    }

    public void setLanguage(String par1Str)
    {
        if (!(par1Str.equals(currentLanguage)))
## JADFIX _L1 _L2
            _L1:
            Properties properties;

        properties = new Properties();

        try
        {
            loadLanguage(properties, "en_US");
        }
        catch (IOException ioexception) { }

        isUnicode = false;

        if (!("en_US".equals(par1Str)))
## JADFIX _L3 _L4
            _L3:
            Enumeration enumeration;

        loadLanguage(properties, par1Str);
        enumeration = properties.propertyNames();
        _L6:

        if (enumeration.hasMoreElements() && !isUnicode)
        {
            Object obj = enumeration.nextElement();
            Object obj1 = properties.get(obj);

            if (obj1 == null)
            {
                continue;
            }

            String s = obj1.toString();
            int i = 0;

            do
            {
                if (i >= s.length())
                {
                    continue;
                }

                if (s.charAt(i) >= 256)
                {
                    isUnicode = true;
                    continue;
                }

                i++;
            }
            while (true);
        }

        goto _L4
        IOException ioexception1;
        ioexception1;
        ioexception1.printStackTrace();
        return;
        _L4:
        currentLanguage = par1Str;
        translateTable = properties;
        _L2:
        return;

        if (!(true))
## JADFIX _L5 _L6
            _L5:
        }

    public String getCurrentLanguage()
    {
        return currentLanguage;
    }

    public boolean isUnicode()
    {
        return isUnicode;
    }

    /**
     * Translate a key to current language.
     */
    public String translateKey(String par1Str)
    {
        return translateTable.getProperty(par1Str, par1Str);
    }

    /**
     * Translate a key to current language applying String.format()
     */
    public String translateKeyFormat(String par1Str, Object par2ArrayOfObj[])
    {
        String s = translateTable.getProperty(par1Str, par1Str);
        return String.format(s, par2ArrayOfObj);
    }

    /**
     * Translate a key with a extra '.name' at end added, is used by blocks and items.
     */
    public String translateNamedKey(String par1Str)
    {
        return translateTable.getProperty((new StringBuilder()).append(par1Str).append(".name").toString(), "");
    }

    public static boolean isBidrectional(String par0Str)
    {
        return "ar_SA".equals(par0Str) || "he_IL".equals(par0Str);
    }

    public Properties getTranslationTable()
    {
        return translateTable;
    }
}

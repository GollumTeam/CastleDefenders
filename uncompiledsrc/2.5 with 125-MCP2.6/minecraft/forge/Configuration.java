package forge;

import java.io.*;
import java.text.DateFormat;
import java.util.*;
import net.minecraft.src.Block;

public class Configuration
{
    private boolean configBlocks[];
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_BLOCK = "block";
    public static final String CATEGORY_ITEM = "item";
    File file;
    public Map categories;
    public TreeMap blockProperties;
    public TreeMap itemProperties;
    public TreeMap generalProperties;

    public Configuration(File file1)
    {
        configBlocks = null;
        categories = new TreeMap();
        blockProperties = new TreeMap();
        itemProperties = new TreeMap();
        generalProperties = new TreeMap();
        file = file1;
        categories.put("general", generalProperties);
        categories.put("block", blockProperties);
        categories.put("item", itemProperties);
    }

    public Property getOrCreateBlockIdProperty(String s, int i)
    {
        if (configBlocks == null)
        {
            configBlocks = new boolean[256];

            for (int j = 0; j < configBlocks.length; j++)
            {
                configBlocks[j] = false;
            }
        }

        Map map = (Map)categories.get("block");

        if (map.containsKey(s))
        {
            Property property = getOrCreateIntProperty(s, "block", i);
            configBlocks[Integer.parseInt(property.value)] = true;
            return property;
        }

        Property property1 = new Property();
        map.put(s, property1);
        property1.name = s;

        if (Block.blocksList[i] == null && !configBlocks[i])
        {
            property1.value = Integer.toString(i);
            configBlocks[i] = true;
            return property1;
        }

        for (int k = configBlocks.length - 1; k >= 0; k--)
        {
            if (Block.blocksList[k] == null && !configBlocks[k])
            {
                property1.value = Integer.toString(k);
                configBlocks[k] = true;
                return property1;
            }
        }

        throw new RuntimeException((new StringBuilder()).append("No more block ids available for ").append(s).toString());
    }

    public Property getOrCreateIntProperty(String s, String s1, int i)
    {
        Property property = getOrCreateProperty(s, s1, Integer.toString(i));

        try
        {
            Integer.parseInt(property.value);
            return property;
        }
        catch (NumberFormatException numberformatexception)
        {
            property.value = Integer.toString(i);
        }

        return property;
    }

    public Property getOrCreateBooleanProperty(String s, String s1, boolean flag)
    {
        Property property = getOrCreateProperty(s, s1, Boolean.toString(flag));

        if ("true".equals(property.value.toLowerCase()) || "false".equals(property.value.toLowerCase()))
        {
            return property;
        }
        else
        {
            property.value = Boolean.toString(flag);
            return property;
        }
    }

    public Property getOrCreateProperty(String s, String s1, String s2)
    {
        s1 = s1.toLowerCase();
        Object obj = (Map)categories.get(s1);

        if (obj == null)
        {
            obj = new TreeMap();
            categories.put(s1, obj);
        }

        if (((Map)(obj)).containsKey(s))
        {
            return (Property)((Map)(obj)).get(s);
        }

        if (s2 != null)
        {
            Property property = new Property();
            ((Map)(obj)).put(s, property);
            property.name = s;
            property.value = s2;
            return property;
        }
        else
        {
            return null;
        }
    }

    public void load()
    {
        try
        {
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }

            if (!file.exists() && !file.createNewFile())
            {
                return;
            }

            if (file.canRead())
            {
                FileInputStream fileinputstream = new FileInputStream(file);
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(fileinputstream, "8859_1"));
                Object obj = null;

                do
                {
                    String s = bufferedreader.readLine();

                    if (s == null)
                    {
                        break;
                    }

                    int i = -1;
                    int j = -1;
                    boolean flag = false;
                    int k = 0;

                    while (k < s.length() && !flag)
                    {
                        if (Character.isLetterOrDigit(s.charAt(k)) || s.charAt(k) == '.')
                        {
                            if (i == -1)
                            {
                                i = k;
                            }

                            j = k;
                        }
                        else if (!Character.isWhitespace(s.charAt(k)))
                        {
                            switch (s.charAt(k))
                            {
                                case 35:
                                    flag = true;
                                    break;

                                case 123:
                                    String s1 = s.substring(i, j + 1);
                                    obj = (Map)categories.get(s1);

                                    if (obj == null)
                                    {
                                        obj = new TreeMap();
                                        categories.put(s1, obj);
                                    }

                                    break;

                                case 125:
                                    obj = null;
                                    break;

                                case 61:
                                    String s2 = s.substring(i, j + 1);

                                    if (obj == null)
                                    {
                                        throw new RuntimeException((new StringBuilder()).append("property ").append(s2).append(" has no scope").toString());
                                    }

                                    Property property = new Property();
                                    property.name = s2;
                                    property.value = s.substring(k + 1);
                                    k = s.length();
                                    ((Map)(obj)).put(s2, property);
                                    break;

                                default:
                                    throw new RuntimeException((new StringBuilder()).append("unknown character ").append(s.charAt(k)).toString());
                            }
                        }

                        k++;
                    }
                }
                while (true);
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public void save()
    {
        try
        {
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }

            if (!file.exists() && !file.createNewFile())
            {
                return;
            }

            if (file.canWrite())
            {
                FileOutputStream fileoutputstream = new FileOutputStream(file);
                BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(fileoutputstream, "8859_1"));
                bufferedwriter.write("# Configuration file\r\n");
                bufferedwriter.write((new StringBuilder()).append("# Generated on ").append(DateFormat.getInstance().format(new Date())).append("\r\n").toString());
                bufferedwriter.write("\r\n");

                for (Iterator iterator = categories.entrySet().iterator(); iterator.hasNext(); bufferedwriter.write("}\r\n\r\n"))
                {
                    java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                    bufferedwriter.write("####################\r\n");
                    bufferedwriter.write((new StringBuilder()).append("# ").append((String)entry.getKey()).append(" \r\n").toString());
                    bufferedwriter.write("####################\r\n\r\n");
                    bufferedwriter.write((new StringBuilder()).append((String)entry.getKey()).append(" {\r\n").toString());
                    writeProperties(bufferedwriter, ((Map)entry.getValue()).values());
                }

                bufferedwriter.close();
                fileoutputstream.close();
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    private void writeProperties(BufferedWriter bufferedwriter, Collection collection) throws IOException
    {
        for (Iterator iterator = collection.iterator(); iterator.hasNext(); bufferedwriter.write("\r\n"))
        {
            Property property = (Property)iterator.next();

            if (property.comment != null)
            {
                bufferedwriter.write((new StringBuilder()).append("   # ").append(property.comment).append("\r\n").toString());
            }

            bufferedwriter.write((new StringBuilder()).append("   ").append(property.name).append("=").append(property.value).toString());
        }
    }
}

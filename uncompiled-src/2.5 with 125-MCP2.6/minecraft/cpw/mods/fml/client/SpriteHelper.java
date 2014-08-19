package cpw.mods.fml.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import java.util.BitSet;
import java.util.HashMap;
import java.util.logging.Logger;

public class SpriteHelper
{
    private static HashMap spriteInfo = new HashMap();

    public SpriteHelper()
    {
    }

    private static void initMCSpriteMaps()
    {
        BitSet bitset = toBitSet("0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111110000000000111111110000000011111000000000011111110000000001111110000000000000000000");
        spriteInfo.put("/terrain.png", bitset);
        bitset = toBitSet("0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001000000000000000111000000000000010000000001111111010000000111111101010000011111111111111001111111111111111111111111111111111111111111111110000000000000000");
        spriteInfo.put("/gui/items.png", bitset);
    }

    public static void registerSpriteMapForFile(String s, String s1)
    {
        if (spriteInfo.size() == 0)
        {
            initMCSpriteMaps();
        }

        if (spriteInfo.containsKey(s))
        {
            FMLCommonHandler.instance().getFMLLogger().finer(String.format("Duplicate attempt to register a sprite file %s for overriding -- ignoring", new Object[]
                    {
                        s
                    }));
            return;
        }
        else
        {
            spriteInfo.put(s, toBitSet(s1));
            return;
        }
    }

    public static int getUniqueSpriteIndex(String s)
    {
        if (!spriteInfo.containsKey("/terrain.png"))
        {
            initMCSpriteMaps();
        }

        BitSet bitset = (BitSet)spriteInfo.get(s);

        if (bitset == null)
        {
            Exception exception = new Exception(String.format("Invalid getUniqueSpriteIndex call for texture: %s", new Object[]
                    {
                        s
                    }));
            Loader.log.throwing("ModLoader", "getUniqueSpriteIndex", exception);
            FMLCommonHandler.instance().raiseException(exception, "Invalid request to getUniqueSpriteIndex", true);
        }

        int i = getFreeSlot(bitset);

        if (i == -1)
        {
            Exception exception1 = new Exception(String.format("No more sprite indicies left for: %s", new Object[]
                    {
                        s
                    }));
            Loader.log.throwing("ModLoader", "getUniqueSpriteIndex", exception1);
            FMLCommonHandler.instance().raiseException(exception1, "No more sprite indicies left", true);
        }

        return i;
    }

    public static BitSet toBitSet(String s)
    {
        BitSet bitset = new BitSet(s.length());

        for (int i = 0; i < s.length(); i++)
        {
            bitset.set(i, s.charAt(i) == '1');
        }

        return bitset;
    }

    public static int getFreeSlot(BitSet bitset)
    {
        int i = bitset.nextSetBit(0);
        bitset.clear(i);
        return i;
    }

    public static int freeSlotCount(String s)
    {
        return ((BitSet)spriteInfo.get(s)).cardinality();
    }
}

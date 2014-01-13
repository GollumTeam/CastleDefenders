package net.minecraft.src;

import java.io.File;
import java.net.*;
import java.util.*;

public class SoundPool
{
    /** The RNG used by SoundPool. */
    private Random rand;

    /**
     * Maps a name (can be sound/newsound/streaming/music/newmusic) to a list of SoundPoolEntry's.
     */
    private Map nameToSoundPoolEntriesMapping;

    /** A list of all SoundPoolEntries that have been loaded. */
    private List allSoundPoolEntries;

    /**
     * The number of soundPoolEntry's. This value is computed but never used (should be equal to
     * allSoundPoolEntries.size()).
     */
    public int numberOfSoundPoolEntries;
    public boolean isGetRandomSound;

    public SoundPool()
    {
        rand = new Random();
        nameToSoundPoolEntriesMapping = new HashMap();
        allSoundPoolEntries = new ArrayList();
        numberOfSoundPoolEntries = 0;
        isGetRandomSound = true;
    }

    /**
     * Adds a sound to this sound pool.
     */
    public SoundPoolEntry addSound(String par1Str, File par2File)
    {
        try
        {
            return addSound(par1Str, par2File.toURI().toURL());
        }
        catch (MalformedURLException malformedurlexception)
        {
            malformedurlexception.printStackTrace();
            throw new RuntimeException(malformedurlexception);
        }
    }

    public SoundPoolEntry addSound(String s, URL url)
    {
        try
        {
            String s1 = s;
            s = s.substring(0, s.indexOf("."));

            if (isGetRandomSound)
            {
                for (; Character.isDigit(s.charAt(s.length() - 1)); s = s.substring(0, s.length() - 1)) { }
            }

            s = s.replaceAll("/", ".");

            if (!nameToSoundPoolEntriesMapping.containsKey(s))
            {
                nameToSoundPoolEntriesMapping.put(s, new ArrayList());
            }

            SoundPoolEntry soundpoolentry = new SoundPoolEntry(s1, url);
            ((List)nameToSoundPoolEntriesMapping.get(s)).add(soundpoolentry);
            allSoundPoolEntries.add(soundpoolentry);
            numberOfSoundPoolEntries++;
            return soundpoolentry;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    /**
     * gets a random sound from the specified (by name, can be sound/newsound/streaming/music/newmusic) sound pool.
     */
    public SoundPoolEntry getRandomSoundFromSoundPool(String par1Str)
    {
        List list = (List)nameToSoundPoolEntriesMapping.get(par1Str);
        return list != null ? (SoundPoolEntry)list.get(rand.nextInt(list.size())) : null;
    }

    /**
     * Gets a random SoundPoolEntry.
     */
    public SoundPoolEntry getRandomSound()
    {
        return allSoundPoolEntries.size() != 0 ? (SoundPoolEntry)allSoundPoolEntries.get(rand.nextInt(allSoundPoolEntries.size())) : null;
    }
}

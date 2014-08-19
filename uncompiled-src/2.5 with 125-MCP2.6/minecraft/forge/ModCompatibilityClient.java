package forge;

import java.io.*;
import java.lang.reflect.*;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecIBXM;

public class ModCompatibilityClient
{
    public static SoundPool audioModSoundPoolCave;
    private static int isMLMPInstalled = -1;

    public ModCompatibilityClient()
    {
    }

    private static Class getClass(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch (Exception exception) { }

        try
        {
            return Class.forName((new StringBuilder()).append("net.minecraft.src.").append(s).toString());
        }
        catch (Exception exception1)
        {
            return null;
        }
    }

    public static void audioModLoad(SoundManager soundmanager)
    {
        audioModSoundPoolCave = new SoundPool();
        audioModLoadModAudio("resources/mod/sound", soundmanager.getSoundsPool());
        audioModLoadModAudio("resources/mod/streaming", soundmanager.getStreamingPool());
        audioModLoadModAudio("resources/mod/music", soundmanager.getMusicPool());
        audioModLoadModAudio("resources/mod/cavemusic", audioModSoundPoolCave);
        SoundManager _tmp = soundmanager;

        if (SoundManager.MUSIC_INTERVAL == 12000)
        {
            SoundManager _tmp1 = soundmanager;
            SoundManager.MUSIC_INTERVAL = 6000;
        }
    }

    private static void audioModLoadModAudio(String s, SoundPool soundpool)
    {
        File file = new File(Minecraft.getMinecraftDir(), s);

        try
        {
            audioModWalkFolder(file, file, soundpool);
        }
        catch (IOException ioexception)
        {
            ModLoader.getLogger().fine((new StringBuilder()).append("Loading Mod audio failed for folder: ").append(s).toString());
            ModLoader.getLogger().fine(ioexception.toString());
            ioexception.printStackTrace();
        }
    }

    private static void audioModWalkFolder(File file, File file1, SoundPool soundpool) throws IOException
    {
        if (file1.exists() || file1.mkdirs())
        {
            File afile[] = file1.listFiles();
            int i = afile.length;

            for (int j = 0; j < i; j++)
            {
                File file2 = afile[j];

                if (file2.getName().startsWith("."))
                {
                    continue;
                }

                if (file2.isDirectory())
                {
                    audioModWalkFolder(file, file2, soundpool);
                    continue;
                }

                if (file2.isFile())
                {
                    String s = file2.getPath().substring(file.getPath().length() + 1).replace('\\', '/');
                    soundpool.addSound(s, file2);
                }
            }
        }
    }

    public static void audioModAddCodecs()
    {
        SoundSystemConfig.setCodec("xm", paulscode.sound.codecs.CodecIBXM.class);
        SoundSystemConfig.setCodec("s3m", paulscode.sound.codecs.CodecIBXM.class);
        SoundSystemConfig.setCodec("mod", paulscode.sound.codecs.CodecIBXM.class);
    }

    public static SoundPoolEntry audioModPickBackgroundMusic(SoundManager soundmanager, SoundPoolEntry soundpoolentry)
    {
        Minecraft minecraft = ModLoader.getMinecraftInstance();

        if (minecraft != null && minecraft.theWorld != null && audioModSoundPoolCave != null)
        {
            net.minecraft.src.EntityLiving entityliving = minecraft.renderViewEntity;
            int i = MathHelper.func_40346_b(((Entity)(entityliving)).posX);
            int j = MathHelper.func_40346_b(((Entity)(entityliving)).posY);
            int k = MathHelper.func_40346_b(((Entity)(entityliving)).posZ);
            return minecraft.theWorld.canBlockSeeTheSky(i, j, k) ? soundpoolentry : audioModSoundPoolCave.getRandomSound();
        }
        else
        {
            return soundpoolentry;
        }
    }

    public static boolean isMLMPInstalled()
    {
        if (isMLMPInstalled == -1)
        {
            isMLMPInstalled = getClass("ModLoaderMp") == null ? 0 : 1;
        }

        return isMLMPInstalled == 1;
    }

    public static Object mlmpVehicleSpawn(int i, World world, double d, double d1, double d2, Entity entity, Object obj) throws Exception
    {
        Class class1 = getClass("ModLoaderMp");

        if (!isMLMPInstalled() || class1 == null)
        {
            return obj;
        }

        Object obj1 = class1.getDeclaredMethod("handleNetClientHandlerEntities", new Class[]
                {
                    Integer.TYPE
                }).invoke(null, new Object[]
                        {
                            Integer.valueOf(i)
                        });

        if (obj1 == null)
        {
            return obj;
        }

        Class class2 = (Class)obj1.getClass().getDeclaredField("entityClass").get(obj1);
        Entity entity1 = (Entity)class2.getConstructor(new Class[]
                {
                    net.minecraft.src.World.class, Double.TYPE, Double.TYPE, Double.TYPE
                }).newInstance(new Object[]
                        {
                            world, Double.valueOf(d), Double.valueOf(d1), Double.valueOf(d2)
                        });

        if (obj1.getClass().getDeclaredField("entityHasOwner").getBoolean(obj1))
        {
            Field field = class2.getField("owner");

            if (!(net.minecraft.src.Entity.class).isAssignableFrom(field.getType()))
            {
                throw new Exception(String.format("Entity's owner field must be of type Entity, but it is of type %s.", new Object[]
                        {
                            field.getType()
                        }));
            }

            if (entity == null)
            {
                System.out.println("Received spawn packet for entity with owner, but owner was not found.");
                ModLoader.getLogger().fine("Received spawn packet for entity with owner, but owner was not found.");
            }
            else
            {
                if (!field.getType().isAssignableFrom(entity.getClass()))
                {
                    throw new Exception(String.format("Tried to assign an entity of type %s to entity owner, which is of type %s.", new Object[]
                            {
                                entity.getClass(), field.getType()
                            }));
                }

                field.set(entity1, entity);
            }
        }

        return entity1;
    }

    public static void mlmpOpenWindow(Packet100OpenWindow packet100openwindow)
    {
        Class class1 = getClass("ModLoaderMp");

        if (!isMLMPInstalled() || class1 == null)
        {
            return;
        }

        try
        {
            class1.getDeclaredMethod("handleGUI", new Class[]
                    {
                        net.minecraft.src.Packet100OpenWindow.class
                    }).invoke(null, new Object[]
                            {
                                packet100openwindow
                            });
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}

package forge.adaptors;

import forge.ISoundHandler;
import net.minecraft.src.*;

public class SoundHandlerAdaptor implements ISoundHandler
{
    public SoundHandlerAdaptor()
    {
    }

    public void onSetupAudio(SoundManager soundmanager)
    {
    }

    public void onLoadSoundSettings(SoundManager soundmanager)
    {
    }

    public SoundPoolEntry onPlayBackgroundMusic(SoundManager soundmanager, SoundPoolEntry soundpoolentry)
    {
        return soundpoolentry;
    }

    public SoundPoolEntry onPlayStreaming(SoundManager soundmanager, SoundPoolEntry soundpoolentry, String s, float f, float f1, float f2)
    {
        return soundpoolentry;
    }

    public SoundPoolEntry onPlaySound(SoundManager soundmanager, SoundPoolEntry soundpoolentry, String s, float f, float f1, float f2, float f3, float f4)
    {
        return soundpoolentry;
    }

    public SoundPoolEntry onPlaySoundEffect(SoundManager soundmanager, SoundPoolEntry soundpoolentry, String s, float f, float f1)
    {
        return soundpoolentry;
    }

    public String onPlaySoundAtEntity(Entity entity, String s, float f, float f1)
    {
        return s;
    }
}

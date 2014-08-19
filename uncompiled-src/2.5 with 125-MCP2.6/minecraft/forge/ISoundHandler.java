package forge;

import net.minecraft.src.*;

public interface ISoundHandler
{
    public abstract void onSetupAudio(SoundManager soundmanager);

    public abstract void onLoadSoundSettings(SoundManager soundmanager);

    public abstract SoundPoolEntry onPlayBackgroundMusic(SoundManager soundmanager, SoundPoolEntry soundpoolentry);

    public abstract SoundPoolEntry onPlayStreaming(SoundManager soundmanager, SoundPoolEntry soundpoolentry, String s, float f, float f1, float f2);

    public abstract SoundPoolEntry onPlaySound(SoundManager soundmanager, SoundPoolEntry soundpoolentry, String s, float f, float f1, float f2, float f3, float f4);

    public abstract SoundPoolEntry onPlaySoundEffect(SoundManager soundmanager, SoundPoolEntry soundpoolentry, String s, float f, float f1);

    public abstract String onPlaySoundAtEntity(Entity entity, String s, float f, float f1);
}

package forge;

public class EntityTrackerInfo
{
    public final NetworkMod Mod;
    public final int ID;
    public final int Range;
    public final int UpdateFrequency;
    public final boolean SendVelocityInfo;

    public EntityTrackerInfo(NetworkMod networkmod, int i, int j, int k, boolean flag)
    {
        Mod = networkmod;
        ID = i;
        Range = j;
        UpdateFrequency = k;
        SendVelocityInfo = flag;
    }
}

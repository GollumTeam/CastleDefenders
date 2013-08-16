package cpw.mods.fml.relauncher;

public class DummyDownloader implements IDownloadDisplay
{
    public void resetProgress(int var1) {}

    public void setPokeThread(Thread var1) {}

    public void updateProgress(int var1) {}

    public boolean shouldStopIt()
    {
        return false;
    }

    public void updateProgressString(String var1, Object ... var2) {}

    public Object makeDialog()
    {
        return null;
    }

    public void makeHeadless() {}
}

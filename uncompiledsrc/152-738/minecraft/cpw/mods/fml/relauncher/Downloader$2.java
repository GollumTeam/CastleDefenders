package cpw.mods.fml.relauncher;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class Downloader$2 extends WindowAdapter
{
    final Downloader this$0;

    Downloader$2(Downloader var1)
    {
        this.this$0 = var1;
    }

    public void windowClosing(WindowEvent var1)
    {
        this.this$0.requestClose("Closing this window will stop minecraft from launching\nAre you sure you wish to do this?");
    }
}

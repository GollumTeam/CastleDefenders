package cpw.mods.fml.relauncher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class Downloader$1 implements PropertyChangeListener
{
    final Downloader this$0;

    Downloader$1(Downloader var1)
    {
        this.this$0 = var1;
    }

    public void propertyChange(PropertyChangeEvent var1)
    {
        if (var1.getSource() == this.this$0 && var1.getPropertyName() == "value")
        {
            this.this$0.requestClose("This will stop minecraft from launching\nAre you sure you want to do this?");
        }
    }
}

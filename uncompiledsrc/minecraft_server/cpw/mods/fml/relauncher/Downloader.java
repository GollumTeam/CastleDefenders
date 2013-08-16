package cpw.mods.fml.relauncher;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Downloader$1;
import cpw.mods.fml.relauncher.Downloader$2;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class Downloader extends JOptionPane implements IDownloadDisplay
{
    private JDialog container;
    private JLabel currentActivity;
    private JProgressBar progress;
    boolean stopIt;
    Thread pokeThread;

    private Box makeProgressPanel()
    {
        Box var1 = Box.createVerticalBox();
        var1.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel var2 = new JLabel("<html><b><font size=\'+1\'>FML is setting up your minecraft environment</font></b></html>");
        var1.add(var2);
        var2.setAlignmentY(0.0F);
        var2 = new JLabel("<html>Please wait, FML has some tasks to do before you can play</html>");
        var2.setAlignmentY(0.0F);
        var1.add(var2);
        var1.add(Box.createRigidArea(new Dimension(0, 10)));
        this.currentActivity = new JLabel("Currently doing ...");
        var1.add(this.currentActivity);
        var1.add(Box.createRigidArea(new Dimension(0, 10)));
        this.progress = new JProgressBar(0, 100);
        this.progress.setStringPainted(true);
        var1.add(this.progress);
        var1.add(Box.createRigidArea(new Dimension(0, 30)));
        return var1;
    }

    public JDialog makeDialog()
    {
        this.setMessageType(1);
        this.setMessage(this.makeProgressPanel());
        this.setOptions(new Object[] {"Stop"});
        this.addPropertyChangeListener(new Downloader$1(this));
        this.container = new JDialog((Window)null, "Hello", ModalityType.MODELESS);
        this.container.setResizable(false);
        this.container.setLocationRelativeTo((Component)null);
        this.container.add(this);
        this.updateUI();
        this.container.pack();
        this.container.setMinimumSize(this.container.getPreferredSize());
        this.container.setVisible(true);
        this.container.setDefaultCloseOperation(0);
        this.container.addWindowListener(new Downloader$2(this));
        return this.container;
    }

    protected void requestClose(String var1)
    {
        int var2 = JOptionPane.showConfirmDialog(this.container, var1, "Are you sure you want to stop?", 0, 2);

        if (var2 == 0)
        {
            this.container.dispose();
        }

        this.stopIt = true;

        if (this.pokeThread != null)
        {
            this.pokeThread.interrupt();
        }
    }

    public void updateProgressString(String var1, Object ... var2)
    {
        FMLLog.finest(var1, var2);

        if (this.currentActivity != null)
        {
            this.currentActivity.setText(String.format(var1, var2));
        }
    }

    public void resetProgress(int var1)
    {
        if (this.progress != null)
        {
            this.progress.getModel().setRangeProperties(0, 0, 0, var1, false);
        }
    }

    public void updateProgress(int var1)
    {
        if (this.progress != null)
        {
            this.progress.getModel().setValue(var1);
        }
    }

    public void makeHeadless()
    {
        this.container = null;
        this.progress = null;
        this.currentActivity = null;
    }

    public void setPokeThread(Thread var1)
    {
        this.pokeThread = var1;
    }

    public boolean shouldStopIt()
    {
        return this.stopIt;
    }

    public Object makeDialog()
    {
        return this.makeDialog();
    }
}

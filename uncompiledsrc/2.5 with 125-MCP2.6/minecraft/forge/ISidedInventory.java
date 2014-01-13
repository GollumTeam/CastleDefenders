package forge;

import net.minecraft.src.IInventory;

public interface ISidedInventory extends IInventory
{
    public abstract int getStartInventorySide(int i);

    public abstract int getSizeInventorySide(int i);
}

package cpw.mods.fml.common.registry;

import java.util.BitSet;
import net.minecraft.block.Block;

class BlockTracker
{
    private static final BlockTracker INSTANCE = new BlockTracker();
    private BitSet allocatedBlocks = new BitSet(4096);

    private BlockTracker()
    {
        this.allocatedBlocks.set(0, 4096);

        for (int var1 = 0; var1 < Block.blocksList.length; ++var1)
        {
            if (Block.blocksList[var1] != null)
            {
                this.allocatedBlocks.clear(var1);
            }
        }
    }

    public static int nextBlockId()
    {
        return instance().getNextBlockId();
    }

    private int getNextBlockId()
    {
        int var1 = this.allocatedBlocks.nextSetBit(0);
        this.allocatedBlocks.clear(var1);
        return var1;
    }

    private static BlockTracker instance()
    {
        return INSTANCE;
    }

    public static void reserveBlockId(int var0)
    {
        instance().doReserveId(var0);
    }

    private void doReserveId(int var1)
    {
        this.allocatedBlocks.clear(var1);
    }
}

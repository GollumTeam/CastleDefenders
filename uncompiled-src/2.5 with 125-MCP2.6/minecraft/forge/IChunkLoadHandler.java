package forge;

import java.util.Set;
import net.minecraft.src.*;

public interface IChunkLoadHandler
{
    public abstract void addActiveChunks(World world, Set set);

    public abstract boolean canUnloadChunk(Chunk chunk);

    public abstract boolean canUpdateEntity(Entity entity);
}

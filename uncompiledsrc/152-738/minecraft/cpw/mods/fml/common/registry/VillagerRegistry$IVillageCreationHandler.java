package cpw.mods.fml.common.registry;

import java.util.List;
import java.util.Random;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.StructureVillagePieceWeight;

public interface VillagerRegistry$IVillageCreationHandler
{
    StructureVillagePieceWeight getVillagePieceWeight(Random var1, int var2);

    Class getComponentClass();

    Object buildComponent(StructureVillagePieceWeight var1, ComponentVillageStartPiece var2, List var3, Random var4, int var5, int var6, int var7, int var8, int var9);
}

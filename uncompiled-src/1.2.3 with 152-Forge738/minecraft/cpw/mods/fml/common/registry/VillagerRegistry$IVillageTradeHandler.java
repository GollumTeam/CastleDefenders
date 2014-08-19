package cpw.mods.fml.common.registry;

import java.util.Random;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.village.MerchantRecipeList;

public interface VillagerRegistry$IVillageTradeHandler
{
    void manipulateTradesForVillager(EntityVillager var1, MerchantRecipeList var2, Random var3);
}

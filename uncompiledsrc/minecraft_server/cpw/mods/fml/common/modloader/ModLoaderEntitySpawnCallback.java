package cpw.mods.fml.common.modloader;

import com.google.common.base.Function;
import cpw.mods.fml.common.network.EntitySpawnPacket;
import cpw.mods.fml.common.registry.EntityRegistry$EntityRegistration;
import net.minecraft.entity.Entity;

public class ModLoaderEntitySpawnCallback implements Function
{
    private BaseModProxy mod;
    private EntityRegistry$EntityRegistration registration;
    private boolean isAnimal;

    public ModLoaderEntitySpawnCallback(BaseModProxy var1, EntityRegistry$EntityRegistration var2)
    {
        this.mod = var1;
        this.registration = var2;
    }

    public Entity apply(EntitySpawnPacket var1)
    {
        return ModLoaderHelper.sidedHelper.spawnEntity(this.mod, var1, this.registration);
    }

    public Object apply(Object var1)
    {
        return this.apply((EntitySpawnPacket)var1);
    }
}

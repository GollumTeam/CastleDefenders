package mods.CastleDef.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import mods.CastleDef.EntityArcher;
import mods.CastleDef.EntityArcherM;
import mods.CastleDef.EntityEArcher;
import mods.CastleDef.EntityEKnight;
import mods.CastleDef.EntityKnight;
import mods.CastleDef.EntityMerc;
import mods.CastleDef.common.CommonProxyCastleDef;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;

public class ClientProxyCastleDef extends CommonProxyCastleDef
{
    public void registerRenderThings()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityKnight.class, new RenderBiped(new ModelBiped(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityArcher.class, new RenderBiped(new ModelBiped(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityMerc.class, new RenderBiped(new ModelBiped(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityEKnight.class, new RenderBiped(new ModelBiped(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityEArcher.class, new RenderBiped(new ModelBiped(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityArcherM.class, new RenderBiped(new ModelBiped(), 0.5F));
    }
}

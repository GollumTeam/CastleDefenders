package mods.CastleDef.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import mods.CastleDef.EntityArcher;
import mods.CastleDef.EntityArcherM;
import mods.CastleDef.EntityEArcher;
import mods.CastleDef.EntityEKnight;
import mods.CastleDef.EntityKnight;
import mods.CastleDef.EntityMerc;
import mods.CastleDef.common.CommonProxyCastleDef;

public class ClientProxyCastleDef extends CommonProxyCastleDef
{
    public void registerRenderThings()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityKnight.class, new bhg(new bbz(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityArcher.class, new bhg(new bbz(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityMerc.class, new bhg(new bbz(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityEKnight.class, new bhg(new bbz(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityEArcher.class, new bhg(new bbz(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityArcherM.class, new bhg(new bbz(), 0.5F));
    }
}

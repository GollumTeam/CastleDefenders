package mods.castledefender.common;

import mods.castledefender.common.entities.EntityKnight;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxyCastleDefender extends CommonProxyCastleDefender {
	
	@Override
	public void registerRenderers() {

		RenderingRegistry.registerEntityRenderingHandler(EntityKnight.class, new RenderCastleDefender(new ModelBiped(), 0.5F, "Knight"));
//		RenderingRegistry.registerEntityRenderingHandler(EntityArcher.class, new RenderBiped(new ModelBiped(), 0.5F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityMerc.class, new RenderBiped(new ModelBiped(), 0.5F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityEKnight.class, new RenderBiped(new ModelBiped(), 0.5F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityEArcher.class, new RenderBiped(new ModelBiped(), 0.5F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityArcherM.class, new RenderBiped(new ModelBiped(), 0.5F));
		
	}
}

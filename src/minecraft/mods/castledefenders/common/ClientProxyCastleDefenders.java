package mods.castledefenders.common;

import mods.castledefenders.common.entities.EntityArcher;
import mods.castledefenders.common.entities.EntityKnight;
import mods.castledefenders.common.entities.EntityKnight2;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxyCastleDefenders extends CommonProxyCastleDefenders {
	
	@Override
	public void registerRenderers() {

		RenderingRegistry.registerEntityRenderingHandler(EntityKnight.class , new RenderCastleDefenders(new ModelBiped(), 0.5F, "Knight"));
		RenderingRegistry.registerEntityRenderingHandler(EntityKnight2.class, new RenderCastleDefenders(new ModelBiped(), 0.5F, "Knight2"));
		RenderingRegistry.registerEntityRenderingHandler(EntityArcher.class , new RenderCastleDefenders(new ModelBiped(), 0.5F, "Archer"));
//		RenderingRegistry.registerEntityRenderingHandler(EntityMerc.class, new RenderBiped(new ModelBiped(), 0.5F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityEKnight.class, new RenderBiped(new ModelBiped(), 0.5F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityEArcher.class, new RenderBiped(new ModelBiped(), 0.5F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityArcherM.class, new RenderBiped(new ModelBiped(), 0.5F));
		
	}
}

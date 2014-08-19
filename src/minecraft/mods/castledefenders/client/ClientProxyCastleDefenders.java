package mods.castledefenders.client;

import mods.castledefenders.client.render.RenderCastleDefenders;
import mods.castledefenders.client.render.RenderMercCastleDefenders;
import mods.castledefenders.common.CommonProxyCastleDefenders;
import mods.castledefenders.common.entities.EntityArcher;
import mods.castledefenders.common.entities.EntityArcher2;
import mods.castledefenders.common.entities.EntityMercArcher;
import mods.castledefenders.common.entities.EntityEArcher;
import mods.castledefenders.common.entities.EntityEKnight;
import mods.castledefenders.common.entities.EntityEMage;
import mods.castledefenders.common.entities.EntityHealer;
import mods.castledefenders.common.entities.EntityKnight;
import mods.castledefenders.common.entities.EntityKnight2;
import mods.castledefenders.common.entities.EntityMage;
import mods.castledefenders.common.entities.EntityMerc;
import net.minecraft.client.model.ModelBiped;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxyCastleDefenders extends CommonProxyCastleDefenders {
	
	@Override
	public void registerRenderers() {
		
		RenderingRegistry.registerEntityRenderingHandler(EntityKnight.class    , new RenderCastleDefenders(new ModelBiped()    , 0.5F, "Knight"));
		RenderingRegistry.registerEntityRenderingHandler(EntityKnight2.class   , new RenderCastleDefenders(new ModelBiped()    , 0.5F, "Knight2"));
		RenderingRegistry.registerEntityRenderingHandler(EntityArcher.class    , new RenderCastleDefenders(new ModelBiped()    , 0.5F, "Archer"));
		RenderingRegistry.registerEntityRenderingHandler(EntityArcher2.class   , new RenderCastleDefenders(new ModelBiped()    , 0.5F, "Archer2"));
		RenderingRegistry.registerEntityRenderingHandler(EntityMage.class      , new RenderCastleDefenders(new ModelBiped()    , 0.5F, "Mage"));
		RenderingRegistry.registerEntityRenderingHandler(EntityEKnight.class   , new RenderCastleDefenders(new ModelBiped()    , 0.5F, "Enemy Knight"));
		RenderingRegistry.registerEntityRenderingHandler(EntityEArcher.class   , new RenderCastleDefenders(new ModelBiped()    , 0.5F, "Enemy Archer"));
		RenderingRegistry.registerEntityRenderingHandler(EntityEMage.class     , new RenderCastleDefenders(new ModelBiped()    , 0.5F, "Enemy Mage"));
		RenderingRegistry.registerEntityRenderingHandler(EntityMerc.class      , new RenderMercCastleDefenders(new ModelBiped(), 0.5F, "Merc"));
		RenderingRegistry.registerEntityRenderingHandler(EntityMercArcher.class, new RenderMercCastleDefenders(new ModelBiped(), 0.5F, "MercArcher"));
		RenderingRegistry.registerEntityRenderingHandler(EntityHealer.class    , new RenderMercCastleDefenders(new ModelBiped(), 0.5F, "Healer"));
		
	}
	
	public boolean getDisplayHealth() {
		return true;
	}
}

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
		
		RenderingRegistry.registerEntityRenderingHandler(EntityKnight.class    , new RenderCastleDefenders("Knight"));
		RenderingRegistry.registerEntityRenderingHandler(EntityKnight2.class   , new RenderCastleDefenders("Knight2"));
		RenderingRegistry.registerEntityRenderingHandler(EntityArcher.class    , new RenderCastleDefenders("Archer"));
		RenderingRegistry.registerEntityRenderingHandler(EntityArcher2.class   , new RenderCastleDefenders("Archer2"));
		RenderingRegistry.registerEntityRenderingHandler(EntityMage.class      , new RenderCastleDefenders("Mage"));
		RenderingRegistry.registerEntityRenderingHandler(EntityEKnight.class   , new RenderCastleDefenders("Enemy Knight"));
		RenderingRegistry.registerEntityRenderingHandler(EntityEArcher.class   , new RenderCastleDefenders("Enemy Archer"));
		RenderingRegistry.registerEntityRenderingHandler(EntityEMage.class     , new RenderCastleDefenders("Enemy Mage"));
		RenderingRegistry.registerEntityRenderingHandler(EntityMerc.class      , new RenderMercCastleDefenders("Merc"));
		RenderingRegistry.registerEntityRenderingHandler(EntityMercArcher.class, new RenderMercCastleDefenders("MercArcher"));
		RenderingRegistry.registerEntityRenderingHandler(EntityHealer.class    , new RenderMercCastleDefenders("Healer"));
		
	}
	
	public boolean getDisplayHealth() {
		return true;
	}
}

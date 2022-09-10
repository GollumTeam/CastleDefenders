package com.gollum.castledefenders.client;

import com.gollum.castledefenders.client.render.RenderCastleDefenders;
import com.gollum.castledefenders.client.render.RenderMercCastleDefenders;
import com.gollum.castledefenders.common.CommonProxyCastleDefenders;
import com.gollum.castledefenders.common.entities.EntityArcher;
import com.gollum.castledefenders.common.entities.EntityArcher2;
import com.gollum.castledefenders.common.entities.EntityEArcher;
import com.gollum.castledefenders.common.entities.EntityEKnight;
import com.gollum.castledefenders.common.entities.EntityEMage;
import com.gollum.castledefenders.common.entities.EntityHealer;
import com.gollum.castledefenders.common.entities.EntityKnight;
import com.gollum.castledefenders.common.entities.EntityKnight2;
import com.gollum.castledefenders.common.entities.EntityMage;
import com.gollum.castledefenders.common.entities.EntityMerc;
import com.gollum.castledefenders.common.entities.EntityMercArcher;

import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxyCastleDefenders extends CommonProxyCastleDefenders {
	
	@Override
	public void registerRenderers() {
		
		RenderingRegistry.registerEntityRenderingHandler(EntityKnight.class    , new RenderCastleDefenders("knight"));
		RenderingRegistry.registerEntityRenderingHandler(EntityKnight2.class   , new RenderCastleDefenders("knight2"));
		RenderingRegistry.registerEntityRenderingHandler(EntityArcher.class    , new RenderCastleDefenders("archer"));
		RenderingRegistry.registerEntityRenderingHandler(EntityArcher2.class   , new RenderCastleDefenders("archer2"));
		RenderingRegistry.registerEntityRenderingHandler(EntityMage.class      , new RenderCastleDefenders("mage"));
		RenderingRegistry.registerEntityRenderingHandler(EntityEKnight.class   , new RenderCastleDefenders("eknight"));
		RenderingRegistry.registerEntityRenderingHandler(EntityEArcher.class   , new RenderCastleDefenders("earcher"));
		RenderingRegistry.registerEntityRenderingHandler(EntityEMage.class     , new RenderCastleDefenders("emage"));
		RenderingRegistry.registerEntityRenderingHandler(EntityMerc.class      , new RenderMercCastleDefenders("merc"));
		RenderingRegistry.registerEntityRenderingHandler(EntityMercArcher.class, new RenderMercCastleDefenders("mercarcher"));
		RenderingRegistry.registerEntityRenderingHandler(EntityHealer.class    , new RenderMercCastleDefenders("healer"));
		
	}
	
	public boolean getDisplayHealth() {
		return true;
	}
}

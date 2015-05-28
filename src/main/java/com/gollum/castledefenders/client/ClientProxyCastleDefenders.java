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

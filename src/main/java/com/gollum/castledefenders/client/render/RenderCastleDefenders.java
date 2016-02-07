package com.gollum.castledefenders.client.render;

import java.util.Hashtable;

import com.gollum.castledefenders.ModCastleDefenders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCastleDefenders extends RenderBiped {
	
	/**
	 * Cache des location de texture
	 */
	private static Hashtable<String, ResourceLocation> cacheResource = new Hashtable<String, ResourceLocation>();
	
	protected String name;
	
	public RenderCastleDefenders (String name) {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 0.5F);
		this.name = name;
	}
	
	protected ResourceLocation getResource(String name) {
		
		ResourceLocation texture;
		
		if (cacheResource.containsKey(name)) {
			texture = cacheResource.get(name);
		} else {
			texture = new ResourceLocation(ModCastleDefenders.MODID.toLowerCase()+":models/"+name+".png");
			cacheResource.put(name, texture);
		}
		
		return texture;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityLiving par1Entity) {
		return getResource(name);
	}

}

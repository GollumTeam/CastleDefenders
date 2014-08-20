package mods.castledefenders.client.render;

import java.util.Hashtable;

import mods.castledefenders.ModCastleDefenders;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderCastleDefenders extends RenderBiped {
	
	/**
	 * Cache des location de texture
	 */
	private static Hashtable<String, ResourceLocation> cacheResource = new Hashtable<String, ResourceLocation>();
	
	protected String name;
	
	public RenderCastleDefenders (String name) {
		super(new ModelBiped(), 0.5F);
		this.name = name;
	}
	
	protected ResourceLocation getResource(String name) {
		
		ResourceLocation texture;
		
		if (cacheResource.containsKey(name)) {
			texture = cacheResource.get(name);
		} else {
			texture = new ResourceLocation(ModCastleDefenders.MODID.toLowerCase()+":"+name+".png");
			cacheResource.put(name, texture);
		}
		
		return texture;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return getResource(name);
	}

}

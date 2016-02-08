package com.gollum.castledefenders.client.render;

import org.lwjgl.opengl.GL11;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.entities.EntityMercenary;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.ResourceLocation;

public class RenderMercCastleDefenders extends RenderCastleDefenders {
	
	public RenderMercCastleDefenders(String name) {
		super(name);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		if (entity instanceof EntityTameable) {
			return this.getResource (((EntityTameable) entity).isTamed() ? name+"2" : name);
		}
		
		return this.getResource(name);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float par8, float par9) {
		super.doRender(entity, x, y, z, par8, par9);
		
		EntityMercenary entityMercenary = (EntityMercenary) entity;
		if (entityMercenary.isTamed()) {
			if (entityMercenary.isOwner ()) {
				this.renderLife(entityMercenary, x, y, z);
			}
		} else {
			this.renderLivingLabel(entityMercenary, entityMercenary.getMessagePlayer(), x, y, z, 64);
		}

		GL11.glPushMatrix();
		this.modelBipedMain.bipedRightArm.rotateAngleY = 1.6F;
		this.modelBipedMain.bipedRightArm.postRender(0.0222F);
		GL11.glPopMatrix();
		
	}
	
	/**
     * Draws the debug or playername text above a living
     */
	protected void renderLife(EntityLivingBase entity, double x, double y, double z) {
		
		boolean display = ModCastleDefenders.proxy.getDisplayHealth();
		
		if (display && ModCastleDefenders.config.displayMercenaryLife) {
			
			double distance = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);

			if (distance <= (double)(64 * 64)) {

				EntityMercenary entityMerc = (EntityMercenary)entity;
				float zoom = 0.016666668F * 1.6F;
				Tessellator tessellator = Tessellator.getInstance();
				WorldRenderer worldrenderer = tessellator.getWorldRenderer();
				double top = -ModCastleDefenders.config.mercenaryLifeTop/10;
				double height = ModCastleDefenders.config.mercenaryLifeHeight;
				double width = ModCastleDefenders.config.mercenaryLifeWidth;
				
				GlStateManager.disableTexture2D();
				GlStateManager.pushMatrix();
				GlStateManager.translate((float)x + 0.0F, (float)y + entity.height + 0.5F, (float)z);
				GL11.glNormal3f(0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
				GlStateManager.scale(-zoom, -zoom, zoom);
				GlStateManager.disableLighting();
				
				worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
				float Health = entityMerc.getHealth();
				float MaxHealth = entityMerc.getMaxHealth();
				float ratio = Health / MaxHealth;
				double sizeUse = width * ratio;
				
				// Red
				worldrenderer.func_181662_b(-width/2 + sizeUse, -height + top, 0.0D).func_181666_a(0.7F, 0.0F, 0.0F, 1.0F).func_181675_d();
				worldrenderer.func_181662_b(-width/2 + sizeUse, top          , 0.0D).func_181666_a(0.7F, 0.0F, 0.0F, 1.0F).func_181675_d();
				worldrenderer.func_181662_b(width/2           , top          , 0.0D).func_181666_a(0.7F, 0.0F, 0.0F, 1.0F).func_181675_d();
				worldrenderer.func_181662_b(width/2           , -height + top, 0.0D).func_181666_a(0.7F, 0.0F, 0.0F, 1.0F).func_181675_d();

				// Green
				worldrenderer.func_181662_b(-width/2         , -height + top, 0.0D).func_181666_a(0.0F, 0.7F, 0.0F, 1.0F).func_181675_d();
				worldrenderer.func_181662_b(-width/2         , top          , 0.0D).func_181666_a(0.0F, 0.7F, 0.0F, 1.0F).func_181675_d();
				worldrenderer.func_181662_b(sizeUse - width/2, top          , 0.0D).func_181666_a(0.0F, 0.7F, 0.0F, 1.0F).func_181675_d();
				worldrenderer.func_181662_b(sizeUse - width/2, -height + top, 0.0D).func_181666_a(0.0F, 0.7F, 0.0F, 1.0F).func_181675_d();
				
				tessellator.draw();
				
				GlStateManager.enableTexture2D();
				GlStateManager.enableLighting();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.popMatrix();
			}
		}
	}
}

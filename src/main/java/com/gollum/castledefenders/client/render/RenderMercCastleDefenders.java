package com.gollum.castledefenders.client.render;

import org.lwjgl.opengl.GL11;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.entities.EntityMercenary;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
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

	@Override
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
		((ModelBiped)this.mainModel).bipedRightArm.rotateAngleY = 1.6F;
		((ModelBiped)this.mainModel).bipedRightArm.postRender(0.0222F);
		GL11.glPopMatrix();
		
	}
	
	/**
     * Draws the debug or playername text above a living
     */
	protected void renderLife(EntityLivingBase entity, double x, double y, double z) {
		
		boolean display = ModCastleDefenders.proxy.getDisplayHealth();
		
		if (display && ModCastleDefenders.config.displayMercenaryLife) {
			
			double distance = entity.getDistanceSq(
				this.renderManager.viewerPosX,
				this.renderManager.viewerPosY,
				this.renderManager.viewerPosZ
			);

			if (distance <= (double)(64 * 64)) {

				EntityMercenary entityMerc = (EntityMercenary)entity;
				float zoom = 0.016666668F * 1.6F;
						
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
				
				float Health = entityMerc.getHealth();
				float MaxHealth = entityMerc.getMaxHealth();
				float ratio = Health / MaxHealth;
				double sizeUse = width * ratio;


				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder buf = tessellator.getBuffer();
				buf.begin(7, DefaultVertexFormats.POSITION_COLOR);
				
				// Red
				buf.pos(-width/2 + sizeUse, -height + top, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
				buf.pos(-width/2 + sizeUse, top          , 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
				buf.pos(width/2           , top          , 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
				buf.pos(width/2           , -height + top, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();

				// Green
				buf.pos(-width/2         , -height + top, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
				buf.pos(-width/2         , top          , 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
				buf.pos(sizeUse - width/2, top          , 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
				buf.pos(sizeUse - width/2, -height + top, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
				
				tessellator.draw();
				
				GlStateManager.enableTexture2D();
				GlStateManager.enableLighting();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.popMatrix();
			}
		}
	}
}

package mods.castledefenders.client.render;

import mods.castledefenders.ModCastleDefenders;
import mods.castledefenders.common.entities.EntityMercenary;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

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

	public void doRender(Entity entity, double x, double y, double z, float par8, float par9) {
		super.doRender(entity, x, y, z, par8, par9);
		
		EntityMercenary entityMercenary = (EntityMercenary) entity;
		if (entityMercenary.isTamed()) {
			if (entityMercenary.isOwner ()) {
				this.renderLife(entityMercenary, x, y, z);
			}
		} else {
			this.func_147906_a(entityMercenary, entityMercenary.getMessagePlayer(), x, y, z, 64);
		}

		GL11.glPushMatrix();
		this.modelBipedMain.bipedRightArm.rotateAngleY = 1.6F;
		this.modelBipedMain.bipedRightArm.postRender(0.0222F);
		GL11.glPopMatrix();
		
	}
	
	/**
     * Draws the debug or playername text above a living
     */
	protected void renderLife(EntityLivingBase entityLivingBase, double x, double y, double z) {
		
		boolean display = ModCastleDefenders.proxy.getDisplayHealth();
		
		if (display && ModCastleDefenders.config.displayMercenaryLife) {
			
			EntityMercenary entityMerc = (EntityMercenary)entityLivingBase;
			
			float zoom = 0.01666667F * 1.6F;
			Tessellator tessellator = Tessellator.instance;
			double top = -ModCastleDefenders.config.mercenaryLifeTop;
			double height = ModCastleDefenders.config.mercenaryLifeHeight;
			double width = ModCastleDefenders.config.mercenaryLifeWidth;
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x + 0.0F, (float)y + 0.1F, (float)z);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F); // La rotation pour suivre la caméra
			GL11.glScalef(-zoom, -zoom, zoom);
			GL11.glDisable(GL11.GL_LIGHTING);
			
			
			tessellator.startDrawingQuads();
			float Health = entityMerc.getHealth();
			float MaxHealth = entityMerc.getMaxHealth();
			float ratio = Health / MaxHealth;
			double sizeUse = width * ratio;
			
			// Red
			tessellator.setColorRGBA_F(0.7F, 0.0F, 0.0F, 1.0F);
			tessellator.addVertex(-width/2 + sizeUse, -height + top, 0.0D);
			tessellator.addVertex(-width/2 + sizeUse, top          , 0.0D);
			tessellator.addVertex(width/2           , top          , 0.0D);
			tessellator.addVertex(width/2           , -height + top, 0.0D);
			
			// Green
			tessellator.setColorRGBA_F(0.0F, 0.7F, 0.0F, 1.0F);
			tessellator.addVertex(-width/2         , -height + top, 0.0D);
			tessellator.addVertex(-width/2         , top          , 0.0D);
			tessellator.addVertex(sizeUse - width/2, top          , 0.0D);
			tessellator.addVertex(sizeUse - width/2, -height + top, 0.0D);
			
			tessellator.draw();
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
			
	}
}

package com.gollum.castledefenders.common.handlers;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.inits.ModAdvancement;
import com.gollum.core.common.events.BuildingGenerateEvent;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class BuildingHandler {
	
	boolean mustBeSave = false;
	
	@SubscribeEvent
	public void onBuildingGenerate (BuildingGenerateEvent event) {
		
		if (!event.world.isRemote) {
			
			if (event.building.modId.equals(ModCastleDefenders.MODID)) {
				
				Advancement target = null;
				StatBase  counter = null;
				if(event.building.name.equals("castle1"   )) { target = this.getAdvancement(event, "castle1"   ); counter = ModAdvancement.STAT_COUNTER_CASTLE1   ; }
				if(event.building.name.equals("castle2"   )) { target = this.getAdvancement(event, "castle2"   ); counter = ModAdvancement.STAT_COUNTER_CASTLE2   ; }
				if(event.building.name.equals("castle3"   )) { target = this.getAdvancement(event, "castle3"   ); counter = ModAdvancement.STAT_COUNTER_CASTLE3   ; }
				if(event.building.name.equals("castle4"   )) { target = this.getAdvancement(event, "castle4"   ); counter = ModAdvancement.STAT_COUNTER_CASTLE4   ; }
				if(event.building.name.equals("mercenary1")) { target = this.getAdvancement(event, "mercenary1"); counter = ModAdvancement.STAT_COUNTER_MERCENARY1; }
				if(event.building.name.equals("mercenary2")) { target = this.getAdvancement(event, "mercenary2"); counter = ModAdvancement.STAT_COUNTER_MERCENARY2; }
				if(event.building.name.equals("mercenary3")) { target = this.getAdvancement(event, "mercenary3"); counter = ModAdvancement.STAT_COUNTER_MERCENARY3; }
				if(event.building.name.equals("mercenary4")) { target = this.getAdvancement(event, "mercenary4"); counter = ModAdvancement.STAT_COUNTER_MERCENARY4; }
				

				Advancement root = this.getAdvancement(event, "root");
				Advancement all = this.getAdvancement(event, "all");
				Advancement allCastles = this.getAdvancement(event, "all_castles");
				Advancement allMercenaries = this.getAdvancement(event, "all_mercenaries");
				

				if (counter != null && root != null && target != null) {
				
					EntityPlayerMP player = null;
					double diff = 0;
					
					for (Object entity : event.world.playerEntities) {
						if (entity instanceof EntityPlayerMP) {
							
							EntityPlayerMP player2 = (EntityPlayerMP)entity;
							double diff2 = new Vec3d(
								event.position.getX(),
								event.position.getY(),
								event.position.getZ()
							).distanceTo(new Vec3d(
								player2.posX,
								player2.posY,
								player2.posZ
							));
							
							if (player == null || diff2 < diff) {
								diff = diff2;
								player = player2;	
							}
						}
					}
					
					if (player != null) {

						// Add Stat
						player.addStat(counter, 1);

						this.grant(player, root);
						this.grant(player, target);

						if (allCastles != null) {
							if (
								this.isDone(player, event, "castle1") &&
								this.isDone(player, event, "castle2") &&
								this.isDone(player, event, "castle3") &&
								this.isDone(player, event, "castle4")
							) {
								this.grant(player, allCastles);
							}
						}
						if (allMercenaries != null) {
							if (
								this.isDone(player, event, "mercenary1") &&
								this.isDone(player, event, "mercenary2") &&
								this.isDone(player, event, "mercenary3") &&
								this.isDone(player, event, "mercenary4")
							) {
								this.grant(player, allMercenaries);
							}
						}
						if (
							this.isDone(player, event, "all_castles") &&
							this.isDone(player, event, "all_mercenaries") &&
							all != null
						) {
							this.grant(player, all);
						}
					}
				}
			}
			
		}
	}

	private Advancement getAdvancement(BuildingGenerateEvent event, String name) {
		return event.world.getMinecraftServer().getAdvancementManager().getAdvancement(new ResourceLocation(ModCastleDefenders.MODID, name));
	}

	private boolean isDone(EntityPlayerMP player, BuildingGenerateEvent event, String name) {
		Advancement advancement = this.getAdvancement(event, name);
		if (advancement != null) {
			return this.isDone(player, advancement);
		}
		return false;
	}

	private boolean isDone(EntityPlayerMP player, Advancement advancement) {
		return player.getAdvancements().getProgress(advancement).isDone();
	}
	
	private void grant(EntityPlayerMP player, Advancement advancement) {
		AdvancementProgress progress = player.getAdvancements().getProgress(advancement);
		if (!progress.isDone()) {
			for (String s : progress.getRemaningCriteria())
            {
				player.getAdvancements().grantCriterion(advancement, s);
            }
		}
	}
	
}

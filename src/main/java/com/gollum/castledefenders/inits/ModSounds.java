package com.gollum.castledefenders.inits;

import com.gollum.castledefenders.ModCastleDefenders;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class ModSounds {
	
	public static SoundEvent MONK = (new SoundEvent(new ResourceLocation(ModCastleDefenders.MODID, "monk"))).setRegistryName(new ResourceLocation(ModCastleDefenders.MODID, "monk"));
	
	public static void init() {
		ForgeRegistries.SOUND_EVENTS.register(ModSounds.MONK);
	}

}

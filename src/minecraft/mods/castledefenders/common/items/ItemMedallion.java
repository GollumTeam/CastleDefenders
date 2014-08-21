package mods.castledefenders.common.items;

import mods.castledefenders.ModCastleDefenders;
import mods.gollum.core.helper.items.Item;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemMedallion extends Item {
	
	/**
	 * Constructeur
	 * @param id
	 */
	public ItemMedallion(int id, String registerName) {
		super(id, registerName);
		this.maxStackSize = 64;
		this.setCreativeTab(ModCastleDefenders.tabCastleDefenders);
	}
	
	// Enregistre la texture
	public void registerIcons(IconRegister register) {
		this.itemIcon = register.registerIcon("castledefenders:Medallion");
	}
}

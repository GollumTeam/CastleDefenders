package mods.castledefenders.common.items;

import mods.castledefenders.common.ModCastleDefenders;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemMedallion extends Item
{
	
	/**
	 * Constructeur
	 * @param id
	 */
	public ItemMedallion(int id) {
		super(id);
		this.maxStackSize = 64;
		this.setCreativeTab(ModCastleDefenders.tabCastleDefenders);
	}
	
	// Enregistre la texture
	public void registerIcons(IconRegister register) {
		this.itemIcon = register.registerIcon("castledefenders:Medallion");
	}
}

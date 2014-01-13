package mods.castledefender.common.items;

import mods.castledefender.common.ModCastleDefenders;
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
		this.setCreativeTab(ModCastleDefenders.tabsCastleDefender);
	}
	
	// Enregistre la texture
	public void registerIcons(IconRegister register) {
		this.itemIcon = register.registerIcon("castledefender:Medallion");
	}
}

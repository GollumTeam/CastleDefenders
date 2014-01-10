package mods.castledefender.common.items;

import mods.castledefender.common.ModCastleDefender;
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
		this.setCreativeTab(ModCastleDefender.tabsCastleDefender);
	}
	
	// Enregistre la texture
	public void registerIcons(IconRegister register) {
		this.itemIcon = register.registerIcon("castledefender:Medallion");
	}
}

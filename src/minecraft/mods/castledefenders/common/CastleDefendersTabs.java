package mods.castledefenders.common;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CastleDefendersTabs extends CreativeTabs {

	private Block block = null;
	private Item item = null;
	
	
	public CastleDefendersTabs(String label) {
		super(label);
	}

	void setIcon (Block block) {
		this.block = block;
	}
	
	void setIcon (Item item) {
		this.item = item;
	}
	
	
	
	public ItemStack getIconItemStack() {
		if (item != null) {
			return new ItemStack(item);
		}
		return super.getIconItemStack();
	}

	
	@Override
	public int getTabIconItemIndex() {
		if (block != null) {
			return block.blockID;
		}
		return super.getTabIconItemIndex();
	}
}

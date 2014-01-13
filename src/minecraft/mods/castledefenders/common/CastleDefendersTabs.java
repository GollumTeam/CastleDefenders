package mods.castledefenders.common;

import net.minecraft.creativetab.CreativeTabs;

public class CastleDefendersTabs extends CreativeTabs {
	
	private int iconID = 1;

	public CastleDefendersTabs(String label, int id) {
		super(label);
		iconID = id;
	}
	
	@Override
	public int getTabIconItemIndex() {
		return iconID;
	}
}

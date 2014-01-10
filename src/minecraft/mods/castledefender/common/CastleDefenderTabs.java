package mods.castledefender.common;

import net.minecraft.creativetab.CreativeTabs;

public class CastleDefenderTabs extends CreativeTabs {
	
	private int iconID = 1;

	public CastleDefenderTabs(String label, int id) {
		super(label);
		iconID = id;
	}
	
	@Override
	public int getTabIconItemIndex() {
		return iconID;
	}
}

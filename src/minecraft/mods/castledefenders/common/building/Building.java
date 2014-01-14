package mods.castledefenders.common.building;

import java.util.ArrayList;

public class Building {

	int maxX;
	int maxY;
	int maxZ;
	
	public class Unity {
		int idBlock = 0;
		int metadataBlock = 0;
	}
	
	//Liste des block de la constuction
//	Building.Unity[][][] blocks = new Building.Unity[64][256][64];
	ArrayList<ArrayList<ArrayList<Unity>>> blocks = new ArrayList<ArrayList<ArrayList<Unity>>>();
	
	
	public Building() {
	}
	
	
	public void add (int x, int y, int z, Unity unity) {
		
		// Redimention de l'axe x
		if (this.blocks.size() < x) {
			for (int i = this.blocks.size(); i <= x; i++) {
				this.blocks.set(x, new ArrayList<ArrayList<Unity>> ());
			}
			maxX = this.blocks.size();
		}
		
		// Redimention de l'axe y
		if (this.blocks.get(x).size() < y) {
			for (int i = this.blocks.get(x).size(); i <= y; i++) {
				this.blocks.get(x).set(y, new ArrayList<Unity> ());
			}
			maxZ = Math.max (maxZ, this.blocks.get(x).size());
		}
		
		// Redimention de l'axe z
		if (this.blocks.get(x).get(y).size() < z) {
			for (int i = this.blocks.get(x).get(y).size(); i < z; i++) {
				this.blocks.get(x).get(y).set(z, new Unity ());
			}
			maxY = Math.max (maxY, this.blocks.get(x).get(y).size());
		}
		
		this.blocks.get(x).get(y).set(z, unity);
	}
	
	public Unity get (int x, int y, int z) {
		return this.blocks.get(x).get(y).get(z);
	}
	
	
}

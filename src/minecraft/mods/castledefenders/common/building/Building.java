package mods.castledefenders.common.building;

import java.util.ArrayList;

import net.minecraft.block.Block;

public class Building {

	public int maxX;
	public int maxY;
	public int maxZ;
	
	/**
	 * Un element de lamatrice building
	 */
	static public class Unity implements Cloneable {
		public Block block = null;
		public int metadataBlock = 0;
		
		/**
		 * Clone l'objet
		 */
		public Object clone() {
			Unity o = new Unity ();
			o.block         = this.block;
			o.metadataBlock = this.metadataBlock;
			return o;
		}
	}
	
	//Liste des block de la constuction
	private ArrayList<ArrayList<ArrayList<Unity>>> blocks = new ArrayList<ArrayList<ArrayList<Unity>>>();
	
	
	public Building() {
	}
	
	
	public void add (int x, int y, int z, Unity unity) {
		
		// Redimention de l'axe x
		if (this.blocks.size() <= x) {
			for (int i = this.blocks.size(); i <= x; i++) {
				this.blocks.add(x, new ArrayList<ArrayList<Unity>> ());
			}
			maxX = this.blocks.size();
		}
		
		// Redimention de l'axe y
		if (this.blocks.get(x).size() <= y) {
			for (int i = this.blocks.get(x).size(); i <= y; i++) {
				this.blocks.get(x).add(y, new ArrayList<Unity> ());
			}
			maxY = Math.max (maxY, this.blocks.get(x).size());
		}
		
		// Redimention de l'axe z
		if (this.blocks.get(x).get(y).size() <= z) {
			for (int i = this.blocks.get(x).get(y).size(); i <= z; i++) {
				this.blocks.get(x).get(y).add(z, new Unity ());
			}
			maxZ = Math.max (maxZ, this.blocks.get(x).get(y).size());
		}
		
		this.blocks.get(x).get(y).set(z, unity);
	}
	
	public Unity get (int x, int y, int z) {
		return this.blocks.get(x).get(y).get(z);
	}
}

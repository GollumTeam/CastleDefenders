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
		public Block block     = null;
		public int metadata    = 0;
		public int orientation = Unity.ORIENTATION_NONE;

		public static final int ORIENTATION_NONE   = 0;
		public static final int ORIENTATION_UP     = 1;
		public static final int ORIENTATION_DOWN   = 2;
		public static final int ORIENTATION_LEFT   = 3;
		public static final int ORIENTATION_RIGTH  = 4;
		public static final int ORIENTATION_TOP    = 5;
		public static final int ORIENTATION_BOTTOM = 6;
		
		/**
		 * Clone l'objet
		 */
		public Object clone() {
			Unity o = new Unity ();
			o.block       = this.block;
			o.metadata    = this.metadata;
			o.orientation = this.orientation;
			return o;
		}
	}
	
	//Liste des block de la constuction
	private ArrayList<ArrayList<ArrayList<Unity>>> blocks = new ArrayList<ArrayList<ArrayList<Unity>>>();
	
	
	public Building() {
	}
	
	
	public void set (int x, int y, int z, Unity unity) {
		
		// Redimention de l'axe x
		if (this.blocks.size() <= x) {
			for (int i = this.blocks.size(); i <= x; i++) {
				this.blocks.add(new ArrayList<ArrayList<Unity>> ());
			}
			maxX = this.blocks.size();
		}
		
		// Redimention de l'axe y
		if (this.blocks.get(x).size() <= y) {
			for (int i = this.blocks.get(x).size(); i <= y; i++) {
				this.blocks.get(x).add(new ArrayList<Unity> ());
			}
			maxY = Math.max (maxY, this.blocks.get(x).size());
		}
		
		// Redimention de l'axe z
		if (this.blocks.get(x).get(y).size() <= z) {
			for (int i = this.blocks.get(x).get(y).size(); i <= z; i++) {
				this.blocks.get(x).get(y).add(new Unity ());
			}
			maxZ = Math.max (maxZ, this.blocks.get(x).get(y).size());
		}
		
		this.blocks.get(x).get(y).set(z, unity);
	}
	
	public Unity get (int x, int y, int z) {
		try {
			return this.blocks.get(x).get(y).get(z);
		} catch (Exception e) {
			return new Unity();
		}
	}
}

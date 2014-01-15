package mods.castledefenders.common.building;

import java.util.ArrayList;

public class Building {

	public int maxX;
	public int maxY;
	public int maxZ;
	
	/**
	 * Un element de lamatrice building
	 */
	static public class Unity implements Cloneable {
		public int idBlock = 0;
		public int metadataBlock = 0;
		
		/**
		 * Clone l'objet
		 */
		public Object clone() {
			Object o = null;
			try {
				// On récupère l'instance à renvoyer par l'appel de la 
				// méthode super.clone()
				o = super.clone();
			} catch(CloneNotSupportedException cnse) {
				// Ne devrait jamais arriver car nous implémentons 
				// l'interface Cloneable
				cnse.printStackTrace(System.err);
			}
			// on renvoie le clone
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

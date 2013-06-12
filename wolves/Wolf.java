/**
 * 
 */
package wolves;

/**
 * @author thomas
 *
 */
public class Wolf extends PlayerRole {
	private int level;

	//There is only one sensible constructor
	public Wolf(int l){
		super();
		level = l;
	}
	public int getLevel(){
		return level;
	}

	//Some sensible hashing
	@Override
	public int hashCode() {
		//{level bits}{dead 1 or 0}
		return (level*2 + (dead ? 1 : 0));
	}

	//I'll need this for HashMap.
	public boolean equals(PlayerRole w){
		if(w instanceof Wolf){
			return (((Wolf)w).level == this.level);
		} else return false;
	}

}

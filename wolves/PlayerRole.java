package wolves;

public abstract class PlayerRole {
 protected boolean dead = false;
 public boolean isDead(){
	 return dead;
 }
 public void setDead(){
	 dead = true;
 }
}

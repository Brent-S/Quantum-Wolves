package wolves;

public class Lynch extends PlayerAction{
	
	public Lynch(int inRound, int inPlayer){
		super(inRound, inPlayer);
	}
	
	@Override
	public String print(){
		return ("Round " + RoundNum + ", Player " + Player + " was lynched.");
	}
	
}

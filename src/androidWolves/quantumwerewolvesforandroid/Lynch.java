package androidWolves.quantumwerewolvesforandroid;

public class Lynch extends PlayerAction{
	
	public Lynch(int inRound, int inPlayer){
		super(inRound, inPlayer);
	}
	
	@Override
	public String print(String name){
		return ("Round " + RoundNum + ", Player " + Player + " (" + name 
				+ ") was lynched.");
	}

	@Override
	public boolean isRelevant(GameState inState){
		return true;  // Lynches are always relevant
	}
}


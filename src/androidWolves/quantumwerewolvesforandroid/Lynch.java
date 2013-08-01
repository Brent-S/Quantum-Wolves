package androidWolves.quantumwerewolvesforandroid;

public class Lynch extends PlayerAction{
	
	public Lynch(int inRound, int inPlayer){
		super(inRound, inPlayer);
	}
	
	@Override
	public String print(){
		return ("Round " + RoundNum + ", Player " + Player + " (" + MainActivity.getPlayerName(Player) 
				+ ") was lynched.");
	}

	@Override
	public boolean isRelevant(GameState inState){
		return true;  // Lynches are always relevant
	}
}


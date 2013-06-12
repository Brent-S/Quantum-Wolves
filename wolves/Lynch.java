package wolves;

public class Lynch {

	private int RoundNum;
	private int Player;
	
	public Lynch(int inRound, int inPlayer){
		RoundNum = inRound;
		Player = inPlayer;
	}
	
	public int getRound(){return RoundNum;}
	public int getPlayer(){return Player;}
		
}

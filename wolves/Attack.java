package wolves;

public class Attack {
	private int RoundNum;
	private int Player;
	private int Target;
	
	public Attack(int inRound, int inPlayer, int inTarget){
		RoundNum = inRound;
		Player = inPlayer;
		Target = inTarget;
	}
	
	public int getRound(){return RoundNum;}
	public int getPlayer(){return Player;}
	public int getTarget(){return Target;}
}

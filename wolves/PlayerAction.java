package wolves;

public abstract class PlayerAction {
	protected int RoundNum;
	protected int Player;
	
	public PlayerAction(int inRound, int inPlayer){
		RoundNum = inRound;
		Player = inPlayer;
	}
	
	public int getRound(){return RoundNum;}
	public int getPlayer(){return Player;}
	public abstract String print();
	public abstract boolean isRelevant(GameState inState);
}

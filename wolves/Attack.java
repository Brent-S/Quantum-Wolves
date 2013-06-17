package wolves;

public class Attack extends PlayerAction{
	private int Target;
	
	public Attack(int inRound, int inPlayer, int inTarget){
		super(inRound, inPlayer);
		Target = inTarget;
	}
	
	public int getTarget(){return Target;}

	@Override
	public String print() {
		return ("Round " + RoundNum + ", Player " + Target + " (" + RunFileGame.getPlayerName(Target) + ") was wolfed by player " + Player + " (" + RunFileGame.getPlayerName(Player) + ").");
	}

	@Override
	public boolean isRelevant(GameState inState){
		return inState.LeadWolfAtTime(RoundNum) == Player;
	}
}

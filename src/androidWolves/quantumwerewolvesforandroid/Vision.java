package androidWolves.quantumwerewolvesforandroid;

public class Vision extends PlayerAction{
	private int Target;
	private byte Vision;
	
	public Vision(int inRound, int inPlayer, int inTarget, byte inVision){
		super(inRound, inPlayer);
		Target = inTarget;
		Vision = inVision;
	}
	
	public int getTarget(){return Target;}
	public byte getVision(){return Vision;}

	@Override
	public String print(String name) {
		if(Target == 0) return "Round " + RoundNum + ", Player " + Player + " (" 
	+ RunFileGame.getPlayerName(Player) + ") had no vision.";
		String role = null;
		switch (Vision) {
		case 1:
			role = "an innocent.";
			break;
		case 2:
			role = "a werewolf.";
			break;
		}
		return ("Round " + RoundNum + ", Player " + Player + " (" 
		+ name + ") saw player " + Target + " as " + role);
	}
	
	@Override
	public boolean isRelevant(GameState inState){
		boolean isLiveSeer = (inState.playerTest(this.Player) == 2);
		boolean isDeadSeer = (inState.playerTest(this.Player) == -2);
		int TimeOfDeath = inState.getTimeOfDeath(this.Player);
		
		if(isLiveSeer){
			return true;
		} else {
			if(isDeadSeer && (this.RoundNum <= TimeOfDeath)) return true;
		} 
		return false;
	}
}

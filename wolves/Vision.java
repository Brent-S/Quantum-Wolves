package wolves;

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
	public String print() {
		if(Target == 0) return "Round " + RoundNum + ", Player " + Player + " (" + RunFileGame.getPlayerName(Player) + ") had no vision.";
		String role = null;
		switch (Vision) {
		case WolvesUI.VISION_INNO:
			role = "an innocent.";
			break;
		case WolvesUI.VISION_WOLF:
			role = "a werewolf.";
			break;
		}
		return ("Round " + RoundNum + ", Player " + Player + " (" + RunFileGame.getPlayerName(Player) + ") saw player " + Target + " (" + RunFileGame.getPlayerName(Target) + ") as " + role);
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

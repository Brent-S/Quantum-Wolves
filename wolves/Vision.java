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
		if(Target == 0) return "Round " + RoundNum + ", Player " + Player + " had no vision.";
		String role = null;
		switch (Vision) {
		case WolvesUI.VISION_INNO:
			role = "innocent.";
			break;
		case WolvesUI.VISION_WOLF:
			role = "werewolf.";
			break;
		}
		return ("Round " + RoundNum + ", Player " + Player + " saw player " + Target + " as a " + role);
	}
	
	@Override
	public boolean isRelevant(GameState inState){
		return (inState.playerTest(this.Player) == 2);
	}
}

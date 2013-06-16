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
		return ("Round " + RoundNum + ", Player " + Player + " saw player " + Target + " as a ");
	}
}

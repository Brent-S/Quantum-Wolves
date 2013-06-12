package wolves;

public class Vision {
	private int RoundNum;
	private int Player;
	private int Target;
	byte Vision;
	
	public Vision(int inRound, int inPlayer, int inTarget, byte inVision){
		RoundNum = inRound;
		Player = inPlayer;
		Target = inTarget;
		Vision = inVision;
	}
	
	public int getRound(){return RoundNum;}
	public int getPlayer(){return Player;}
	public int getTarget(){return Target;}
	public byte getVision(){return Vision;}
}

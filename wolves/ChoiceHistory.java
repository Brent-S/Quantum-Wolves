package wolves;

import java.util.ArrayList;

public class ChoiceHistory { // object contains history of choices made by players
	// To be instantiated by RunFileGame
	
	private ArrayList<Lynch> Lynchings;
	private ArrayList<Attack> Attacks;
	private ArrayList<Vision> Visions;
	
	public ChoiceHistory(){
		Lynchings = new ArrayList<Lynch>();
		Attacks = new ArrayList<Attack>();
		Visions = new ArrayList<Vision>();
	}
	
	public void SaveLynch(int RoundNum, int Player){
		Lynchings.add(new Lynch(RoundNum, Player));
	}
	
	public void SaveAttack(int RoundNum, int Player, int Target){
		Attacks.add(new Attack(RoundNum, Player, Target));
	}
	
	public void SaveVision(int RoundNum, int Player, int Target, byte Vision){
		Visions.add(new Vision(RoundNum, Player, Target, Vision));
	}
}

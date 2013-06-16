package wolves;

import java.util.ArrayList;
import java.util.List;

public class ChoiceHistory { // object contains history of choices made by players
	// To be instantiated by RunFileGame
	
	private ArrayList<PlayerAction> AllActions;
	
	public ChoiceHistory(){
		AllActions = new ArrayList<PlayerAction>();
	}
	
	public void SaveLynch(int RoundNum, int Player){
		AllActions.add(new Lynch(RoundNum, Player));
	}
	
	public void SaveAttack(int RoundNum, int Player, int Target){
		AllActions.add(new Attack(RoundNum, Player, Target));
	}
	
	public void SaveVision(int RoundNum, int Player, int Target, byte Vision){
		AllActions.add(new Vision(RoundNum, Player, Target, Vision));
	}
	
	public List<PlayerAction> ApplicableActions(GameState inState){
		ArrayList<PlayerAction> output = new ArrayList<PlayerAction>();
		for(PlayerAction Action : AllActions){
			if(Action.isRelevant(inState)) output.add(Action);
		}
		return output;
	}
}

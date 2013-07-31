package androidWolves.quantumwerewolvesforandroid;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

public class AndroidWolves {
	
	private Activity RunningActivity;
	
	
	@SuppressLint("NewApi")
    
    public AndroidWolves(Activity inActivity){
    	RunningActivity = inActivity;
    }
	
//	private int getUserInput(String prompt, boolean inNum){
//		Intent intent = new Intent(RunningActivity, NumberInputActivity.class);
//		intent.putExtra(MainActivity.ACTIVITY_MESSAGE, prompt);
//	    RunningActivity.startActivityForResult(intent, MainActivity.NUMBER_RESULT);
//		return 0;
//	}

	public int getNumPlayers() {
	//	int output = getUserInput("How many players?", true);
		return 0;
	}

	public int getNumWolves() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean getDebugMode() {
		// TODO Auto-generated method stub
		return false;
	}

	public void displayPlayerIDs(String[] playerIDs) {
		// TODO Auto-generated method stub
		displayString("Player IDs here");
	}

	public void displayAllStates(List<GameState> allStates) {
		// TODO Auto-generated method stub
		displayString("All States here");
	}

	public void displayEndGame(int roundNum, WinCodes checkWin, int[] knownRoles) {
		// TODO Auto-generated method stub
		displayString("EndGame here");
	}

	public void displayString(String string) {
		Intent intent = new Intent(RunningActivity, DisplayMessageActivity.class);
		intent.putExtra(MainActivity.ACTIVITY_MESSAGE, string);
	    RunningActivity.startActivityForResult(intent,MainActivity.NO_RESULT);
	}

	public int inputSeerTarget(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void displaySingleVision(int i, int target, byte vision) {
		// TODO Auto-generated method stub
		displayString("Vision Here");
	}

	public int inputLynchTarget() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String inputName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void displayError(String string) {
		// TODO Customise?
		displayString(string);
	}

	public void displayProbabilities(double[][] displayProbs, int[] rolesCodes) {
		// TODO Auto-generated method stub
		displayString("Probabilities here");
	}

	public void displayHistory(ArrayList<PlayerAction> allActions, List<PlayerAction> applicableActions) {
		// TODO Auto-generated method stub
		displayString("Histories here");
	}

}

package androidWolves.quantumwerewolvesforandroid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	// The following are used in startActivityForResult(Intent, int) calls:
	public static final int PLAYER_NUMBER_RESULT = 2;
	public static final int WOLF_NUMBER_RESULT = 3;
	public static final int STRING_RESULT = 4;
	public static final int NO_RESULT = 5;
	public static final int SET_NAME_RESULT = 6;
	public static final int SHOW_IDs = 7;
	public static final int VISION_RESULT = 8;
	public static final int DISPLAY_VISION = 9;
	public static final int ATTACK_RESULT = 10;
	public static final int MORNING_DISPLAY = 11; // before lynch
	public static final int LYNCH_RESULT = 12;
	public static final int AFTERNOON_DISPLAY = 13; //after lynch
	public static final int DISPLAY_ENDGAME = 14; 
	public static final int DISPLAY_ENDGAME_STATES = 15;
	public static final int DISPLAY_HISTORY = 16;

	public static final String ACTIVITY_MESSAGE = "androidWolves.quantumwerewolvesforandroid.Message";
	public static final String NUMBER_STORED = "androidWolves.quantumwerewolvesforandroid.Number";
	public static final String STRING_STORED = "androidWolves.quantumwerewolvesforandroid.String";
	public static final String NAME_ARRAY = "androidWolves.quantumwerewolvesforandroid.NameArray";
	public static final String HTML_STORED = "androidWolves.quantumwerewolvesforandroid.HTMLString";
	public static final String DOUBLE_ARRAY_STORED = "androidWolves.quantumwerewolvesforandroid.ArrayD";
	public static final String STRING_2D_ARRAY_STORED = "androidWolves.quantumwerewolvesforandroid.2DArrayStr";

	public static final int RESULT_ERROR = Activity.RESULT_FIRST_USER + 1; // not sure why I bothered with this...


	//The following are used by the program internally
	private static int players; // number of players
	private int wolves; // number of wolves
	// private int round; // Round number.
	private ChoiceHistory History;
	private static String[] PlayerNames;
	private boolean GameOver;
	private Game RunningGame;
	private int[] LastWolfTargets;
	private int[] TempRandOrd;

	// TODO remove un-needed silly ones...
	public static final byte VISION_INNO = 1;
	public static final byte VISION_WOLF = 2;
	public static final byte VISION_NOPE = 0;	
	public static final int INDEX_GOOD = 0;
	public static final int INDEX_EVIL = 1;
	public static final int INDEX_ALIVE = 2;
	public static final int INDEX_DEAD = 3;	
	public static final int ROLE_BLUE = 1; //negative numbers are dead
	public static final int ROLE_SEER = 2;
	public static final int ROLE_WOLF = 3;

	// The following is used for internal flow control
	private int PlayerCount; // Keeps track of how many players have performed in-progress action.
	private boolean EndGameShown;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Quantum Werewolves");
		setContentView(R.layout.activity_main);   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void Launch(View view) {
		History = new ChoiceHistory();
		GameOver = false;
		inputNumPlayers(false);
	}

	public void OnExit(View view) {
		Intent returnIntent = new Intent();
		setResult(RESULT_OK, returnIntent);        
		finish(); 
	}

	protected void onActivityResult (int requestCode, int resultCode, Intent data){
		switch(requestCode){
		case NO_RESULT : break;
		case PLAYER_NUMBER_RESULT :
			switch(resultCode){
			case RESULT_OK : 
				players = data.getIntExtra(NUMBER_STORED, 0);
				if(players < 2) {
					inputNumPlayers(true);;
				} else {
					inputNumWolves(false);
				}
				break;
			case RESULT_CANCELED : 
				displayError("Enter Player Number was cancelled");
				break;
			}
			break;
		case WOLF_NUMBER_RESULT :
			switch(resultCode){
			case RESULT_OK : 
				wolves = data.getIntExtra(NUMBER_STORED, 0);
				if ((wolves < 1) || (wolves >= players)){
					inputNumWolves(true);
				} else {
					PlayerCount = 0;
					PlayerNames = new String[players];
					LastWolfTargets = new int[players];
					inputPlayerName(false);
				}
				break;
			case RESULT_CANCELED : 
				displayError("Enter Wolf Numbers was cancelled");
				break;
			}
			break;
		case SET_NAME_RESULT : 
			switch(resultCode){
			case RESULT_OK : 
				String tempName = data.getStringExtra(STRING_STORED);
				if(CheckName(tempName)){ // true if repeated
					inputPlayerName(true); //get same name
				} else {
					if(addName(tempName)) { // side effect adds name
						inputPlayerName(false); // get next name
					} else {
						displayPlayerIDs();
					} 
				} 
				break;
			case RESULT_CANCELED : 
				displayError("Enter player name was cancelled");
				break;
			}
			break;
		case SHOW_IDs : 
			switch(resultCode){
			case RESULT_OK : 
				PlayerCount = 0;
				// round = 0;
				RunningGame = new Game(players, wolves);
				TempRandOrd = getRandomOrdering(players);
				currentPlayerVision();
				break;
			case RESULT_CANCELED : 
				displayError("Show IDs was cancelled");
				break;
			}
			break;
		case VISION_RESULT : 
			switch(resultCode){
			case RESULT_OK : 
				int Target = data.getIntExtra(NUMBER_STORED, -1);
				if(Target == 0){ // move on to attack
					currentPlayerWolf();
				} else {
					int currentPlayer = TempRandOrd[PlayerCount] + 1;
					byte Vision = RunningGame.HaveSingleVision(currentPlayer,Target);
					RunningGame.SingleVisionAllStates(currentPlayer, Target, Vision);
					History.SaveVision(RunningGame.getRoundNum(), currentPlayer, Target, Vision);
					RunningGame.UpdateProbabilities();
					RunningGame.CollapseAllDead();	
					displaySingleVision(currentPlayer,Target,Vision);
					updateGameOver(); // this might make things a little odd in some edge cases.
				}
				break;
			case RESULT_CANCELED :
				displayError("Vision Input was cancelled");
				break;
			}
			break;
		case DISPLAY_VISION : 
			switch(resultCode){
			case RESULT_OK : 
				currentPlayerWolf();				
				break;
			case RESULT_CANCELED : 
				displayError("Vision display was cancelled");
				break;
			}
			break;
		case ATTACK_RESULT : 
			switch(resultCode){
			case RESULT_OK : 
				int Target = data.getIntExtra(NUMBER_STORED, -1);
				int currentPlayer = TempRandOrd[PlayerCount];
				LastWolfTargets[currentPlayer] = Target;
				History.SaveAttack(RunningGame.getRoundNum(), currentPlayer + 1, Target);
				CheckLastPlayer();
				break;
			case RESULT_CANCELED : 
				displayError("Attack Input was cancelled");
				break;
			}
			break;
		case MORNING_DISPLAY : 
			switch(resultCode){
			case RESULT_OK : 			
				RunningGame.UpdateProbabilities();
				RunningGame.CollapseAllDead();
				updateGameOver();
				if(!GameOver) inputLynch();
				break;
			case RESULT_CANCELED : 
				displayError("Morning display was cancelled");
				break;
			}
			break;
		case LYNCH_RESULT : 
			switch(resultCode){
			case RESULT_OK : 
				int Target = data.getIntExtra(NUMBER_STORED, -1);
				History.SaveLynch(RunningGame.getRoundNum(), Target);
				RunningGame.LynchAllStates(Target);			
				RunningGame.UpdateProbabilities();
				RunningGame.CollapseAllDead();
				DayTimeDisplay(false);
				break;
			case RESULT_CANCELED : 
				displayError("Lynch input was canceled");
				break;
			}
			break;
		case AFTERNOON_DISPLAY : 
			switch(resultCode){
			case RESULT_OK : 
				updateGameOver();
				if(!GameOver) {
					PlayerCount = 0;
					currentPlayerVision();
				} 
				break;
			case RESULT_CANCELED : 
				displayError("Afternoon display was cancelled");
				break;
			}
			break;
		case DISPLAY_ENDGAME : 
			switch(resultCode){
			case RESULT_OK : // display all states
				displayAllStates(RunningGame.getAllStates());
				break;
			case RESULT_CANCELED : 
				displayError("EndGame display was cancelled");
				break;
			}
			break;
		case DISPLAY_ENDGAME_STATES : 
			switch(resultCode){
			case RESULT_OK : 
				if(RunningGame.getNumStates() == 1){
					displayHistory();
				} else {
					selectFinalState();
				}
				break;
			case RESULT_CANCELED : 
				displayError("EndGame states display was cancelled");
				break;
			}
			break;
		case DISPLAY_HISTORY : 
			switch(resultCode){
			case RESULT_OK : // display all states
				displayString("All done now. :)");
				break;
			case RESULT_CANCELED : 
				displayError("History display was cancelled");
				break;
			}
			break;
		}
		if(GameOver && !EndGameShown) {
			EndGame();
		}
	}

	private boolean	CheckName(String newName){ // returns true if name is already in list
		boolean output = false;
		for(int i = 0; i < PlayerCount; i++){
			if(newName.equals(PlayerNames[i])){
				output = true;
				break;
			}
		}
		return output;
	}

	private void EndGame(){
		EndGameShown = true;
		String text = "";
		WinCodes WinCode = RunningGame.CheckWin();
		int[] knownRoles = RunningGame.getKnownRoles();
		if(WinCode == WinCodes.NoStatesRemain){
			text = "No Gamestates remain.";
		} else {
			String WinningTeam = null;
			switch(WinCode){
			case InnocentsWon : WinningTeam = "Innocents";
			break;
			case WolvesWon : WinningTeam = "Wolves";
			break;
			case ERROR : WinningTeam = "ERROR";
			text = ("SOMETHING HAS GONE WRONG");
			break;
			}
			text += ("Game Over.\nThe " + WinningTeam + " have won after " + RunningGame.getRoundNum() + " rounds.\n");
		}
		int n;
		for (int i = 0; i < players; i++) {
			n = 1;
			String role = null;
			String dead  = "";
			if (knownRoles[i] < 0) {
				dead = "dead ";
				n = -1;
			}
			switch (n*knownRoles[i]) {
			case ROLE_BLUE:
				role = "villager";
				break;
			case ROLE_SEER:
				role = "seer";
				break;
			}
			if(n*knownRoles[i] >= 3) role = "wolf";
			if (knownRoles[i] != 0) {
				text += ("Player " + (i+1) + " (" + PlayerNames[i] + ")" + " is a " + dead + role + "\n");
			}
		}
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		intent.putExtra(MainActivity.ACTIVITY_MESSAGE, text);
		startActivityForResult(intent,MainActivity.DISPLAY_ENDGAME);
	}

	private void displayAllStates(List<GameState> AllStates){
		final String newline = "<br>";
		String output = "<html>";
		for(GameState gameState: AllStates){
			int[] PlayerRoles = gameState.AllPlayers();
			for(int i = 0; i < PlayerRoles.length; i++){
				output += "(" + (i+1) + " " + PlayerNames[i];
				if(PlayerRoles[i] < 0) output += " Dead";
				switch(PlayerRoles[i]){
				case 1: output += " Villager";
				break;
				case 2: output += " Seer";
				break;
				case -1: output += " Villager";
				break;
				case -2: output += " Seer";
				break;
				}
				if(PlayerRoles[i] >= 3) output += " " + (PlayerRoles[i] - 2) + "-Wolf";
				if(PlayerRoles[i] <= -3) output += " " + (-1 * (PlayerRoles[i] + 2)) + "-Wolf";
				output += ")";
			}	
			output += newline;
		}
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		intent.putExtra(MainActivity.HTML_STORED, output + "</html>");
		startActivityForResult(intent,MainActivity.DISPLAY_ENDGAME_STATES);
	}

	private void selectFinalState(){
		if(RunningGame.getNumStates() != 1) { // If there are multiple states, one is randomly chosen.
			String output = "<html>At this point, the choices made<br>" +
					"still leave more than one final outcome.<br>" +
					"Therefore, one outcome is now being<br>" +
					"randomly selected.<br><br>" ;
			RunningGame.SelectEndState();
			RunningGame.UpdateProbabilities();
			GameState gameState = RunningGame.getFirstState();
			int[] PlayerRoles = gameState.AllPlayers();
			for(int i = 0; i < PlayerRoles.length; i++){
				output += "(" + (i+1) + " " + PlayerNames[i];
				if(PlayerRoles[i] < 0) output += " Dead";
				switch(PlayerRoles[i]){
				case 1: output += " Villager";
				break;
				case 2: output += " Seer";
				break;
				case -1: output += " Villager";
				break;
				case -2: output += " Seer";
				break;
				}
				if(PlayerRoles[i] >= 3) output += " " + (PlayerRoles[i] - 2) + "-Wolf";
				if(PlayerRoles[i] <= -3) output += " " + (-1 * (PlayerRoles[i] + 2)) + "-Wolf";
				output += ")<br>";
			}	
			output += "</html>";

			Intent intent = new Intent(this, DisplayMessageActivity.class);
			intent.putExtra(MainActivity.HTML_STORED, output);
			startActivityForResult(intent,MainActivity.DISPLAY_ENDGAME_STATES);
		}
	}

	private void displayHistory(){
		List<PlayerAction> FullHistory = History.AllActions;
		List<PlayerAction> PartHistory = History.ApplicableActions(RunningGame.getFirstState());
		String text = "<html>Relevent actions:<br>";
		for(PlayerAction action : PartHistory){
			text += action.print() + "<br>";			
		}
		text += "<br>All actions:<br>";
		for(PlayerAction action : FullHistory){
			text += action.print() + "<br>";			
		}
		text += "</html>";
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		intent.putExtra(MainActivity.HTML_STORED, text + "</html>");
		startActivityForResult(intent,DISPLAY_HISTORY);
	}

	private void DayTimeDisplay(boolean isMorning){
		double[][] Probabilities = RunningGame.getProbabilities();
		double[] LiveProbs = RunningGame.LivingProbabilities();
		double DisplayProbs[][] = new double[players][4];
		for(int n = 0; n < players; n++){
			DisplayProbs[n][0] = Probabilities[n][0] + Probabilities[n][1] + Probabilities[n][2] + Probabilities[n][3];
			DisplayProbs[n][0] *= 100;
			DisplayProbs[n][1] = 100 - DisplayProbs[n][0];
			DisplayProbs[n][2] = LiveProbs[n] * 100;
			DisplayProbs[n][3] = 100 - DisplayProbs[n][2];
		}
		int[] RolesCodes = RunningGame.getKnownRoles();
		for (int i = 0; i < RolesCodes.length; i++) {
			if (RolesCodes[i] > 3) {
				RolesCodes[i] = 3;
			} else if (RolesCodes[i] < -3) {
				RolesCodes[i] = -3;
			}
		}

		String[] StringProbs = new String[players*4]; // sending a double array via and intent doesn't seem to work...
		for(int i = 0; i < players; i++){
			for(int j = 0; j < 4; j++){
				StringProbs[(4 * i) + j] = Integer.toString((int) Math.round(DisplayProbs[i][j]));
			}
		}// StringProbs now contains each player's probs followed by the next four.

		String rolesText = "<br>";
		int n;
		for (int i = 0; i < players; i++) {
			n = 1;
			String role = null;
			String dead  = "";
			String name = "";
			if (RolesCodes[i] < 0) {
				dead = "dead ";
				n = -1;
				name = " (" + PlayerNames[i] + ")";
			}
			switch (n*RolesCodes[i]) {
			case ROLE_BLUE:
				role = "villager";
				break;
			case ROLE_SEER:
				role = "seer";
				break;
			case ROLE_WOLF:
				role = "wolf";
				break;
			}
			if (RolesCodes[i] != 0) {
				rolesText += ("Player " + (i+1) + name + " is a " + dead + role + "<br>");
			}
		}
		Intent intent = new Intent(this, DayTimeActivity.class);
		intent.putExtra(MainActivity.ACTIVITY_MESSAGE, "Round " + RunningGame.getRoundNum());
		intent.putExtra(MainActivity.HTML_STORED, "<html>" + rolesText + "</html>");
		intent.putExtra(NAME_ARRAY, StringProbs);
		intent.putExtra(NUMBER_STORED, players);
		int returnCode;
		if(isMorning){
			returnCode = MainActivity.MORNING_DISPLAY;
		} else {
			returnCode = MainActivity.AFTERNOON_DISPLAY;
		}
		startActivityForResult(intent,returnCode);
	}

	public void updateGameOver(){
		WinCodes WinCode = RunningGame.CheckWin();
		GameOver = (WinCode != WinCodes.GameNotOver);
	}

	private void currentPlayerVision(){

		List<String> p = getLivePlayers();
		String[] choices = new String[p.size() + 1];
		choices[0] = "NONE";
		for (int i = 1; i < choices.length; i++) {
			choices[i] = p.get(i - 1);
		}

		int currentPlayer = TempRandOrd[PlayerCount];
		if(RunningGame.CheckLiveSeers()[currentPlayer]){ // this player can have a vision
			String prompt = "Who does " + (currentPlayer + 1) + " (" + 
					PlayerNames[currentPlayer] + ") wish to have a vision of?";
			Intent intent = new Intent(this, PlayerSelectActivity.class);
			intent.putExtra(MainActivity.ACTIVITY_MESSAGE, prompt);
			intent.putExtra(MainActivity.NAME_ARRAY, choices);
			startActivityForResult(intent,MainActivity.VISION_RESULT);
		} else { // this player cannot have a vision
			currentPlayerWolf();
		}
	}

	private void currentPlayerWolf(){
		int currentPlayer = TempRandOrd[PlayerCount]; // 0 indexed
		RunningGame.UpdateProbabilities();
		RunningGame.CollapseAllDead();	
		if(RunningGame.CheckLiveWolves()[currentPlayer]){ // this player must attack
			String prompt = "Who does " + (currentPlayer + 1) + " (" + 
					PlayerNames[currentPlayer] + ") wish to attack?";			
			List<String> LivePlayers = getLivePlayers();
			LivePlayers.remove(PlayerNames[currentPlayer]);

			// The following used to remove all past targets, but this can remove all options
			// because the target may have been chosen when the player was a secondary wolf
			//			List<Integer> TargetIDs = History.WolfTargets(currentPlayer + 1); // 1 indexed
			//			List<String> TargetNames = new ArrayList<String>();
			//			for(Integer ID : TargetIDs){
			//				TargetNames.add(PlayerNames[ID - 1]);
			//			}
			//			LivePlayers.removeAll(TargetNames);
			String[] choices = new String[LivePlayers.size()];
			for (int i = 0; i < choices.length; i++) {
				choices[i] = LivePlayers.get(i);
			}

			Intent intent = new Intent(this, PlayerSelectActivity.class);
			intent.putExtra(MainActivity.ACTIVITY_MESSAGE, prompt);
			intent.putExtra(MainActivity.NAME_ARRAY, choices);
			startActivityForResult(intent,MainActivity.ATTACK_RESULT);
		} else { // this player cannot attack
			CheckLastPlayer();
		}
	}

	private void CheckLastPlayer(){
		if(PlayerCount == (players-1)){ // the last player has just had his actions stored
			RunningGame.AttackAllStates(LastWolfTargets);
			RunningGame.UpdateProbabilities();
			RunningGame.CollapseAllDead();
			updateGameOver();
			if(GameOver) {
				EndGame();
			} else {
				DayTimeDisplay(true);
			}
		} else { // there are more players yet
			PlayerCount++;
			currentPlayerVision();
			// in the event than many players have no actions in a row
			// this will create many 'empty' entries on the stack,
			// until a new activity is called, when they will all be removed.
			// If no new Activity is called, and it continues looping, this could result in a memory leak.
			// But this shouldn't (if written correctly...) happen...
		}
	}

	private void inputLynch(){
		List<String> p = getLivePlayers();
		String[] choices = new String[p.size()];
		for (int i = 0; i < choices.length; i++) {
			choices[i] = p.get(i);
		}
		String prompt = "Who has been voted to be lynched?";
		Intent intent = new Intent(this, PlayerSelectActivity.class);
		intent.putExtra(MainActivity.ACTIVITY_MESSAGE, prompt);
		intent.putExtra(MainActivity.NAME_ARRAY, choices);
		startActivityForResult(intent,MainActivity.LYNCH_RESULT);
	}

	public List<String> getLivePlayers(){ // returns a sorted list of all live players' names
		List<String> output = new ArrayList<String>();
		double[] LiveProbs = RunningGame.LivingProbabilities();
		for(int i = 0; i < players; i++){
			if(LiveProbs[i] != 0) output.add(PlayerNames[i]);
		}
		Collections.sort(output);
		return output;
	}

	public static int getPlayerIDFromName(String inName) {
		int n = 0;
		for(int i = 0; i < players; i++){
			if(PlayerNames[i].equals(inName)){
				n = i + 1;
				break;				
			}
		}
		return (n);
	}

	private void inputPlayerName(boolean RepeatPrevious){
		String message;
		if(RepeatPrevious){
			message = "That name has already been entered, please try again:";
		} else {
			message = "Enter a player name:";
		}
		Intent intent = new Intent(this, StringInputActivity.class);
		intent.putExtra(MainActivity.ACTIVITY_MESSAGE, message);
		startActivityForResult(intent,MainActivity.SET_NAME_RESULT);
	}

	private boolean addName(String Name){ // Adds Name to (private String[] PlayerNames), 
		// if array is then full, it shuffles it, and returns false.
		PlayerNames[PlayerCount] = Name;
		PlayerCount++;
		if (PlayerCount == players) {
			int[] RandOrd = getRandomOrdering(players);
			String[] temp = new String[players];
			for(int i = 0; i < players; i++){
				int n = RandOrd[i];
				temp[i] = PlayerNames[n];
			}
			for(int i = 0; i < players; i++){
				PlayerNames[i] = temp[i];
			}
			return false;
		} else {
			return true;
		}
	}

	private void inputNumPlayers(boolean repeat) {
		String message;
		if(repeat) {
			message = "Please enter an integer greater than 1. (but not TOO large...)";
		} else {
			message = "How many people are playing?";
		}
		Intent intent = new Intent(this, NumberInputActivity.class);
		intent.putExtra(MainActivity.ACTIVITY_MESSAGE, message);
		startActivityForResult(intent,MainActivity.PLAYER_NUMBER_RESULT);
	}

	private void inputNumWolves(boolean repeat) {
		String message;
		if(repeat) {
			message = "You must have at least one wolf, " +
					"but fewer than the total number of players.";
		} else {
			message = "How many should be wolves?";
		}
		Intent intent = new Intent(this, NumberInputActivity.class);
		intent.putExtra(MainActivity.ACTIVITY_MESSAGE, message);
		startActivityForResult(intent,MainActivity.WOLF_NUMBER_RESULT);
	}

	private void displayPlayerIDs() {
		String text = "";		
		text = ("Assigned player names: \n");
		for(int i = 0; i < players; i++){
			text += ((i+1) + " - " + PlayerNames[i] +"\n");
		}
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		intent.putExtra(MainActivity.ACTIVITY_MESSAGE, text);
		startActivityForResult(intent,MainActivity.SHOW_IDs);		
	}

	private void displayString(String string) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		intent.putExtra(MainActivity.ACTIVITY_MESSAGE, string);
		startActivityForResult(intent,MainActivity.NO_RESULT);
	}

	private void displaySingleVision(int Seer, int Target, byte Vision) {
		if(Target == 0){
			// Do Nothing.
		} else {
			String role = null;
			switch (Vision) {
			case VISION_INNO:
				role = "an innocent.";
				break;
			case VISION_WOLF:
				role = "a werewolf.";
				break;
			}
			String text = "Player " + Seer + " (" + PlayerNames[Seer-1] + 
					") sees that " + PlayerNames[Target-1] + "<br>is " + role;
			Intent intent = new Intent(this, DisplayMessageActivity.class);
			intent.putExtra(MainActivity.HTML_STORED, "<html>" + text + "</html>");
			startActivityForResult(intent,MainActivity.DISPLAY_VISION);
		}		
	}

	private void displayError(String string) {
		// Customise?
		displayString(string);
	}

	public static int[] getRandomOrdering(int Size){ // returns a zero indexed random ordering
		double[] randArray = new double[Size];
		for(int n = 0; n < Size; n++){
			randArray[n] = Math.random();
		}
		int[] randOrder = new int[Size];
		double lowestRand = 1;
		for(int i = 0; i < Size; i++){
			for(int n = 0; n < Size; n++){
				if(randArray[n] < lowestRand) {
					randOrder[i] = n;
					lowestRand = randArray[n];
				}
			}
			randArray[randOrder[i]] = 1;
			lowestRand = 1;
		}
		return randOrder;
	}

	public static String getPlayerName(int inID){
		return PlayerNames[inID - 1];
	}

}
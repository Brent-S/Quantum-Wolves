package wolves;

import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class SwingWolves implements WolvesUI {
	
	private int players;
	private int wolves;
	
	public SwingWolves(){
		//I think we should use this space as a convenient alternative for commit messages
		// If you like, but it is no longer empty...
		
		boolean startGame = false;
		while(!startGame){
			Object[] buttons = {"Instructions", "About", "Lets play this game!"};
			int UserInput = JOptionPane.showOptionDialog(null,
						    "Welcom to this Java implementation of Quantum Werewolves",
						    "Quantum Werewolves",
						    JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null,
						    buttons,
						    buttons[2]);
			switch(UserInput){
			case JOptionPane.YES_OPTION : displayString("Instructions");
			break;
			case JOptionPane.NO_OPTION : displayString("About");
			break;
			case JOptionPane.CLOSED_OPTION : System.exit(0);
			break;
			case JOptionPane.CANCEL_OPTION : startGame = true;		
			}
		}
	}

	private String getUserInput(String prompt) {
		Object output = JOptionPane.showInputDialog(null, prompt, "Quantum Werewolves", JOptionPane.QUESTION_MESSAGE);
		if(output == null){
			System.exit(0); // terminate program...
			return null; // why is this nessecary...  (I can't spell...)
		}
		else return (String) output;
	}
	
	private String getPlayerFromUser(String prompt) {
		List<String> p = RunFileGame.getLivePlayers();
		String[] arrplay = new String[p.size() + 1];
		arrplay[0] = "NONE";
		for (int i = 1; i < arrplay.length; i++) {
			arrplay[i] = p.get(i - 1);
		}
		
		// return PlayerSelectFrame.choosePlayer(prompt, arrplay);
		return "";
	}
	
	private int getIntFromUser(String prompt) {
		while (true) {
			try {
				return Integer.parseInt(getUserInput(prompt));
			} catch (Exception e) {
				displayError("FUCK OFF THATS NOT A NUMBER");
			}
		}
	}
	
	@Override
	public int getNumPlayers() {
		this.players = getIntFromUser("PLEASE CHOOSE HOW MANY OF PEOPLE");
		return players;
	}

	@Override
	public int getNumWolves() {
		this.wolves = getIntFromUser("PLEASE CHOOSE HOW MANY OF WOLVES");
		return wolves;
	}
	
	private int getNameIDFromUser(String prompt){
		while(true){		
			try {
				String Name = getPlayerFromUser(prompt);
				if (Name.equals("NONE")) return 0;
				return RunFileGame.getPlayerIDFromName(Name);
			} catch (WrongNameException e) {
				displayError("FUCK OFF THATS NOT A NAME");
			}
		}
	}

	@Override
	public void displayEndGame(int RoundNum, WinCodes WinCode, int[] knownRoles) {
		String text = "";
		
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
			text = ("Game Over. The " + WinningTeam + " have won after " + RoundNum + " rounds.\n");
		}
		int n;
		for (int i = 0; i < players; i++) {
			n = 1;
			String role = null;
			String dead  = "";
			if (knownRoles[i] < 0) {
				dead = "DEAD ";
				n = -1;
			}
			switch (n*knownRoles[i]) {
			case ROLE_BLUE:
				role = "VILLAGER";
				break;
			case ROLE_SEER:
				role = "SEER";
				break;
			}
			if(n*knownRoles[i] >= 3) role = "WOLF";
			if (knownRoles[i] != 0) {
				text += ("PLAYER " + (i+1) + " (" + RunFileGame.getPlayerName(i+1) + ")" + " IS " + getAdjective() + " " + dead + role + "\n");
			}
		}
		
		displayString(text);
	}

	private String getAdjective() {
		String[] words = {"A FILTHY", "A CLEAN", "A CAPRICIOUS", "A WELL-INFORMED", "AN INSANE", "A SANITARY", "A TUMULTUOUS",
			"AN INFLAMED", "AN INFLAMMATORY", "A CALAMATOUS", "AN INCONGRUOUS", "A RADISHY", "A FERAL", "A FINGER-LICKIN'", 
			"A PERNICIOUS", "A LOOPY", "A DIRTY", "AN ANGRY", "A FOCUSED", "AN ANTICLIMATIC", "A HORRIBLE",
			"A SCARY", "A MONSTROUS", "A TERRIBLE", "A TERRIFYING", "A DESPICABLE", "A SHIT",
			"AN OVERPRICED", "AN EGREGIOUS", "A GREGARIOUS", "A MISSPELLED", "A HORRIFIC", "A FUCKED-UP",
			"AN ABOMINABLE", "A COLD-HEARTED", "A HERETICAL", "A CANTANKEROUS", "A POLISHED", "A HAPPY",
			"A FUZZY", "A FLUFFY", "A CUTE", "A TECHNOLOGICAL", "A MEGARIFFIC", "A RURAL", "A MORBID",
			"A GRIM", "AN ANIMALISTIC", "A FRIENDLY", "A KIND-HEARTED", "A MURDEROUS", "A DANGEROUS",
		"A BRUTAL", "A CALM", "A PSYCHOTIC", "A PSYCOPATHIC", "AN EVIL"};
		Random generator = new Random();
		return words[generator.nextInt(words.length)];
	}
	
	@Override
	public int inputLynchTarget() {
		return getNameIDFromUser("PLEASE CHOOSE WHO IS LUNCHED");
	}

	@Override
	public void displayProbabilities(double[][] probabilities, int[] knownRoles) {
		String text = "";
		
		text += ("PLAY GOOD EVIL LIVE DEAD \n");
		for (int i = 0; i < players; i++) {
			text += (pad(i + 1));
			for (int j = 0; j < 4; j++) {				
				text += (pad((int) Math.round(probabilities[i][j])));
			}
			text += "\n";
		}
		text += "KNOWN ROLLS \n";
		int n;
		for (int i = 0; i < players; i++) {
			n = 1;
			String role = null;
			String dead  = "";
			String name = "";
			if (knownRoles[i] < 0) {
				dead = "DEAD ";
				n = -1;
				name = " (" + RunFileGame.getPlayerName(i+1) + ")";
			}
			switch (n*knownRoles[i]) {
			case ROLE_BLUE:
				role = "VILLAGER";
				break;
			case ROLE_SEER:
				role = "SEER";
				break;
			case ROLE_WOLF:
				role = "WOLF";
				break;
			}
			if (knownRoles[i] != 0) {
				text += ("PLAYER " + (i+1) + name + " IS " + getAdjective() + " " + dead + role + "\n");
			}
		}
		displayString(text);
	}
	
	private String pad(int i) {
		String s = String.valueOf(i);
		int padding = 5 - s.length();
		for (int j = 0; j < padding; j++) {
			s = s + "  ";
		}
		return s;
	}

	@Override
	public int inputSeerTarget(int inSeer) {
		return getNameIDFromUser("\nPLEASE CHOOSE WHO IS SAW BY PLAYER " + inSeer + " (" + RunFileGame.getPlayerName(inSeer) + "), or 'NONE' for no vision");
	}

	@Override
	public void displaySingleVision(int Seer, int Target, byte Vision) {
		if(Target == 0){
			// Do Nothing.
		} else {
			String role = null;
			switch (Vision) {
			case WolvesUI.VISION_INNO:
				role = "INNOCENT";
				break;
			case WolvesUI.VISION_WOLF:
				role = "WHEREWOLF";
				break;
			}
			displayString("PLAYER " + Seer + " (" + RunFileGame.getPlayerName(Seer) + ") SEES THAT " + RunFileGame.getPlayerName(Target) + " IS " + getAdjective() + " " + role);
		}

	}

	@Override
	public void displayAllStates(String AllStateText) {
		displayString(AllStateText);
	}

	@Override
	public boolean getDebugMode() {
		int UserInput = JOptionPane.showConfirmDialog(
			    null,
			    "Would you like to use debug mode? \n (This will display all game-states at every update.)",
			    "Quantum Werewolves",
			    JOptionPane.YES_NO_OPTION);
		if(UserInput == JOptionPane.CLOSED_OPTION){
			System.exit(0);
			return false;
		} else return (UserInput == JOptionPane.YES_OPTION);
	}

	@Override
	public int InputSingleWolfTarget(int inPlayer) {
		return getNameIDFromUser("\nPLEASE CHOOSE WHO IS WOLFED DOWN BY PLAYER " + inPlayer + " (" + RunFileGame.getPlayerName(inPlayer) + ")");
	}

	@Override
	public String inputName() {
		return getUserInput("PLEASE ENTER A NAME OF PLAYER");
	}

	@Override
	public void displayPlayerIDs(String[] inArray) {
		String text = "";
		
		text = ("Assigned player names: \n");
		for(int i = 0; i < players; i++){
			text += ((i+1) + " - " + inArray[i] +"\n");
		}
		
		displayString(text);
	}

	@Override
	public String[] SetNames() {
		String[] Players = new String[players];
		int[] RandOrd = RunFileGame.getRandomOrdering(players);
		for(int i = 0; i < players; i++){
			int n = RandOrd[i];
			boolean BadName = false;
			String Name;
			do{
				Name = inputName();
				BadName = false;
				if(Name.equals("NONE")){
					BadName = true;
					displayError("FUCK OFF THAT NAMES RESERVED");
				}				
			}while(BadName);
			Players[n] = Name;			
		}
		return Players;
	}

	@Override
	public void displayHistory(String HistoryText) {
		displayString(HistoryText);

	}
	
	private void displayString(String text){
		JOptionPane.showMessageDialog(null,
			    text,
			    "Quantum Wolves",
			    JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void displayError(String message){
		JOptionPane.showMessageDialog(null,
			    message,
			    "Quantum Wolves",
			    JOptionPane.ERROR_MESSAGE);
	}

}

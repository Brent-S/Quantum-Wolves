package wolves;

// Inspired by http://puzzle.cisra.com.au/2008/quantumwerewolf.html

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RunFileGame {
	
	private static int NumPlayers;
	private static int NumWolves;
	private static Game RunningGame;

	public static void main(String[] args) {

		NumPlayers = InputNumPlayers();
		NumWolves = InputNumWolves();

		RunningGame = new Game(NumPlayers,NumWolves);
		// Game Object created, initialised, and probabilities updated.

		boolean GameOver = false;

		while(!GameOver){
			// Each turn, one must:
			// Take input of visions
			int[] VisionTargets = InputVisionTargets();
			byte[] Visions = RunningGame.HaveVisions(VisionTargets);
			// Get output of visions, and inform players
			OutputVisions(Visions);
			// Update gamestates based on visions
			RunningGame.VisionAllStates(VisionTargets, Visions);
			// Take input of wolf attacks
			int[] WolfTargets = InputWolfTargets();
			// Update gamestates based on attacks
			RunningGame.AttackAllStates(WolfTargets);
			// Wake players
			RunningGame.UpdateProbabilities();
			RunningGame.CollapseAllDead();
			DayTimeDisplay();
			// Take Lynching target
			int LynchTarget = InputLynchTarget();
			// Update gamestates based on lynch
			RunningGame.LynchAllStates(LynchTarget);
			// run CollapseAllDead()
			RunningGame.CollapseAllDead();
			DayTimeDisplay();
			byte WinCode = RunningGame.CheckWin();
			GameOver = (WinCode != 0);
		}
		// Game is now over
		String WinningTeam = (RunningGame.CheckWin() == 1) ? "Innocents" : "Wolves";
		System.out.println("Game Over. The " + WinningTeam + " have won.");
			
	}

	private static int InputNumPlayers(){
		int NumPlayers = 0;
		try{
			BufferedReader ChoicesFile = new BufferedReader(new FileReader("FileGameChoices.txt"));
			String FirstLine = ChoicesFile.readLine();
			String[] PlayerData = FirstLine.split(":");
			NumPlayers = Integer.parseInt(PlayerData[1]);
		} catch (FileNotFoundException e){
			System.out.println("File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Error");
			e.printStackTrace();
		} catch (NumberFormatException e){
			System.out.println("Number Format Error in Player Number");
			e.printStackTrace();
		}
		return NumPlayers;
	}
	
	private static int InputNumWolves(){
		int NumWolves = 0;
		try{
			BufferedReader ChoicesFile = new BufferedReader(new FileReader("FileGameChoices.txt"));
			String FirstLine = ChoicesFile.readLine();
			String[] PlayerData = FirstLine.split(":");
			NumWolves = Integer.parseInt(PlayerData[3]);
		} catch (FileNotFoundException e){
			System.out.println("File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Error");
			e.printStackTrace();
		} catch (NumberFormatException e){
			System.out.println("Number Format Error in Wolf Number");
			e.printStackTrace();
		}
		return NumWolves;
	}
	
	private static int[] InputVisionTargets(){ // return 0 if player cannot be seer.
		return new int[2];
	}
	
	private static int[] InputWolfTargets(){ // return 0 if player cannot be a wolf.
		return new int[2];
	}
	
	private static void OutputVisions(byte[] visions){
		// Display Visions to players.
	}
	
	private static int InputLynchTarget(){ // return playerID for highest voted.
		return 0;
	}
	
	private static void DayTimeDisplay(){ // Must display Good/Evil/Alive/Dead probabilities.
		double[][] Probabilities = RunningGame.getProbabilities();
		double[] LiveProbs = RunningGame.LivingProbabilities();
		double DisplayProbs[][] = new double[NumPlayers][4];
		for(int n = 0; n < NumPlayers; n++){
			DisplayProbs[n][0] = Probabilities[n][0] + Probabilities[n][1] + Probabilities[n][2] + Probabilities[n][3];
			DisplayProbs[n][0] *= 100;
			DisplayProbs[n][1] = 100 - DisplayProbs[n][0];
			DisplayProbs[n][2] = LiveProbs[n];
			DisplayProbs[n][2] *= 100;
			DisplayProbs[n][3] = 100 - DisplayProbs[n][0];
		}
		int[] RolesCodes = RunningGame.getKnownRoles();
		String[] Roles = new String[NumPlayers];
		
		
	}
}

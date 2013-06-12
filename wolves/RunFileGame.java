package wolves;

// Inspired by http://puzzle.cisra.com.au/2008/quantumwerewolf.html


public class RunFileGame {
	
	private static int NumPlayers;
	private static int NumWolves;
	private static Game RunningGame;
	private static ChoiceHistory History;
	private static WolvesUI ui;
	private static boolean DebugMode;

	public static void main(String[] args) {
		
		ui = new TextWolves(); //To be replaced with actual UI

		NumPlayers = ui.getNumPlayers();
		NumWolves = ui.getNumWolves();
		DebugMode = ui.getDebugMode();
		

		RunningGame = new Game(NumPlayers,NumWolves);
		// Game Object created, initialised, and probabilities updated.

		boolean GameOver = false;

		while(!GameOver){
			// Each turn, one must:
			// Take input of visions
			DoVisionsOneByOne();
			
			RunningGame.UpdateProbabilities();
			RunningGame.CollapseAllDead();			
			WinCodes WinCode = RunningGame.CheckWin();
			GameOver = (WinCode != WinCodes.GameNotOver);
			if(GameOver) break;
			if(DebugMode) DisplayAllStates(RunningGame.AllStatesToString());
			
			// Take input of wolf attacks
			int[] WolfTargets = InputWolfTargets();
			// Update gamestates based on attacks
			RunningGame.AttackAllStates(WolfTargets);
			// Wake players
			
			RunningGame.UpdateProbabilities();
			RunningGame.CollapseAllDead();
			DayTimeDisplay();
			WinCode = RunningGame.CheckWin();
			GameOver = (WinCode != WinCodes.GameNotOver);
			if(GameOver) break;
			if(DebugMode) DisplayAllStates(RunningGame.AllStatesToString());
			
			// Take Lynching target
			int LynchTarget = InputLynchTarget();
			// Update gamestates based on lynch
			RunningGame.LynchAllStates(LynchTarget);
			
			RunningGame.UpdateProbabilities();
			// run CollapseAllDead()
			RunningGame.CollapseAllDead();
			DayTimeDisplay();
			WinCode = RunningGame.CheckWin();
			GameOver = (WinCode != WinCodes.GameNotOver);
			if(DebugMode) DisplayAllStates(RunningGame.AllStatesToString());
		}
		// Game is now over
		ui.displayEndGame(RunningGame.getRoundNum(), RunningGame.CheckWin());
		DayTimeDisplay();
		DisplayAllStates(RunningGame.AllStatesToString());
	}
	
	private static void DoVisionsOneByOne(){
		// This will take inputs of visions for each player in turn, and give them their
		// visions immediately.
		boolean[] CanSee = RunningGame.CheckLiveSeers();
		for(int n = 0; n < NumPlayers; n++){
			if(CanSee[n]){
				int Target = InputSingleVisionTarget(n+1);
				byte Vision = RunningGame.HaveSingleVision(n+1,Target);
				OutputSingleVision(n+1,Target,Vision);
				RunningGame.SingleVisionAllStates(n, Target, Vision);
				// History.SaveVision(RunningGame.getRoundNum(), n, Target, Vision);
			}
		}
	}
	
	private static void DisplayAllStates(String AllStateText){
		ui.displayAllStates(AllStateText);
	}
	
	private static int InputSingleVisionTarget(int Seer){
		return ui.inputSeerTarget(Seer);
	}
	
	private static void OutputSingleVision(int Seer, int Target, byte Vision){
		ui.displaySingleVision(Seer, Target, Vision);
	}
	
	private static int[] InputWolfTargets(){ // return 0 if player cannot be a wolf.
		int[] Targets = ui.inputWolfTargets(RunningGame.CheckLiveWolves());
		for(int n = 0; n < Targets.length; n++){
			//History.SaveAttack(RunningGame.getRoundNum(), n + 1, Targets[n]);
		}
		return Targets;
	}
	
	
	private static int InputLynchTarget(){ // return playerID for highest voted.
		int Target = ui.inputLynchTarget();
		//History.SaveLynch(RunningGame.getRoundNum(), Target);
		return Target;
	}
	
	private static void DayTimeDisplay(){ // Must display Good/Evil/Alive/Dead probabilities.
		double[][] Probabilities = RunningGame.getProbabilities();
		double[] LiveProbs = RunningGame.LivingProbabilities();
		double DisplayProbs[][] = new double[NumPlayers][4];
		for(int n = 0; n < NumPlayers; n++){
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
		ui.displayProbabilities(DisplayProbs, RolesCodes);		
	}
}

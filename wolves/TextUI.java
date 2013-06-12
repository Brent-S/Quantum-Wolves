package wolves;


public class TextUI implements WolvesUI {

	@Override
	public int getNumPlayers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumWolves() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] inputWolfTargets(boolean[] CanWolf) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int inputLynchTarget() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void displayProbabilities(double[][] probabilities, int[] knownRoles) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void displayEndGame(int RoundNum, WinCodes WinCode){
		
	}
	@Override
	public void displayAllStates(String AllStateText){
		
	}
	
	@Override
	public int inputSeerTarget(int inSeer){
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void displaySingleVision(int Seer, int Target, byte Vision){
		
	}
	
}
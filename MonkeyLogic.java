/*
 * Class that provides static methods to assist with the main monkey algorithm
 */

public class MonkeyLogic
{
	
	public static int diff(String target, String currentPop){
	
		int difference = 0;
		
		if(target.length() == currentPop.length()){
			
			
			
			for (int i = 0; i < target.length(); i++){
				
			if (target.charAt(i) != currentPop.charAt(i)){
			
				difference ++;
			}
			}

			
		}else{
			throw new RuntimeException("Strings are not equal!");
		}
		
		
		
		
		
		return difference;
	}

	
	
	public static int randomParent(int totalWeight, int[] breedingWeights){
		
		int val = (int) ((Math.random()) * totalWeight);
		
		for (int i = 0; i < breedingWeights.length; i++){
			int weight = breedingWeights[i];
			
			if(val < weight){
				return i;
			}
			
				val = val - weight;
			
					

		}


		
			return -1;
		}
	
	public static int getBestMonkey(int[] scores){ //returns index of current best fitness score
		int currentBest = Generator.getTargetText().length();
		
		for (int i = 0; i < scores.length; i++){
			if(scores[i] < currentBest){
				currentBest = i;
			}
		}
		
		
		
		
		
		return currentBest;
	}



	
}

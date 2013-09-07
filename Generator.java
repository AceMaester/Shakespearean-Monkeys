
/*
 * Class with static methods and variables to assist the MonkeyFrame class
 */








public class Generator
{

	private static int initialPop;
	private static String targetText;
	//private static char[] asciiArray;
	private static int generations;
	
	
	
	
	
	public static char[] generateStrings(){
	
	
		char[] asciiArray = new char[97];
		
		for(int i=0; i<95; i++){
			asciiArray[i] = (char)(i +32);
			
			
			
		}
		asciiArray[95] = (char)10;//newline char
		asciiArray[96] = (char)13;//carraige return char
		
		return asciiArray;
		
		
	}
	
	public static char getRandomChar(){
		
		
		char[] chars = generateStrings();
		int rand = (int) ((Math.random()) * chars.length);
		
		
		
		
		return chars[rand];
		
		
		
		
	}
		
		
		
		
		
		
		//return asciiArray;
	//}

	public static String generateRandomMonkey()
	{
		StringBuilder sb = new StringBuilder(targetText.length());
		char[] asciiArray = generateStrings();

		
		
		for(int i=0; i < targetText.length(); i++){
			//randomMonkey = randomMonkey + asciiArray[(int)(97*Math.random())];
			sb.append(asciiArray[(int)(97*Math.random())]);

			
		}
		
		
		
		
		return sb.toString();
		
	}
	
	public static String[] CreateRandomPopulation(){
		
		String[] initGen = new String[initialPop];
		
		
		
		for(int i=0; i < initialPop; i++){
			initGen[i] = generateRandomMonkey();
			
			
		}
		
		
		
		return initGen;
		
	}
	
	public static int[] GetFitnessScores(String[] pop){
		int[] scores = new int[pop.length];
		
		for(int i=0; i < pop.length; i++){
			scores[i] = MonkeyLogic.diff(targetText, pop[i]);
			//System.out.print(scores[i] + " ");
		}
		
		
		
		
		return scores;
	}
	
	public static int GetTotalWeight(int[] scores){
		
		int sum = 0;
		for(int i=0; i < scores.length; i++){
			sum = sum + scores[i];
			
		}	
		
		
		
		return sum;
		
		
	}
	
	
	
	public static boolean checkTargetText(String text){
		
		
		int current;
		
		if(text.length() < 1 || text.length() > 300){
			
			return false;
		
		}else{
			for(int i=0; i < text.length(); i++){
				
				current = (int)text.charAt(i);
				
				
				if(current > 126 || (current < 32 && !(current == 10 || current == 13) )){
					
					return false;
				}
			}
			
			
			
		}
		
		setTargetText(text);
		
		return true;
		
		
	}
	
	
	public static boolean checkPopulationSize(String size){
		int pop;
		
		try{
			pop = Integer.parseInt(size);
			
			if((pop > 10000 || pop < 1) || pop % 2 == 1){
				return false;
			}
			
			
			
		}catch(NumberFormatException e){
			
			return false;
		}
		
		setInitialPop(pop);
		
		return true;
		
	}

	
	//GETTERS AND SETTERS
	public static int getInitialPop() {
		return initialPop;
	}

	public static void setInitialPop(int initialPop) {
		Generator.initialPop = initialPop;
	}

	public static String getTargetText() {
		return targetText;
	}

	public static void setTargetText(String targetText) {
		Generator.targetText = targetText;
	}






	public static int[] GetBreedingWeights(int[] currentScores) {
		
		int largest = currentScores[0];
		int[] breedingWeights = new int[currentScores.length];
		
		for(int i=1; i < currentScores.length; i++){
			if(currentScores[i] > largest){
				largest = currentScores[i];
			}
			
		}
		
		for(int i=0; i < currentScores.length; i++){
			breedingWeights[i] = largest - currentScores[i] + 1;
			
		}
		
		
		
		return breedingWeights;
	}

	public static int getGenerations() {
		return generations;
	}

	public static void resetGenerations() {
		Generator.generations = 0;
	}
	
	public static void incrementGenerations() {
		generations++;
	}

	
	
	
	

}

package hw1;

import java.util.Arrays;

public class Evaluate {
	
	public static void main (String[] args) {
		
		/*
		float[][] pathresults = spfAStarComp();
		
		for (int i = 0; i < pathresults.length; i++) {
			System.out.println(Arrays.toString(pathresults[i]));
		}
		*/
		
		float[][] puzzleresults = hillClimbingPopulationComp();
		for (int i = 0; i < puzzleresults.length; i++) {
			System.out.println(Arrays.toString(puzzleresults[i]));
		}
		
	}

	
	public static float[][] spfAStarComp() {
		
		//create array for n, value, spf runtime, a* runtime
		float[][] results = new float[200][4];
		int record = 0;
		int eval = 0;
		
		for (int n = 5; n <= 11; n += 2) {
			
			//generate puzzle of size n, store n
			
			Node workingGrid[][] = new Node[n][n];
			
			for (int test = 0; test < 50; test++) {
				
				//generate random grid until it is valid
				do {
					main.populate(workingGrid, n);
					eval = main.evaluate(workingGrid, n);
				} while (eval < 0);
				
				//store n, store value
				results[record][0] = n;	
				results[record][1] = eval;
				
				//pass puzzle to spf, store spf runtime
				long start = System.nanoTime();
				main.spf(workingGrid, n);
				long end = System.nanoTime();
				results[record][2] = end - start;
				
				main.setHeuristic(workingGrid, n);
				
				//pass puzzle to a*, store a* runtime
				start = System.nanoTime();
				main.aSearch(workingGrid, n);
				end = System.nanoTime();
				results[record][3] = end - start;
				
				record++;
			}
		}
		
		return results;
	}
	
	public static float[][] hillClimbingPopulationComp() {
		
		//create array for n, value, spf runtime, a* runtime
		float[][] results = new float[200][5];
		int record = 0;
		int eval = 0;
		
		for (int n = 5; n <= 11; n += 2) {
			
			//generate puzzle of size n, store n
			
			Node workingGrid[][] = new Node[n][n];
			
			for (int test = 0; test < 50; test++) {
				
				//generate random grid until it is valid
				do {
					main.populate(workingGrid, n);
					eval = main.evaluate(workingGrid, n);
				} while (eval < 0);
				
				//store n, store value
				results[record][0] = n;	
				results[record][1] = eval; //store original eval
				
				//pass puzzle to hill climbing, store runtime
				long start = System.currentTimeMillis();
				results[record][3]= main.hillClimbing(workingGrid, n, 100); //store best eval
				long end = System.currentTimeMillis();
				results[record][2] = (end - start); //store runtime
				
						
				//pass puzzle to population, store population runtime
				results[record][4] = main.populationAlg(n, results[record][2]); //store best eval from popalg for same runtime
				record++;
			}
		}
		
		return results;
	}
}

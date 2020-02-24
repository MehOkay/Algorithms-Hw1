package hw1;

import java.util.Arrays;

public class PlotData {

	public static void main (String[] args) {
		
		//hill climbing and population testing
		
		//hillClimbingTesting();
		
		populationTesting();
		
	}
	
	public static float average(int[] input) { //this code from internet
		  float sum = 0f;
		  for (int number : input) {
			  sum = sum + number;
		  }
		  return sum / input.length;
		  
	}
	
	
	public static void hillClimbingTesting() {
		//hillclimbing
				//loop 50 times per n
				//find avg change in optimized value for 50 runs of each iteration
				int eval;
				int optChanges[] = new int[50];
				float averages[] = new float[20];
				int testIter = 10;
				int optEval = 0;
				int n = 5;
				
				
				for (int testN = 5; testN <= 11; testN += 2) {
					n = testN;
					Node workingGrid[][] = new Node[n][n];
					for (testIter = 10; testIter <= 200; testIter += 10) {
						for (int test = 0; test < 50; test++) {
							//generate random grid until it is valid
							do {
								main.populate(workingGrid, n);
								eval = main.evaluate(workingGrid, n);
							} while (eval < 0);
							//do hill climb
							optEval = main.hillClimbing(workingGrid, n, testIter);
							
							//store result
							optChanges[test] = optEval - eval;
						}
						averages[testIter/10 - 1] = average(optChanges);
					}
					System.out.println("n: " +testN+ " averages for 20 values of iter");
					System.out.println(Arrays.toString(averages));
				}
		  
	}
	
	public static void populationTesting() {

				//loop 50 times per n
				//find avg change in optimized value for 50 runs of each iteration

				int optChanges[] = new int[50];
				float averages[] = new float[20];
				double testTime = 0.005;
				
				int n = 5;
				
				
				for (int testN = 5; testN <= 11; testN += 2) {
					n = testN;
					for (testTime = 0.1; testTime <= 1.1; testTime += 0.1 ) {
						System.out.println("\n" +"testing:" + testTime +"\n");
						for (int test = 0; test < 50; test++) {
							//store result
							System.out.print("w");
							optChanges[test] = main.populationAlg(n, testTime);
						}
						averages[(int)Math.round(testTime*10)] = average(optChanges);
					}
					System.out.println("n: " +testN+ " averages for 20 values of runtime");
					System.out.println(Arrays.toString(averages));
				}
		  
	
	}
	
}




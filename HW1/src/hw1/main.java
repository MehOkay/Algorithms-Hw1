package hw1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Random;

public class main { 
	
	//Fills grid with legal moves according to N
	public static void populate(Node grid[][], int n) {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				//System.out.println(i + " " + j);
				boolean valid = false;
				int move = 0;
				
				//if(i == n-1 && j == n-1)
					//break;
				
				//repeats until cell is filled with a valid move
				while (!valid) {
					int validMoves = 0;
					
					//random number between 0 to n-1
					move = (int) (Math.random() * n) + 1; 
					//System.out.print(" " + i + " " + j + " " + move + " ");
					//if the cell can move to the right
					if (i + move < n)
						validMoves++;
					//if the cell can move down
					if (j + move < n)
						validMoves++;
					//if the cell can move left
					if (i - move >= 0)
						validMoves++;
					//if the cell can move up
					if (j - move >= 0)
						validMoves++;
					//if there are can valid moves
					if (validMoves > 0)
						valid = true; 
				}
				//fill cell with final Node
				grid[i][j] = new Node(i,j,move);
			}
			grid[n-1][n-1] = new Node(n-1,n-1,0);
		}
	}	
	//creates 2d array
	//each cell contains lowest number of moves to get to that cell
	//returns value for evaluate function
	public static int evaluate(Node grid[][], int n) {
		Queue<Node> q = new LinkedList<Node>();
		
		grid[0][0].count = 0;
		q.add(grid[0][0]);
	
		
		//BFS starting from first cell
		while(!q.isEmpty()) {
			Node cell = q.remove();
			int x = cell.x;
			int y = cell.y; 
			int newCount = grid[x][y].count + 1;
			int move = cell.move;
			
			
			if(!cell.visited) {
				cell.visited = true;
				
				//adds the cells to the queue
				//check right
				if(x + move < n && !grid[x + move][y].visited) {
					q.add(grid[x + move][y]);
					//if target cell's count is greater than current count or is empty 
					if(grid[x + move][y].count > newCount || grid[x + move][y].count == -1) {
						grid[x + move][y].count = newCount;
						grid[x + move][y].touchedBy = cell;
					}
					
				}
				//check down
				if(y + move < n && !grid[x][y + move].visited) {
					q.add(grid[x][y + move]);
					//if target cell's count is greater than current count or is empty 
					if(grid[x][y + move].count > newCount || grid[x][y + move].count == -1) {
						grid[x][y + move].count = newCount;
						grid[x][y + move].touchedBy = cell;
					}
				}
				//check left
				if(x - move >= 0 && !grid[x - move][y].visited) { 
					q.add(grid[x - move][y]);
					//if target cell's count is greater than current count or is empty 
					if(grid[x - move][y].count > newCount || grid[x - move][y].count == -1) {
						grid[x - move][y].count = newCount;
						grid[x - move][y].touchedBy = cell;
					}
				}
				//check up
				if((y - move >= 0) && !grid[x][y - move].visited) {
					q.add(grid[x][y - move]);
					//if target cell's count is greater than current count or is empty 
					if(grid[x][y - move].count > newCount || grid[x][y - move].count == -1) {
						grid[x][y - move].count = newCount;
						grid[x][y - move].touchedBy = cell;
					}
				}
					
				
			}
		}
		if (grid[n-1][n-1].count > 0) {
			return grid[n-1][n-1].count;
		}
		else {
			int unreachables = 0;
			for (int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					if (grid[i][j].count < 0) {
						unreachables--;
					}
				}
			}
			return unreachables;
		}
	}
	
	public static int hillClimbing(Node grid[][], int n, int iter) {
		//for number of iterations, change a random move value, evaluate grid, and save the better grid
		
		
		Node oldGrid[][] = grid.clone();
		Node tempGrid[][];
		Random rand = new Random();
		int oldEval = 0;
		int newEval = 0;
		long start = System.currentTimeMillis();
		
		//loop that iterates for input number of iterations
		for (int i = 0; i < iter; i++) {
			
			//choosing random place on puzzle, if it's goal piece runs again
			int randX = 0;
			int randY = 0;
			do {
				randX = rand.nextInt(n-1);
				randY = rand.nextInt(n-1);
			}
			while(randX == randY && randX == n-1);
			
			//assign new valid move to random place on puzzle
			boolean valid = false;
			int newMove = 1;
			
			while (!valid) {
				int validMoves = 0;
				
				//random number between 0 to n-1
				newMove = (int) (Math.random() * n) + 1; 
				
				//if the cell can move to the right
				if (randX + newMove < n)
					validMoves++;
				//if the cell can move down
				if (randY + newMove < n)
					validMoves++;
				//if the cell can move left
				if (randX - newMove >= 0)
					validMoves++;
				//if the cell can move up
				if (randY - newMove >= 0)
					validMoves++;
				//if there are valid moves
				if (validMoves > 0)
					valid = true; 
			}
			
			//save new move in puzzle
			tempGrid = oldGrid.clone();
			tempGrid[randX][randY].move = newMove;
			
			//clear cell.visited for both grids so evaluate actually runs
			for (int p = 0; p < n; p++) {
				for(int q = 0; q < n; q++) {
					tempGrid[p][q].visited = false;
					oldGrid[p][q].visited = false;
				}
			}
			
			//evaluate old puzzle
			oldEval = evaluate(oldGrid, n);
			//evaluate new puzzle
			newEval = evaluate(tempGrid, n);
			//compare new evaluation to old evaluation and save if new better than old
			if (newEval > oldEval) {
				oldGrid = tempGrid.clone();
				oldEval = newEval;
			}
			
		}
		long end = System.currentTimeMillis();
		
		//print new grid, value, and time to compute
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.print(oldGrid[i][j].move + " ");
			}
			System.out.println();
		}
		System.out.println("Optimized value: " + oldEval);
		float sec = (end - start) / 1000F; 
		System.out.println("Compute time: " + sec + " seconds");
		
		return oldEval;
	}
	
	public static void printOptimalPath(Node grid[][], int goalX, int goalY) {
		LinkedList <Node> optPath = new LinkedList <Node>();
		if (grid[goalX][goalY].count <= 0) {
			return;
		}
		int size = grid.length - 1;
		if (goalX <= size && goalY <= size) {
			optPath.addFirst(grid[goalX][goalY]);
		}
		
		//add path to linked list
		while (optPath.peekFirst() != null) {
			if (optPath.peekFirst().touchedBy != null) {
				optPath.addFirst(optPath.getFirst().touchedBy);
				
			}
			else {
				break;
			}
		}
		
		//print ll
		System.out.println("Optimal Path:");
		int step = 0;
		while (!optPath.isEmpty()) {
			System.out.println("Step " + step + ": "+optPath.getFirst().x +", "+ optPath.getFirst().y);
			optPath.removeFirst();
			step++;
		}
			
		return;
	}
	
	private static void setHeuristic(Node[][] grid, int n) {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				grid[i][j].heuristic = grid[n-1][n-1].count - grid[i][j].count;
			}
		}
	}
	
	
	
	
	public static void populationAlg(Node grid[][]) {
		//create random population of size p
		
		//evaluate each puzzle
		
		//choose p/2 best puzzles -- selection
		
		//while current best is not solution
			
		//add p/2 random puzzles and loop
		
	}
	
	//exits after finding goal
	public static int aSearch(Node[][] grid, int n) {
		
		Comparator<Node> nodeComparator = new Comparator<Node>() {
			@Override
			public int compare(Node node1, Node node2) {
				if(node1.heuristic < node2.heuristic)
					return 1;
				else if (node1.heuristic > node1.heuristic)
					return -1;
				return 0;
			}
		};

		// reset grid
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j].visited = false;
			}
		}

		PriorityQueue<Node> h = new PriorityQueue<>(nodeComparator);
		grid[0][0].hCount = 0;
		
		h.add(grid[0][0]);

		//A* Search from first cell
		while (!h.isEmpty()) {
			Node cell = h.remove();
			int x = cell.x;
			int y = cell.y;
			int move = cell.move;
			int newCount = grid[x][y].hCount + 1;

			if (!cell.visited) {
				cell.visited = true;

				if (x + move < n && !grid[x + move][y].visited) {
					h.add(grid[x + move][y]);
					if (grid[x + move][y].hCount > newCount || grid[x + move][y].hCount == -1) {
						grid[x + move][y].hCount = newCount;
						grid[x + move][y].hTouchedBy = cell;
					}
				}

				if (y + move < n && !grid[x][y + move].visited) {
					h.add(grid[x][y + move]);
					if (grid[x][y + move].hCount > newCount || grid[x][y + move].hCount == -1) {
						grid[x][y + move].hCount = newCount;
						grid[x][y + move].hTouchedBy = cell;
					}
				}

				if (x - move >= 0 && !grid[x - move][y].visited) {
					h.add(grid[x - move][y]);
					if (grid[x - move][y].hCount > newCount || grid[x - move][y].hCount == -1) {
						grid[x - move][y].hCount = newCount;
						grid[x - move][y].hTouchedBy = cell;
					}
				}

				if (y - move >= 0 && !grid[x][y - move].visited) {
					h.add(grid[x][y - move]);
					if (grid[x][y - move].hCount > newCount || grid[x][y - move].hCount == -1) {
						grid[x][y - move].hCount = newCount;
						grid[x][y - move].hTouchedBy = cell;
					}
				}
				
				if(x == n-1 && y == n-1)
					return 0;
			}
		}
		return 0;
	}
	//call methods for each task here
	public static void main(String args[]) {
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a number for n: ");
		int n = reader.nextInt(); // Scans the next token of the input as an int.
		
		
		//initialize grid
		Node grid[][] = new Node[n][n];
		
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				grid[i][j] = new Node();
				grid[i][j].count = -1;
			}
		}
		//give grid new values
		populate(grid, n);
		
		//check if grid has a solvable path
		System.out.println("Value: " + evaluate(grid, n));
		
		System.out.println();
		//print out grid and 2d array of paths
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.print(grid[i][j].move + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		//print BFS solution
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if (grid[i][j].count == -1) {
					System.out.print("X ");
				}
				else {
				System.out.print(grid[i][j].count + " ");
				}
			}
			System.out.println();
		}
		
		setHeuristic(grid,n);
		aSearch(grid,n);
		
		System.out.println("a*\n");
		//print A* solution
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if (grid[i][j].hCount == -1) {
					System.out.print("X ");
				}
				else {
				System.out.print(grid[i][j].hCount + " ");
				}
			}
			System.out.println("");
		}
		
		//print optimum path
		printOptimalPath(grid, n-1, n-1);
		
		//hill climbing
		System.out.println("Enter a number for iterations: ");
		int iter = reader.nextInt(); // Scans the next token of the input as an int.
		
		hillClimbing(grid, n, iter);
		
		
		//once finished
		reader.close();
	}
	
}

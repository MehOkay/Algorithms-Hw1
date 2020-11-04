package hw1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Random;

public class main {

	// Fills grid with legal moves according to N
	public static void populate(Node grid[][], int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				// System.out.println(i + " " + j);
				boolean valid = false;
				int move = 0;

				// if(i == n-1 && j == n-1)
				// break;

				// repeats until cell is filled with a valid move
				while (!valid) {
					int validMoves = 0;

					// random number between 0 to n-1
					move = (int) (Math.random() * n) + 1;
					// System.out.print(" " + i + " " + j + " " + move + " ");
					// if the cell can move to the right
					if (i + move < n)
						validMoves++;
					// if the cell can move down
					if (j + move < n)
						validMoves++;
					// if the cell can move left
					if (i - move >= 0)
						validMoves++;
					// if the cell can move up
					if (j - move >= 0)
						validMoves++;
					// if there are can valid moves
					if (validMoves > 0)
						valid = true;
				}
				// fill cell with final Node
				grid[i][j] = new Node(i, j, move);
			}
			grid[n - 1][n - 1] = new Node(n - 1, n - 1, 0);
		}
	}

	// creates 2d array
	// each cell contains lowest number of moves to get to that cell
	// returns value for evaluate function
	public static int evaluate(Node grid[][], int n) {
		Queue<Node> q = new LinkedList<Node>();
		Queue<Node> path = new LinkedList<Node>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[i][j].visited = false;
			}
		}
		grid[0][0].count = 0;
		q.add(grid[0][0]);
		// BFS starting from first cell
		while (!q.isEmpty()) {
			Node cell = q.remove();
			int x = cell.x;
			int y = cell.y;
			int newCount = grid[x][y].count + 1;
			int move = cell.move;

			if (!cell.visited) {
				cell.visited = true;

				// adds the cells to the queue
				// check right
				if (x + move < n && !grid[x + move][y].visited) {
					q.add(grid[x + move][y]);
					// if target cell's count is greater than current count or is empty
					if (grid[x + move][y].count > newCount || grid[x + move][y].count == -1) {
						grid[x + move][y].count = newCount;
						grid[x + move][y].touchedBy = cell;
						if (x + move == n - 1 && y == n - 1) {
							Node pCell = cell;
							while (pCell.touchedBy != null) {
								path.add(pCell);
								pCell = pCell.touchedBy;
							}

							int pathLength = path.size() + 1;
							while (!path.isEmpty()) {
								pCell = path.remove();
								if (grid[pCell.x][pCell.y].pathLength == -1
										|| grid[pCell.x][pCell.y].pathLength > pathLength)
									grid[pCell.x][pCell.y].pathLength = pathLength;
							}
							grid[n - 1][n - 1].pathLength = pathLength;
						}

					}
				}
				// check down
				if (y + move < n && !grid[x][y + move].visited) {
					q.add(grid[x][y + move]);
					// if target cell's count is greater than current count or is empty
					if (grid[x][y + move].count > newCount || grid[x][y + move].count == -1) {
						grid[x][y + move].count = newCount;
						grid[x][y + move].touchedBy = cell;
						if (x == n - 1 && y + move == n - 1) {
							Node pCell = cell;
							while (pCell.touchedBy != null) {
								path.add(pCell);
								pCell = pCell.touchedBy;
							}

							int pathLength = path.size() + 1;
							while (!path.isEmpty()) {
								pCell = path.remove();
								if (grid[pCell.x][pCell.y].pathLength == -1
										|| grid[pCell.x][pCell.y].pathLength > pathLength)
									grid[pCell.x][pCell.y].pathLength = pathLength;
							}
							grid[n - 1][n - 1].pathLength = pathLength;
						}
					}

				}
				// check left
				if (x - move >= 0 && !grid[x - move][y].visited) {
					q.add(grid[x - move][y]);
					// if target cell's count is greater than current count or is empty
					if (grid[x - move][y].count > newCount || grid[x - move][y].count == -1) {
						grid[x - move][y].count = newCount;
						grid[x - move][y].touchedBy = cell;
						if (x - move == n - 1 && y == n - 1) {
							Node pCell = cell;
							while (pCell.touchedBy != null) {
								path.add(pCell);
								pCell = pCell.touchedBy;
							}

							int pathLength = path.size() + 1;
							while (!path.isEmpty()) {
								pCell = path.remove();
								if (grid[pCell.x][pCell.y].pathLength == -1
										|| grid[pCell.x][pCell.y].pathLength > pathLength)
									grid[pCell.x][pCell.y].pathLength = pathLength;
							}
							grid[n - 1][n - 1].pathLength = pathLength;
						}
					}
				}
				// check up
				if ((y - move >= 0) && !grid[x][y - move].visited) {
					q.add(grid[x][y - move]);
					// if target cell's count is greater than current count or is empty
					if (grid[x][y - move].count > newCount || grid[x][y - move].count == -1) {
						grid[x][y - move].count = newCount;
						grid[x][y - move].touchedBy = cell;
						if (x == n - 1 && y - move == n - 1) {
							Node pCell = cell;
							while (pCell.touchedBy != null) {
								path.add(pCell);
								pCell = pCell.touchedBy;
							}

							int pathLength = path.size() + 1;
							while (!path.isEmpty()) {
								pCell = path.remove();
								if (grid[pCell.x][pCell.y].pathLength == -1
										|| grid[pCell.x][pCell.y].pathLength > pathLength)
									grid[pCell.x][pCell.y].pathLength = pathLength;
							}
							grid[n - 1][n - 1].pathLength = pathLength;
						}
					}
				}
			}
		}
		if (grid[n - 1][n - 1].count > 0) {
			return grid[n - 1][n - 1].count;
		} else {
			int unreachables = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (grid[i][j].count < 0) {
						unreachables--;
					}
				}
			}
			return unreachables;
		}
	}

	public static int hillClimbing(Node grid[][], int n, int iter) {
		// for number of iterations, change a random move value, evaluate grid, and save
		// the better grid

		Node oldGrid[][] = new Node[n][n];
		Node tempGrid[][] = new Node[n][n];

		Random rand = new Random();
		int oldEval = 0;
		int newEval = 0;
		for (int q = 0; q < n; q++) {
			for (int w = 0; w < n; w++) {
				oldGrid[q][w] = new Node(q, w, grid[q][w].move);
			}
		}
		long start = System.currentTimeMillis();

		// loop that iterates for input number of iterations
		for (int i = 0; i < iter; i++) {

			// choosing random place on puzzle, if it's goal piece runs again
			int randX = 0;
			int randY = 0;
			do {
				randX = rand.nextInt(n - 1);
				randY = rand.nextInt(n - 1);
			} while (randX == randY && randX == n - 1);

			// assign new valid move to random place on puzzle
			boolean valid = false;
			int newMove = 1;

			while (!valid) {
				int validMoves = 0;

				// random number between 0 to n-1
				newMove = (int) (Math.random() * n) + 1;

				// if the cell can move to the right
				if (randX + newMove < n)
					validMoves++;
				// if the cell can move down
				if (randY + newMove < n)
					validMoves++;
				// if the cell can move left
				if (randX - newMove >= 0)
					validMoves++;
				// if the cell can move up
				if (randY - newMove >= 0)
					validMoves++;
				// if there are valid moves
				if (validMoves > 0)
					valid = true;
			}

			// create new temp to save move, clear visited for grids
			for (int q = 0; q < n; q++) {
				for (int w = 0; w < n; w++) {
					oldGrid[q][w] = new Node(q, w, oldGrid[q][w].move);
					tempGrid[q][w] = new Node(q, w, oldGrid[q][w].move);
				}
			}

			// save new move
			tempGrid[randX][randY].move = newMove;

			// evaluate old puzzle
			oldEval = evaluate(oldGrid, n);
			// evaluate new puzzle
			newEval = evaluate(tempGrid, n);

			// compare new evaluation to old evaluation and save if new better than old
			if (newEval >= oldEval) {
				oldGrid[randX][randY].move = newMove;

			}

		}
		long end = System.currentTimeMillis();

		/*
		 * //print new grid, value, and time to compute for (int i = 0; i < n; i++) {
		 * for(int j = 0; j < n; j++) { System.out.print(oldGrid[i][j].move + " "); }
		 * System.out.println(); } System.out.println("Optimized value: " + oldEval);
		 * float sec = (end - start) / 1000F; System.out.println("Compute time: " + sec
		 * + " seconds");
		 */
		return oldEval;
	}

	public static void printOptimalPath(Node grid[][], int goalX, int goalY) {
		LinkedList<Node> optPath = new LinkedList<Node>();
		if (grid[goalX][goalY].count <= 0) {
			return;
		}
		int size = grid.length - 1;
		if (goalX <= size && goalY <= size) {
			optPath.addFirst(grid[goalX][goalY]);
		}

		// add path to linked list
		while (optPath.peekFirst() != null) {
			if (optPath.peekFirst().touchedBy != null) {
				optPath.addFirst(optPath.getFirst().touchedBy);

			} else {
				break;
			}
		}

		// print ll
		System.out.println("Optimal Path:");
		int step = 0;
		while (!optPath.isEmpty()) {
			System.out.println("Step " + step + ": " + optPath.getFirst().x + ", " + optPath.getFirst().y);
			optPath.removeFirst();
			step++;
		}

		return;
	}

	public static void setHeuristic(Node[][] grid, int n) {
		// reached goal: return path length
		// reset grid
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j].visited = false;
			}
		}

		// setPath(grid, n, 0, 0, 0);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j].pathLength != -1)
					grid[i][j].heuristic = grid[i][j].pathLength - grid[i][j].count;
			}
		}
	}

	public static int getIndexOfLargest(int[] array) // this method from stackoverflow
	{
		if (array == null || array.length == 0)
			return -1; // null or empty

		int largest = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > array[largest])
				largest = i;
		}
		return largest; // position of the first largest found
	}

	public static int populationAlg(int n, double compTime) {

		int iter = 1;
		int first = 0;
		int second = 0;
		int initValue = 0;
		int optChange = 0;

		Node bestGrid[][] = new Node[n][n];
		int allEvals[] = new int[4];
		int bestEval = 0;

		Node popGrids[][][] = new Node[4][n][n];
		Node evolveGrids[][][] = new Node[4][n][n];

		// create random population of size 4
		for (int i = 0; i < 4; i++) {
			populate(popGrids[i], n);
		}

		long start = System.currentTimeMillis();

		do {

			// evaluate each puzzle
			for (int i = 0; i < 4; i++) {
				allEvals[i] = evaluate(popGrids[i], n);
			}

			// choose 2 best puzzles -- selection
			first = getIndexOfLargest(allEvals);
			bestEval = allEvals[first];
			if (iter == 1) {
				initValue = bestEval;
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					bestGrid[i][j] = popGrids[first][i][j];
					bestGrid[i][j].visited = false;
				}
			}
			allEvals[first] = 0;
			second = getIndexOfLargest(allEvals);

			// create 2 more puzzles with each half of the 2 best puzzles and put them all
			// in evolveGrids

			// put first in evolveGrids[0]
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					evolveGrids[0][i][j] = popGrids[first][i][j];
					evolveGrids[0][i][j].visited = false;
				}
			}
			// put second in evolveGrids[1]
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					evolveGrids[1][i][j] = popGrids[second][i][j];
					evolveGrids[1][i][j].visited = false;
				}
			}
			// put first crossover in evolveGrids[2]
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i < n / 2) {
						evolveGrids[2][i][j] = popGrids[first][i][j];
					} else {
						evolveGrids[2][i][j] = popGrids[second][i][j];
					}
					evolveGrids[2][i][j].visited = false;

				}
			}
			// put second crossover in evolveGrids[3]
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i < n / 2) {
						evolveGrids[3][i][j] = popGrids[second][i][j];
					} else {
						evolveGrids[3][i][j] = popGrids[first][i][j];
					}
					evolveGrids[3][i][j].visited = false;
				}
			}

			// evaluate all puzzles
			for (int i = 0; i < 4; i++) {
				allEvals[i] = evaluate(evolveGrids[i], n);
			}

			// choose 2 best puzzles -- selection
			first = getIndexOfLargest(allEvals);
			bestEval = allEvals[first];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					bestGrid[i][j] = evolveGrids[first][i][j];
					bestGrid[i][j].visited = false;
				}
			}
			allEvals[first] = 0;
			second = getIndexOfLargest(allEvals);

			// add p/2 random puzzles and loop
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < 4; k++) {
						if (k == 0) {
							popGrids[k][i][j] = evolveGrids[first][i][j];
						} else if (k == 1) {
							popGrids[k][i][j] = evolveGrids[second][i][j];
						} else {
							populate(popGrids[k], n);
						}
						popGrids[k][i][j].visited = false;
					}
				}
			}

			iter++;

		} while (System.currentTimeMillis() - start < compTime * 1000F);

		optChange = bestEval - initValue;
		return bestEval;

	}

	public static int spf(Node[][] grid, int n) {
		// reset grid
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j].visited = false;
			}
		}

		Queue<Node> q = new LinkedList<Node>();
		grid[0][0].sCount = 0;
		q.add(grid[0][0]);

		// BFS starting from first cell
		while (!q.isEmpty()) {
			Node cell = q.remove();
			int x = cell.x;
			int y = cell.y;
			int newCount = grid[x][y].sCount + 1;
			int move = cell.move;

			if (!cell.visited) {
				cell.visited = true;

				// adds the cells to the queue
				// check right
				if (x + move < n && !grid[x + move][y].visited) {
					q.add(grid[x + move][y]);
					// if target cell's count is greater than current count or is empty
					if (grid[x + move][y].sCount > newCount || grid[x + move][y].sCount == -1) {
						grid[x + move][y].sCount = newCount;
						grid[x + move][y].touchedBy = cell;
					}

				}
				// check down
				if (y + move < n && !grid[x][y + move].visited) {
					q.add(grid[x][y + move]);
					// if target cell's count is greater than current count or is empty
					if (grid[x][y + move].sCount > newCount || grid[x][y + move].sCount == -1) {
						grid[x][y + move].sCount = newCount;
						grid[x][y + move].touchedBy = cell;
					}
				}
				// check left
				if (x - move >= 0 && !grid[x - move][y].visited) {
					q.add(grid[x - move][y]);
					// if target cell's count is greater than current count or is empty
					if (grid[x - move][y].sCount > newCount || grid[x - move][y].sCount == -1) {
						grid[x - move][y].sCount = newCount;
						grid[x - move][y].touchedBy = cell;
					}
				}
				// check up
				if (y - move >= 0 && !grid[x][y - move].visited) {
					q.add(grid[x][y - move]);
					// if target cell's count is greater than current count or is empty
					if (grid[x][y - move].sCount > newCount || grid[x][y - move].sCount == -1) {
						grid[x][y - move].sCount = newCount;
						grid[x][y - move].touchedBy = cell;
					}
				}

				// Reached Goal
				if (x == n - 1 && y == n - 1)
					return 0;
			}

		}
		return 0;
	}

	// exits after finding goal
	public static int aSearch(Node[][] grid, int n) {

		Comparator<Node> nodeComparator = new Comparator<Node>() {
			@Override
			public int compare(Node node1, Node node2) {
				if (node1.heuristic != -1 && node2.heuristic == -1)
					return -1;
				else if (node1.heuristic == -1 && node2.heuristic != -1)
					return 1;
				else if (node1.heuristic < node2.heuristic)
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

		// A* Search from first cell
		while (!h.isEmpty()) {
			Node cell = h.remove();
			int x = cell.x;
			int y = cell.y;
			int newCount = grid[x][y].hCount + 1;
			int move = cell.move;

			if (!cell.visited) {
				cell.visited = true;
				if (x + move < n && !grid[x + move][y].visited) {
					h.add(grid[x + move][y]);
					if (grid[x + move][y].hCount > newCount || grid[x + move][y].hCount == -1) {
						grid[x + move][y].hCount = newCount;
					}
				}

				if (y + move < n && !grid[x][y + move].visited) {
					h.add(grid[x][y + move]);
					if (grid[x][y + move].hCount > newCount || grid[x][y + move].hCount == -1) {
						grid[x][y + move].hCount = newCount;
					}
				}

				if (x - move >= 0 && !grid[x - move][y].visited) {
					h.add(grid[x - move][y]);
					if (grid[x - move][y].hCount > newCount || grid[x - move][y].hCount == -1) {
						grid[x - move][y].hCount = newCount;
					}
				}

				if (y - move >= 0 && !grid[x][y - move].visited) {
					h.add(grid[x][y - move]);
					if (grid[x][y - move].hCount > newCount || grid[x][y - move].hCount == -1) {
						grid[x][y - move].hCount = newCount;
					}
				}

				// Reached Goal
				if (x == n - 1 && y == n - 1)
					return 0;
			}
		}
		return 0;
	}

	// call methods for each task here
	public static void main(String args[]) {

		Scanner reader = new Scanner(System.in); // Reading from System.in
		System.out.println("Enter a number for n: ");
		int n = reader.nextInt(); // Scans the next token of the input as an int.

		
		
		// initialize grid
		Node workingGrid[][] = new Node[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				workingGrid[i][j] = new Node();
				workingGrid[i][j].count = -1;
			}
		}
		// give grid new values
		populate(workingGrid, n);
		
		System.out.println("Gameboard");
		// print out grid and 2d array of paths
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(workingGrid[i][j].move + " ");
			}
			System.out.println();
		}
		// check if grid has a solvable path
		System.out.println("Value: " + evaluate(workingGrid, n));

		System.out.println("Evaluation grid");
		// print Evaluation
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (workingGrid[i][j].count == -1) {
					System.out.print("X ");
				} else {
					System.out.print(workingGrid[i][j].count + " ");
				}
			}
			System.out.println();
		}
		
		/*

		long start = System.nanoTime();
		spf(workingGrid, n);
		long end = System.nanoTime();
		System.out.println("SPF: " + (end - start) + " nanoseconds");
		
		System.out.println("SPF");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (workingGrid[i][j].sCount == -1) {
					System.out.print("X ");
				} else {
					System.out.print(workingGrid[i][j].sCount + " ");
				}
			}
			System.out.println("");
		}

		setHeuristic(workingGrid, n);

		System.out.println("Heuristic Grid");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (workingGrid[i][j].heuristic == -1) {
					System.out.print("X ");
				} else {
					System.out.print(workingGrid[i][j].heuristic + " ");
				}
			}
			System.out.println("");
		}

		start = System.nanoTime();
		aSearch(workingGrid, n);
		end = System.nanoTime();
		System.out.println("A*: " + (end - start) + " nanoseconds");

		// print A* solution
		System.out.println("a*");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (workingGrid[i][j].hCount == -1) {
					System.out.print("X ");
				} else {
					System.out.print(workingGrid[i][j].hCount + " " );
				}
			}
			System.out.println("");
		}

		

*/

		// hill climbing
		System.out.println("Enter a number for iterations: ");
		int iter = reader.nextInt(); // Scans the next token of the input as an int.

		System.out.println("Value: " + hillClimbing(workingGrid, n, iter));
		
		System.out.println("Gameboard");
		// print out grid and 2d array of paths
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(workingGrid[i][j].move + " ");
			}
			System.out.println();
		}
		
		// print optimum path
		printOptimalPath(workingGrid, n - 1, n - 1);


		// once finished
		reader.close();
	}

}

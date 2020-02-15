package hw1;

import java.util.LinkedList;
import java.util.Queue;

public class main {
	
	public static int n = 5;
	
	public static Node grid[][] = new Node[n][n]; 
	public static int solution[][] = new int[n][n];
	
	//Fills grid with legal moves according to N
	public static void populate() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				//System.out.println(i + " " + j);
				boolean valid = false;
				int move = 0;
				
				if(i == n-1 && j == n-1)
					break;
				//repeats until cell is filled with a valid move
				while (!valid) {
					int count = 0;
					
					//random number between 0 to n-1
					move = (int) (Math.random() * n) + 1; 
					//System.out.print(" " + i + " " + j + " " + move + " ");
					//if the cell can move to the right
					if (i + move < n)
						count++;
					//if the cell can move down
					if (j + move < n)
						count++;
					//if the cell can move left
					if (i - move >= 0)
						count++;
					//if the cell can move up
					if (j - move >= 0)
						count++;
					//if there are can valid moves
					if (count > 0)
						valid = true; 
				}
				//fill cell with final Node
				grid[i][j] = new Node(i,j,move);
			}
		}
	}
	
	//creates 2d array
	//each cell contains lowest number of moves to get to that cell
	public static void evaluate() {
		Queue<Node> q = new LinkedList<Node>();
		q.add(grid[0][0]);
		int count = 0;
		solution[0][0] = count;
		
		//BFS starting from first cell
		while(!q.isEmpty()) {
			Node cell = q.remove();
			int x = cell.x;
			int y = cell.y; 
			int move = cell.move;
			
			count++;
			//if BFS has reached the goal
			if(x == n && y == n)
			{
				solution[5][5] = count;
				return;
			}
			
			//if BFS has hit the max number of moves
			if(count == n * n)
				return;
			
			if(!cell.visited) {
				cell.visited = true;
				solution[x][y] = count;
				
				//adds the cells to the queue
				//check right
				if(x + move < n)
					q.add(grid[x + move][y]);
				//check down
				if(y + move < n) 
					q.add(grid[x][y + move]);
				//check left
				if(x - move >= 0) 
					q.add(grid[x - move][y]);
				//check up
				if(y - move >= 0) 
					q.add(grid[x][y - move]);
			}
			//checks if each cell has the lowest count
			else if(count < solution[x][y]) {
				solution[x][y] = count;
			}
		}
	}
	
	//call methods for each task here
	public static void main(String args[]) {
		//initialize grid
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				grid[i][j] = new Node();
			}
		}
		//give grid new values
		populate();
		
		//initialize 2d array
		//-1 is basically X
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++)
				solution[i][j] = -1;
		}
		
		//check if grid has a solvable path
		evaluate();
		
		//print out grid and 2d array of paths
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.print(grid[i][j].move + " ");
			}
			System.out.println();
		}
		System.out.println();
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.print(solution[i][j] + " ");
			}
			System.out.println();
		}
	}
	
}

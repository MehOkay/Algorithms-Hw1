package hw1;

import java.util.LinkedList;
import java.util.Queue;

public class main {
	
	public static int n = 5;
	
	public static Node grid[][] = new Node[n][n]; 
	
	
	//public static int solution[][] = new int[n][n];
	
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
	
	public static void evaluate(Queue<Node> q, int x, int y, int count) {
		
		grid[x][y].count = count;
		Node cell = q.remove();
		int move = grid[x][y].move;
		
		//if BFS has reached the goal
		if(x == n && y == n)
			return;
		
		if(q.isEmpty())
			return;
		
		if(count == n * n) 
			return;
		
		if(!cell.visited) {
			cell.visited = true;
			count++;
			
			//adds the cells to the queue
			//check right
			if(x + move < n && !grid[x + move][y].visited) {
				q.add(grid[x + move][y]);
				evaluate(q, x + move, y, count++);
				
			}
			//check down
			if(y + move < n && !grid[x][y + move].visited) {
				q.add(grid[x][y + move]);
				evaluate(q, x, y + move, count++);
			}
			//check left
			if(x - move >= 0 && !grid[x - move][y].visited) { 
				q.add(grid[x - move][y]);
				evaluate(q, x - move, y, count++);
			}
			//check up
			if((y - move >= 0) && !grid[x][y - move].visited) {
				q.add(grid[x][y - move]);
				evaluate(q, x, y - move, count++);
			}
			
		}
		
	}
	
	//creates 2d array
	//each cell contains lowest number of moves to get to that cell
	public static void evaluate() {
		Queue<Node> q = new LinkedList<Node>();
		//grid[0][0].visited = true;
		grid[0][0].count = 0;
		q.add(grid[0][0]);
		int count = 0;
		
		//BFS starting from first cell
		while(!q.isEmpty()) {
			Node cell = q.remove();
			int x = cell.x;
			int y = cell.y; 
			int move = cell.move;
			
			//if BFS has reached the goal
			if(x == n && y == n)
			{
				grid[n][n].count = count;
				return;
			}
			
			if(count == n * n) {
				return;
			}
			
			if(!cell.visited) {
				cell.visited = true;
				count++;
				
				//adds the cells to the queue
				//check right
				if(x + move < n && !grid[x + move][y].visited) {
					q.add(grid[x + move][y]);
					grid[x + move][y].count = count;
					
				}
				//check down
				if(y + move < n && !grid[x][y + move].visited) {
					q.add(grid[x][y + move]);
					grid[x][y + move].count = count;
				}
				//check left
				if(x - move >= 0 && !grid[x - move][y].visited) { 
					q.add(grid[x - move][y]);
					grid[x - move][y].count = count;
					}
				}
				//check up
				if((y - move >= 0) && !grid[x][y - move].visited) {
					q.add(grid[x][y - move]);
					grid[x][y - move].count = count;
				}
				
			}

	}
	
	//call methods for each task here
	public static void main(String args[]) {
		//initialize grid
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				grid[i][j] = new Node();
				grid[i][j].count = -1;
			}
		}
		//give grid new values
		populate();
		
		Queue<Node> q = new LinkedList<Node>();
		q.add(grid[0][0]);
		
		//check if grid has a solvable path
		evaluate(q,0,0,0);
		
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
				if (grid[i][j].count == -1) {
					System.out.print("X ");
				}
				else {
				System.out.print(grid[i][j].count + " ");
				}
			}
			System.out.println();
		}
	}
	
}

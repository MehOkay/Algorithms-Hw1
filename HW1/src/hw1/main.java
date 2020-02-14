package hw1;

import java.util.LinkedList;
import java.util.Queue;

public class main {
	
	public static Node grid[][] = new Node[5][5]; 
	public static int solution[][] = new int[5][5];
	
	public static int n = 5;
	
	public static void populate() {
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				boolean valid = false;
				int move = 0;
				if(i == 0 && j == 0) {
					grid[0][0] = new Node(0,0,0);
					break;
				}
				if(i == n-1 && j == n-1)
					break;
				while (!valid) {
					int count = 0;
					move = (int) (Math.random() * 5); 
					if (i + move < n)
						count++;
					if (j + move < n)
						count++;
					if (i - move >= 0)
						count++;
					if (i - move >= 0)
						count++;
					if (count > 0)
						valid = true; 
				}
				grid[i][j] = new Node(i,j,move);
			}
		}
	}
	
	public void evaluate() {
		Queue<Node> q = new LinkedList<Node>();
		q.add(grid[0][0]);
		int count = 0;
		solution[0][0] = count;
		
		while(!q.isEmpty()) {
			Node cell = q.remove();
			int x = cell.x;
			int y = cell.y; 
			int move = cell.move;
			
			count++;
			if(x == n && y == n)
			{
				solution[5][5] = count;
				return;
			}
			
			if(count == n * n)
				return;
			
			if(!cell.visited) {
				cell.visited = true;
				solution[x][y] = count;
				if(x + move < n)
					q.add(grid[x+move][y]);
				if(y + move < n) 
					q.add(grid[x][y+move]);
				if(x - move >= 0) 
					q.add(grid[x-move][y]);
				if(y - move >= 0) 
					q.add(grid[x][y-move]);
			}
			else if(count < solution[x][y]) {
				solution[x][y] = count;
			}
		}
	}
	
	//call methods for each task here
	public static void main(String args[]) {
		populate();
		for (int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++)
				solution[i][j] = -1;
		}
	}
	
}

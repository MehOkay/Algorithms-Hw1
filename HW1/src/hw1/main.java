package hw1;

public class main {
	
	public static Node grid[][] = new Node[5][5]; 
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
	
	//call methods for each task here
	public static void main(String args[]) {
		
		populate();
		
		
	}
}

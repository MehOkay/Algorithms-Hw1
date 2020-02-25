package hw1;

public class Node {

	public int x;
	public int y;
	public int move;
	public boolean visited;
	public int count;
	public int heuristic;
	public int hCount;
	public int sCount;
	public int pathLength;
	public Node touchedBy;
	
	public Node() {
		this.x = 0;
		this.y = 0;
		this.move = 0;
		this.visited = false;
		this.count = -1;
		this.heuristic = -1;
		this.hCount = -1;
		this.sCount = -1;
		this.pathLength = -1;
	}
	
	public Node(int x, int y, int move){
		this.x = x;
		this.y = y;
		this.move = move;
		this.visited = false;
		this.count = -1;
		this.heuristic = -1;
		this.hCount = -1;
		this.sCount = -1;
		this.pathLength = -1;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	} 
	
	public int getMove() {
		return this.move;
	}
	
	public boolean getVisited() {
		return this.visited; 
	}
	
}

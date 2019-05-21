import java.util.ArrayList;

public class Main {

	private static ArrayList<Tile> tiles = new ArrayList<Tile>();
	private static ArrayList<Tile> tiles2 = new ArrayList<Tile>();
	private static Board board, goal;
	private static Tile agent;
	private static int n;
	private static double time;

	public static void main(String[] args) {
		
		
		n = 4;
		agent = new Tile(3, 3);
		
		// Initial
		tiles.add(new Tile(3, 0, 1));
		tiles.add(new Tile(3, 1, 2));
		tiles.add(new Tile(3, 2, 3));
		
		board = new Board(n, tiles, agent);
		
		// Goal
		tiles2.add(new Tile(1, 1, 1));
		tiles2.add(new Tile(2, 1, 2));
		tiles2.add(new Tile(3, 1, 3));
		
		goal = new Board(n, tiles2, agent);
		
		// Solver
		Solver solver = new Solver(board, goal);
		
		time = System.nanoTime();
		
		// start search
		solver.BFS();
		solver.DFS();
		solver.IDDFS();
		solver.AStarMan();
		solver.AStarHam();
		
		time = System.nanoTime() - time;
		System.out.println("Time: " + time/1000000000);
	}

}

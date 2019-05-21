import java.util.ArrayList;
import java.util.Arrays;

public class Board {

	private int n;
	private int[][] matrix;
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private Tile agent;

	public Board(int n, int[][] matrix, ArrayList<Tile> tiles, Tile agent) {
		this.n = n;
		this.matrix = matrix;
		this.tiles = tiles;
		this.agent = agent;
		this.update();
	}

	public Board(int n, ArrayList<Tile> tiles, Tile agent) {
		this.n = n;
		matrix = new int[n][n];
		this.tiles = tiles;
		this.agent = agent;
		this.update();
	}

	private void update() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = 0;
			}
		}
		for (Tile t : tiles) {
			matrix[t.getX()][t.getY()] = t.getC();
		}
		matrix[agent.getX()][agent.getY()] = -1;
	}

	public enum Move {
		up, down, left, right
	}

	public void makeMove(Move m) throws Exception {
		int x = agent.getX();
		int y = agent.getY();

		switch (m) {
		case up:
			x--;
			move(x, y);
			break;

		case down:
			x++;
			move(x, y);
			break;

		case left:
			y--;
			move(x, y);
			break;
			
		case right:
			y++;
			move(x, y);
			break;
			
		default:
			break;
		}
	}

	private void move(int x, int y) throws Exception {
		if (x >= 0 && x < n && y >= 0 && y < n) {
			if (matrix[x][y] == 0) {
				matrix[agent.getX()][agent.getY()] = 0;
				matrix[x][y] = -1;

				agent.setX(x);
				agent.setY(y);
			} else {
				int c = matrix[x][y];
				matrix[agent.getX()][agent.getY()] = c;
				matrix[x][y] = -1;

				for (Tile t : tiles) {
					if (t.getX() == x && t.getY() == y) {
						t.setX(agent.getX());
						t.setY(agent.getY());
						break;
					}
				}

				agent.setX(x);
				agent.setY(y);
			}
		} else {
			throw new Exception("Invalid move");
		}
	}

	public int getHam(Board goal) {
		int sum = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (this.matrix[i][j] > 0) {
					if (this.matrix[i][j] != goal.matrix[i][j]) {
						sum++;
					}
				}
			}
		}
		return sum;
	}

	public int getMan(Board goal) {
		int sum = 0;
		for (Tile t1 : this.tiles) {
			for (Tile t2 : goal.tiles) {
				if (t1.getC() == t2.getC()) {
					sum += Math.abs(t1.getX() - t2.getX()) + Math.abs(t1.getY() - t2.getY());
				}
			}
		}
		return sum;
	}

	public Board copy() {
		ArrayList<Tile> tilesCopy = new ArrayList<Tile>();
		for (Tile t : tiles) {
			tilesCopy.add(t.copy());
		}

		int[][] matrixCopy = new int[this.n][this.n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrixCopy[i][j] = this.matrix[i][j];
			}
		}

		Board copy = new Board(this.n, matrixCopy, tilesCopy, agent.copy());
		return copy;
	}

	public boolean isEqual(Board b) {
		if (Arrays.deepEquals(this.matrix, b.matrix)) {
			return true;
		}
		return false;
	}

	public Boolean isGoal(Board board) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board.matrix[i][j] != this.matrix[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public void print() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}

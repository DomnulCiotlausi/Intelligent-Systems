
public class Tile {

	private int x, y;
	private int c;

	public Tile(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setC(-1);
	}

	public Tile(int x, int y, int c) {
		this.setX(x);
		this.setY(y);
		this.setC(c);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public Tile copy() {
		Tile copy = new Tile(x, y, c);
		return copy;
	}
}

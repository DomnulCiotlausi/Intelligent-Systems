
public class SearchNode {
	private Board current;
	private SearchNode parent;
	private double cost;

	public SearchNode(Board b) {
		current = b;
		parent = null;
		cost = 0;
	}

	public SearchNode(SearchNode prev, Board b, double c) {
		parent = prev;
		current = b;
		cost = c;
	}

	public Board getCurrent() {
		return current;
	}

	public SearchNode getParent() {
		return parent;
	}

	public double getCost() {
		return cost;
	}
}

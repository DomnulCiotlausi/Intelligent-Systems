import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.Comparator;

public class Solver {

	public Board board, goal;
	private int margin;

	public Solver(Board board, Board goal) {
		this.board = board;
		this.goal = goal;
		this.margin = 10;
	}

	public ArrayList<Board> genSuccessors(Board board) {
		ArrayList<Board> states = new ArrayList<Board>();

		Board b1 = board.copy();
		Board b2 = board.copy();
		Board b3 = board.copy();
		Board b4 = board.copy();

		try {
			b1.makeMove(Board.Move.up);
			states.add(b1);
		} catch (Exception e) {
		}
		try {
			b2.makeMove(Board.Move.down);
			states.add(b2);
		} catch (Exception e) {
		}
		try {
			b3.makeMove(Board.Move.left);
			states.add(b3);
		} catch (Exception e) {
		}
		try {
			b4.makeMove(Board.Move.right);
			states.add(b4);
		} catch (Exception e) {
		}
		return states;
	}

	public void printSolution(SearchNode solution) {
		Stack<SearchNode> path = new Stack<SearchNode>();

		try {
			do {
				path.push(solution);
				solution = solution.getParent();
			} while (solution.getParent() != null);
			path.push(solution);
		} catch (Exception e) {
			System.out.println("Initial state is equal to the goal state.");
		}

		int loopSize = path.size();
		for (int i = 0; i < loopSize; i++) {
			solution = path.pop();
			solution.getCurrent().print();
		}
		System.out.println("Cost:" + solution.getCost());
	}

	private boolean checkRepeats(SearchNode n) {
		boolean ok = false;
		SearchNode checkNode = n;
		while (n.getParent() != null && !ok) {
			if (n.getParent().getCurrent().isEqual(checkNode.getCurrent())) {
				ok = true;
			}
			n = n.getParent();
		}
		return ok;
	}

	public void DFS() {
		System.out.println("DFS:\n");
		SearchNode root = new SearchNode(board);
		Stack<SearchNode> stack = new Stack<SearchNode>();

		stack.add(root);

		searchDFS(stack);
	}

	private void shuffle(ArrayList<Board> s) {
		long seed = System.nanoTime();
		Collections.shuffle(s, new Random(seed));
	}

	private void searchDFS(Stack<SearchNode> s) {
		int size = -1;
		
		while (!s.isEmpty()) {
			if (s.size() > size){
				size = s.size();
			}
			
			SearchNode tempNode = s.pop();
			if (!tempNode.getCurrent().isGoal(goal)) {
				ArrayList<Board> tempSuccessors = this.genSuccessors(tempNode.getCurrent());
				
				
				this.shuffle(tempSuccessors);

				for (Board b : tempSuccessors) {
					SearchNode newNode = new SearchNode(tempNode, b, tempNode.getCost() + 1);
					if (!checkRepeats(newNode)) {
						s.add(newNode);
					}
				}
			} else {
				printSolution(tempNode);
				System.out.println("Space: " + size);
				break;
			}
		}
	}

	public void BFS() {
		System.out.println("BFS:\n");
		SearchNode root = new SearchNode(board);
		Queue<SearchNode> queue = new LinkedList<SearchNode>();

		queue.add(root);

		searchBFS(queue);
	}

	private void searchBFS(Queue<SearchNode> q) {
		int size = -1;
		ArrayList<Board> visited = new ArrayList<Board>();
		
		while (!q.isEmpty()) {
			if (q.size() > size){
				size = q.size();
			}
			
			SearchNode tempNode = q.poll();
			visited.add(tempNode.getCurrent());
			if (!tempNode.getCurrent().isGoal(goal)) {
				ArrayList<Board> tempSuccessors = this.genSuccessors(tempNode.getCurrent());
				
				for (Board b1: visited){
					for (Board b2: tempSuccessors){
						if (b1.isEqual(b2)){
							tempSuccessors.remove(b2);
							break;
						}
					}
				}

				for (Board b : tempSuccessors) {
					SearchNode newNode = new SearchNode(tempNode, b, tempNode.getCost() + 1);
					if (!checkRepeats(newNode)) {
						q.add(newNode);
					}
				}
			} else {
				printSolution(tempNode);
				System.out.println("Space: " + size);
				break;
			}
		}
	}

	public void IDDFS() {
		System.out.println("IDDFS:\n");
		SearchNode root = new SearchNode(board);
		Stack<SearchNode> stack = new Stack<SearchNode>();

		stack.add(root);

		int limit = 1;
		while (!searchIDDFS(stack, limit)) {
			stack.add(root);
			limit += this.margin;
		}

	}

	private boolean searchIDDFS(Stack<SearchNode> s, int limit) {
		int size = -1;
		
		while (!s.isEmpty()) {
			if (s.size() > size){
				size = s.size();
			}
			
			SearchNode tempNode = s.pop();
			if (tempNode.getCost() <= limit) {

				if (!tempNode.getCurrent().isGoal(goal)) {

					ArrayList<Board> tempSuccessors = this.genSuccessors(tempNode.getCurrent());
					shuffle(tempSuccessors);

					for (Board b : tempSuccessors) {

						SearchNode newNode = new SearchNode(tempNode, b, tempNode.getCost() + 1);
						if (!checkRepeats(newNode)) {
							s.add(newNode);
						}
					}
				} else {
					printSolution(tempNode);
					System.out.println("Space: " + size);
					return true;
				}
			}
		}
		return false;
	}

	public void AStarMan() {
		System.out.println("A* Manhattan:\n");
		SearchNode root = new SearchNode(board);
		Comparator<SearchNode> comparator = new Comparator<SearchNode>() {
			@Override
			public int compare(SearchNode x, SearchNode y) {
				return x.getCurrent().getMan(goal) - y.getCurrent().getMan(goal);
			}
		};
		PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>(comparator);

		pq.add(root);

		searchAStar(pq);
	}

	public void AStarHam() {
		System.out.println("A* Hamming:\n");
		SearchNode root = new SearchNode(board);
		Comparator<SearchNode> comparator = new Comparator<SearchNode>() {
			@Override
			public int compare(SearchNode x, SearchNode y) {
				return x.getCurrent().getHam(goal) - y.getCurrent().getHam(goal);
			}
		};
		PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>(comparator);

		pq.add(root);

		searchAStar(pq);
	}

	private void searchAStar(PriorityQueue<SearchNode> pq) {
		ArrayList<Board> visited = new ArrayList<Board>();
		int size = -1;
		
		while (!pq.isEmpty()) {
			if (pq.size() > size){
				size = pq.size();
			}
			
			SearchNode tempNode = pq.poll();
			visited.add(tempNode.getCurrent());
			if (!tempNode.getCurrent().isGoal(goal)) {
				ArrayList<Board> tempSuccessors = this.genSuccessors(tempNode.getCurrent());

				for (Board b1 : visited) {
					for (Board b2 : tempSuccessors) {
						if (b1.isEqual(b2)) {
							tempSuccessors.remove(b2);
							break;
						}
					}
				}
				for (Board b : tempSuccessors) {
					SearchNode newNode = new SearchNode(tempNode, b, tempNode.getCost() + 1);
					pq.add(newNode);
				}
			} else {
				printSolution(tempNode);
				System.out.println("Space: " + size);
				break;
			}
		}
	}
}

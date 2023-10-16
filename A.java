import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class A {

	int counter = 0;
	PriorityQueue<PuzzleBoard> priorityQueue;

	public PuzzleBoard solve(PuzzleBoard start, PuzzleBoard goalState) {
		priorityQueue = new PriorityQueue<>(new AStarComparator(goalState));
		LinkedList<int[]> visitedNodes = new LinkedList<int[]>();
		priorityQueue.add(start); // Insert the root into the priority queue
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long elapsedTime = 0;

		while (!priorityQueue.isEmpty()) {
			PuzzleBoard dequeuedElement = priorityQueue.poll(); // Dequeue the element with the highest priority
			visitedNodes.add(dequeuedElement.tiles);
			counter++;
			if (PuzzleSolver.DEBUG_MODE) {
				System.out.println(dequeuedElement.toString() + " dequeued.");
				System.out.println("-----------------Visit #" + counter + "-------------------");
			}
			if (Arrays.equals(dequeuedElement.tiles, PuzzleSolver.GOAL_CONFIGURATION)) {
				endTime = System.currentTimeMillis();
				elapsedTime = endTime - startTime;
				System.out.println("[A*] Goal Reached at Visit#: " + counter + " at " + elapsedTime + " milliseconds");
				return dequeuedElement; // Solution found
			} else {
				LinkedList<PuzzleBoard> children = new LinkedList<PuzzleBoard>();
				try {
					children = dequeuedElement.generatePossibleMoves();
				} catch (IllegalBoardException e) {
					e.printStackTrace();
				}
				for (PuzzleBoard child : children) {
					boolean isVisited = false;
					for (int[] visitedTiles : visitedNodes) {
						if (Arrays.equals(child.tiles, visitedTiles)) {
							isVisited = true;
							break;
						}
					}
					if (!isVisited) {
						if (PuzzleSolver.DEBUG_MODE) {
							System.out.println((child.toString()) + " unvisited child found.");
						}
						priorityQueue.add(child);// FIFO
					} else {
						if (PuzzleSolver.DEBUG_MODE) {
							System.out.println((child.toString()) + " VISITED CHILD");
						}
					}

				}
			}
		}
		return null; // No solution found

	}

	// Custom Comparator to prioritize nodes using A* f-value (cost + heuristic)
	private static class AStarComparator implements Comparator<PuzzleBoard> {
		private PuzzleBoard goalState;

		public AStarComparator(PuzzleBoard goal) {
			this.goalState = goal;
		}

		@Override
		public int compare(PuzzleBoard board1, PuzzleBoard board2) {
			int f1 = board1.currentState.ordinal() + board1.heuristic(goalState);
			int f2 = board2.currentState.ordinal() + board2.heuristic(goalState);
			return Integer.compare(f1, f2);
		}
	}
}

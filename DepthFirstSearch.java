import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class DepthFirstSearch {

	int counter = 0;
	PriorityQueue<PuzzleBoard> priorityQueue = new PriorityQueue<>();

	public PuzzleBoard solve(PuzzleBoard start) {
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
				System.out.println("[DFS] Goal Reached at Visit#: " + counter + " at " + elapsedTime + " milliseconds");

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
}

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {
	LinkedList<int[]> visitedNodes = new LinkedList<int[]>();
	int counter = 0;
	Queue<PuzzleBoard> expandingQueue = new LinkedList<>();
	Queue<PuzzleBoard> temporaryQueue = new LinkedList<>();
	long startTime = System.currentTimeMillis();
	long endTime = 0;
	long elapsedTime = 0;

	public PuzzleBoard solve(PuzzleBoard root) {
		expandingQueue.add(root);// Insert the root into the expanding queue

		while (!expandingQueue.isEmpty()) {// While the expanding queue is not empty
			temporaryQueue.addAll(expandingQueue);// Copy all contents of the expanding queue to temporary queue
			expandingQueue.clear();// Empty the expanding queue
			PuzzleBoard dequeuedElement;

			while ((dequeuedElement = temporaryQueue.poll()) != null) {// For each node in the temporary queue (FIFO)
				// Dequeue one element from the temporary queue
				visitedNodes.add(dequeuedElement.tiles);
				counter++;
				if (PuzzleSolver.DEBUG_MODE) {
					System.out.println((dequeuedElement.toString()) + " popped off stack");
					System.out.println("-----------------Visit # " + counter + "-------------------");
				}
				if (Arrays.equals(dequeuedElement.tiles, PuzzleSolver.GOAL_CONFIGURATION)) {
					// If the path is ending in the goal state
					endTime = System.currentTimeMillis();
					elapsedTime = endTime - startTime;
					System.out.println(
							"[BFS] Goal Reached at Visit#: " + counter + " at " + elapsedTime + " milliseconds");
					return dequeuedElement; // Print the path and exit
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
							expandingQueue.add(child);// FIFO
						} else {
							if (PuzzleSolver.DEBUG_MODE) {
								System.out.println((child.toString()) + " VISITED CHILD");
							}
						}

					}
				}
			}
		}
		return null; // No solution found
	}
}

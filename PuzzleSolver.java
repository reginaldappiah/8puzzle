public class PuzzleSolver {

	public static final int[] GOAL_CONFIGURATION = { 1, 2, 3, 8, 0, 4, 7, 6, 5 };
	public static final int[] LECTURE_CONFIGURATION = { 2, 8, 3, 1, 6, 4, 7, 0, 5 };
	public static final boolean isRandom = true;
	public static final boolean DEBUG_MODE = false;

	public static void main(String[] args) {
		PuzzleBoard puzzle = isRandom ? new PuzzleBoard() : new PuzzleBoard(LECTURE_CONFIGURATION);
		System.out.println(puzzle.toString());
		new DepthFirstSearch().solve(puzzle);
		new BreadthFirstSearch().solve(puzzle);
		new UniformCostSearch().solve(puzzle, new PuzzleBoard(GOAL_CONFIGURATION));
		new A().solve(puzzle, new PuzzleBoard(GOAL_CONFIGURATION));
	}

}
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class PuzzleBoard implements Comparable<PuzzleBoard> {
	public static final int MAX_LENGTH = 9;
	int[] tiles = new int[MAX_LENGTH];
	PuzzleBoardState currentState;
	int cost;
	int freeTileIndex;

	public PuzzleBoard() {
		this.tiles = generate();
		try {
			checkState();
		} catch (IllegalBoardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PuzzleBoard(int[] tiles) {
		this.tiles = tiles;
		try {
			checkState();
		} catch (IllegalBoardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	int[] generate() {
		int[] generatedPuzzle = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		Random random = new Random();
		// Create a list of numbers from 0 to 8
		int[] numbers = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

		for (int i = 0; i < PuzzleBoard.MAX_LENGTH; i++) {
			int index = random.nextInt(PuzzleBoard.MAX_LENGTH - i); // Choose a random index
			generatedPuzzle[i] = numbers[index]; // Assign the number at that index
			numbers[index] = numbers[PuzzleBoard.MAX_LENGTH - i - 1]; // Replace with the last number
		}

		return generatedPuzzle;
	}

	LinkedList<PuzzleBoard> generatePossibleMoves() throws IllegalBoardException {
		LinkedList<PuzzleBoard> possibleMoves = new LinkedList<PuzzleBoard>();
		switch (this.currentState) {
		case CENTER:
			if (freeTileIndex == 4) {
				int[] centerMoves = { 3, 1, 5, 7 };
				for (int move : centerMoves) {
					PuzzleBoard choice = createMove(move);
					possibleMoves.add(choice);
				}
			}
			break;
		case SIDE:
			if (freeTileIndex == 1) {
				// Possible moves for tile 1
				int[] movesForTile1 = { 0, 2, 4 };
				for (int move : movesForTile1) {
					PuzzleBoard choice = createMove(move);
					possibleMoves.add(choice);
				}
			}
			if (freeTileIndex == 3) {
				// Possible moves for tile 3
				int[] movesForTile3 = { 0, 4, 6 };
				for (int move : movesForTile3) {
					PuzzleBoard choice = createMove(move);
					possibleMoves.add(choice);
				}
			}
			if (freeTileIndex == 5) {
				// Possible moves for tile 5
				int[] movesForTile5 = { 2, 4, 8 };
				for (int move : movesForTile5) {
					PuzzleBoard choice = createMove(move);
					possibleMoves.add(choice);
				}
			}
			if (freeTileIndex == 7) {
				// Possible moves for tile 7
				int[] movesForTile7 = { 6, 4, 8 };
				for (int move : movesForTile7) {
					PuzzleBoard choice = createMove(move);
					possibleMoves.add(choice);
				}
			}
			break;
		case CORNER:
			if (freeTileIndex == 0) {
				// Possible moves for tile 0
				int[] movesForTile0 = { 1, 3 };
				for (int move : movesForTile0) {
					PuzzleBoard choice = createMove(move);
					possibleMoves.add(choice);
				}
			}
			if (freeTileIndex == 2) {
				// Possible moves for tile 2
				int[] movesForTile2 = { 1, 5 };
				for (int move : movesForTile2) {
					PuzzleBoard choice = createMove(move);

					possibleMoves.add(choice);
				}
			}
			if (freeTileIndex == 6) {
				// Possible moves for tile 6
				int[] movesForTile6 = { 3, 7 };
				for (int move : movesForTile6) {
					PuzzleBoard choice = createMove(move);
					possibleMoves.add(choice);
				}
			}
			if (freeTileIndex == 8) {
				// Possible moves for tile 8
				int[] movesForTile8 = { 5, 7 };
				for (int move : movesForTile8) {
					PuzzleBoard choice = createMove(move);
					possibleMoves.add(choice);
				}
			}
			break;
		default:
			System.out.println("Invalid State");
			break;
		}
		return possibleMoves;
	}

	boolean isCornerFree() {
		if (freeTileIndex == 0 | freeTileIndex == 2 | freeTileIndex == 6 | freeTileIndex == 8) {
			return true;
		}
		return false;
	}

	PuzzleBoard createMove(int fromTile) throws IllegalBoardException {
		return createMove(fromTile, freeTileIndex);
	}

	PuzzleBoard createMove(int moveFrom, int moveTo) throws IllegalBoardException {
		PuzzleBoard choice = copyBoard(this);
		choice.tiles[moveTo] = Integer.valueOf(choice.tiles[moveFrom]);
		choice.tiles[moveFrom] = 0;
		choice.freeTileIndex = moveFrom;
		choice.checkState();
		return choice;
	}

	PuzzleBoard copyBoard(PuzzleBoard puzzle) {
		PuzzleBoard copy = new PuzzleBoard();
		copy.tiles = new int[PuzzleBoard.MAX_LENGTH];
		for (int i = 0; i < PuzzleBoard.MAX_LENGTH; i++) {
			copy.tiles[i] = Integer.valueOf(this.tiles[i]);
		}
		copy.currentState = PuzzleBoardState.valueOf(this.currentState.toString());
		copy.freeTileIndex = Integer.valueOf(this.freeTileIndex);
		copy.cost = Integer.valueOf(this.cost);
		return copy;
	}

	public void checkState() throws IllegalBoardException {
		int duplicateCount = 0;
		for (int num : tiles) {
			if (num == 0) {
				duplicateCount++;
			}
			if (duplicateCount > 1) {
				throw new IllegalBoardException(freeTileIndex, currentState);
			}
		}
		for (int i = 0; i < PuzzleBoard.MAX_LENGTH; i++) {
			if (tiles[i] == 0) {
				freeTileIndex = i;
				if (isCornerFree()) {
					currentState = PuzzleBoardState.CORNER;
					break;
				} else if (freeTileIndex == 4) {
					currentState = PuzzleBoardState.CENTER;
					break;
				}
				currentState = PuzzleBoardState.SIDE;
				break;
			}
		}
	}

	public void setCost(int newCost) {
		this.cost = newCost;
	}

	public int heuristic(PuzzleBoard goal) {
		int h = 0;
		for (int i = 0; i < PuzzleBoard.MAX_LENGTH; i++) {
			int tile = tiles[i];
			if (tile != 0) { // Skip the empty tile
				int goalIndex = goal.getIndex(tile);
				int rowDiff = Math.abs(i / 3 - goalIndex / 3);
				int colDiff = Math.abs(i % 3 - goalIndex % 3);
				h += rowDiff + colDiff;
			}
		}
		return h;
	}

	public int getIndex(int tile) {
		for (int i = 0; i < PuzzleBoard.MAX_LENGTH; i++) {
			if (tiles[i] == tile) {
				return i;
			}
		}
		return -1; // Tile not found
	}

	@Override
	public int compareTo(PuzzleBoard other) {// Cost Measurement
		if (Arrays.equals(tiles, other.tiles)) {
			return 0;
		} else if (this.currentState.ordinal() == other.currentState.ordinal()) {
			return 1;
		}
		return -1;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		for (int i = 0; i < PuzzleBoard.MAX_LENGTH; i++) {
			if (i % 3 == 0 && i > 1) {
				builder.append("\n");
			}
			builder.append("[" + (tiles[i] == 0 ? " " : tiles[i]) + "]");
		}
		builder.append("\n");
		return builder.toString();
	}

}

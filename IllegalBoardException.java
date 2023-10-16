
public class IllegalBoardException extends Exception {

	public IllegalBoardException(int freeTileIndex, PuzzleBoardState currentState) {
		System.out.println("Illegal Board at " + freeTileIndex + " {BOARD STATE}: " + currentState.toString());
	}

	private static final long serialVersionUID = 1L;

}

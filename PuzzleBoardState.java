/* POSSIBLE MOVES
CORNER

0 ->  1 and 3
2 ->  1 and 5
6 ->  3 and 7
8 ->  5 and 7


SIDE

1 -> 0, 2, 4
3 -> 0, 4, 6
5 -> 2, 4, 8
7 -> 4, 6, 8 


CENTER

4 -> 1, 3, 5, 7
*/

public enum PuzzleBoardState {
	CORNER, SIDE, CENTER
};

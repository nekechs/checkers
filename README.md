# Simply Checkers

Basically, this is just a simple checkers application for Android. There is **no AI** and **no multiplayer online**. It is just a checkers board where you can simulate checkers.

Note that this is my first actual android project, so this probably will not be the absolute best app that you've ever seen.

Current Features
----------------
- You can play checkers
- Undo moves
- Keep track of all played moves
- Highlighting of all possible moves based on a given selection of a piece
- Detection of win by capture or by stalemate


Possible Further Features
-------------------------
- Implement a basic minimax algorithm—most of the pieces are already here. For a given board, we can produce a list of all possible moves, and we can implement a simple method to count pieces and show the "leaf node" evaluation of a board. Then, designing a recursive algorithm for minimax would be trivial to implement.
- Save game—make the game class implement serializable, and allow the user to save a game and replay it
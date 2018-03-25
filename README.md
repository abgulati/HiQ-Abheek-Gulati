# HiQ-Abheek-Gulati
HiQ board game

Referred to by many names, HiQ is a board game often played with marbles(referred to as ‘pegs’) wherein the game consists of a board full 
of pegs barring one initially empty hole into which other pegs may jump. In the HiQ programs, the user specifies this initial empty hole 
along with the maximum number of pegs that could be left on the board for it to be considered a goal state. Pegs may jump into an empty 
hole by jumping over other pegs. The programs read in a list of valid moves from a text file. The list of moves have the format
(from, over, into). For a move to be legal at a given point, the ‘from‘ and ‘over’ holes must contain pegs while the ‘into’ hole must be 
empty. The program reaches a goal state once the specified number of pegs are left on the board, for example, the user may say beginning 
from a full board, start out with empty hole 5, and report it as a goal state only when a single peg is left on the board. When a peg jumps
over a peg into the empty hole, both the ‘from’ and ‘into’ holes are left empty and only the ‘into’ hole is now occupied. This is because
the peg that was jumped over must now be removed from the board. The output of the programs is a list of moves that got it to the goal 
state. On being presented with a solution, the user may ask for another one, as the programs are designed such that given an initial
starting empty hole, they find ALL possible solutions beginning from that hole. 

The code in this repository hence consist of the recursive Java, C++ and Python implementations on this game. These were written by me as part of coursework for the grad-level Computer Programming Languages course undertaken while pursuing my MS CS degree at NJIT. 

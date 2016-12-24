
#Abheek Gulati
#31375703
#akg29
#CS635-101

import sys

#defining some 'global' variables

empty_holes = set()

movesMade = []

validMoves = []

def isLegal(check_this_move):

	from_over_empty = False;		#should be false
	to_hole_empty = False;			#should be true

	if check_this_move[0] in empty_holes: from_over_empty = True

	if check_this_move[1] in empty_holes: from_over_empty = True

	if check_this_move[2] in empty_holes: to_hole_empty = True

	if (from_over_empty == False and to_hole_empty == True): return True

	else: return False



def makeMove(make_this_move):

	empty_holes.add(make_this_move[0])
	empty_holes.add(make_this_move[1])

	if make_this_move[2] in empty_holes: empty_holes.remove(make_this_move[2])

	movesMade.append(make_this_move)

def undoMove(undo_this_move):
	
	if undo_this_move[0] in empty_holes: empty_holes.remove(undo_this_move[0])
	if undo_this_move[1] in empty_holes: empty_holes.remove(undo_this_move[1])

	empty_holes.add(undo_this_move[2])

	index = 0
	for count, item in enumerate(movesMade):
		if item == undo_this_move:
			index = count

	del movesMade[index]

	
#central recursive function:
def playGame():

	#base-case:
	if( holeCount - len(empty_holes) == endCount):
		
		print ("A solution has been found and the moves leading to it are: ")
		for items in movesMade:
			print (items)
		print ("Total number of holes on the board: ", holeCount)
		print ("Number of empty holes: ", len(empty_holes))

		userChoice = input("Do you want to find another solution? y/n: ")

		if (userChoice == "y"):
			return
		elif (userChoice == "n"):
			sys.exit()
			


	#recursive aspect begins:

	#Get list of legal moves in current state
	curr_avail_moves = []

	for items in validMoves:
		currentMove = []
		currentMove = items
		if (isLegal(currentMove)):
			curr_avail_moves.append(currentMove)

	#We now have a list of legal moves!
	#print ("Current available moves are: ", curr_avail_moves)

	#return if no more moves available:
	if len(curr_avail_moves) == 0:
		return

	for move in curr_avail_moves:
		currMove = []
		currMove = move

		makeMove(currMove)
		# print ("Moves made so far: ", movesMade)
		# print ("Empty holes list on making a move: ", empty_holes)
		playGame()
		undoMove(currMove)
		# print ("Moves removed ", movesMade)
		# print ("Empty holes list on move removal: ", empty_holes)

	return
#end recursive function



#'main()' function begins::
#Firstly, we read in the moves:

with open("moves1.txt") as f:
	
	#reading in as-is from moves1.txt
	lst1 = []
	f.seek(0)								#ensures that reading starts from the first line
	for line in f:
		line = line.split()
		line = [int(i) for i in line]
		lst1.append(line)

#print (lst1)

#reversing moves
lst2 = []
for arr in lst1:
	temp = []
	temp = arr[::-1]
	lst2.append(temp)

#print (lst2) 

validMoves = lst1 + lst2					#We now have our list of valid moves!

print ("Our list of valid moves are: ")
for arr in validMoves:
	print (arr)						


max_hole = 0
zeroPresent = None
holeCount = 0

for arr in validMoves:
	for x in arr:
		if x > max_hole:
			max_hole = x
		if x == 0:
			zeroPresent = True


# print ("The maximum value in this list is: ", max_hole)
# print ("Is zero present? ", zeroPresent)


if (zeroPresent):
	holeCount = max_hole + 1
else:
	holeCount = max_hole
#print ("The number of holes on the board are: ", holeCount)
#We now have the number of pegs on this board!


#now ask user for the initial empty hole
initialEmpty = int(input("Enter the hole that should be empty at the start of the game: "))
endCount = int(input("Number of pegs that can be left on the board to be considered an acceptable solution? "))

empty_holes.add(initialEmpty)

#print ("empty hole set at the start of the program: ", empty_holes)

#Begin game:
playGame()
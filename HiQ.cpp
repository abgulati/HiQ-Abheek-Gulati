/*
Author: Abheek Gulati
For: NJIT class CS 635
*/

#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <sstream>
#include <string>

using namespace std;

//Global variables:
static vector < vector <int> > validMoves;
static int holeCount = 0;
static int endCount = 0;
static set<int> empty_holes;
static vector < vector <int> > movesMade;

bool isLegal(vector<int> check_this_move) {

	bool from_over_empty = false;		//should be false
	bool to_hole_empty = false;			//should be true

	for (set<int>::iterator i = empty_holes.begin(); i != empty_holes.end(); i++) {

		if (*i == check_this_move.at(0)) from_over_empty = true;

		if (*i == check_this_move.at(1)) from_over_empty = true;

		if (*i == check_this_move.at(2)) to_hole_empty = true;

	}

	if (from_over_empty == false && to_hole_empty == true) return true;

	else return false;
}

void makeMove(vector<int> make_this_move) {
	
	empty_holes.insert(make_this_move.at(0));
	empty_holes.insert(make_this_move.at(1));

	empty_holes.erase(make_this_move.at(2));
	
	movesMade.push_back(make_this_move);
}

void undoMove(vector<int> undo_this_move) {
	
	empty_holes.erase(undo_this_move.at(0));
	empty_holes.erase(undo_this_move.at(1));

	empty_holes.insert(undo_this_move.at(2));
	
	int k = 0;
	for (int i = 0; i < movesMade.size(); i++) {
		vector<int> temp;
		for (int j = 0; j < movesMade[i].size(); j++) {
			temp.push_back(movesMade[i][j]);
		}
		if (temp == undo_this_move) {
			//cout << "here";
			k = i;
		}
		
	}

	movesMade.erase(movesMade.begin() + k);
}

//recursive function:
void playGame() {
	
	//base-case:
	if ((holeCount - empty_holes.size()) == endCount) {
		cout << "A solution has been found and the moves leading upto this solution are: " << endl;
		for (int i = 0; i < movesMade.size(); i++) {
			for (int j = 0; j < movesMade[i].size(); j++) {
				cout << movesMade[i][j] << " ";
			}
			cout << endl;
		}

		cout << "Peg count: " << holeCount << endl;
		cout << "Number of empty holes are: " << empty_holes.size() << endl;

		string userCoice;

		cout << "Would you like to find another solution? y/n: ";
		getline(cin, userCoice);

		if (userCoice.compare("y") == 0) return;
		
		else if (userCoice.compare("n") == 0) exit(0);
	}

	//recursive aspect:
	vector < vector <int> > curr_aval_moves;

	for (int i = 0; i < validMoves.size(); i++) {
		vector <int> currentMove;
		for (int j = 0; j < validMoves[i].size(); j++) {
			currentMove.push_back(validMoves[i][j]);
		}
	
		if (isLegal(currentMove)) {
			curr_aval_moves.push_back(currentMove);
		}
	}

	if (curr_aval_moves.size() == 0) return;

	for (int i = 0; i < curr_aval_moves.size(); i++) {
		vector <int> currMove;
		
		for (int j = 0; j < curr_aval_moves[i].size(); j++) {
			currMove.push_back(curr_aval_moves[i][j]);
		}

		makeMove(currMove);
		playGame();
		undoMove(currMove);

	}
	
	return;

}//end recurv function

//main function:
int main()
{
	int i;
	int no_of_values = 0;

	char *inname = "moves1.txt";

	ifstream infile(inname);

	if (!infile) {
		cout << "There was a problem opening the file" << endl;
		return 0;
	}

	//reading in moves as in text file:
	vector <int> temp;

	while (infile >> i) {	
		//cout << "value is: " << i << endl;
		temp.push_back(i);
		if (temp.size() == 3) {
			validMoves.push_back(temp);
			temp.clear();
		}
	}

	//reset file read pointer:
	infile.clear();
	infile.seekg(0, ios::beg);

	//read reverse of moves:
	vector <int> temp2;
	vector <int>::iterator it;
	it = temp2.begin();
	
	while (infile >> i) {
		temp2.insert(it, i);
		it = temp2.begin();
		if (temp2.size() == 3) {
			validMoves.push_back(temp2);
			temp2.clear();
			it = temp2.begin();
		}
	}


	bool zeroPresent = false;
	int max_hole = 0;


	cout << "The list of valid moves are: " << endl;

	for (int z = 0; z < validMoves.size(); z++) {
		for (int p = 0; p < validMoves[z].size(); p++) {
			cout << validMoves[z][p] << " ";

			//check if a zero is present as a board position
			if (validMoves[z][p] == 0) {
				zeroPresent = true;
			}

			//find max value on board
			if (validMoves[z][p] > max_hole) {
				max_hole = validMoves[z][p];
			}
		}
		cout << endl;
	}

	if (zeroPresent) {
		holeCount = max_hole + 1;
	}
	else if (!zeroPresent) {
		holeCount = max_hole;
	}

	cout << "Max value is: " << max_hole << endl;
	cout << "Number of holes are: " << holeCount << endl;

	int initialEmpty = 0;
	cout << "Enter the hole that should be empty at the start of the game: " << endl;
	cin >> initialEmpty;

	cout << "Number of pegs that can be left on the board for it to be a valid solution: " << endl;
	cin >> endCount;

	empty_holes.insert(initialEmpty);
	
	playGame();

	return 0;
}

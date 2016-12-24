/*
Abheek Gulati
31375703
akg29
CS635-101
*/


import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class HiQ {

  public static int[][] validMoves;

  public static int holeCount = 0;					//counts number of pegs on the board at initialization: Fixed at init

  public static int endCount = 0;

  public static HashSet<Integer> empty_holes = new HashSet<>();

  public static ArrayList<ArrayList<Integer>> movesMade = new ArrayList<ArrayList<Integer>>();

  public static int cnt = 0;


  //function to check if a move is legal:
  public static boolean isLegal(int[] check_this_move) {

	  boolean from_over_empty = false;			//should be false
	  boolean to_hole_empty = false;			//should be true

	  if (empty_holes.contains(check_this_move[0])) from_over_empty = true;

	  if (empty_holes.contains(check_this_move[1])) from_over_empty = true;

	  if (empty_holes.contains(check_this_move[2])) to_hole_empty = true;

	  if (from_over_empty == false && to_hole_empty == true) return true;

	  else return false;

  }

  //function to make a move
  public static void makeMove(int[] make_this_move) {

	  //First, add the (from,over) holes to our list of empty holes
	  for (int i = 0; i < 2; i++) {
		  empty_holes.add(make_this_move[i]);
	  }

	  //Second, remove the (into) hole from our list of empty holes, should it be found on the list
	  if (empty_holes.contains(make_this_move[2])) {
		  empty_holes.remove(make_this_move[2]);
	  }

	  //Lastly, add this move to our list of moves made
	  ArrayList<Integer> addMove = new ArrayList<>();

	  for (int i = 0; i < make_this_move.length; i++) {
		  addMove.add(make_this_move[i]);
	  }

	  movesMade.add(addMove);

  }

  //function to undo a move
  public static void undoMove(int[] undo_this_move) {

	  //Firstly, remove (from, over) holes from the empty hole list
	  if (empty_holes.contains(undo_this_move[0])) {
		  empty_holes.remove(undo_this_move[0]);
	  }

	  if (empty_holes.contains(undo_this_move[1])) {
		  empty_holes.remove(undo_this_move[1]);
	  }

	  //Second, add the (into) hole to the empty hole list
	  empty_holes.add(undo_this_move[2]);

	  //Lastly, remove this move from our list of moves that have been made
	  ArrayList<Integer> removeMove = new ArrayList<>();

	  for (int i = 0; i < undo_this_move.length; i++) {
		  removeMove.add(undo_this_move[i]);
	  }

	  int y = movesMade.lastIndexOf(removeMove);
	  movesMade.remove(y);

  }


  //recursive function
  public static void playGame() throws IOException {

	  //base-case:
	  if ( (holeCount - empty_holes.size()) == endCount) {
		  //print this goal-state solution
		  System.out.println("A solution has been found and the moves leading to this solution are: ");
		  for(int i = 0; i < movesMade.size(); i++) {
			  System.out.println(movesMade.get(i) + " ");
		  }
		  System.out.println("peg count: " + holeCount);
		  System.out.println("number of empty holes: " + empty_holes.size());

		  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		  String userChoice;

		  System.out.println("Do you want to continue looking for more solutions? y/n: ");
		  userChoice = br.readLine();

		  if (userChoice.compareToIgnoreCase("y") == 0) return;

		  else if (userChoice.compareToIgnoreCase("n") == 0) System.exit(0);

		  cnt += 1;
		  //return;
	  }

	  //recursive function:
	  int[][] curr_avail_moves = new int[validMoves.length][3];
	  int curr_aval_moves_counter = 0;

	  for (int i = 0; i < validMoves.length; i++) {
		  int[] currentMove = new int[3];
		  for (int j = 0; j < 3; j++) {
			  currentMove[j] = validMoves[i][j];
		  }
		  if (isLegal(currentMove)) {
			  for (int j = 0; j < 3; j++) {
				  curr_avail_moves[curr_aval_moves_counter][j] = currentMove[j];
			  }
			  curr_aval_moves_counter++;
		  }
	  }

	  if (curr_aval_moves_counter == 0) {
		  /*System.out.println("A solution has been found and the moves leading to this solution are: ");
		  for(int i = 0; i < movesMade.size(); i++) {
			  System.out.println(movesMade.get(i) + " ");
		  }
		  System.out.println("peg count: " + pegCount);
		  System.out.println("number of empty holes: " + empty_holes.size());*/
		  return;
	  }

	  for (int i = 0; i < curr_aval_moves_counter; i++) {
		  int[] currMove = new int[3];
		  for (int j = 0; j < 3; j++) {
			  currMove[j] = curr_avail_moves[i][j];
		  }

		  makeMove(currMove);
		  playGame();
		  undoMove(currMove);
	  }
	  return;

  }//end function

  public static void main(String[] args) throws Exception {

      File in = new File("moves1.txt");
      byte[] file_bytes = new byte[(int) in.length()];

      FileInputStream fis = new FileInputStream(in);
      fis.read(file_bytes);
      fis.close();

      String[] valueStr = new String(file_bytes).trim().split("\\s+");

      int no_of_values = valueStr.length;
      int no_of_moves = ((no_of_values)/3)*2;

      validMoves = new int[no_of_moves][3];

      int k = 0;
      boolean zeroPresent = false;
      int max_hole = 0;

      for (int i = 0; i < no_of_moves/2; i++) {

        for (int j = 0; j < 3; j++) {

          validMoves[i][j] = Integer.parseInt(valueStr[k]);

          if (validMoves[i][j] == 0) {
        	  zeroPresent = true;
          }
          if (validMoves[i][j] > max_hole) {
            max_hole = validMoves[i][j];
          }
          k++;
        }
      }

      k = 0;

      for (int i = no_of_moves/2; i < no_of_moves; i++ ) {

    	  for (int j = 2; j > -1; j--) {
    		  validMoves[i][j] = Integer.parseInt(valueStr[k]);
    		  k++;
    	  }
      }

      System.out.println("Valid moves are: ");

      for (int i = 0; i < no_of_moves; i++) {
    	  for (int j = 0; j < 3; j++) {
    		  System.out.print(" " + validMoves[i][j] + " ");
    	  }
    	  System.out.println();
      }

      if (zeroPresent) {
    	  holeCount = max_hole+1;
      }
      else if (!zeroPresent) {
    	  holeCount = max_hole;
      }

      int initialEmpty = 0;
      Scanner readInt = new Scanner(System.in);

      System.out.println("Enter the hole that should be empty at the start of the game: ");
      initialEmpty = readInt.nextInt();       //initialEmpty is a 'global' variable

      System.out.println("Number of pegs that can be left on the board for it to be an acceptable solution? ");
      endCount = readInt.nextInt();

      empty_holes.add(initialEmpty);

      playGame();
      System.out.println("Total number of solutions are: " + cnt);
      readInt.close();


  }//end main()

}//end class

import java.util.Scanner;

// possibly allow user to
//change size of board as well as change difficulty which will change the quantity of bombs.

// get time method to do writing slowly.

class Main {

  public static int boardWidth = 11;
  public static double difficultyNum = 0.2;

  public static void main(String[] args) {

    Scanner scan = new Scanner(System.in);

    int play = 1;
    while (play == 1) {

      System.out.println(
          "\nWelcome to minesweeper! Created by C.K. In this game you will reveal squares on a board by inputing the square number and letter. If the square is not a bomb, it will show the number of bombs that surround it. However, if it is a bomb, you will lose the game. It is impossible to hit a bomb on your first round. Uncover all of the non - bombs to win!");

      System.out.println(
          "\nChose the size of your board. (Input a positive integer that is between 4 and 26. (9 is normal difficulty.))");
      Main.boardWidth = scan.nextInt() + 2;
      scan.nextLine();

      System.out.println(
          "\nChose the dificulty of the game from 1 - 5. (input any positive number between 1 and 5. (2 is normal difficulty.))");
      Main.difficultyNum = (scan.nextDouble() / 14.0);
      scan.nextLine();

      int[][] realBoard = new int[Main.boardWidth][Main.boardWidth];

      // playerboard will have a number value of -l in the event that the number is
      // not revealed. This will then become a special character when printing.

      int[][] playerBoard = new int[Main.boardWidth][Main.boardWidth];

      System.out.println(" ");
      // the following line with resetBoard makes all the squares into 0. This is not
      // really needed since we already are creating a new board each time with all
      // zeros, but just to be safe we have this function.
      resetBoard(realBoard);
      createBombs(realBoard);
      // don't do following line since we alreado do it later.
      // createNumsForBombs(realBoard);
      // print what the real board would look like
      // print2dIntArray(realBoard);
      hidePlayerBoardSquares(playerBoard);
      // makes playerBoard all -1;

      // print2dIntArray(playerBoard);
      print2dCharactersArray(playerBoard);

      System.out.println("\n We will be uncovering squares by indicating the position with the number and letter. For example, enter 'A1' or 'a1' to place a yellow square there. (caps does not matter for the letter).");
      
      System.out.println();
        String inputTile;
        int columnNumber = 0;
        int rowNumber = 0;

        inputTile = scan.nextLine();

        columnNumber = (int) (inputTile.substring(0, 1).toUpperCase().charAt(0)) - 65 + 1;
        rowNumber = Integer.valueOf(inputTile.substring(1));
      // for first round so that they cant immediately lose:
      //System.out.println("\n Input a number for the row of the square you want to reveal.");
      //int rowNumber = 0;
      //rowNumber = (scan.nextInt());
      // +1 not needed because row 1 is index one as well since index - is the hidden
      // one.

      //scan.nextLine();

      //System.out.println("\n Input a letter for the column of the square you want to reveal.");
      //int columnNumber = 0;
      //columnNumber = (int) (scan.next().toUpperCase().charAt(0)) - 65 + 1;
      //  cuase of the hidden square that goes around the board, so making A into
      // 1instead of 0.

      clearScreen();

      removeBombsFromAroundChosenSquare(realBoard, rowNumber, columnNumber);
      // so this removes the bombs on and around the chosen square, and then the next
      // following line makes the numbers around the bombs correct.
      createNumsForBombs(realBoard);
      // then reveals the squares around the chosen square.
      revealSquaresAroundChosenSquare(playerBoard, realBoard, rowNumber, columnNumber);

      // doing all the revealing for the free squres and their adjacent squares,
      // looping through each square quantity of squares amount of times, then doing
      // reveal free squares.
      for (int z = 0; z < ((Main.boardWidth - 1) * (Main.boardWidth - 1)); z++) {
        for (int a = 1; a < Main.boardWidth - 1; a++) {
          for (int b = 1; b < Main.boardWidth - 1; b++) {
            revealFreeSquaresAroundChosenSquare(playerBoard, realBoard, a, b);

            // revealSquaresAroundChosenSquare(playerBoard, realBoard, a, b);

            // NEED TO DO THIS WHERE THE NUMBERS NEXT TO THE FREE SQUARES ARE SEEN.
          }
        }
      }

      // print2dIntArray(playerBoard);
      print2dCharactersArray(playerBoard);

      // then the actual loop. Need to add a function that checks if the player has
      // one, by seeing if the number of revealed squares + the number of bombs
      // matches the number of total squares...

      boolean stillPlaying = true;
      while (stillPlaying == true) {

      System.out.println("\n We will be uncovering squares by indicating the position with the number and letter. For example, enter 'A1' or 'a1' to place a yellow square there. (caps does not matter for the letter).");
      
      System.out.println();

      inputTile = scan.nextLine();

      columnNumber = (int) (inputTile.substring(0, 1).toUpperCase().charAt(0)) - 65 + 1;
      rowNumber = Integer.valueOf(inputTile.substring(1));

        if (realBoard[rowNumber][columnNumber] == 9) {
          playerBoard[rowNumber][columnNumber] = realBoard[rowNumber][columnNumber];
          clearScreen();
          System.out.println();
          // print2dIntArray(playerBoard);
          print2dCharactersArray(playerBoard);
          stillPlaying = false;
          System.out.println("\n You uncovered a bomb! The game has ended. This is what the real board looked like.");
          System.out.println();
          // print2dIntArray(playerBoard);
          print2dCharactersArray(realBoard);

        } else {
          clearScreen();
          playerBoard[rowNumber][columnNumber] = realBoard[rowNumber][columnNumber];

          if (playerBoard[rowNumber][columnNumber] == 0) {
            // so if you uncover a 0, then do the whole thing where you reveall all of the
            // near zeros and the numbers around them.

            for (int z = 0; z < ((Main.boardWidth - 1) * (Main.boardWidth - 1)); z++) {
              for (int a = 1; a < Main.boardWidth - 1; a++) {
                for (int b = 1; b < Main.boardWidth - 1; b++) {
                  revealFreeSquaresAroundChosenSquare(playerBoard, realBoard, a, b);
                  // revealSquaresAroundChosenSquare(playerBoard, realBoard, a, b);

                }
              }
            }
          }

          System.out.println();
          // print2dIntArray(playerBoard);
          print2dCharactersArray(playerBoard);
          System.out.println("\n You uncovered a " + playerBoard[rowNumber][columnNumber] + ".");

          // check if game has been won.
          if (checkIfPlayerWon(playerBoard, realBoard) == true) {
            stillPlaying = false;
            System.out.println("\n You won the game! Congrats! This is what the real board looked like.");
            System.out.println();
            // print2dIntArray(playerBoard);
            print2dCharactersArray(realBoard);
          }
        }
      }

      System.out.println("\n Would you like to play again? (input 1 for yes, or input 0 for no.)");
      play = (scan.nextInt());
      scan.nextLine();
      if (play == 1) {
        clearScreen();
      }

      // follow bracket ends while play function.
    }

    scan.close();
    // end of main function with the following bracket.
  }

  // board is 11x 11, but real board is 9x9. we need that extra 2 to make it 11 so
  // that we can have one extra on the left and right for when the scanning is
  // done for the bombs, so that we don't get an out of bounds error.

  public static void createBombs(int[][] arr2d) {
    for (int i = 1; i < arr2d.length - 1; i++) {
      for (int j = 1; j < arr2d.length - 1; j++) {

        if (Math.random() < Main.difficultyNum) {
          arr2d[i][j] = 9;
          // bombs are a value of 9 on the int board. creates them only in the 9x9 area;
        }
      }
    }
  }

  public static void createNumsForBombs(int[][] arr2d) {
    for (int i = 1; i < arr2d.length - 1; i++) {
      for (int j = 1; j < arr2d.length - 1; j++) {

        // if its not a bomb,
        if (arr2d[i][j] != 9) {
          // check all 8 squares around it.
          // add one to the number for each bomb next to it.
          if (arr2d[i - 1][j - 1] == 9) {
            arr2d[i][j]++;
          }

          if (arr2d[i][j - 1] == 9) {
            arr2d[i][j]++;
          }

          if (arr2d[i + 1][j - 1] == 9) {
            arr2d[i][j]++;
          }

          if (arr2d[i + 1][j] == 9) {
            arr2d[i][j]++;
          }

          if (arr2d[i + 1][j + 1] == 9) {
            arr2d[i][j]++;
          }

          if (arr2d[i][j + 1] == 9) {
            arr2d[i][j]++;
          }

          if (arr2d[i - 1][j + 1] == 9) {
            arr2d[i][j]++;
          }

          if (arr2d[i - 1][j] == 9) {
            arr2d[i][j]++;
          }

        }
      }
    }
  }

  public static void hidePlayerBoardSquares(int[][] arr2d) {
    for (int i = 1; i < arr2d.length - 1; i++) {
      for (int j = 1; j < arr2d.length - 1; j++) {
        arr2d[i][j] = -1;
        // makes hidden values -1 for inner square only.
      }
    }
  }

  public static void resetBoard(int[][] arr2d) {

    for (int i = 0; i < arr2d.length; i++) {
      for (int j = 0; j < arr2d.length; j++) {
        arr2d[i][j] = 0;
        // makes all the squares 0.
      }
    }
  }

  public static boolean checkIfPlayerWon(int[][] arr2d, int[][] arr2d2) {
    // arr2d is the player board and arr2d2 is the real board.

    // only checking the inner square, as usual.
    // sees if the playboard has a -1, and if on the realboard that space is not a
    // 9, then the player hasn't won, since they haven't revealed all the squares
    // that the need to.
    boolean hasWonGame = true;

    for (int i = 1; i < arr2d.length - 1; i++) {
      for (int j = 1; j < arr2d.length - 1; j++) {

        if ((arr2d[i][j] == -1) && (arr2d2[i][j] != 9)) {
          hasWonGame = false;
        }
      }
    }
    return hasWonGame;
  }

  // print just basic 2d array with letters on bottom and numbers on the left
  // side.
  public static void print2dIntArray(int[][] arr2d) {
    for (int i = 1; i < arr2d.length - 1; i++) {
      // printing side numbers correctly if over 9
      if (i < 10) {
        System.out.print(" " + i + "  ");
      } else {
        System.out.print(i + "  ");
      }

      for (int j = 1; j < arr2d.length - 1; j++) {
        // possibly add printing special characters based off of the number here.
        System.out.print(arr2d[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println();
    System.out.print("    ");
    for (int a = 0; a < Main.boardWidth - 2; a++) {
      System.out.print((char) (65 + a) + " ");
      // prints letters, allowing user to customize the size of the board.
    }
    System.out.println();
  }

  public static void print2dCharactersArray(int[][] arr2d) {
    for (int i = 1; i < arr2d.length - 1; i++) {
      // printing side numbers correctly if over 9
      if (i < 10) {
        System.out.print(" " + i + "  ");
      } else {
        System.out.print(i + "  ");
      }

      for (int j = 1; j < arr2d.length - 1; j++) {
        // printing special characters.

        // uncovered tile.
        if (arr2d[i][j] == -1) {
          System.out.print("\u001b[32;1m" + "■" + "\u001b[0m" + " ");
        }
        // number 0 tile.
        if (arr2d[i][j] == 0) {
          System.out.print("\u001b[33;1m" + "■" + "\u001b[0m" + " ");
        }
        // number 1 tile.
        if (arr2d[i][j] == 1) {
          System.out.print("\u001b[34;1m" + "1" + "\u001b[0m" + " ");
        }
        // number 2 tile.
        if (arr2d[i][j] == 2) {
          System.out.print("\u001b[35;1m" + "2" + "\u001b[0m" + " ");
        }
        // number 3 tile.
        if (arr2d[i][j] == 3) {
          System.out.print("\u001b[31;1m" + "3" + "\u001b[0m" + " ");
        }
        // number 4 tile.
        if (arr2d[i][j] == 4) {
          System.out.print("\u001b[31;1m" + "4" + "\u001b[0m" + " ");
        }
        // number 5 tile.
        if (arr2d[i][j] == 5) {
          System.out.print("\u001b[31;1m" + "5" + "\u001b[0m" + " ");
        }
        // number 6 tile.
        if (arr2d[i][j] == 6) {
          System.out.print("\u001b[31;1m" + "6" + "\u001b[0m" + " ");
        }
        // number 7 tile.
        if (arr2d[i][j] == 7) {
          System.out.print("\u001b[31;1m" + "7" + "\u001b[0m" + " ");
        }
        // number 8 tile.
        if (arr2d[i][j] == 8) {
          System.out.print("\u001b[31;1m" + "8" + "\u001b[0m" + " ");
        }
        // bomb tile.
        if (arr2d[i][j] == 9) {
          System.out.print("\u001b[0m" + "⎊" + "\u001b[0m" + " ");
        }

      }
      System.out.println();
    }
    System.out.println();
    System.out.print("    ");
    for (int a = 0; a < Main.boardWidth - 2; a++) {
      System.out.print((char) (65 + a) + " ");
      // prints letters, allowing user to customize the size of the board.
    }
    System.out.println();
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void removeBombsFromAroundChosenSquare(int[][] arr2d, int row, int column) {

    arr2d[row][column] = 0;
    arr2d[row - 1][column - 1] = 0;
    arr2d[row][column - 1] = 0;
    arr2d[row + 1][column - 1] = 0;
    arr2d[row + 1][column] = 0;
    arr2d[row + 1][column + 1] = 0;
    arr2d[row][column + 1] = 0;
    arr2d[row - 1][column + 1] = 0;
    arr2d[row - 1][column] = 0;
  }

  public static void revealSquaresAroundChosenSquare(int[][] arr2d, int[][] arr2d2, int row, int column) {

    // arr2d is the player board and arr2d2 is the real board.
    arr2d[row][column] = arr2d2[row][column];
    arr2d[row - 1][column - 1] = arr2d2[row - 1][column - 1];
    arr2d[row][column - 1] = arr2d2[row][column - 1];
    arr2d[row + 1][column - 1] = arr2d2[row + 1][column - 1];
    arr2d[row + 1][column] = arr2d2[row + 1][column];
    arr2d[row + 1][column + 1] = arr2d2[row + 1][column + 1];
    arr2d[row][column + 1] = arr2d2[row][column + 1];
    arr2d[row - 1][column + 1] = arr2d2[row - 1][column + 1];
    arr2d[row - 1][column] = arr2d2[row - 1][column];
  }

  public static void revealFreeSquaresAroundChosenSquare(int[][] arr2d, int[][] arr2d2, int row, int column) {

    // arr2d is the player board and arr2d2 is the real board.
    // so if the square revealed is yellow (a zero) which it always will be becuase
    // of the reveal bombs around chosen square function, then were going to look
    // for adjacent free squares. In the top when we do this at the begining of the
    // game, we will call this function a bunch of times, probably like the amount
    // of times as the amout of squares there are, so that we check all the adjacent
    // squares.

    if (arr2d[row][column] == 0) {

      arr2d[row][column] = arr2d2[row][column];
      arr2d[row - 1][column - 1] = arr2d2[row - 1][column - 1];
      arr2d[row][column - 1] = arr2d2[row][column - 1];
      arr2d[row + 1][column - 1] = arr2d2[row + 1][column - 1];
      arr2d[row + 1][column] = arr2d2[row + 1][column];
      arr2d[row + 1][column + 1] = arr2d2[row + 1][column + 1];
      arr2d[row][column + 1] = arr2d2[row][column + 1];
      arr2d[row - 1][column + 1] = arr2d2[row - 1][column + 1];
      arr2d[row - 1][column] = arr2d2[row - 1][column];

    }

  }

  // End main class with following bracket.
}

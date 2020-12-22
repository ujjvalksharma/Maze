package maze.driver;

import java.io.IOException;
import java.io.StringReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import maze.model.MatchOverException;
import maze.model.Maze;
import maze.model.NonWrappingMaze;
import maze.model.WrappingMaze;
import maze.text.control.IController;
import maze.text.control.IControllerImpl;

/**
 * This driver class starts the program why taking input from the user to construct the model, then
 * this input is passed on to the controller to perform the actions such as move/shoot/print/find
 * status.
 */
public class TextDriver {


  Maze maze;

  /**
   * This function starts the program to initiate the controller and the model.
   */
  public void start() throws IOException, MatchOverException {


    final String nextPlayerMsg = "It is next player's turn.";
    final String invalidInputMsg = "Invalid input.try again!!";
    Scanner scan;
    String mazeType = "";
    int mazeSize;
    int remainingWalls;
    int totalBats;
    int totalPits;
    boolean isPlayer2 = false;
    int seed;
    scan = new Scanner(System.in);
    while (true) {
      System.out.println("Enter the maze type (Wrapping/NonWrapping)");
      mazeType = scan.next();

      if (mazeType.equals("Wrapping")) {
        break;
      } else if (mazeType.equals("NonWrapping")) {
        break;
      } else {
        System.out.println("Invalid Input!!");
      }

    }
    while (true) {
      System.out.println("Enter the maze size");
      try {
        mazeSize = Integer.parseInt(scan.next());
        if (mazeSize > 3) {
          break;
        } else {
          System.out.println("maze size should be atleast 4");
        }
      } catch (IllegalArgumentException e) {
        System.out.println("maze size can only be a integer");
      }


    }

    while (true) {
      System.out.println("Enter a seed value");
      try {
        seed = Integer.parseInt(scan.next());
        break;
      } catch (IllegalArgumentException e) {
        System.out.println("maze size can only be a integer");
      }


    }


    while (true) {
      System.out.println("Enter the number of remaining walls");
      try {
        remainingWalls = Integer.parseInt(scan.next());
        if (remainingWalls > 0) {
          break;
        } else {
          System.out.println("remaining walls should be atleast 1");
        }
      } catch (IllegalArgumentException e) {
        System.out.println("remaining walls can only be a integer");
      }


    }


    while (true) {
      System.out.println("Enter maximum number of total bats");
      try {
        totalBats = Integer.parseInt(scan.next());
        if (totalBats >= 0) {
          break;
        } else {
          System.out.println("bats should be atleast 1");
        }
      } catch (IllegalArgumentException e) {
        System.out.println("bats can only be a integer");
      }


    }

    while (true) {
      System.out.println("Enter maximum number of total pits");
      try {
        totalPits = Integer.parseInt(scan.next());
        if (totalPits >= 0) {
          break;
        } else {
          System.out.println("pits should be atleast 1");
        }
      } catch (IllegalArgumentException e) {
        System.out.println("pits can only be a integer");
      }


    }


    while (true) {
      System.out.println("Is player2 playing (Yes/No)");
      mazeType = scan.next();

      if (mazeType.equals("Yes")) {
        isPlayer2 = true;
        break;
      } else if (mazeType.equals("No")) {
        isPlayer2 = false;
        break;
      } else {
        System.out.println("Enter Yes or No only");
      }

    }

    if (mazeType.equals("Wrapping")) {
      maze = new WrappingMaze(mazeSize, mazeSize,
              totalBats, totalPits, remainingWalls, isPlayer2, seed);
    } else {
      maze = new NonWrappingMaze(mazeSize, mazeSize,
              totalBats, totalPits, remainingWalls, isPlayer2, seed);

    }
    System.out.println("---- Welcome to the maze. Kill the wumpus------");
    System.out.println("Available direction name are North/South/East/West");
    StringBuffer out = null;
    StringReader in;
    IController controller;

    while (true) {


      String operation = scan.nextLine();

      if (operation.equalsIgnoreCase("Refresh") || operation.equalsIgnoreCase("Restart")) {

        if (mazeType.equals("Wrapping")) {
          maze = new WrappingMaze(mazeSize, mazeSize,
                  totalBats, totalPits, remainingWalls, isPlayer2, seed);
        } else {
          maze = new NonWrappingMaze(mazeSize, mazeSize,
                  totalBats, totalPits, remainingWalls, isPlayer2, seed);

        }
        System.out.println("You game has beed restarted!!");
        System.out.println("---- Welcome to the maze. Kill the wumpus------");

      } else {


        try {

          if (operation.length() > 0) {
            if (!operation.equals("Print")) {
              operation = operation + " " + maze.getCurrentPlayer();
            }
            out = new StringBuffer();
            in = new StringReader(operation);
            controller = new IControllerImpl(maze, in, out);
            controller.start();
            System.out.println(out.toString());
            if (isPlayer2 && maze.getRestrictedPlayer() == -1) {
              System.out.println(nextPlayerMsg);
              maze.switchPlayer();
            }
          }

        } catch (IllegalArgumentException e) {
          System.out.println(invalidInputMsg);
        } catch (NoSuchElementException e) {
          System.out.println(invalidInputMsg);
        } catch (MatchOverException e) {
          matchOver(e.getMessage());
          return;
        } catch (IllegalStateException e) {
          if (isPlayer2) {
            if (maze.getRestrictedPlayer() == -1) {
              maze.setRestrictedPlayer(maze.getCurrentPlayer());
              System.out.println(e.getMessage() + "." + nextPlayerMsg);
              maze.switchPlayer();
            }
          }
        }

      }

      System.out.println("Enter the operation name you want to perform "
              + "\n1)move <directionName>"
              + "\n2)shootArrow  <directionName> <Distance>"
              + "\n3)showStatus"
              + "\n4)Quit or q "
              + " \n 5) Refresh or Restart"
              + " \n 5) Print");

    }


  }

  private void matchOver(String msg) {


    if (maze.getPlayers().length > 1) {
      if (maze.getPlayers()[0].getTotalGold() > maze.getPlayers()[1].getTotalGold()) {
        msg = "Player1 won as he has more gold than player2";
      } else if (maze.getPlayers()[0].getTotalGold() < maze.getPlayers()[1].getTotalGold()) {
        msg = "Player2 won as he has more gold than player1";
      } else {
        msg = "Match is a draw as they have same gold amount";
      }
    }
    System.out.println(msg);


  }

}

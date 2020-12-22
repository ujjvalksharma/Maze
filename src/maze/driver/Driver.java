package maze.driver;

import java.io.IOException;

import maze.model.MatchOverException;

/**
 * This is a driver class that call GUIDriver or TextDriver based on the argument passed to it for
 * playing text based or graphical game.
 */
public class Driver {

  /**
   * This function starts program to choose the driver which should run.
   *
   * @param args paramter passed at runtime in the jar
   * @throws IOException        if unable to read the input
   * @throws MatchOverException this happens when the match is over.
   */
  public static void main(String[] args) throws IOException, MatchOverException {

    if (args.length != 1) {
      System.out.println("Invalid argument.The argument can be only --text --gui");
    }

    if (args[0].equals("--text")) {
      TextDriver driver = new TextDriver();
      driver.start();
    }

    if (args[0].equals("--gui")) {
      GUIDriver driver = new GUIDriver();
      driver.start();

    }

  }

}

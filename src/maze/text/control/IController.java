package maze.text.control;

import java.io.IOException;

import maze.model.MatchOverException;

/**
 * The controller interface for the maze program. The functions here have been designed to give
 * control to the controller, and the primary operation for the controller to function is to make
 * the player move in the maze, find the state of the maze, and shoot arrow to the humpus.
 */
public interface IController {

  /**
   * Start the program, i.e. give control to the controller
   *
   * @throws IOException        this exception occurs when the controller is unable to read and
   *                            write due to data or data type
   * @throws MatchOverException if game is over by loss/win/quit
   */
  public void start() throws IOException, MatchOverException;

}

package maze.text.control.command;

import maze.model.MatchOverException;
import maze.model.Maze;

/**
 * This is a maze command interface which aggregates all the maze command under a common command go
 * to perform an operation on the maze.
 */
public interface MazeCommand {
  /**
   * This function represents the command that is used to perform operation in the maze.
   *
   * @param m the model to use
   * @return returns the state of the player after performing the command
   * @throws MatchOverException if match has ended due loss or win
   */
  public String apply(Maze m) throws MatchOverException;
}

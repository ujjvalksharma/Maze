package maze.text.control.command;

import maze.model.Maze;

/**
 * This class represents the showStatus command that is used by the controller to print the maze.
 */
public class Print implements MazeCommand {


  @Override
  public String apply(Maze model) {

    return model.print();
  }
}

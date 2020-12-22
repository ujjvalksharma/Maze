package maze.text.control.command;

import maze.model.Maze;

/**
 * This class represents the showStatus command that is used by the controller to identify the
 * status of the maze.
 */
public class ShowStatus implements MazeCommand {


  private int playerNumber;

  public ShowStatus(int playerNumber) {

    this.playerNumber = playerNumber;
  }


  @Override
  public String apply(Maze model) {

    return model.showStatus(playerNumber);
  }

}

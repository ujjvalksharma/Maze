package maze.text.control.command;

import maze.model.GlobalEnum;
import maze.model.MatchOverException;
import maze.model.Maze;

/**
 * This class represents the Move command that is used by the controller to perform movement in the
 * move.
 */
public class Move implements MazeCommand {

  private String direction;
  private int playerNumber;

  /**
   * This contructs the object that is required to perfrom movement in the maze.
   *
   * @param direction direction of the movement
   */
  public Move(String direction, int playerNumber) {
    this.direction = direction;
    this.playerNumber = playerNumber;
  }

  @Override
  public String apply(Maze model) throws MatchOverException {


    return model.move(GlobalEnum.direction.valueOf(direction), playerNumber).toString();


  }

}

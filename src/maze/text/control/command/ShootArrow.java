package maze.text.control.command;

import maze.model.GlobalEnum;
import maze.model.MatchOverException;
import maze.model.Maze;

/**
 * This class represents the shootarrow command that is used by the controller to perform shooting
 * of the arrow in the maze.
 */
public class ShootArrow implements MazeCommand {

  private String direction;
  private int distance;
  private int playerNumber;

  /**
   * This contructs the object that is required to perfrom movement in the maze.
   *
   * @param direction direction of the movement
   * @param distance  number of caves arrow can travel in the maze
   */
  public ShootArrow(String direction, int distance, int playerNumber) {
    this.direction = direction;
    this.distance = distance;
    this.playerNumber = playerNumber;
  }

  @Override
  public String apply(Maze model) throws MatchOverException {


    return model.shootArrow(GlobalEnum.direction.valueOf(direction), distance, playerNumber);


  }

}

package maze.model;

import java.util.HashMap;

/**
 * This is a read only interface for the Maze in which we perform only read operations on the maze,
 * and player. It is bascially used to restricte the a user or a view from changing or manipulating
 * the model.
 */
public interface MazeGUI {

  /**
   * Gets the maze size.
   *
   * @return return size of the maze.
   */
  public int getMazeSize();

  /**
   * Gets all the players details. The size can depending upon the number of players.
   *
   * @return array of player
   */
  public Player[] getPlayers();

  /**
   * Gets the distance of the closed wumpus.
   *
   * @param playerNumber playerNumber whose wumpus details has to be returned
   * @return distance from the wumpus
   */
  public int closestDistanceFromWumpus(int playerNumber);

  /**
   * Gets the distance of the closed pit.
   *
   * @param playerNumber playerNumber whose pit details has to be returned
   * @return distance from the pit
   */
  public int closestDistanceFromPit(int playerNumber);

  /**
   * Gets the cells of the maze.
   *
   * @return array of cells
   */
  public Cell[][] getCells();

  /**
   * Gets the current player number.
   *
   * @return returns the player number
   */
  public int getCurrentPlayer();

  /**
   * Gets the restricted player number.
   *
   * @return returns the restircted player number
   */
  public int getRestrictedPlayer();

  /**
   * This returns the update coordinate object which update coordinate based on wrapping and
   * non-wrapping maze.
   *
   * @return updateCoordinate for updating the coordinate if they overlap
   */
  public UpdateCoordinate getUpdateCoordinate();

  /**
   * This is used to get the direction mapping to coordinate.
   *
   * @return directions mapping of coordinate
   */
  public HashMap<GlobalEnum.direction, int[]> getMapDirectionToValue();

  /**
   * This is used to get the direction mapping to integer.
   *
   * @return directions mapping of integer
   */
  public HashMap<GlobalEnum.direction, Integer> getMapWallsToIndex();
}

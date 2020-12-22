package maze.model;

/**
 * This is an interface which will provide functionality for a maze such as shooting arrow, knowing
 * maze state, movement in maze. In this interface we can perform actions on the model along with
 * readOnly features from MazeGUI interface.
 */
public interface Maze extends MazeGUI {
  /**
   * This functions shoots arrow in the maze and returns where the arrow was shot or if the player
   * won/lost.
   *
   * @param direction     direction where arrow is shot
   * @param arrowDistance distance arrow can travel
   * @return the state of the player after shooting the arrow.
   * @throws MatchOverException if match is over/won/quit
   */
  public String shootArrow(GlobalEnum.direction direction,
                           int arrowDistance, int playerNumber) throws MatchOverException;

  /**
   * It moves the player by based on the input direction.
   *
   * @param direction direction the player is about to move
   * @return gets the player's new location with its state
   * @throws MatchOverException if match is over/won/quit
   */
  public Point move(GlobalEnum.direction direction, int playerNumber) throws MatchOverException;

  /**
   * This function tells about the current state of the maze.For instance, distance of pit, avaiable
   * neighbouring tunnel/caves, number of remaining arrows,current position, and presence of walls.
   *
   * @return returns the current state of the player and the maze.
   */
  public String showStatus(int playerNumber);

  /**
   * This prints the maze to the user in string form in which N(North),S(South),W(West),E(West)
   * represent the walls in those directions, and It also releveals in which visited cells
   * Pit/Bat/Wumpu/Gold/Theif is present.
   *
   * @return string form of the maze.
   */
  public String print();

  /**
   * This is used to set a player into restricted mode when one of two player in two player mode
   * loses the match. In restricted mode a player cannot make any move.
   *
   * @param playerNumber playerNumber of the restricted player
   */
  public void setRestrictedPlayer(int playerNumber);

  /**
   * This function is used to switch the players in 2player mode when one player's turn is over.
   */
  public void switchPlayer();

}

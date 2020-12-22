package maze.model;

/**
 * This class represents wrapping maze for a room maze, and provides functionality for movement,
 * shooting arrow, and knowing maze state. Moreover, in this maze we input the number of walls that
 * should exist and update over lapping coordinates.
 */
public class WrappingMaze extends MazeImpl {


  /**
   * This constructs object for wrapping maze by updating over-lapping coordinates and taking input
   * for remainingWalls.
   *
   * @param rowSize        number of rows in the maze
   * @param columnSize     number of column in the maze
   * @param totalBats      total bats in the maze
   * @param totalPits      total pits in the maze
   * @param remainingWalls total remaining walls in the maze
   * @param isPlayer2      true if player2 is present else false
   * @param seed           seed that is used to generate the maze randomly
   */
  public WrappingMaze(int rowSize, int columnSize, int totalBats, int totalPits,
                      int remainingWalls, boolean isPlayer2, int seed) {


    super(rowSize, columnSize,
            totalBats, totalPits, remainingWalls, new NewCoordinate(), isPlayer2, seed);

  }


}

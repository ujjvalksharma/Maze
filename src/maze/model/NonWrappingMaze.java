package maze.model;

/**
 * This class represents non-wrapping maze for a room maze, and provides functionality for movement,
 * shooting arrow, and knowing maze state. Moreover, in this maze the coordinates are not updated
 * and no extra walls are removed.
 */
public class NonWrappingMaze extends MazeImpl {

  /**
   * This constructs object for non-wrapping maze by discarding lapping coordinates and setting
   * remainingWalls to invalid number as it is not valid for this case.
   *
   * @param rowSize        number of rows in the maze
   * @param columnSize     number of column in the maze
   * @param totalBats      total bats in the maze
   * @param totalPits      total pits in the maze
   * @param remainingWalls total remaining walls in the maze
   * @param isPlayer2      true if player2 is present else false
   * @param seed           seed that is used to generate the maze randomly
   */
  public NonWrappingMaze(int rowSize, int columnSize, int totalBats,
                         int totalPits, int remainingWalls, boolean isPlayer2, int seed) {
    super(rowSize, columnSize, totalBats,
            totalPits, remainingWalls, new SameCoordinate(), isPlayer2, seed);


  }


}

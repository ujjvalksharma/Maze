package maze.model;

/**
 * This class is a enumerations class for java for representing direction, type of maze, and maze
 * name.
 */
public class GlobalEnum {
  /**
   * This represents the direction.
   */
  public static enum direction {
    /**
     * North Direction.
     */
    North,
    /**
     * South Direction.
     */
    South,
    /**
     * East Direction.
     */
    East,
    /**
     * West Direction.
     */
    West,
  }


  /**
   * This represents the status of the player playing the game.
   */
  protected static enum status {
    /**
     * Maze game has started.
     */
    Started,
    /**
     * Won the maze game.
     */
    Won,
    /**
     * Lost the maze game.
     */
    Lost
  }

}

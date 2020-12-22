package maze.model;

/**
 * This is an interface that will update the coordinate a x and y coordinate of point based on input
 * limit for wrapping and non-wrapping maze.
 */
public interface UpdateCoordinate {

  /**
   * This function updates x and y by knowing the maximum limit of x and maximum limit of y.
   *
   * @param x    x-coordinate
   * @param y    y-coordinate
   * @param maxX maximum value of x
   * @param maxY maximum value of y
   * @return array which contains updated value of x and y at first and second position respectively
   */
  public int[] updateValues(int x, int y, int maxX, int maxY);
}

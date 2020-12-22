package maze.model;

/**
 * This class is used to implement UpdateCoordinate interface so it will update the coordinate based
 * on maximum limit of x and maximum limit of y. This is implementation is generally used for
 * non-wrapping maze as coordinates can over-lap.
 */
public class NewCoordinate implements UpdateCoordinate {

  @Override
  public int[] updateValues(int x, int y, int maxX, int maxY) {

    if (x < 0) {
      x = maxX - 1;
    }

    if (x >= maxX) {
      x = 0;
    }

    if (y < 0) {
      y = maxY - 1;
    }

    if (y >= maxY) {
      y = 0;
    }

    return new int[]{x, y};
  }

}

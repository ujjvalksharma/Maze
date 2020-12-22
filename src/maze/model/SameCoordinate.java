package maze.model;

/**
 * This class is used to implement UpdateCoordinate interface so it will not update the values. This
 * implementation is generally used for non-wrapping maze as coordinates do not over-lap.
 */
public class SameCoordinate implements UpdateCoordinate {

  @Override
  public int[] updateValues(int x, int y, int maxX, int maxY) {
    return new int[]{x, y};
  }

}
 
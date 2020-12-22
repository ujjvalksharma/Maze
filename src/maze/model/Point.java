package maze.model;

/**
 * This class represents the point in X-Y coordinate plane.
 */
public class Point {

  private int x;


  private int y;

  /**
   * This constructs an object which has x and y coordinate.
   *
   * @param x x-coordinate
   * @param y y-coordinate
   */
  protected Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Copy constructor of the point.
   *
   * @param point point
   */
  protected Point(Point point) {
    this.x = point.x;
    this.y = point.y;
  }

  /**
   * Gets the x-coordinate of the point.
   *
   * @return x-coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y-coordinate of the point.
   *
   * @return y-coordinate
   */
  public int getY() {
    return y;
  }

  /**
   * Sets the x-coordinate of the point.
   *
   * @param x x-coordinate
   */
  protected void setX(int x) {
    this.x = x;
  }

  /**
   * Sets the y-coordinate of the point.
   *
   * @param y y-coordinate
   */
  protected void setY(int y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return "Your location is :[x=" + x + ", y=" + y + "]";
  }
}

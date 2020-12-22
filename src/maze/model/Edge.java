package maze.model;

/**
 * This represents a undirected Edge which contains two points.
 */
public class Edge {


  private Point pointA;
  private Point pointB;

  /**
   * This constructs an edje using four coordinates provided.
   *
   * @param x1 x-coordinate of pointA
   * @param y1 y-coordinate of pointA
   * @param x2 x-coordinate of pointB
   * @param y2 y-coordinate of pointB
   */
  protected Edge(Point pointA, Point pointB) {
    this.pointA = pointA;
    this.pointB = pointB;

  }

  /**
   * This returns PointA of the edge.
   *
   * @return returns pointA
   */
  protected Point getPointA() {
    return pointA;
  }

  /**
   * This set PointA of the edge.
   */
  protected void setPointA(Point pointA) {
    this.pointA = pointA;
  }

  /**
   * This returns PointB of the edge.
   *
   * @return returns pointB
   */
  protected Point getPointB() {
    return pointB;
  }

  /**
   * This set PointB of the edge.
   */
  protected void setPointB(Point pointB) {
    this.pointB = pointB;
  }


}

package maze.model;

/**
 * This class represent the player playing a maze game which has some gold as he moves through the
 * game and has arrows to shoot the wumpus.
 */
public class Player {
  private Point currentLocation;
  private int totalArrows;
  private GlobalEnum.status matchStatus;
  private int totalGold;

  /**
   * This constructs the player with a starting point.
   *
   * @param point starting point of the player
   */
  protected Player(Point point) {
    this.currentLocation = new Point(point);
    matchStatus = GlobalEnum.status.Started;
    totalGold = 0;

  }

  /**
   * Gets the current location of the player.
   *
   * @return returns the player current location
   */
  public Point getCurrentLocation() {
    return currentLocation;
  }


  /**
   * Sets the current location's x coordinate.
   *
   * @param x new x coordinate
   */
  protected void setCurrentLocationX(int x) {
    currentLocation.setX(x);
  }

  /**
   * Sets the current location's y coordinate.
   *
   * @param y new y coordinate
   */
  protected void setCurrentLocationY(int y) {
    currentLocation.setY(y);
  }

  /**
   * Updates the current location to new points.
   *
   * @param point new coordinates of the player
   */
  protected void setCurrentLocation(Point point) {
    currentLocation.setX(point.getX());
    currentLocation.setY(point.getY());
  }

  /**
   * Gets the total arrows with the player.
   *
   * @return arrows with the player
   */
  public int getTotalArrows() {
    return totalArrows;
  }

  /**
   * Sets the arrow with the players.
   *
   * @param totalArrows total arrow with the player
   */
  protected void setTotalArrows(int totalArrows) {
    this.totalArrows = totalArrows;
  }

  /**
   * Gets the match status of the player.
   *
   * @return match status
   */
  public GlobalEnum.status getMatchStatus() {
    return matchStatus;
  }

  /**
   * Sets the match status of player.
   *
   * @param matchStatus returns the match status
   */
  protected void setMatchStatus(GlobalEnum.status matchStatus) {
    this.matchStatus = matchStatus;
  }

  /**
   * Gets the total gold with the player.
   *
   * @return total gold value
   */
  public int getTotalGold() {
    return totalGold;
  }

  /**
   * Sets the gold amount of the player such that it is never less than 0.
   *
   * @param totalGold new gold amount with the player
   */
  protected void setTotalGold(int totalGold) {
    if (totalGold < 0) {
      totalGold = 0;
    }
    this.totalGold = totalGold;
  }

  @Override
  public String toString() {
    return "Player [currentLocation=" + currentLocation
            + ", totalArrows=" + totalArrows + ", matchStatus="
            + matchStatus + ", totalGold=" + totalGold + "]";
  }


}

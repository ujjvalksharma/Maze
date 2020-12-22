package maze.model;


/**
 * This class represents the cell for a maze which contains 4 four at north,south,east,west. These
 * directions are represented by array index in which i=0 (east), i=1(west),i=2(south),i=3 (north).
 * Moreover, each cell has a chance of having wumpus, bat, tunnel,gold, theif, and pit.
 */
public class Cell {

  private boolean[] walls = {true, true, true, true}; //i=0 (east),i=1(west),i=2(south),i=3 (north).
  private int distanceFromWumpus;
  private int distanceFromPit;
  private boolean isTunnel;
  private boolean isBat;
  private boolean isPit;
  private boolean wumpus;
  private int gold;
  private boolean theif;

  protected Cell() {
    this.isTunnel = false;
    this.distanceFromPit = 0;
    this.distanceFromWumpus = 0;
    this.isBat = false;
    this.isPit = false;
    this.wumpus = false;
    this.gold = 0;
    this.theif = false;

  }

  /**
   * Sets value for a the wall in which true means wall is present and false means wall is removed.
   *
   * @param index index of the wall
   * @param value value for the wall
   */
  protected void setWallsByIndex(int index, boolean value) {
    if (index < 0 || index >= walls.length) {
      throw new IllegalArgumentException("invalid wall index");
    }
    walls[index] = value;
  }

  /**
   * Gets value of a wall for which true means wall is present and false means wall is removed.
   *
   * @param index index of the wall
   * @return
   */
  public boolean getWallsIndexValue(int index) {
    if (index < 0 || index >= walls.length) {
      throw new IllegalArgumentException("invalid wall index");
    }
    return walls[index];
  }

  /**
   * Gets walls of the cell in form of array.
   *
   * @return returns walls of the cell
   */
  public boolean[] getWalls() {
    return walls;
  }

  /**
   * Returns whether the current cell is a tunnel.
   *
   * @return whether tunnel is present
   */
  public boolean isTunnel() {
    return isTunnel;
  }

  /**
   * Sets the presence of tunnel in the cell.
   */
  public void setTunnel(boolean isTunnel) {
    this.isTunnel = isTunnel;
  }

  /**
   * Returns whether the current cell has a bat.
   *
   * @return whether bat is present
   */
  public boolean isBat() {
    return isBat;
  }

  /**
   * Sets the presence of bat in the cell.
   */
  protected void setBat(boolean isBat) {
    this.isBat = isBat;
  }

  /**
   * Returns whether the current cell has a pit.
   *
   * @return whether pit is present
   */
  public boolean isPit() {
    return isPit;
  }

  /**
   * Sets the presence of pit in the cell.
   */
  protected void setPit(boolean isPit) {
    this.isPit = isPit;
  }

  /**
   * Returns whether the current cell has a wumpus.
   *
   * @return whether wumpus is present
   */
  public boolean isWumpus() {
    return wumpus;
  }

  /**
   * Sets the presence of wumpus in the cell.
   */
  protected void setWumpus(boolean wumpus) {
    this.wumpus = wumpus;
  }

  /**
   * Returns closest distance from wumpus.
   *
   * @return distance from the wumpus
   */
  public int getDistanceFromWumpus() {
    return distanceFromWumpus;
  }

  /**
   * Sets closest distance from wumpus.
   */
  protected void setDistanceFromWumpus(int distanceFromWumpus) {
    this.distanceFromWumpus = distanceFromWumpus;
  }

  /**
   * Returns closest distance from any pit.
   *
   * @return distance from the pit
   */
  public int getDistanceFromPit() {
    return distanceFromPit;
  }

  /**
   * Sets closest distance from pit.
   */
  protected void setDistanceFromPit(int distanceFromPit) {
    this.distanceFromPit = distanceFromPit;
  }

  /**
   * Gets the gold value in the cell.
   *
   * @return returns gold value
   */
  public int getGold() {
    return gold;
  }

  /**
   * Sets gold value of the cell.
   *
   * @param gold gold value of the cell
   */
  public void setGold(int gold) {
    this.gold = gold;
  }

  /**
   * Returns true if theif is present or returns false.
   *
   * @return
   */
  public boolean isTheif() {
    return theif;
  }

  /**
   * Gets true if theif is present or false.
   *
   * @param theif presence of theif
   */
  public void setTheif(boolean theif) {
    this.theif = theif;
  }

  @Override
  public String toString() {

    StringBuilder cellStr = new StringBuilder("");
    cellStr.append("[");
    if (isTunnel) {
      cellStr.append("Tunnel,");
    }
    if (isBat) {
      cellStr.append("Bat,");
    }
    if (isPit) {
      cellStr.append("Pit,");
    }
    if (wumpus) {
      cellStr.append("wumpus,");
    }
    if (getGold() > 0) {
      cellStr.append("Gold:" + getGold() + ",");
    }
    if (isTheif()) {
      cellStr.append("Theif,");
    }
    cellStr.append("Walls:[");
    if (walls[0]) {
      cellStr.append(",E");
    }
    if (walls[1]) {
      cellStr.append(",W");
    }
    if (walls[2]) {
      cellStr.append(",S");
    }
    if (walls[3]) {
      cellStr.append(",N");
    }
    cellStr.append("]");
    cellStr.append("]");
    return cellStr.toString();
  }


}

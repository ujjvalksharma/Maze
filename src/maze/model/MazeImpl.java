package maze.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import maze.model.GlobalEnum.direction;

/**
 * This class is an implementation of maze interface in which we implements function for movement,
 * throwing arrow and finding state of player and maze.
 */
public class MazeImpl implements Maze, MazeGUI {


  private Point wumpusPosition;
  private int numberOfWallsRemoved;
  private List<Edge> allEdjes;
  private HashMap<String, Integer> mapParentCount;
  private Cell[][] cells;
  private String[][] parent;
  private int totalBats;
  private int totalPits;
  private Player[] players;
  private int remainingWalls;
  private int columnSize;
  private int rowSize;
  private Point startPosition;
  private HashMap<GlobalEnum.direction, int[]> mapDirectionToValue;
  private HashMap<GlobalEnum.direction, Integer> mapWallsToIndex;
  private UpdateCoordinate updateCoordinate;
  private String matchWonMsg = "Match has been won. We cannot shoot arrow now!!";
  private String arrowDistanceMsg = "Arrow distance is negative";
  private final String winnerMsg = "You hit the wumpus. You won!!";
  private final String tunnelMsg = "Tunnel are present are present at: ";
  private final String caveMsg = "Caves are present are present at: ";
  private final String fellPitMsg = "You lose!! You fell in pit at: ";
  private final String batCapturedMsg = "Bat caught you and transfered you to ";
  private final String wumpusCaughtMsg = "You lose!! Wumpus caught you at ";
  private final String noTunnelsMsg = "No tunnels are present";
  private final String noCavesMsg = "No caves are present";
  private final String noBatMsg = "total Bats is less than 0";
  private final String noPitMsg = "total Pits is less than 0";
  private final String wumpusName = "wumpus";
  private final String pitName = "pit";
  protected final String remainingWallsMsg = "Remaining walls is less than 0";
  private final int maximumGoldCoins = 101;
  private int currentPlayer;
  private int restrictedPlayer;
  private int totalPlayers;
  private Random random;
  private String[][] mazeStr;
  private boolean isPlayer2;

  /**
   * This constructs maze objects.
   *
   * @param rowSize          row size of the maze.
   * @param columnSize       column size of the maze
   * @param totalBats        total bats present in the maze
   * @param totalPits        total pits present in the maze
   * @param remainingWalls   total walls remaining in the maze which is -1 for non-wrapping
   * @param updateCoordinate object to determine whether over wrapping coordinates should be
   *                         updated
   * @param isPlayer2        true if player2 is present else false
   * @param seed             seed that is used to generate the maze randomly
   */
  protected MazeImpl(int rowSize, int columnSize,
                     int totalBats, int totalPits, int remainingWalls,
                     UpdateCoordinate updateCoordinate, boolean isPlayer2, int seed) {

    if (updateCoordinate == null) {
      throw new IllegalArgumentException("update Coordinate is missing");
    }
    if (rowSize < 0) {
      throw new IllegalArgumentException("row size is less than 0");
    }

    if (columnSize < 0) {
      throw new IllegalArgumentException("column size is less than 0");
    }

    if (totalBats < 0) {
      throw new IllegalArgumentException(noBatMsg);
    }

    if (totalPits < 0) {
      throw new IllegalArgumentException(noPitMsg);
    }

    if (remainingWalls < 0) {
      throw new IllegalArgumentException(remainingWallsMsg);
    }
    this.isPlayer2 = isPlayer2;
    if (isPlayer2) {
      this.players = new Player[2];

    } else {
      this.players = new Player[1];
    }
    mazeStr = new String[rowSize][columnSize];

    for (int i = 0; i < mazeStr.length; i++) {
      for (int j = 0; j < mazeStr.length; j++) {
        mazeStr[i][j] = "not available";
      }
    }
    this.currentPlayer = 0;
    this.restrictedPlayer = -1;
    this.totalPlayers = 0;
    this.cells = new Cell[rowSize][columnSize];
    this.updateCoordinate = updateCoordinate;
    this.remainingWalls = remainingWalls;
    this.columnSize = columnSize;
    this.rowSize = rowSize;
    this.mapParentCount = new HashMap<String, Integer>();
    this.allEdjes = new ArrayList<>();
    this.totalBats = totalBats;
    this.totalPits = totalPits;
    this.mapDirectionToValue = new HashMap<>();
    this.mapWallsToIndex = new HashMap<>();
    this.random = new Random();
    this.random.setSeed(seed);
    setMapForDirection();
    this.parent = new String[rowSize][columnSize];
    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {
        mapParentCount.put(i + "," + j, 1);
        parent[i][j] = "";
        cells[i][j] = new Cell();
      }
    }
    generateAllEdjes();
    this.numberOfWallsRemoved = allEdjes.size() - remainingWalls;
    generateMaze();
    removeExtraEdjes();
    addTunnels();

    players[0] = getPlayer1();
    if (isPlayer2) {
      players[1] = getPlayer2();
    }


    totalPlayers++;
    players[0].setTotalArrows(getRandomInteger(rowSize * columnSize));

    if (isPlayer2) {
      players[1].setTotalArrows(getRandomInteger(rowSize * columnSize));
      totalPlayers++;
    }

    fillWumpus();
    fillBatsAndPits();
    fillTheifAndGold();
    fillWumpusDistance();
    fillPitDistance();
    if (players[0] != null) {
      mazeStr[players[0].getCurrentLocation().getX()]
              [players[0].getCurrentLocation().getY()]
              = cells[players[0].getCurrentLocation().getX()]
              [players[0].getCurrentLocation().getY()].toString();
    }
    if (isPlayer2 && players[1] != null) {
      mazeStr[players[1].getCurrentLocation().getX()]
              [players[1].getCurrentLocation().getY()]
              = cells[players[1].getCurrentLocation().getX()]
              [players[1].getCurrentLocation().getY()].toString();
    }


  }

  /**
   * Returns the player1 locations based on the random seed value.
   *
   * @return player1 location
   */
  private Player getPlayer1() {

    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {

        if (!cells[i][j].isTunnel()) {
          int probabilityOfPlayer = getRandomInteger(2);
          if (probabilityOfPlayer == 1) {
            return new Player(new Point(i, j));

          }

        }
      }

    }

    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {

        if (!cells[i][j].isTunnel()) {

          return new Player(new Point(i, j));


        }
      }

    }

    return null;

  }

  /**
   * Returns the player2 locations based on the random seed value.
   *
   * @return player2 location
   */
  private Player getPlayer2() {


    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {


        if (!cells[i][j].isTunnel()) {
          int probabilityOfPlayer = getRandomInteger(2);
          if (probabilityOfPlayer == 1) {
            return new Player(new Point(i, j));
          }

        }
      }

    }

    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {

        if (!cells[i][j].isTunnel()) {

          return new Player(new Point(i, j));


        }
      }

    }

    return null;

  }


  /**
   * This is a helper function that sets hashmap with direction, so we avoid boiler of if else for
   * getting the direction.
   */
  private void setMapForDirection() {
    this.mapDirectionToValue.put(GlobalEnum.direction.East, new int[]{0, 1});
    this.mapDirectionToValue.put(GlobalEnum.direction.West, new int[]{0, -1});
    this.mapDirectionToValue.put(GlobalEnum.direction.North, new int[]{-1, 0});
    this.mapDirectionToValue.put(GlobalEnum.direction.South, new int[]{1, 0});
    this.mapWallsToIndex.put(GlobalEnum.direction.East, 0);
    this.mapWallsToIndex.put(GlobalEnum.direction.West, 1);
    this.mapWallsToIndex.put(GlobalEnum.direction.South, 2);
    this.mapWallsToIndex.put(GlobalEnum.direction.North, 3);
  }

  /**
   * This function return true if player2 is present at a cell location or else false.
   *
   * @param i row index of the cell
   * @param j column index of the cell
   * @return true if player2 is present or else false
   */
  private boolean player2PresenceAtCell(int i, int j) {

    return isPlayer2 && players[1].getCurrentLocation().getX() == i
            && players[1].getCurrentLocation().getY() == j;

  }

  /**
   * This function return true if player1 is present at a cell location or else false.
   *
   * @param i row index of the cell
   * @param j column index of the cell
   * @return true if player1 is present or else false
   */
  private boolean player1PresenceAtCell(int i, int j) {

    return players[0] == null && players[0].getCurrentLocation().getX() == i
            && players[0].getCurrentLocation().getY() == j;

  }

  /**
   * This functions fills wumpus at a random position in the maze.
   */
  private void fillWumpus() {

    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {
        if (!cells[i][j].isWumpus()
                && !player2PresenceAtCell(i, j)
                && !player1PresenceAtCell(i, j)
                && !cells[i][j].isTunnel()
        ) {

          int probabilityOfWumpus = getRandomInteger(2);
          if (probabilityOfWumpus == 1) {
            cells[i][j].setWumpus(true);
            this.wumpusPosition = new Point(i, j);
            return;
          }
        }

      }

    }


    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {
        if (!cells[i][j].isWumpus()
                && !player2PresenceAtCell(i, j)
                && !player1PresenceAtCell(i, j)
                && !cells[i][j].isTunnel()
        ) {
          if (wumpusPosition == null) {
            this.wumpusPosition = new Point(i, j);
            cells[i][j].setWumpus(true);
            return;
          }

        }

      }
    }

  }

  /**
   * This function fills bats and pits at random position in the maze.
   */
  private void fillBatsAndPits() {

    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {
        if (!cells[i][j].isWumpus()
                && !player1PresenceAtCell(i, j)
                && !player2PresenceAtCell(i, j)
                && !cells[i][j].isTunnel()) {

          int probabilityOfBats = getRandomInteger(2);
          int probabilityOfPits = getRandomInteger(2);

          if (probabilityOfBats > 0 && totalBats > 0) {
            totalBats--;
            cells[i][j].setBat(true);
          }

          if (probabilityOfPits > 0 && totalPits > 0) {
            totalPits--;
            cells[i][j].setPit(true);
          }
        }
      }
    }

    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {

        if (!cells[i][j].isWumpus()
                && !player1PresenceAtCell(i, j)
                && !player2PresenceAtCell(i, j)
                && !cells[i][j].isTunnel()) {
          if (totalBats > 0 && !cells[i][j].isBat()) {
            totalBats--;
            cells[i][j].setBat(true);
          }

          if (totalPits > 0 && !cells[i][j].isPit()) {
            totalPits--;
            cells[i][j].setPit(true);
          }
        }
      }
    }

  }

  /**
   * This function fill gold and theif in a cell based on a random seed.
   */
  private void fillTheifAndGold() {

    int numberOfGoldCell = (int) Math.round(0.7 * rowSize * columnSize);
    int numberOfTheifCell = (int) Math.round(0.4 * rowSize * columnSize);
    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {

        int probabilityOfGold = getRandomInteger(2);
        int probabilityOfThief = getRandomInteger(2);

        int currentGoldAmount = 0;
        boolean thiefExist = false;
        if (!cells[i][j].isWumpus()
                && !player2PresenceAtCell(i, j)
                && !player1PresenceAtCell(i, j)
                && !cells[i][j].isPit()
                && !cells[i][j].isTunnel()) {

          if (probabilityOfGold > 0 && numberOfGoldCell > 0) {
            numberOfGoldCell--;
            currentGoldAmount = getRandomInteger(maximumGoldCoins);
          }
          if (probabilityOfThief > 0 && numberOfTheifCell > 0) {
            numberOfTheifCell--;
            thiefExist = true;
          }

        }
        cells[i][j].setGold(currentGoldAmount);
        cells[i][j].setTheif(thiefExist);
      }
    }

    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {

        if (!cells[i][j].isWumpus()
                && !player2PresenceAtCell(i, j)
                && !player1PresenceAtCell(i, j)
                && !cells[i][j].isPit()
                && !cells[i][j].isTunnel()) {
          if (numberOfGoldCell > 0) {
            if (cells[i][j].getGold() == 0) {
              cells[i][j].setGold(getRandomInteger(maximumGoldCoins));
              numberOfGoldCell--;
            }
          }

          if (numberOfTheifCell > 0) {
            if (!cells[i][j].isTheif()) {
              cells[i][j].setTheif(true);
              numberOfTheifCell--;
            }
          }
        }
      }
    }
  }

  /**
   * This function loops randomly through every cell for removing the walls.
   */
  private void generateMaze() {
    List<Edge> temp = new ArrayList<>(allEdjes);

    while (allEdjes.size() != 0) {
      int nodeIndex = getRandomInteger(allEdjes.size());
      Edge edge = allEdjes.get(nodeIndex);
      allEdjes.remove(nodeIndex);
      removeWalls(edge.getPointA().getX(),
              edge.getPointA().getY(), edge.getPointB().getX(),
              edge.getPointB().getY());
    }

    for (int i = 0; i < temp.size(); i++) {
      allEdjes.add(temp.get(i));
    }
  }

  /**
   * This function generates all edges in the maze for wrapping and non-wrapping case depending on
   * the whether we should update coordinate.
   */
  private void generateAllEdjes() {

    Set<String> visitedEdje = new HashSet<>();
    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {
        int[] row = {0, 0, 1, -1};
        int[] col = {1, -1, 0, 0};

        for (int k = 0; k < row.length; k++) {
          int newX = i + row[k];
          int newY = j + col[k];


          int[] neighArr = updateCoordinate.updateValues(newX, newY, rowSize, columnSize);
          newX = neighArr[0];
          newY = neighArr[1];

          if (newX >= rowSize || newY >= columnSize || newX < 0 || newY < 0) {
            continue;
          }

          String currentEdje = i + "," + j + "-" + newX + "," + newY;
          String oppositeEdje = newX + "," + newY + "-" + i + "," + j;
          if (visitedEdje.contains(currentEdje) || visitedEdje.contains(oppositeEdje)) {
            continue;
          }

          visitedEdje.add(currentEdje);
          allEdjes.add(new Edge(new Point(i, j), new Point(newX, newY)));
        }

      }
    }

  }

  /**
   * This function finds a random integer between two number. It includes first argument and
   * excludes second argument.
   *
   * @param max value least maximum greater than the value generated by the function
   * @return returns the random integer
   */
  public int getRandomInteger(int max) {
    return random.nextInt(max);
  }

  /**
   * This function removes the wall between two edges if they belong to different set.
   *
   * @param currentX x-coordinate of start vertex
   * @param currentY y-coordinate of start vertex
   * @param neighX   x-coordinate of end vertex
   * @param neighY   y-coordinate of end vertex
   */
  private void removeWalls(int currentX, int currentY, int neighX, int neighY) {


    String startParent = find(currentX + "," + currentY);
    String[] startParentIndex = startParent.split(",");

    int rowStartParentIndex = Integer.parseInt(startParentIndex[0]);
    int columnStartParentIndex = Integer.parseInt(startParentIndex[1]);

    String endParent = find(neighX + "," + neighY);
    String[] endParentIndex = endParent.split(",");
    int rowEndParentIndex = Integer.parseInt(endParentIndex[0]);
    int columnEndParentIndex = Integer.parseInt(endParentIndex[1]);
    if (!startParent.equals(endParent)) {


      int[] row = {0, 0, 1, -1};
      int[] col = {1, -1, 0, 0};

      int directionInt = -1;
      for (int i = 0; i < row.length; i++) {
        int tempX = currentX + row[i];
        int tempY = currentY + col[i];

        if (tempX == neighX && tempY == neighY) {
          directionInt = i;
          break;
        }
      }
      removeCellWall(directionInt, currentX, currentY, neighX, neighY);

      union(rowStartParentIndex, columnStartParentIndex,
              rowEndParentIndex, columnEndParentIndex);

    }


  }

  /**
   * This function combines two cell's parent.
   *
   * @param x1 row index of 1st cell
   * @param y1 column index of 1st cell
   * @param x2 row index of 2nd cell
   * @param y2 column index of 2nt cell
   */
  private void union(int x1, int y1, int x2, int y2) {

    if (mapParentCount.get(x2 + "," + y2)
            > mapParentCount.get(x1 + "," + y1)) {
      parent[x1][y1] = x2 + "," + y2;
      int count = mapParentCount.get(x2 + "," + y2);
      count++;
      mapParentCount.put(x2 + "," + y2, count);
    } else {
      parent[x2][y2] = x1 + "," + y1;
      int count = mapParentCount.get(x1 + "," + y1);
      count++;
      mapParentCount.put(x1 + "," + y1, count);
    }
  }

  /**
   * This function find the parent cell of a cell.
   *
   * @param indexStr cell index in the form of a string for instance "rowIndex,ColumnIndex"
   * @return returns the parent cell index in the form a string for instance "rowIndex,ColumnIndex"
   */
  private String find(String indexStr) {

    String[] indexs = indexStr.split(",");
    int r = Integer.parseInt(indexs[0]);
    int c = Integer.parseInt(indexs[1]);

    if (parent[r][c].equals("")) {
      return indexStr;
    }

    return find(parent[r][c]);
  }

  /**
   * Removes extra edges for wrapping roomaze. It will not work non-wrapping as remainingWalls is
   * -1.
   */
  private void removeExtraEdjes() {


    while (numberOfWallsRemoved > 0 && allEdjes.size() > 0 && remainingWalls > 0) {
      int nodeIndex = getRandomInteger(allEdjes.size());
      Edge edge = allEdjes.get(nodeIndex);
      allEdjes.remove(nodeIndex);
      int startX = edge.getPointA().getX();
      int startY = edge.getPointA().getY();
      int endX = edge.getPointB().getX();
      int endY = edge.getPointB().getY();

      int[] row = {0, 0, 1, -1};
      int[] col = {1, -1, 0, 0};

      int directionInt = -1;
      for (int i = 0; i < row.length; i++) {
        int tempX = startX + row[i];
        int tempY = startY + col[i];


        int[] neighArr = updateCoordinate.updateValues(tempX,
                tempY, rowSize, columnSize);
        tempX = neighArr[0];
        tempY = neighArr[1];
        if (tempX == endX && tempY == endY) {
          directionInt = i;

          removeCellWall(directionInt, startX, startY, endX, endY);
          break;
        }
      }
    }
  }

  /**
   * This function removes the wall between two different cells.
   *
   * @param i  integer value of direction i=0(wall at East), i=1(wall at West), i=2(wall at
   *           South),i=3(wall at North)
   * @param x1 1st cell x-coordinate
   * @param y1 1st cell y-coordinate
   * @param x2 2nd cell x-coordinate
   * @param y2 2nd cell y-coordinate
   */
  private void removeCellWall(int i, int x1, int y1, int x2, int y2) {
    numberOfWallsRemoved--;
    if (i == 0
            && cells[x1][y1].getWallsIndexValue(0)
            && cells[x2][y2].getWallsIndexValue(1)) {
      cells[x1][y1].setWallsByIndex(0, false);
      cells[x2][y2].setWallsByIndex(1, false);
    } else if (i == 1
            && cells[x1][y1].getWallsIndexValue(1)
            && cells[x2][y2].getWallsIndexValue(0)) {
      cells[x1][y1].setWallsByIndex(1, false);
      cells[x2][y2].setWallsByIndex(0, false);
    } else if (i == 2
            && cells[x1][y1].getWallsIndexValue(2)
            && cells[x2][y2].getWallsIndexValue(3)) {
      cells[x1][y1].setWallsByIndex(2, false);
      cells[x2][y2].setWallsByIndex(3, false);
    } else if (i == 3
            && cells[x1][y1].getWallsIndexValue(3)
            && cells[x2][y2].getWallsIndexValue(2)) {
      cells[x1][y1].setWallsByIndex(3, false);
      cells[x2][y2].setWallsByIndex(2, false);
    } else {
      numberOfWallsRemoved++;
    }
  }

  /**
   * This function makes a cell into tunnel if the cell contains only two walls.
   */
  private void addTunnels() {

    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {
        int totalWalls = 0;

        for (int k = 0; k < cells[i][j].getWalls().length; k++) {
          if (cells[i][j].getWallsIndexValue(k)) {
            totalWalls++;
          }
        }

        if (totalWalls == 2) {
          cells[i][j].setTunnel(true);
        }
      }
    }
  }

  /**
   * This functions reveals whether the the playerNumber entered is valid.
   *
   * @param playerNumber player number of the player
   */
  private void verifyPlayerNumber(int playerNumber) {

    if (playerNumber > 0 && !isPlayer2) {
      throw new IllegalArgumentException(playerNumber + " doesn't exist!!");
    }
    if (playerNumber > 1 || playerNumber < 0) {
      throw new IllegalArgumentException("Only two player are allowed"
              + ". Player number can be 0 or 1");
    }
  }

  @Override
  public String shootArrow(GlobalEnum.direction direction,
                           int arrowDistance, int playerNumber) throws MatchOverException {

    verifyPlayerNumber(playerNumber);

    if (players[playerNumber].getMatchStatus() != GlobalEnum.status.Started
            || players[playerNumber].getTotalArrows() <= 0) {
      throw new IllegalStateException("This match is over for you");
    }

    if (arrowDistance <= 0) {
      throw new IllegalArgumentException(arrowDistanceMsg);
    }
    players[playerNumber].setTotalArrows(players[playerNumber].getTotalArrows() - 1);
    int tempX = players[playerNumber]
            .getCurrentLocation().getX()
            + mapDirectionToValue.get(direction)[0];
    int tempY = players[playerNumber]
            .getCurrentLocation().getY()
            + mapDirectionToValue.get(direction)[1];

    int[] neighArr = updateCoordinate.updateValues(tempX, tempY,
            rowSize, columnSize);
    tempX = neighArr[0];
    tempY = neighArr[1];
    String arrowMsg = "Arrow is used";
    if (tempX >= 0 && tempX < rowSize && tempY >= 0 && tempY < columnSize) {
      if (cells[tempX][tempY].isTunnel()) {
        Set<String> visitedCells = new HashSet<String>();
        visitedCells.add(players[playerNumber].getCurrentLocation().getX(
        ) + "," + players[playerNumber].getCurrentLocation().getY());
        int[] arrowPosition = moveArrowThroughTunnel(tempX, tempY, visitedCells);
        int[] incrArr = mapDirectionToValue.get(direction);
        arrowDistance--;
        arrowMsg = throwArrowsInCaveHelper(direction, incrArr[0],
                incrArr[1], arrowPosition[0], arrowPosition[1], arrowDistance, playerNumber);
      } else {
        int[] incrArr = mapDirectionToValue.get(direction);
        arrowMsg = throwArrowsInCaveHelper(direction,
                incrArr[0], incrArr[1], players[playerNumber].getCurrentLocation().getX()
                , players[playerNumber].getCurrentLocation().getY(), arrowDistance, playerNumber);

      }
    }


    if (!arrowMsg.equals(winnerMsg) && players[playerNumber].getTotalArrows() == 0) {
      players[playerNumber].setMatchStatus(GlobalEnum.status.Lost);

      if (totalPlayers == 1) {
        if (players[0] != null
                && players[0].getMatchStatus() == GlobalEnum.status.Lost) {
          throw new MatchOverException("You are out of arrows. Match Over you lost!!");
        }
        if (isPlayer2 && players[1] != null
                && players[1].getMatchStatus() == GlobalEnum.status.Lost) {
          throw new MatchOverException("You are out of arrows. Match Over you lost!!");
        }
      }

      if (totalPlayers == 2) {
        if (isPlayer2 && players[1].getMatchStatus()
                == GlobalEnum.status.Lost
                && players[0].getMatchStatus() == GlobalEnum.status.Lost) {
          throw new MatchOverException("You are out of arrows. Match Over you lost!!");
        }

      }

    }

    if (players[playerNumber].getMatchStatus() == GlobalEnum.status.Won) {
      throw new MatchOverException(matchWonMsg);
    }


    return arrowMsg;

  }


  /**
   * This function moves the player through tunnel depending on the direction.
   *
   * @param x            current x-coordinate of the player in the tunnel.
   * @param y            current y-coordinate of the player in the tunnel.
   * @param visitedCells visited cells so a visited tunnel opening is not visited again.
   */
  private int[] moveArrowThroughTunnel(int x, int y, Set<String> visitedCells) {

    visitedCells.add(x + "," + y);

    if (!cells[x][y].isTunnel()) {
      return new int[]{x, y};
    }
    for (Entry<GlobalEnum.direction, int[]> entry : mapDirectionToValue.entrySet()) {
      int tempX = x + entry.getValue()[0];
      int tempY = y + entry.getValue()[1];

      if (!visitedCells.contains(tempX + "," + tempY)) {

        int[] neighArr = updateCoordinate.updateValues(tempX,
                tempY, rowSize, columnSize);
        tempX = neighArr[0];
        tempY = neighArr[1];
        if (tempX >= 0 && tempX < rowSize && tempY >= 0 && tempY < columnSize) {
          if (!cells[x][y].getWallsIndexValue(mapWallsToIndex.get(entry.getKey()))) {
            visitedCells.add(tempX + "," + tempY);
            return moveArrowThroughTunnel(tempX, tempY, visitedCells);
          }
        }
      }
    }
    return new int[]{x, y};

  }

  /**
   * This is helper function for throwing the arrow to the wumpus such that the arrow travels in a
   * straight line.
   *
   * @param xIncr         distance of movement in x direction
   * @param yIncr         distance of movement in x direction
   * @param x             current x-coordinate of the arrow
   * @param y             current y-coordinate of the arrow
   * @param arrowDistance remaining distance a arrow can travel
   * @param playerNumber  player number of the player
   * @return returns whether wumpus died or the arrow was wasted
   */
  private String throwArrowsInCaveHelper(GlobalEnum.direction direction,
                                         int xIncr, int yIncr,
                                         int x, int y, int arrowDistance, int playerNumber) {

    if (x < 0 || y < 0 || x >= rowSize || y >= columnSize) {
      return "Arrow went out of maze.";
    }

    if (x == wumpusPosition.getX() && y == wumpusPosition.getY() && arrowDistance == 0) {
      players[playerNumber].setMatchStatus(GlobalEnum.status.Won);
      return winnerMsg;
    }
    if (arrowDistance == 0 || cells[x][y].getWallsIndexValue(mapWallsToIndex.get(direction))) {
      return "Your arrow is wasted it hit at " + x + "," + y;
    }
    return throwArrowsInCaveHelper(direction, xIncr, yIncr,
            x + xIncr, yIncr + y, arrowDistance - 1, playerNumber);
  }

  /**
   * This function fill closest wumpus distance to all cells in the maze.
   */
  private void fillWumpusDistance() {


    Queue<Point> bfsQueue = new LinkedList<>();
    bfsQueue.add(new Point(wumpusPosition));
    while (!bfsQueue.isEmpty()) {

      Point point = bfsQueue.poll();
      int[] row = {0, 0, 1, -1};
      int[] col = {1, -1, 0, 0};

      for (int i = 0; i < row.length; i++) {
        int tempX = point.getX() + row[i];
        int tempY = point.getY() + col[i];

        int[] neighArr = updateCoordinate.updateValues(tempX, tempY,
                rowSize, columnSize);
        tempX = neighArr[0];
        tempY = neighArr[1];

        if (tempX >= 0 && tempX < rowSize && tempY >= 0 && tempY < columnSize) {

          cells[tempX][tempY].setDistanceFromWumpus(1);

        }
      }

    }


    for (Entry<GlobalEnum.direction, int[]> entry : mapDirectionToValue.entrySet()) {

      int tempX = wumpusPosition.getX() + entry.getValue()[0];
      int tempY = wumpusPosition.getY() + entry.getValue()[1];

      int[] neighArr = updateCoordinate.updateValues(tempX, tempY,
              rowSize, columnSize);
      tempX = neighArr[0];
      tempY = neighArr[1];
      if (tempX >= 0 && tempX < rowSize && tempY >= 0
              && tempY < columnSize && cells[tempX][tempY].isTunnel()) {
        Set<String> visitedCells = new HashSet<String>();
        visitedCells.add(tempX + "," + tempY);
        visitedCells.add(wumpusPosition.getX() + "," + wumpusPosition.getY());
        fillSmellThroughTunnelHelper(tempX, tempY, visitedCells, wumpusName);

      }
    }

  }

  /**
   * This is a helper function that reaches to a cave from a tunnel to fill smell of wumpus at that
   * cave.
   *
   * @param x            current x-coordinate of the player in the tunnel.
   * @param y            current y-coordinate of the player in the tunnel.
   * @param visitedCells visited cells so a visited tunnel opening is not visited again.
   */
  private void fillSmellThroughTunnelHelper(int x, int y, Set<String> visitedCells,
                                            String obstacleName) {

    visitedCells.add(x + "," + y);
    if (!cells[x][y].isTunnel()) {
      if (obstacleName.equals(wumpusName)) {
        cells[x][y].setDistanceFromWumpus(1);
      }
      if (obstacleName.equals(pitName)) {
        cells[x][y].setDistanceFromPit(1);
      }
      return;
    }
    for (Entry<GlobalEnum.direction, int[]> entry : mapDirectionToValue.entrySet()) {
      int tempX = x + entry.getValue()[0];
      int tempY = y + entry.getValue()[1];

      if (!visitedCells.contains(tempX + "," + tempY)) {

        int[] neighArr = updateCoordinate.updateValues(tempX, tempY,
                rowSize, columnSize);
        tempX = neighArr[0];
        tempY = neighArr[1];
        if (tempX >= 0 && tempX < rowSize && tempY >= 0 && tempY < columnSize) {
          if (!cells[x][y].getWallsIndexValue(mapWallsToIndex.get(entry.getKey()))) {
            visitedCells.add(tempX + "," + tempY);
            fillSmellThroughTunnelHelper(tempX, tempY, visitedCells, obstacleName);
            break;
          }
        }
      }
    }

  }

  /**
   * This function fills the closest pit distance to cells in the maze.
   */
  private void fillPitDistance() {

    Queue<Point> bfsQueue = new LinkedList<>();
    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {
        if (cells[i][j].isPit()) {
          bfsQueue.add(new Point(new Point(i, j)));

          for (Entry<GlobalEnum.direction, int[]> entry : mapDirectionToValue.entrySet()) {

            int tempX = i + entry.getValue()[0];
            int tempY = j + entry.getValue()[1];

            int[] neighArr = updateCoordinate.updateValues(tempX, tempY,
                    rowSize, columnSize);
            tempX = neighArr[0];
            tempY = neighArr[1];
            if (tempX >= 0 && tempX < rowSize && tempY >= 0 && tempY < columnSize
                    && cells[tempX][tempY].isTunnel()) {
              Set<String> visitedCells = new HashSet<String>();
              visitedCells.add(tempX + "," + tempY);
              visitedCells.add(i + "," + j);
              fillSmellThroughTunnelHelper(tempX, tempY, visitedCells, pitName);

            }
          }


        }
      }
    }
    while (!bfsQueue.isEmpty()) {

      Point point = bfsQueue.poll();
      int[] row = {0, 0, 1, -1};
      int[] col = {1, -1, 0, 0};

      for (int i = 0; i < row.length; i++) {
        int tempX = point.getX() + row[i];
        int tempY = point.getY() + col[i];

        int[] neighArr = updateCoordinate.updateValues(tempX, tempY,
                rowSize, columnSize);
        tempX = neighArr[0];
        tempY = neighArr[1];

        if (tempX >= 0 && tempX < rowSize && tempY >= 0 && tempY < columnSize) {

          cells[tempX][tempY].setDistanceFromPit(1);

        }

      }

    }
  }


  /**
   * Returns available tunnels from the current postion of the player.
   *
   * @return returns neighbouring tunnels.
   */
  private String findTunnels(int playerNumber) {

    int[] row = {0, 0, 1, -1};
    int[] col = {1, -1, 0, 0};
    StringBuilder allTunnelSb = new StringBuilder(tunnelMsg);
    for (int i = 0; i < row.length; i++) {

      int tempX = players[playerNumber].getCurrentLocation().getX() + row[i];
      int tempY = players[playerNumber].getCurrentLocation().getY() + col[i];

      int[] coorArr = updateCoordinate.updateValues(tempX, tempY, rowSize, columnSize);
      tempX = coorArr[0];
      tempY = coorArr[1];

      if (tempX >= 0
              && tempX < rowSize
              && tempY >= 0
              && tempY < columnSize &&
              cells[tempX][tempY].isTunnel()) {
        allTunnelSb.append("(" + tempX + "," + tempY + "),");
      }
    }
    if (allTunnelSb.toString().equals(tunnelMsg)) {

      return noTunnelsMsg;
    }
    return allTunnelSb.toString();
  }

  /**
   * Returns available caves from the current postion of the player.
   *
   * @return returns neighbouring caves.
   */
  private String findCaves(int playerNumber) {


    StringBuilder allCavesSb = new StringBuilder(caveMsg);
    for (Entry<GlobalEnum.direction, int[]> entry : mapDirectionToValue.entrySet()) {
      int tempX = players[playerNumber].getCurrentLocation().getX() + entry.getValue()[0];
      int tempY = players[playerNumber].getCurrentLocation().getY() + entry.getValue()[1];


      int[] neighArr = updateCoordinate.updateValues(tempX,
              tempY, rowSize, columnSize);
      tempX = neighArr[0];
      tempY = neighArr[1];
      if (tempX >= 0 && tempX < rowSize && tempY >= 0 && tempY < columnSize) {
        if (!cells[players[playerNumber].getCurrentLocation().getX()]
                [players[playerNumber].getCurrentLocation().getY()]
                .getWallsIndexValue(mapWallsToIndex.get(entry.getKey()))) {
          allCavesSb.append("(" + tempX + "," + tempY + "),");
        }
      }

    }

    if (allCavesSb.toString().equals(caveMsg)) {
      return noCavesMsg;
    }

    return allCavesSb.toString();
  }


  /**
   * Return the player's location.
   *
   * @return gets the player's current location
   */
  private String currentPosition(int playerNumber) {
    return players[playerNumber].getCurrentLocation().getX()
            + ","
            + players[playerNumber].getCurrentLocation().getY();
  }


  /**
   * Return the closest pit distance in the current player's location.
   *
   * @return gets closest pit distance
   */
  public int closestDistanceFromPit(int playerNumber) {
    return cells[players[playerNumber].getCurrentLocation().getX()]
            [players[playerNumber].getCurrentLocation().getY()].getDistanceFromPit();
  }


  /**
   * Return the closest wumpus distance in the current player's location.
   *
   * @return gets closest pit distance
   */
  public int closestDistanceFromWumpus(int playerNumber) {
    return cells[players[playerNumber].getCurrentLocation().getX()]
            [players[playerNumber].getCurrentLocation().getY()].getDistanceFromWumpus();
  }


  @Override
  public Point move(GlobalEnum.direction direction, int playerNumber) throws MatchOverException {

    verifyPlayerNumber(playerNumber);

    if (restrictedPlayer == playerNumber) {
      throw new IllegalArgumentException("player cannot move. He is restricted");
    }

    if (players[playerNumber].getMatchStatus() != GlobalEnum.status.Started) {
      throw new IllegalStateException("player cannot take his turn");
    }

    int originalX = players[playerNumber].getCurrentLocation().getX();
    int originalY = players[playerNumber].getCurrentLocation().getY();
    int tempX = players[playerNumber].getCurrentLocation().getX()
            + mapDirectionToValue.get(direction)[0];
    int tempY = players[playerNumber].getCurrentLocation().getY()
            + mapDirectionToValue.get(direction)[1];

    int[] neighArr = updateCoordinate.updateValues(tempX, tempY, rowSize, columnSize);
    tempX = neighArr[0];
    tempY = neighArr[1];

    if (tempX >= 0 && tempX < rowSize && tempY >= 0 && tempY < columnSize
            && !cells[originalX][originalY]
            .getWallsIndexValue(mapWallsToIndex.get(direction))) {
      if (cells[tempX][tempY].isTunnel()) {
        Set<String> visitedCells = new HashSet<String>();
        visitedCells.add(players[playerNumber].getCurrentLocation().getX()
                + "," + players[playerNumber].getCurrentLocation().getY());
        moveThroughTunnel(tempX, tempY, visitedCells, playerNumber);

      } else {

        players[playerNumber].setCurrentLocationX(tempX);
        players[playerNumber].setCurrentLocationY(tempY);


      }
    }


    mazeStr[players[playerNumber].getCurrentLocation().getX()]
            [players[playerNumber].getCurrentLocation().getY()]
            = cells[players[playerNumber].getCurrentLocation().getX()]
            [players[0].getCurrentLocation().getY()].toString();

    verifyGoldAndTheif(playerNumber);
    String obstacleMsg = verifyObstacles(playerNumber);

    if (obstacleMsg.length() != 0) {


      if (players[playerNumber].getMatchStatus() == GlobalEnum.status.Won) {
        throw new MatchOverException(matchWonMsg);
      }

      if (totalPlayers == 1) {

        if (players[0] != null && players[0].getMatchStatus() == GlobalEnum.status.Lost) {
          throw new MatchOverException(obstacleMsg + ". Match Over you lost!!");
        }
        if (isPlayer2 && players[1] != null
                && players[1].getMatchStatus() == GlobalEnum.status.Lost) {
          throw new MatchOverException(obstacleMsg + ". Match Over you lost!!");
        }
      }

      if (totalPlayers == 2) {
        if (isPlayer2 && players[1].getMatchStatus() == GlobalEnum.status.Lost
                && players[0].getMatchStatus() == GlobalEnum.status.Lost) {
          throw new MatchOverException(obstacleMsg + ". Match Over you lost!!");
        }
        if (!obstacleMsg.contains(batCapturedMsg)) {
          throw new IllegalStateException(obstacleMsg);
        }
      }

    }

    return players[playerNumber].getCurrentLocation();


  }


  private void verifyGoldAndTheif(int playerNumber) {
    Cell currentCell = cells[players[playerNumber].getCurrentLocation().getX()]
            [players[playerNumber].getCurrentLocation().getY()];
    int goldInCell = currentCell.getGold();
    if (goldInCell > 0) {
      players[playerNumber].setTotalGold(goldInCell + players[playerNumber].getTotalGold());
      currentCell.setGold(0);
    }
    if (currentCell.isTheif()) {
      players[playerNumber].setTotalGold((int) (players[playerNumber].getTotalGold()
              - 0.1 * players[playerNumber].getTotalGold()));
    }

  }

  /**
   * This function moves the player through tunnel depending on the direction.
   *
   * @param x            current x-coordinate of the player in the tunnel.
   * @param y            current y-coordinate of the player in the tunnel.
   * @param visitedCells visited cells so a visited tunnel opening is not visited again.
   * @param playerNumber player Number who is performing the operation
   */
  private void moveThroughTunnel(int x, int y, Set<String> visitedCells, int playerNumber) {

    players[playerNumber].setCurrentLocationX(x);
    players[playerNumber].setCurrentLocationY(y);
    visitedCells.add(x + "," + y);
    mazeStr[x][y] = cells[x][y].toString();
    if (!cells[x][y].isTunnel()) {
      return;
    }
    for (Entry<GlobalEnum.direction, int[]> entry : mapDirectionToValue.entrySet()) {
      int tempX = x + entry.getValue()[0];
      int tempY = y + entry.getValue()[1];

      if (!visitedCells.contains(tempX + "," + tempY)) {

        int[] neighArr = updateCoordinate.updateValues(tempX, tempY,
                rowSize, columnSize);
        tempX = neighArr[0];
        tempY = neighArr[1];
        if (tempX >= 0 && tempX < rowSize && tempY >= 0 && tempY < columnSize) {
          if (!cells[x][y].getWallsIndexValue(mapWallsToIndex.get(entry.getKey()))) {
            visitedCells.add(tempX + "," + tempY);
            moveThroughTunnel(tempX, tempY, visitedCells, playerNumber);
            break;
          }
        }
      }
    }

  }

  /**
   * This function verify that whether an obstacle is present when a person moves and returns the
   * state of the player.
   *
   * @return returns the state of the player after movement
   */
  private String verifyObstacles(int playerNumber) {
    if (cells[players[playerNumber].getCurrentLocation().getX()]
            [players[playerNumber].getCurrentLocation().getY()].isWumpus()) {

      players[playerNumber].setMatchStatus(GlobalEnum.status.Lost);
      return wumpusCaughtMsg + players[playerNumber].getCurrentLocation().getX()
              + "," + players[playerNumber].getCurrentLocation().getY();

    } else if (cells[players[playerNumber].getCurrentLocation().getX()]
            [players[playerNumber].getCurrentLocation().getY()].isBat()
            && cells[players[playerNumber].getCurrentLocation().getX()]
            [players[playerNumber].getCurrentLocation().getY()].isPit()) {

      int probabilityOfBatAttack = getRandomInteger(2);
      if (probabilityOfBatAttack > 0) {
        return batCapturedHelper(playerNumber);
      } else {
        players[playerNumber].setMatchStatus(GlobalEnum.status.Lost);
        return fellPitMsg + players[playerNumber].getCurrentLocation().getX()
                + "," + players[playerNumber].getCurrentLocation().getY();
      }
    } else if (cells[players[playerNumber].getCurrentLocation().getX()]
            [players[playerNumber].getCurrentLocation().getY()].isBat()) {

      return batCapturedHelper(playerNumber);

    } else if (cells[players[playerNumber].getCurrentLocation().getX()]
            [players[playerNumber].getCurrentLocation().getY()].isPit()) {

      players[playerNumber].setMatchStatus(GlobalEnum.status.Lost);
      return fellPitMsg + players[playerNumber].getCurrentLocation().getX()
              + "," + players[playerNumber].getCurrentLocation().getY();
    }

    return "";
  }


  /**
   * This is a helper function which transports player to a random location when bat captures the
   * player.
   *
   * @return state of the player after bat captures it
   */
  private String batCapturedHelper(int playerNumber) {

    List<String> allVertix = new ArrayList<>();

    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < columnSize; j++) {
        allVertix.add(i + "," + j);
      }
    }

    while (allVertix.size() != 0) {
      int randomIndex = getRandomInteger(allVertix.size());
      String coorStr = allVertix.get(randomIndex);
      String[] coorArr = coorStr.split(",");
      int coorX = Integer.parseInt(coorArr[0]);
      int coorY = Integer.parseInt(coorArr[1]);
      allVertix.remove(randomIndex);
      if (!cells[coorX][coorY].isBat() && !cells[coorX][coorY].isPit()
              && !cells[coorX][coorY].isWumpus() && !cells[coorX][coorY].isTunnel()) {
        players[playerNumber].setCurrentLocationX(coorX);
        players[playerNumber].setCurrentLocationY(coorY);
        break;
      }
    }

    return batCapturedMsg + players[playerNumber].getCurrentLocation().getX()
            + "," + players[playerNumber].getCurrentLocation().getY();
  }

  @Override
  public String showStatus(int playerNumber) {

    verifyPlayerNumber(playerNumber);

    StringBuilder currentStateSb = new StringBuilder("");

    currentStateSb.append("Match Status: " + players[playerNumber].getMatchStatus());
    currentStateSb.append("\n");
    currentStateSb.append("Current location of the player: " + currentPosition(playerNumber));
    currentStateSb.append("\n");
    currentStateSb.append("Number of remaining arrow: " + players[playerNumber].getTotalArrows());
    currentStateSb.append("\n");
    currentStateSb.append("Available tunnels from current location : " + findTunnels(playerNumber));
    currentStateSb.append("\n");
    currentStateSb.append("Available caves from current location : " + findCaves(playerNumber));
    currentStateSb.append("\n");
    if (players.length > 1) {
      currentStateSb.append("Total gold  : " + players[playerNumber].getTotalGold());
    }
    currentStateSb.append("\n");
    if (closestDistanceFromWumpus(playerNumber) == 1) {
      currentStateSb.append("I smell wumpus at Wumpus : "
              + closestDistanceFromWumpus(playerNumber) + " metre");
      currentStateSb.append("\n");
    }
    if (closestDistanceFromPit(playerNumber) == 1) {
      currentStateSb.append("I feel wrath of Pit at : "
              + closestDistanceFromPit(playerNumber) + " metre");
    }
    return currentStateSb.toString();
  }

  /**
   * Gets maze in form of cell 2d array.
   *
   * @return 2d array of the maze
   */
  @Override
  public Cell[][] getCells() {
    return cells;
  }

  @Override
  public String toString() {
    return "totalBats="
            + totalBats + ", totalPits=" + totalPits + ", "
            + "players=" + players + ","
            + ", columnSize=" + columnSize + ", rowSize="
            + rowSize + ", startPosition=" + startPosition + "]";
  }

  /**
   * This function prints the whole maze with all the pits, bats, and wumpus. In this each
   * coordinate has direction abreviation such as (N/S/E/W) to show the walls present, and
   * additional information about bats, pits,gold, theif, wumpus, and the cell that is not not
   * available means its information cannot be displayed.
   *
   * @return 2d maze in string form
   */
  @Override
  public String print() {
    mazeStr.toString();

    StringBuilder printMaze = new StringBuilder("");
    for (int i = 0; i < mazeStr.length; i++) {

      for (int j = 0; j < mazeStr.length; j++) {
        printMaze.append("|" + i + "," + j + "(");
        printMaze.append(mazeStr[i][j]);

        printMaze.append("|");
        printMaze.append(")");

      }
      printMaze.append("\n");
    }
    return printMaze.toString();
  }

  @Override
  public Player[] getPlayers() {
    return players;
  }


  @Override
  public int getCurrentPlayer() {

    return currentPlayer;
  }

  @Override
  public void switchPlayer() {


    if (players.length > 1) {
      if (currentPlayer == 0) {
        currentPlayer = 1;
      } else if (currentPlayer == 1) {
        currentPlayer = 0;
      }
    }
  }

  @Override
  public int getRestrictedPlayer() {
    return restrictedPlayer;
  }

  @Override
  public int getMazeSize() {
    return rowSize;
  }

  @Override
  public void setRestrictedPlayer(int restrictedPlayer) {
    this.restrictedPlayer = restrictedPlayer;
  }

  @Override
  public UpdateCoordinate getUpdateCoordinate() {
    return updateCoordinate;
  }

  @Override
  public HashMap<direction, int[]> getMapDirectionToValue() {
    return mapDirectionToValue;

  }

  @Override
  public HashMap<direction, Integer> getMapWallsToIndex() {
    return mapWallsToIndex;
  }


}
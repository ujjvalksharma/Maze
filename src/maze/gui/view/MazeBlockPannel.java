package maze.gui.view;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import maze.model.Cell;
import maze.model.GlobalEnum;
import maze.model.MazeGUI;

/**
 * This class is used for constructing the Maze along with its controls for various actions such as
 * movement,shoot arrow, total arrows, total gold, restart, and quit.
 */
public class MazeBlockPannel extends JPanel {

  private static final long serialVersionUID = 1L;
  private JPanel mazePannel;
  private JPanel movePannel;
  private JPanel shootPannel;
  private JPanel statusPannel;
  private JPanel gameSettingPannel;
  private JButton checkWumpusPresence;
  private JButton checkPitPresence;
  private JButton moveNorth;
  private JButton moveSouth;
  private JButton moveEast;
  private JButton moveWest;
  private JLabel player1Gold;
  private JLabel playingPlayer;
  private JLabel player2Gold;
  private JLabel player1Arrows;
  private JLabel player2Arrows;
  private JButton restartGame;
  private JButton quitGame;
  private MazeButton[][] buttons;
  private JTextField distanceOfArrow;
  private JButton submitArrow;
  private JComboBox<String> directions;
  private int mazeSize;
  private boolean isPlayer2;
  private final String player1GoldMsg = "[Player1 gold amount:";
  private final String player2GoldMsg = "[Player2 gold amount:";
  private final String player1ArrowMsg = "[Player1 total arrows:";
  private final String player2ArrowMsg = "[Player2 total arrows:";
  private final String playingPlayerDecision = "[Currently playing player";
  private MazeGUI readOnlyMaze;

  /**
   * This is a contructor that initiates the maze for its initial setting.
   *
   * @param readOnlyMaze This is a read only where we can perform only read operations.
   */
  protected MazeBlockPannel(MazeGUI readOnlyMaze) {

    this.mazePannel = new JPanel();

    this.statusPannel = new JPanel();
    this.movePannel = new JPanel();
    this.shootPannel = new JPanel();
    this.gameSettingPannel = new JPanel();
    this.readOnlyMaze = readOnlyMaze;
    if (readOnlyMaze.getPlayers().length > 1) {
      this.isPlayer2 = true;
    } else {
      this.isPlayer2 = false;
    }
    this.mazeSize = readOnlyMaze.getMazeSize();
    this.buttons = new MazeButton[mazeSize][mazeSize];
    String[] directionChoices = {"North", "South", "East", "West"};
    this.directions = new JComboBox<String>(directionChoices);
    this.distanceOfArrow = new JTextField("Enter arrow distance ");
    this.distanceOfArrow.setSize(20, 20);
    this.submitArrow = new JButton("Shoot Arrow");
    this.checkWumpusPresence = new JButton("Wumpus Presence");
    this.checkPitPresence = new JButton("Pit Presence");
    this.moveNorth = new JButton("Move North");
    this.moveSouth = new JButton("Move South");
    this.moveEast = new JButton("Move East");
    this.moveWest = new JButton("Move West");
    this.player1Arrows = new JLabel(player1ArrowMsg);
    this.playingPlayer = new JLabel(playingPlayerDecision + "1]");
    this.restartGame = new JButton("Restart Game");
    this.quitGame = new JButton("Quit Game");
    this.player1Gold = new JLabel(player1GoldMsg + "0]");
    if (isPlayer2) {
      this.player2Gold = new JLabel(player2GoldMsg + "0]");
      this.player2Arrows = new JLabel(player2ArrowMsg);
    }

    GridLayout layout = new GridLayout(mazeSize, mazeSize, 0, 0);

    mazePannel.setLayout(layout);
    for (int i = 0; i < mazeSize; i++) {
      for (int j = 0; j < mazeSize; j++) {
        Border emptyBorder = BorderFactory.createEmptyBorder();

        this.buttons[i][j] = new MazeButton();
        this.buttons[i][j].setBorder(emptyBorder);
        this.buttons[i][j].setFocusable(false);
        this.buttons[i][j].setActionCommand("buttons" + i + "," + j);
      }
    }

  }

  /**
   * This function is used to add different components to the maze such as buttons, text, and
   * labels.
   */
  public void addComponents() {
    for (int i = 0; i < mazeSize; i++) {
      for (int j = 0; j < mazeSize; j++) {
        this.buttons[i][j].setCurrentIconToOriginal();
        mazePannel.add(this.buttons[i][j]);
      }
    }
    moveNorth.setFocusable(false);
    moveSouth.setFocusable(false);
    moveEast.setFocusable(false);
    moveWest.setFocusable(false);
    directions.setFocusable(false);
    submitArrow.setFocusable(false);
    checkWumpusPresence.setFocusable(false);
    checkPitPresence.setFocusable(false);

    movePannel.add(moveNorth);
    movePannel.add(moveSouth);
    movePannel.add(moveEast);
    movePannel.add(moveWest);
    shootPannel.add(directions);
    shootPannel.add(distanceOfArrow);

    statusPannel.add(player1Gold);
    statusPannel.add(player1Arrows);
    statusPannel.add(playingPlayer);
    if (isPlayer2) {
      statusPannel.add(player2Gold);
      statusPannel.add(player2Arrows);
    }
    gameSettingPannel.add(quitGame);
    gameSettingPannel.add(restartGame);


    add(mazePannel);
    add(movePannel);
    add(submitArrow);
    add(statusPannel);
    add(gameSettingPannel);

  }

  /**
   * This function shows the dialog which prompts the user to enter details for shooting arrow.
   */
  public void showShootArrowDialog() {
    JOptionPane.showMessageDialog(
            this, shootPannel, "Shoot Arrow", JOptionPane.QUESTION_MESSAGE);
  }

  /**
   * Return the maze cells which is present in form of buttons.
   *
   * @return returns maze buttons
   */
  public MazeButton[][] getButtons() {
    return buttons;
  }

  /**
   * Adds action listner for the all the pannel.
   *
   * @param listener listner that will listen all buttons
   */
  protected void addActionListner(ActionListener listener) {
    submitArrow.addActionListener(listener);
    checkWumpusPresence.addActionListener(listener);
    checkPitPresence.addActionListener(listener);
    moveNorth.addActionListener(listener);
    moveSouth.addActionListener(listener);
    moveEast.addActionListener(listener);
    moveWest.addActionListener(listener);

    quitGame.addActionListener(listener);
    restartGame.addActionListener(listener);
  }

  /**
   * Returns the arrow distance in string form.
   *
   * @return
   */
  public String getDistanceOfArrow() {
    return distanceOfArrow.getText();

  }

  /**
   * Returns the directions where the player wants to shoot the arrow.
   *
   * @return direction of the arrow
   */
  public String getDirections() {
    return directions.getSelectedItem().toString();
  }

  /**
   * Sets gold amount for player1.
   *
   * @param goldAmount gold amount for player1
   */
  protected void setPlayer1Gold(int goldAmount) {
    player1Gold.setText(player1GoldMsg + goldAmount + "]");
  }

  /**
   * Sets gold amount for player2.
   *
   * @param goldAmount gold amount for player2
   */
  protected void setPlayer2Gold(int goldAmount) {
    player2Gold.setText(player2GoldMsg + goldAmount + "]");
  }

  /**
   * Sets total arrows for player1.
   *
   * @param totalArrows total arrows for player1
   */
  public void setPlayer1Arrows(int totalArrows) {
    player1Arrows.setText(player1ArrowMsg + totalArrows + "]");
  }

  /**
   * Sets total arrows for player2.
   *
   * @param totalArrows total arrows for player2
   */
  public void setPlayer2Arrows(int totalArrows) {
    player2Arrows.setText(player2ArrowMsg + totalArrows + "]");
  }

  /**
   * Sets images for the buttons for tunnels,caves,bats,player,pits, and wumpus based on readOnly
   * maze model.
   */
  public void setMazeImages() {

    for (int i = 0; i < readOnlyMaze.getMazeSize(); i++) {

      for (int j = 0; j < readOnlyMaze.getMazeSize(); j++) {

        try {
          Cell tempCell = readOnlyMaze.getCells()[i][j];
          BufferedImage cavesImg = null;
          BufferedImage caveWithObstacle = null;
          BufferedImage obstacle = null;

          String caveFileName = "";
          if (!tempCell.getWallsIndexValue(3)) {
            caveFileName = caveFileName + "N";
          }
          if (!tempCell.getWallsIndexValue(2)) {
            caveFileName = caveFileName + "S";
          }
          if (!tempCell.getWallsIndexValue(0)) {
            caveFileName = caveFileName + "E";
          }
          if (!tempCell.getWallsIndexValue(1)) {
            caveFileName = caveFileName + "W";
          }
          caveFileName = caveFileName + ".png";

          caveFileName = "resources/" + caveFileName;


          cavesImg = ImageIO.read(getClass().getResource(caveFileName));


          caveWithObstacle = cavesImg;


          if (tempCell.isWumpus()) {


            obstacle = ImageIO.read(getClass().getResource("resources/wumpus.png"));
            obstacle = resize(obstacle, 0.5);
            caveWithObstacle = overlayImages(caveWithObstacle, obstacle, "wumpus");


          }


          if (tempCell.isPit()) {


            obstacle = ImageIO.read(getClass().getResource("resources/pit.png"));
            obstacle = resize(obstacle, 0.5);
            caveWithObstacle = overlayImages(caveWithObstacle, obstacle, "pit");

          }

          if (tempCell.isBat()) {


            obstacle = ImageIO.read(getClass().getResource("resources/bats.png"));
            obstacle = resize(obstacle, 0.5);
            caveWithObstacle = overlayImages(caveWithObstacle, obstacle, "bat");

          }

          if (tempCell.isTheif()) {


            obstacle = ImageIO.read(getClass().getResource("resources/thief.png"));
            obstacle = resize(obstacle, 0.5);
            caveWithObstacle = overlayImages(caveWithObstacle, obstacle, "theif");

          }

          if (tempCell.getGold() > 0) {


            obstacle = ImageIO.read(getClass().getResource("resources/gold.png"));
            obstacle = resize(obstacle, 0.5);
            caveWithObstacle = overlayImages(caveWithObstacle, obstacle, "gold");

          }

          if (tempCell.getGold() > 0) {


            obstacle = ImageIO.read(getClass().getResource("resources/gold.png"));
            obstacle = resize(obstacle, 0.5);
            caveWithObstacle = overlayImages(caveWithObstacle, obstacle, "gold");


          }


          if (readOnlyMaze.getCells()[i][j].getDistanceFromPit() > 0) {


            obstacle = ImageIO.read(getClass().getResource("resources/PitSlime.png"));
            obstacle = resize(obstacle, 0.5);
            caveWithObstacle = overlayImages(caveWithObstacle, obstacle, "PitSlime");

          }

          if (readOnlyMaze.getCells()[i][j].getDistanceFromPit() > 0) {


            obstacle = ImageIO.read(getClass().getResource("resources/WumpusSmell.png"));
            obstacle = resize(obstacle, 0.5);
            caveWithObstacle = overlayImages(caveWithObstacle, obstacle, "WumpusSmell");

          }


          buttons[i][j].setOriginalIcon(caveWithObstacle);
        } catch (IOException e) {
          return;
        }
      }

    }


    for (int i = 0; i < readOnlyMaze.getMazeSize(); i++) {

      for (int j = 0; j < readOnlyMaze.getMazeSize(); j++) {

        Cell tempCell = readOnlyMaze.getCells()[i][j];

        try {
          BufferedImage cavesImg = null;
          BufferedImage player = null;
          BufferedImage finalPlayerImg = null;
          BufferedImage obstacle = null;
          String caveFileName = "";
          if (!tempCell.getWallsIndexValue(3)) {
            caveFileName = caveFileName + "N";
          }
          if (!tempCell.getWallsIndexValue(2)) {
            caveFileName = caveFileName + "S";
          }
          if (!tempCell.getWallsIndexValue(0)) {
            caveFileName = caveFileName + "E";
          }
          if (!tempCell.getWallsIndexValue(1)) {
            caveFileName = caveFileName + "W";
          }
          caveFileName = caveFileName + ".png";

          caveFileName = "resources/" + caveFileName;


          buttons[i][j].setInitialBlackImg(ImageIO.read(getClass()
                  .getResource("resources/black.png")));
          cavesImg = ImageIO.read(getClass().getResource(caveFileName));
          player = ImageIO.read(getClass().getResource("resources/player.png"));
          finalPlayerImg = overlayImages(cavesImg, player, "player");

          if (readOnlyMaze.getCells()[i][j].getDistanceFromPit() > 0) {


            obstacle = ImageIO.read(getClass().getResource("resources/PitSlime.png"));
            obstacle = resize(obstacle, 0.5);
            finalPlayerImg = overlayImages(finalPlayerImg, obstacle, "PitSlime");
          }

          if (readOnlyMaze.getCells()[i][j].getDistanceFromPit() > 0) {


            obstacle = ImageIO.read(getClass().getResource("resources/WumpusSmell.png"));
            obstacle = resize(obstacle, 0.5);
            finalPlayerImg = overlayImages(finalPlayerImg, obstacle, "WumpusSmell");

          }


          buttons[i][j].setPlayerIcon(finalPlayerImg);
        } catch (IOException e) {
          return;
        }
      }
    }


  }


  /**
   * Resizes the obstacle images, so it can fit into the cave.
   *
   * @param inputImage input image which has to be resized
   * @param percent    percent by which we want to resize
   * @return input image after resize is returned
   */
  private BufferedImage resize(BufferedImage inputImage, double percent) {
    int scaledWidth = (int) (inputImage.getWidth() * percent);
    int scaledHeight = (int) (inputImage.getHeight() * percent);
    BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());
    Graphics2D g2d = outputImage.createGraphics();
    g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
    g2d.dispose();
    return outputImage;
  }

  /**
   * Overlaps two input images and adjuts the position of the foreground image.
   *
   * @param bgImage background image on which foreground image is set
   * @param fgImage foregound image which is on top of background image
   * @param images  criteria of selecting positioning the image
   * @return return the overlapped image
   */
  private BufferedImage overlayImages(BufferedImage bgImage, BufferedImage fgImage,
                                      String images) {
    Graphics2D g = bgImage.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.drawImage(bgImage, 0, 0, null);
    if (images.equals("bat")) {
      g.drawImage(fgImage, 15, 20, null);
    } else if (images.equals("wumpus")) {
      g.drawImage(fgImage, 15, 15, null);
    } else if (images.equals("pit")) {
      g.drawImage(fgImage, 15, 20, null);
    } else if (images.equals("player")) {
      g.drawImage(fgImage, 15, 20, null);
    } else if (images.equals("gold")) {
      g.drawImage(fgImage, 40, 25, null);
    } else if (images.equals("theif")) {
      g.drawImage(fgImage, 10, 25, null);
    } else if (images.equals("WumpusSmell")) {
      g.drawImage(fgImage, 16, 30, null);
    } else if (images.equals("PitSlime")) {
      g.drawImage(fgImage, 15, 2, null);
    }
    g.dispose();
    return bgImage;
  }

  /**
   * Updated the players gold amount.
   */
  public void updatePlayerGold() {
    setPlayer1Gold(readOnlyMaze.getPlayers()[0].getTotalGold());
    if (readOnlyMaze.getPlayers().length > 1) {
      setPlayer2Gold(readOnlyMaze.getPlayers()[1].getTotalGold());
    }

  }

  /**
   * Updates the current player's cell to OriginalIcon.
   */
  public void updateCurrentCellToOriginalIcon() {
    buttons[readOnlyMaze.getPlayers()[readOnlyMaze.getCurrentPlayer()].getCurrentLocation().getX()]
            [readOnlyMaze.getPlayers()[readOnlyMaze.getCurrentPlayer()].getCurrentLocation().getY()]
            .setCurrentIconToOriginal();
  }

  /**
   * Updates the current player's cell to Player Icon.
   */
  public void updateCurrentCellToPlayerIcon() {
    buttons[readOnlyMaze.getPlayers()[readOnlyMaze.getCurrentPlayer()].getCurrentLocation().getX()]
            [readOnlyMaze.getPlayers()[readOnlyMaze.getCurrentPlayer()].getCurrentLocation().getY()]
            .setCurrentIconToPlayer();
  }

  /**
   * Updated the players total arrows.
   */
  public void updateArrows() {
    setPlayer1Arrows(readOnlyMaze.getPlayers()[0].getTotalArrows());
    if (readOnlyMaze.getPlayers().length > 1) {
      setPlayer2Arrows(readOnlyMaze.getPlayers()[1].getTotalArrows());
    }

  }

  /**
   * Updates the a cell to OriginalIcon.
   *
   * @param x x-coordinate of the cell
   * @param y y-coordinate of the cell
   */
  public void updateCellToOriginalIcon(int x, int y) {
    buttons[x][y].setCurrentIconToOriginal();

  }

  /**
   * Gets current playing player desciption in the view.
   *
   * @Param playerNumber player Number of the player
   */
  public void setPlayingPlayer(int playerNumber) {

    int tempPlayerNumber = playerNumber + 1;
    this.playingPlayer.setText(playingPlayerDecision + tempPlayerNumber + "]");


  }

  /**
   * Sets Icons of tunnel to orginal icon on movement of player.
   *
   * @param playerPositionX player previous x position
   * @param playerPositionY player previous y position
   * @param dir             direction in which player's moves
   */
  public void setTunnelIconsOnmovement(int playerPositionX,
                                       int playerPositionY, GlobalEnum.direction dir) {
    HashSet<String> visitedCell = new HashSet<String>();
    visitedCell.add(playerPositionX + "," + playerPositionY);
    int tempX = playerPositionX;
    int tempY = playerPositionY;
    if (dir == GlobalEnum.direction.East) {
      tempY++;
    }
    if (dir == GlobalEnum.direction.West) {
      tempY--;
    }

    if (dir == GlobalEnum.direction.North) {
      tempX--;
    }

    if (dir == GlobalEnum.direction.South) {
      tempX++;
    }
    int[] arr = readOnlyMaze
            .getUpdateCoordinate()
            .updateValues(tempX, tempY, readOnlyMaze.getMazeSize(), readOnlyMaze.getMazeSize());
    if (arr[0] >= 0
            && arr[0] < readOnlyMaze.getMazeSize()
            && arr[1] >= 0 && arr[1] < readOnlyMaze.getMazeSize()) {
      makeTunnelIconVisible(tempX, tempY, visitedCell);
    }

  }

  /**
   * This function make the tunnel icons visible when a player visits them.
   *
   * @param x x-coordinate of the tunnel
   * @param y y-coordinate of the tunnel
   */
  private void makeTunnelIconVisible(int x, int y, HashSet<String> visitedCell) {


    visitedCell.add(x + "," + y);
    if (!readOnlyMaze.getCells()[x][y].isTunnel()) {
      return;
    }
    getButtons()[x][y].setCurrentIconToOriginal();
    for (Entry<GlobalEnum.direction, int[]> entry :
            readOnlyMaze.getMapDirectionToValue().entrySet()) {
      int tempX = x + entry.getValue()[0];
      int tempY = y + entry.getValue()[1];

      if (!visitedCell.contains(tempX + "," + tempY)) {

        int[] neighArr = readOnlyMaze.getUpdateCoordinate().updateValues(tempX, tempY,
                readOnlyMaze.getMazeSize(), readOnlyMaze.getMazeSize());
        tempX = neighArr[0];
        tempY = neighArr[1];
        if (tempX >= 0 && tempX < readOnlyMaze.getMazeSize() && tempY >= 0
                && tempY < readOnlyMaze.getMazeSize()) {
          if (!readOnlyMaze.getCells()[x][y]
                  .getWallsIndexValue(readOnlyMaze.getMapWallsToIndex().get(entry.getKey()))) {
            visitedCell.add(tempX + "," + tempY);
            makeTunnelIconVisible(tempX, tempY, visitedCell);
            break;
          }
        }
      }
    }

  }
}

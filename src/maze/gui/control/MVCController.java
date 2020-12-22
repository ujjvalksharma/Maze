package maze.gui.control;

import maze.gui.view.IMazeView;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import maze.model.GlobalEnum;
import maze.model.MatchOverException;
import maze.model.Maze;
import maze.model.MazeGUI;
import maze.model.NonWrappingMaze;
import maze.model.Point;
import maze.model.WrappingMaze;

/**
 * This class is a controller class for the maze, which is as a mid way between the model and the
 * view. It initiates the view and model, then it takes the input from the view and perform the
 * actions on the model.
 */
public class MVCController implements IMVCController {
  private IMazeView view;
  private Maze model;
  private final String switchTurnMsg = "It is next player's turn";

  /**
   * This contructs the controller with the view, so it can take input from the view to perform
   * actions in the model.
   *
   * @param v view that is given to the controller
   */
  public MVCController(IMazeView v) {
    this.model = null;
    this.view = v;
  }

  @Override
  public void start() {
    view.display();
    configureButtonListner();
    configureKeyListner();
  }

  /**
   * This initiates the key listner and sets the operations for up,down,left,rght arrow keys.
   */
  private void configureKeyListner() {
    Map<Integer, Runnable> mapKeyPressToObj = new HashMap<>();
    KeyBoardListner keyBoardListner = new KeyBoardListner();

    mapKeyPressToObj.put(KeyEvent.VK_UP, () -> move(GlobalEnum.direction.North));
    mapKeyPressToObj.put(KeyEvent.VK_DOWN, () -> move(GlobalEnum.direction.South));
    mapKeyPressToObj.put(KeyEvent.VK_RIGHT, () -> move(GlobalEnum.direction.East));
    mapKeyPressToObj.put(KeyEvent.VK_LEFT, () -> move(GlobalEnum.direction.West));
    keyBoardListner.setKeyPressMapListner(mapKeyPressToObj);
    this.view.addKeyListner(keyBoardListner);

  }

  /**
   * This initiates the button listner and sets the click operations on the button such as shoot
   * arrow, movement, buidl maze, quit and refresh the game.
   */
  private void configureButtonListner() {
    Map<String, Runnable> buttonToObj = new HashMap<>();
    ButtonListner buttonListner = new ButtonListner();
    buttonToObj.put("Build Maze", new BuildMaze());
    buttonToObj.put("Shoot Arrow", new ShootArrow());
    buttonToObj.put("Build Maze", new BuildMaze());
    buttonToObj.put("Move South", () -> move(GlobalEnum.direction.South));
    buttonToObj.put("Move North", () -> move(GlobalEnum.direction.North));
    buttonToObj.put("Move East", () -> move(GlobalEnum.direction.East));
    buttonToObj.put("Move West", () -> move(GlobalEnum.direction.West));
    buttonToObj.put("Quit Game", () -> view.endMaze("You gave up!! Game over!!"));
    buttonToObj.put("Restart Game", new Restart());
    buttonListner.setButtonListnerMap(buttonToObj);
    this.view.addActionListner(buttonListner);

  }


  /**
   * This is function that changes the view on movement and tells the model to change player's
   * location on the basis of input direction.
   *
   * @param dir direction which player moves in the view
   */
  private void move(GlobalEnum.direction dir) {


    int playerPositionX = model.getPlayers()[model.getCurrentPlayer()].getCurrentLocation().getX();
    int playerPositionY = model.getPlayers()[model.getCurrentPlayer()].getCurrentLocation().getY();

    Point newPosition = null;
    try {
      newPosition = model.move(dir, model.getCurrentPlayer());

      view.getMazePannel().updateCellToOriginalIcon(playerPositionX, playerPositionY);
      switchTurnAfterMove("Your new location is:" + newPosition.getX() + "," + newPosition.getY());
    } catch (MatchOverException e2) {
      matchOver(e2.getMessage());
    } catch (IllegalStateException e1) {
      view.getMazePannel().updateCellToOriginalIcon(playerPositionX, playerPositionY);
      msgOnPlayerLoss(e1.getMessage());
    }
    view.getMazePannel().updatePlayerGold();
    view.getMazePannel().setPlayingPlayer(model.getCurrentPlayer());

    view.getMazePannel().setTunnelIconsOnmovement(playerPositionX, playerPositionY, dir);

  }


  /**
   * This function performs changes the view such that player's old position icon is replaced and
   * new player's icon is added, and the player's turns are switched.
   *
   * @param msg message that view shows on turn switch
   */
  private void switchTurnAfterMove(String msg) {

    view.getMazePannel().updateCurrentCellToOriginalIcon();
    if (model.getPlayers().length > 1 && model.getRestrictedPlayer() == -1) {
      model.switchPlayer();
      view.showMessage(msg + ". It is next player's turn");
    } else {
      view.showMessage(msg);
    }

    view.getMazePannel().updateCurrentCellToPlayerIcon();

  }

  /**
   * This function changes the view for 1st player when in two player mode a player has reached a
   * illgeal state such as falling into a pit or caught by wumpus or arrows are over.
   *
   * @param message message that shown on reaching illegal state
   */
  private void msgOnPlayerLoss(String message) {
    if (model.getPlayers().length > 1 && model.getRestrictedPlayer() == -1) {
      view.showMessage(message);
      view.getMazePannel().updateCurrentCellToOriginalIcon();
      view.showMessage(switchTurnMsg);
      model.setRestrictedPlayer(model.getCurrentPlayer());
      model.switchPlayer();
      view.getMazePannel().updateCurrentCellToPlayerIcon();

    }

  }

  /**
   * This is function that ends the game with message depending upon whether is 2 player mode or 1
   * player as in 2 player mode we compare the gold amount to find the winner.
   *
   * @param msg message that occurs when match has ended
   */
  private void matchOver(String msg) {


    if (model.getPlayers().length > 1) {
      if (model.getPlayers()[0].getTotalGold() > model.getPlayers()[1].getTotalGold()) {
        msg = "Player1 won as he has more gold than player2";
      } else if (model.getPlayers()[0].getTotalGold() < model.getPlayers()[1].getTotalGold()) {
        msg = "Player2 won as he has more gold than player1";
      } else {
        msg = "Match is a draw as they have same gold amount";
      }
    }
    view.endMaze(msg);


  }

  /**
   * This function constructs a wrapping and non-wrapping depending on the user input.
   */
  private void constructMaze() {


    if ("Wrapping".equals(view.getStartPannel().getMazeType())) {

      model = new WrappingMaze(
              view.getStartPannel().getMazeSize()
              , view.getStartPannel().getMazeSize()
              , view.getStartPannel().getTotalBats()
              , view.getStartPannel().getTotalPits()
              , view.getStartPannel().getRemainingWalls()
              , view.getStartPannel().isPlayer2()
              , view.getStartPannel().getSeed());
    } else {


      model = new NonWrappingMaze(
              view.getStartPannel().getMazeSize()
              , view.getStartPannel().getMazeSize()
              , view.getStartPannel().getTotalBats()
              , view.getStartPannel().getTotalPits()
              , view.getStartPannel().getRemainingWalls()
              , view.getStartPannel().isPlayer2()
              , view.getStartPannel().getSeed());
    }

  }


  /**
   * This class is a runnable class that restarts the maze to the intital settings.
   */
  private class Restart implements Runnable {

    @Override
    public void run() {
      view.showMessage("You game has been restarted!!");
      constructMaze();
      view.removeMazePannel();
      view.setMaze((MazeGUI) model);


    }
  }

  /**
   * This class is a runnable class that builds the a new maze based on input given by the user in
   * the view.
   */
  private class BuildMaze implements Runnable {

    @Override
    public void run() {
      if (view.getStartPannel().verifyInput()) {
        constructMaze();
        view.removeStartPannel();
        view.setMaze((MazeGUI) model);

      }

    }

  }

  /**
   * This class is a runnable class that perform the actions when arrow is shot in the view.
   */
  private class ShootArrow implements Runnable {

    @Override
    public void run() {


      try {
        view.getMazePannel().showShootArrowDialog();
        int distance = Integer.parseInt(view.getMazePannel().getDistanceOfArrow());
        String direction = view.getMazePannel().getDirections();


        GlobalEnum.direction dir = GlobalEnum.direction.valueOf(direction);
        String msg = model.shootArrow(dir, distance, model.getCurrentPlayer());

        switchTurnAfterMove(msg);
      } catch (MatchOverException e3) {
        matchOver(e3.getMessage());
      } catch (IllegalArgumentException e3) {

        view.showMessage("Arrow distance has to be a positive Integer");
      } catch (IllegalStateException e3) {
        msgOnPlayerLoss(e3.getMessage());
      }
      view.getMazePannel().setPlayingPlayer(model.getCurrentPlayer());
      view.getMazePannel().updateArrows();


    }


  }
}


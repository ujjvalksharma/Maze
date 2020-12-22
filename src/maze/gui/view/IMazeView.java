package maze.gui.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import maze.model.MazeGUI;

/**
 * This is an restricted view interface based on which controller interacts with the maze and
 * performs actions on the maze.
 */
public interface IMazeView {

  /**
   * This functions displays the view.
   */
  public void display();

  /**
   * This function shows a message in the view.
   *
   * @param msg message to be shown in the view
   */
  public void showMessage(String msg);

  /**
   * This function removes the setting pannel when we start the maze game.
   */
  public void removeStartPannel();

  /**
   * This function is used to set the maze view based on the read only model of the maze.
   *
   * @param maze this is a readonly maze
   */
  public void setMaze(MazeGUI maze);

  /**
   * This is used add action listner for buttons of the view.
   *
   * @param listener listner that performs all buttons actions
   */
  public void addActionListner(ActionListener listener);

  /**
   * This is used the view details of the maze.
   *
   * @return maze pannel
   */
  public MazeBlockPannel getMazePannel();

  /**
   * This functions is used to end the maze game.
   *
   * @param msg message that is shown when maze is ended
   */
  public void endMaze(String msg);

  /**
   * This is used add key listner for the keys pressed,released and typed in the view.
   *
   * @param keys listner that performs all key actions
   */
  public void addKeyListner(KeyListener keys);

  /**
   * This function is used to get the maze pannel to know the details for constructing the maze.
   *
   * @return maze setting pannel
   */
  public MazeSettingPannel getStartPannel();

  /**
   * This function removes the maze game pannel.
   */
  public void removeMazePannel();
}

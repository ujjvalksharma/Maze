package maze.driver;

import maze.gui.control.MVCController;
import maze.gui.view.IMazeView;
import maze.gui.view.MazeView;

/**
 * This is a driver class that will take a view as an input for the maze and initiate the
 * controller, so a user can interact graphically between the view and the model.
 */
public class GUIDriver {

  /**
   * This function starts the controller.
   */
  public void start() {
    IMazeView mazeView = new MazeView();
    MVCController controller = new MVCController(mazeView);
    controller.start();
  }
}

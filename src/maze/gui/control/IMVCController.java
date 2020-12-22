package maze.gui.control;

/**
 * This is a mvc controller that starts the controller, which then interacts with the model and the
 * view.
 */
public interface IMVCController {

  /**
   * This method is used to start the controller and provide the user a view, so it can interact
   * with the model.
   */
  public void start();

}

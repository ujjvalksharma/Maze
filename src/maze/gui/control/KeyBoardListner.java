package maze.gui.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * This is class is used to for handling the keylistner functionality such as operations to be
 * performed when key is pressed, when key is released, and when key is typed for the maze view.
 */
public class KeyBoardListner implements KeyListener {

  private Map<Integer, Runnable> mapKeyPressToObj;
  private Map<Integer, Runnable> mapKeyTypedToObj;
  private Map<Integer, Runnable> mapKeyReleasedToObj;

  /**
   * This constructs the key listner maps to null. They are initialised on user demand.
   */
  KeyBoardListner() {
    mapKeyPressToObj = null;
    mapKeyTypedToObj = null;
    mapKeyReleasedToObj = null;
  }

  /**
   * This sets the mapping for key press to runnable class.
   *
   * @param mapKeyPressToObj map which maps action command to runnable class object
   */
  protected void setKeyPressMapListner(Map<Integer, Runnable> mapKeyPressToObj) {
    this.mapKeyPressToObj = mapKeyPressToObj;
  }

  /**
   * This sets the mapping for key type to runnable class.
   *
   * @param mapKeyTypedToObj map which maps action command to runnable class object
   */
  protected void setKeyTypedMapListner(Map<Integer, Runnable> mapKeyTypedToObj) {
    this.mapKeyTypedToObj = mapKeyTypedToObj;
  }

  /**
   * This sets the mapping for key release to runnable class.
   *
   * @param mapKeyReleasedToObj map which maps action command to runnable class object
   */
  protected void setKeyReleasedMapListner(Map<Integer, Runnable> mapKeyReleasedToObj) {
    this.mapKeyReleasedToObj = mapKeyReleasedToObj;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    return;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (mapKeyPressToObj.containsKey(e.getKeyCode())) {
      mapKeyPressToObj.get(e.getKeyCode()).run();
    }

  }

  @Override
  public void keyReleased(KeyEvent e) {

    return;

  }

}

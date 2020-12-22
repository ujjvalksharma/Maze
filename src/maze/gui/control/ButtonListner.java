package maze.gui.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * This is class is used to for handling the buttonclick functionality such as operations to be
 * performed when a button is clicked.
 */
public class ButtonListner implements ActionListener {

  private Map<String, Runnable> buttonToObj;

  /**
   * This sets the mapping for button click to runnable class.
   *
   * @param buttonToObj which maps action command to runnable class object
   */
  protected void setButtonListnerMap(Map<String, Runnable> buttonToObj) {
    this.buttonToObj = buttonToObj;

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (buttonToObj.containsKey(e.getActionCommand())) {
      buttonToObj.get(e.getActionCommand()).run();
    }


  }

}

package maze.gui.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This is Jpannel that represents the view for taking the input from the user for constructing thr
 * maze. This view takes serveral details such as maze size, bats, pits , seed, and remaining
 * walls.
 */
public class MazeSettingPannel extends JPanel {


  private static final long serialVersionUID = 1L;
  private JComboBox<String> mazeTypeSelectBar;
  private JComboBox<String> player2SelectBar;
  private JTextField mazeSize;
  private JTextField totalBats;
  private JTextField remainingWalls;
  private JTextField totalPits;
  private JTextField seed;
  private JLabel player2Label;
  private JLabel mazeTypeLabel;
  private JButton buildmaze;

  /**
   * This contructs maze setting jpannel which takes input from user then is used to contruct the
   * maze.
   */
  MazeSettingPannel() {
    this.mazeSize = new JTextField("Enter the maze size ");
    this.totalBats = new JTextField("Enter maximum no of bats allowed");
    this.totalPits = new JTextField("Enter maximum no of pits alloweed");
    this.seed = new JTextField("Enter the seed value");
    this.remainingWalls = new JTextField("Enter no of remaingWalls.");
    String[] isPlayer2 = {"No", "Yes"};
    this.player2Label = new JLabel("Please select yes/no for 2nd Player:");
    this.player2SelectBar = new JComboBox<String>(isPlayer2);
    String[] mazeTypeArr = {"Wrapping", "NonWrapping"};
    this.mazeTypeLabel = new JLabel("Select type of maze");
    this.mazeTypeSelectBar = new JComboBox<String>(mazeTypeArr);
    buildmaze = new JButton("Build Maze");

    GridLayout layout = new GridLayout(10, 1);
    setLayout(layout);

    addComponents();
  }

  /**
   * This adds componets int the Jpannel for initiating the setting view for the maze.
   */
  private void addComponents() {
    add(mazeTypeLabel);
    add(mazeTypeSelectBar);
    add(mazeSize);
    add(seed);
    add(totalBats);
    add(totalPits);
    add(remainingWalls);
    add(player2Label);
    add(player2SelectBar);
    add(buildmaze);

  }

  /**
   * This sets the listner for for building the maze button.
   */
  protected void setListener(ActionListener listener) {

    buildmaze.addActionListener(listener);


  }

  /**
   * This function verifies the inputs entered by the user for starting the maze.
   *
   * @return returns true if the inputs are valid else false.
   */
  public boolean verifyInput() {
    int row = 0;
    try {
      row = Integer.parseInt(mazeSize.getText());
      Integer.parseInt(totalBats.getText());
      Integer.parseInt(totalPits.getText());
      Integer.parseInt(remainingWalls.getText());
      Integer.parseInt(seed.getText());
      return true;
    } catch (IllegalArgumentException e) {
      printDialogBox("All the input field have to be positive integer");
    }

    if (row < 4) {
      printDialogBox("Maze size should be minimum 4");
    }
    return false;

  }

  /**
   * Returns whether the 2nd player is present.
   *
   * @return true if 2 players are playing else false
   */
  public boolean isPlayer2() {
    String decision = (String) player2SelectBar.getSelectedItem();
    return decision.equals("Yes");

  }

  /**
   * This function return the of the maze.
   *
   * @return retuns type of maze
   */
  public String getMazeType() {
    return (String) mazeTypeSelectBar.getSelectedItem();
  }

  /**
   * Gets the maze size.
   *
   * @return maze size
   */
  public int getMazeSize() {
    return Integer.parseInt(mazeSize.getText());
  }

  /**
   * Gets the total bats.
   *
   * @return return number of bats
   */
  public int getTotalBats() {
    return Integer.parseInt(totalBats.getText());
  }

  /**
   * Gets the number of remaining walls.
   *
   * @return return total number of remaining walls
   */
  public int getRemainingWalls() {
    return Integer.parseInt(remainingWalls.getText());
  }

  /**
   * Gets the total number of pits.
   *
   * @return return total number of pits.
   */
  public int getTotalPits() {
    return Integer.parseInt(totalPits.getText());
  }

  /**
   * Gets seed value for the maze.
   *
   * @return return seed value
   */
  public int getSeed() {
    return Integer.parseInt(seed.getText());
  }

  /**
   * This functions prints the message in the view.
   *
   * @param msg message to be printed in the view
   */
  private void printDialogBox(String msg) {
    JOptionPane optionPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE);
    JDialog dialog = optionPane.createDialog("Information!");
    dialog.setAlwaysOnTop(true);
    dialog.setVisible(true);
  }

  /**
   * This functions adds action listner for the build maze button.
   *
   * @param listener lister for build maze button
   */
  protected void addActionListner(ActionListener listener) {
    buildmaze.addActionListener(listener);

  }
}

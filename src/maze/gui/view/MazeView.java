package maze.gui.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import maze.model.MazeGUI;

/**
 * This is class that gives the maze a view. It gives a view to take details from the user and also
 * displays the maze based on those details.
 */
public class MazeView extends JFrame implements IMazeView {
  private static final long serialVersionUID = 1L;
  private MazeSettingPannel settingPannel;
  private MazeBlockPannel mazeBlockPannel;
  private ActionListener listener;
  private KeyListener keys;
  private JScrollPane scrollPane;

  /**
   * This is used to construct the maze view object that initiates the view for taking input from
   * the user for starting the maze.
   */
  public MazeView() {

    settingPannel = new MazeSettingPannel();
    this.add(settingPannel);
    setFocusable(true);
    setSize(400, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    display();
  }

  @Override
  public void display() {
    this.setVisible(true);

  }

  @Override
  public MazeSettingPannel getStartPannel() {
    return settingPannel;
  }

  @Override
  public void removeStartPannel() {

    this.remove(this.settingPannel);


  }

  @Override
  public void removeMazePannel() {

    this.remove(this.mazeBlockPannel);
    this.mazeBlockPannel = null;
    this.remove(scrollPane);
    this.scrollPane = null;
  }

  @Override
  public void setMaze(MazeGUI maze) {
    setSize(500, 500);
    this.mazeBlockPannel = new MazeBlockPannel(maze);
    scrollPane = new JScrollPane(mazeBlockPannel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.addKeyListener(keys);
    this.mazeBlockPannel.addActionListner(listener);
    this.add(scrollPane);
    getMazePannel().setMazeImages();
    getMazePannel().addComponents();

    for (int i = 0; i < getMazePannel().getButtons().length; i++) {

      for (int j = 0; j < getMazePannel().getButtons().length; j++) {
        getMazePannel().getButtons()[i][j].setCurrentIconToBlackImg();
      }
    }
    getMazePannel().updateCurrentCellToPlayerIcon();
    getMazePannel().updateArrows();
    getMazePannel().updatePlayerGold();

  }

  @Override
  public void addActionListner(ActionListener listener) {
    this.listener = listener;
    settingPannel.addActionListner(listener);

  }

  @Override
  public void addKeyListner(KeyListener keys) {
    this.keys = keys;
  }

  @Override
  public MazeBlockPannel getMazePannel() {
    return mazeBlockPannel;
  }

  @Override
  public void endMaze(String msg) {

    showMessage(msg);
    mazeBlockPannel.setVisible(false);
    System.exit(0);
  }

  @Override
  public void showMessage(String msg) {
    JOptionPane optionPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE);
    JDialog dialog = optionPane.createDialog("Information!");
    dialog.setAlwaysOnTop(true);
    dialog.setVisible(true);
  }

}

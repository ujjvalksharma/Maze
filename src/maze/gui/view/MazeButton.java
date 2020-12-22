package maze.gui.view;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;

/**
 * This class is Jbutton class that represents ever cell in maze and It contains icons when player
 * is present, when ostacle are present and when it is simply black in the initial stage.
 */
public class MazeButton extends JButton implements ActionListener {

  private static final long serialVersionUID = 1L;
  private BufferedImage original;
  private BufferedImage playerIcon;
  private BufferedImage initialBlackImg;


  /**
   * This functions sets the icon when a cell hasn't been visited.
   *
   * @param initialBlackImg original state of the cell without player
   */
  public void setInitialBlackImg(BufferedImage initialBlackImg) {
    this.initialBlackImg = initialBlackImg;

  }

  /**
   * This functions set current Icon to black img.
   */
  public void setCurrentIconToBlackImg() {
    setIcon(new ImageIcon(initialBlackImg));
  }

  /**
   * This functions sets the icon for the cell when no player is present.
   *
   * @param original original state of the cell without player
   */
  public void setOriginalIcon(BufferedImage original) {
    this.original = original;

  }

  /**
   * This functions sets the icon for the cell when player is present.
   *
   * @param playerIcon state of maze when player is present
   */
  public void setPlayerIcon(BufferedImage playerIcon) {
    this.playerIcon = playerIcon;

  }

  /**
   * This functions sets the current cell to an icon that doesn't have a player.
   */
  public void setCurrentIconToOriginal() {
    setIcon(new ImageIcon(original));
  }

  /**
   * This functions sets the current cell to an icon that has a player.
   */
  public void setCurrentIconToPlayer() {
    setIcon(new ImageIcon(playerIcon));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    return;
  }
}
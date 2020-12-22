package maze.model;

/**
 * This is an exception that occurs when the maze game has ended to trigger the controller for
 * ending the maze view.
 */
public class MatchOverException extends Exception {

  private static final long serialVersionUID = 1L;
  String message;

  /**
   * This constructs the MatchOverException.
   *
   * @param str message when the match is over
   */
  public MatchOverException(String str) {
    super(str);
    message = str;
  }

  @Override
  public String toString() {
    return ("MatchOverException Occurred : " + message);
  }
}
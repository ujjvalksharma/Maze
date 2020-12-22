package maze.text.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import maze.model.MatchOverException;
import maze.model.Maze;
import maze.text.control.command.MazeCommand;
import maze.text.control.command.Move;
import maze.text.control.command.Print;
import maze.text.control.command.ShootArrow;
import maze.text.control.command.ShowStatus;

/**
 * This class starts the controller and then the controller taken input from the user and performs
 * actions on the maze such as move and shoot arrow. It also presents the current state of the
 * maze.
 */
public class IControllerImpl implements IController {

  private Maze model;
  private final Readable in;
  private final Appendable out;
  private final String modelNotFoundMsg = "Model is not present";
  private final String inputNotFoundMsg = "input is not present";
  private final String outputNotFoundMsg = "output is not present";
  private final String gameWonMsg = "Game over!! You won";
  private final String gameLoseMsg = "Game over!! You lost";
  private final String invalidCommandMsg = "You entered Invalid command. Valid command are only "
          + "\n1)move <directionName> "
          + "\n2)shootArrow  <directionName> <Distance>"
          + "\n3)showStatus"
          + " \n4)Quit ";

  /**
   * This is an constructor that constructs the controller model by taking input,output, and model.
   *
   * @param model model which will be runned by the controller
   * @param in    input to feed parameter for the model
   * @param out   output to display output from model
   */
  public IControllerImpl(Maze model, Readable in, Appendable out) {

    if (model == null) {
      throw new IllegalArgumentException(modelNotFoundMsg);
    }
    if (in == null) {
      throw new IllegalArgumentException(inputNotFoundMsg);
    }
    if (out == null) {
      throw new IllegalArgumentException(outputNotFoundMsg);
    }
    this.model = model;
    this.in = in;
    this.out = out;
  }

  @Override
  public void start() throws IOException, MatchOverException {
    Scanner scan = new Scanner(this.in);

    Map<String, Function<Scanner, MazeCommand>> knownCommands = new HashMap<>();
    knownCommands.put("move", s -> new Move(s.next(), s.nextInt()));
    knownCommands.put("shootArrow", s -> new ShootArrow(s.next(), s.nextInt(), s.nextInt()));
    knownCommands.put("showStatus", s -> new ShowStatus(s.nextInt()));
    knownCommands.put("Print", s -> new Print());
    while (scan.hasNext()) {
      MazeCommand mazeCommand;
      String in = scan.next();
      StringBuilder matchStatus = new StringBuilder("");

      if (matchStatus.toString().contains("Match Status: Won")) {
        throw new MatchOverException(gameWonMsg);
      }

      if (matchStatus.toString().contains("Match Status: Lost")) {
        throw new MatchOverException(gameLoseMsg);
      }

      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        throw new MatchOverException("Match over you quit!!");
      }

      Function<Scanner, MazeCommand> cmd;
      try {
        cmd = knownCommands
                .getOrDefault(in, null);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(invalidCommandMsg);
      }
      if (cmd == null) {

        throw new IllegalArgumentException(invalidCommandMsg);
      } else {

        mazeCommand = cmd.apply(scan);
        String modelMsg = mazeCommand.apply(model);
        this.out.append(String.format("%s\n", modelMsg));
      }
    }

  }

}

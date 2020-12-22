


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import maze.model.GlobalEnum;
import maze.model.MatchOverException;
import maze.model.Maze;
import maze.model.NonWrappingMaze;

/**
 * This is a test class for the maze that will test the movement/walls/shooting
 * arrows/wumpus in two player and single player mode.
 */
public class MazeTest {
  Maze maze;

  @Before
  public void setUp() {
    maze = new NonWrappingMaze(4, 4, 1, 1, 20, true, 20);
  }

  @Test
  public void testTwoPlayerMovement() throws MatchOverException {

    assertEquals("Your location is :[x=3, y=1]",
            maze.move(GlobalEnum.direction.South, 0).toString());
    assertEquals("Your location is :[x=3, y=1]",
            maze.move(GlobalEnum.direction.South, 1).toString());

  }

  @Test(expected = MatchOverException.class)
  public void testTwoplayershootArrow() throws MatchOverException {

    assertEquals("Your arrow is wasted it hit at 3,1",
            maze.shootArrow(GlobalEnum.direction.South, 1, 0));
    assertEquals("Match has been won. We cannot shoot arrow now!!",
            maze.shootArrow(GlobalEnum.direction.East, 1, 1));

  }

  @Test
  public void testOnePlayerMovement() throws MatchOverException {

    maze = new NonWrappingMaze(4, 4, 1, 1, 20, false, 20);
    assertEquals("Your location is :[x=3, y=1]",
            maze.move(GlobalEnum.direction.South, 0).toString());

  }

  @Test
  public void testOneplayershootArrow() throws MatchOverException {
    maze = new NonWrappingMaze(4, 4, 1, 1, 20, false, 20);
    assertEquals("Your arrow is wasted it hit at 3,1",
            maze.shootArrow(GlobalEnum.direction.South, 1, 0));

  }

  @Test
  public void testDetectWall() throws MatchOverException {
    assertEquals("Your location is :[x=0, y=1]",
            maze.move(GlobalEnum.direction.North, 0).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalPlayerMove() throws MatchOverException {

    maze = new NonWrappingMaze(4, 4, 1, 1, 20, false, 20);
    assertEquals("Your location is :[x=3, y=1]",
            maze.move(GlobalEnum.direction.South, 2).toString());

  }


  @Test
  public void testTunnelMovement() throws MatchOverException {

    maze = new NonWrappingMaze(4, 4, 1, 1, 20, false, 20);
    assertEquals("Your location is :[x=3, y=1]",
            maze.move(GlobalEnum.direction.South, 0).toString());

  }

  @Test(expected = IllegalStateException.class)
  public void testPitHit() throws MatchOverException {

    assertEquals("You got hit by a pit",
            maze.move(GlobalEnum.direction.West, 0).toString());

  }

  @Test(expected = IllegalStateException.class)
  public void testWumpusHit() throws MatchOverException {

    assertEquals("You got hit by a Wumpus",
            maze.move(GlobalEnum.direction.East, 0).toString());

  }

  @Test(expected = MatchOverException.class)
  public void testWumpusShoot() throws MatchOverException {

    assertEquals("Match has been won. We cannot shoot arrow now!!",
            maze.shootArrow(GlobalEnum.direction.East, 1, 1));

  }

  @Test(expected = MatchOverException.class)
  public void testMatchLoss() throws MatchOverException {

    try {
      assertEquals("Your location is :[x=3, y=1]",
              maze.move(GlobalEnum.direction.East, 0).toString());
    } catch (IllegalStateException e) {
      assertEquals("Your location is :[x=3, y=1]",
              maze.move(GlobalEnum.direction.East, 1).toString());
    }


  }
}

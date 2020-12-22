#How to use your program?
1) Make sure you have JDK or JRE installed 
2)  Download the jar from res folder 
3) Open command line/ terminal and go the jar location folder
4) Run the command to start the text-based maze game: java -jar Program.jar --text
5) Run the command to start the Gui based maze game:  java -jar Program.jar --gui

#Which parts of the program are complete?

1) Exposed all game settings through menus or other controls along with 2 player mode
2) Provide an option to restart and or play a new game
3) Maze to be bigger than the area allocated to it
4)Allow a player to move by keys, buttons and move through tunnel/caves and encounter theif/bats/pits/wumpus
5)Allow a player to shoot an arrow and it should not hit another player
6)provide a clear indication of the results for 2 player and 1 player mode is there.
7)The view identifies which player is currently taking their turn
8) A player can anytime restart or quit the game

#Design changes

In this design, we extended the wumpus game by adding functionality for two players. For this new functionality, we have an array of Players in MazeImpl as this contains the implementation of the maze for single player, and we also added gold and thief in the cell for which we have added function fillGoldAndTheif to the maze. This function was present earlier when maze initial version had only gold and theif, but it was removed when wumpus implementation came. Now, the function is added again. Moreover, I have added an attribute gold value for a player as in two-player mode we compare the gold value to find the winner, also gold attribute was there in previous maze version. We have also created a read-only implementation of the maze by creating a new interface MazeGUI which can be used by a view for read-only purposes. This contains different methods to read data about players, cells, and maze. We have also added a switchPlayer, and setRestrictedPlayer int the maze interface for 2 player functionality. ReadOnly functionality was useful now the view can change itself and will not change the model.


#Assumptions

1) In 2 player mode the player who has more gold wins while in 1 player mode you either get killed or kill the wumpus
2) Gold value is 0 after a cell is visited, but theif is still present
3) A cell cannot have wumpus and gold-theif combination
4) Game can be started and ended anytime.
5)User has to enter all the details before the maze can start.

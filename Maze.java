import java.util.*;
import java.io.*;
public class Maze{

    private char[][]maze;
    private boolean animate;//false by default
    private int[] moveset;
    /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)
      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!

      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:
         throw a FileNotFoundException or IllegalStateException
    */
    public Maze(String filename) throws FileNotFoundException{
        File in = new File(filename);
        animate = false;
        List<String> temp = new ArrayList<String>();
        Scanner text = new Scanner(in);
        while (text.hasNextLine()) {
          String line = text.nextLine();
          temp.add(line);
        }
        moveset = new int[] {1, 0, 0, 1, -1, 0, 0, -1};
        int r = temp.size();
        int c = temp.get(0).length();
        maze = new char[r][c];
        for (int row = 0; row < r; row++) {
          for (int col = 0; col < c; col++) {
            maze[row][col] = temp.get(row).charAt(col);
          }
        }
        if (!validFile()) throw new IllegalStateException("The maze must have only one end and one start.");
    }
    /*Return the string that represents the maze.
    It should look like the text file with some characters replaced.
    */
    private boolean validFile() {
      int eCount = 0;
      int sCount = 0;
      for (char[] elem:maze) {
        for (char c:elem) {
          if (c == 'E') eCount++;
          if (c == 'S') sCount++;
        }
      }
      return eCount == 1 && sCount == 1;
    }
    public String toString(){
      String output = "";
      for (char[] elem:maze) {
        for (char c: elem) {
          output += c;
        }
        output += '\n';
      }
      return output;
    }
    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }

    public void setAnimate(boolean b){
        animate = b;
    }

    public void clearTerminal(){
        //erase terminal, go to top left of screen.
        System.out.println("\033[2J\033[1;1H");
    }


    /*Wrapper Solve Function returns the helper function
      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.
    */
    public int solve(){
            //find the location of the S.
      int[] start = findStart();
            //erase the S
      return solve(start[0], start[1]);
            //and start solving at the location of the s.
            //return solve(???,???);
    }

    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.

      Postcondition:
        The S is replaced with '@' but the 'E' is not.
        All visited spots that were not part of the solution are changed to '.'
        All visited spots that are part of the solution are changed to '@'
    */
    private int countAt() {
      int countAt = 0;
      for (char[] elem:maze) {
        for (char c:elem) {
          if (c == '@') countAt++;
        }
      }
      return countAt;
    }
    private int[] findStart() {
      for (int i = 0; i < maze.length; i++) {
        for (int i2 = 0; i2 < maze[0].length; i2++) {
          if (maze[i][i2] == 'S') return new int[] {i, i2};
        }
      }
      return new int[0];
    }
    private boolean move(int r, int c) {
      if (maze[r][c] != '.' && maze[r][c] != '#') {
        return true;
      }
      return false;
    }
    private int solve(int row, int col){ //you can add more parameters since this is private
        //automatic animation! You are welcome.
        if(animate){
            clearTerminal();
            System.out.println(this);
            wait(20);
        }
        if (maze[row][col] == 'E') return 1;
        maze[row][col] = '@';
        for (int i = 0; i < moveset.length; i += 2) {
          if (move(row + moveset[i], col + moveset[i + 1])) {
            solve(row + moveset[i], col + moveset[i + 1]);
          }
        }
        maze[row][col] = '.';

        //COMPLETE SOLVE
        return -1; //so it compiles
    }

}

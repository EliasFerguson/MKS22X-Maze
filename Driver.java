import java.util.*;
import java.io.*;
public class Driver{
    public static void main(String[]args){
      String filename = "data1.dat";
      try{
        Maze f;
        f = new Maze(filename);//true animates the maze.

        f.setAnimate(true);
        f.solve();
        System.out.println(f);
      }catch(FileNotFoundException e){
        System.out.println("Invalid filename: "+filename);
      } 
      /* try {
        Maze test = new Maze("test.txt");
        System.out.println(test.toString());
      }
      catch (FileNotFoundException e) {
        System.out.println(0);
      }
      */
    }
}

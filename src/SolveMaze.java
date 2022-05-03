/**
 * Name: Jerimey Simons
 * Date: 4/8/2022
 * Description: Program to solve a maze from a text file and proceed through the maze
 * Using a recursive method.
 */
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class SolveMaze {
    //global variable library
    public static char[][][] maze = new char[15][15][2];
    public static int  count =0;

    public static void main(String[] arg) {
        //main variable library
        int counter = 1;
        SolveMaze horse = new SolveMaze();
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader("Mazes-2.txt")));
            String c = sc.next();
            System.out.println();
            System.out.println("Welcome to Maze Solve: Where mazes are solved using recursion\n");
            //while loop to scan maze
            while(sc.hasNextLine()){
                for(int height =0; height<=1; height++){
                    for(int row = 0; row<=14; row++){
                        for(int column = 0; column<=14; column++){
                            maze[row][column][height] = c.charAt(column);
                        }
                        if(sc.hasNextLine()){
                        c= sc.next();
                        }
                    }
                }
                System.out.print("Maze: " + counter);
                System.out.print("                   ");
                System.out.println("HyperSpace: ");
                for(int row = 0; row<=14; row++){
                //print normal maze
                    for(int column = 0; column<=14; column++){
                        System.out.print(maze[row][column][0]);
                    }

                    //output space between mazes
                    System.out.print("           ");

                    //print hyberSpace maze
                    for(int column = 0; column<=14; column++){
                        System.out.print(maze[row][column][1]);
                    }
                    System.out.println();
                }
                System.out.println();
                counter++;

                //scan the first row for the door to the maze and pass the cord to solveMaze
                int rw= 0,col =0, ht =0;
                for(int i =0; i<15; i++){
                    if(maze[rw][col][ht] == '1'){
                        System.out.println("The Door opens at row: " + rw+ " amd column: "+ col);
                        boolean solved = horse.solveMaze(rw,col,0);
                        count = 0;
                        System.out.println("Maze solved: "+ solved);
                        System.out.println("-------------------------------------------------------------------\n");
                        break;
                    }
                    col++;
                }
            }
        }catch (FileNotFoundException e) {
              System.out.println();

        }
    }


/**
 * recursive method to solve maze
 * @param row <code>int</code> row number in position of maze
 * @param col <code>int</code> col number in position of maze
 * @param dir <code>int</code> dir number representing directional position of movement within the maze
 * @return true <code>boolean</code> exit point in the maze was found
 */
  public boolean solveMaze(int row, int col, int dir){
      count++;
      System.out.print("Step: " + count + "\trow:" + row +"\tcolumn:\t"+ col+ "\t "+"Coming from the:\t");

      SolveMaze worker = new SolveMaze();
      boolean returnValue = false;

      //0:N 1:E 2:S 3:W 4:H
      boolean[] compass = new boolean[5];
      compass[dir] = true;

      switch (dir) {
          case 0 -> System.out.print("North:>\t");
          case 1 -> System.out.print("East:>\t");
          case 2 -> System.out.print("South:>\t");
          case 3 -> System.out.print("West:>\t");
          case 4 -> System.out.print("HyperSpace:>\t");
      }
      //base case
      if(row == 15){ //solved maze
          return true;
      }
      if(row <0){  //out of bounds
          System.out.println("Out of Bounds!");
          return false;
      }
      if(col <0){ //out of bounds
          System.out.println("Out of Bounds!");
          return false;
      }
      if(maze[row][col][0] == '0'){  //hit a wall
          System.out.println("Hit a Wall!");
          return false;
      }
      System.out.println("On the right path!");
      Random r = new Random();
      while(!returnValue && (!compass[0] || !compass[1] || !compass[2]
              || !compass[3] || !compass[4]) ){
          int randomDirection = r.nextInt(5);
          while(compass[randomDirection]){
              randomDirection = r.nextInt(5);
          }
          switch (randomDirection) {
              case 0 -> {
                  //go north
                  compass[randomDirection] = true;
                  returnValue = worker.solveMaze(row - 1, col, 2);
              }
              case 1 -> {
                  //go east
                  compass[randomDirection] = true;
                  returnValue = worker.solveMaze(row, col + 1, 3);
              }
              case 2 -> {
                  //go south
                  compass[randomDirection] = true;
                  returnValue = worker.solveMaze(row + 1, col, 0);
              }
              case 3 -> {
                  //go west
                  compass[randomDirection] = true;
                  returnValue = worker.solveMaze(row, col - 1, 1);
              }
              //go hyper space
              case 4 -> {
                  compass[randomDirection] = true;
                  if (maze[row][col][1] == '0') {
                      returnValue = worker.solveMaze(row, col, 4);
                      break;
                  }
                  returnValue = worker.findMate(row, col, 4);
              }
          }
      }
      return returnValue;
  }

/**
 * Method to accept char in maze and find is counterpart
 * @param row <code>int</code> row number in maze  of char
 * @param col <code>int</code> column number in maze of char
 * @param dir <code>int</code> direction in the maze
 * @return returns row and col of char counterpart and passes the cords to solveMaze
 */
  public boolean findMate(int row, int col, int dir){

    SolveMaze worker = new SolveMaze();
    char a = maze[row][col][1];
    if(maze[row][col][1] != '0' ){
         for(int i = 0; i<15; i++){
             for(int j = 0; j<15; j++){
                 if (maze[i][j][1] == a && i != row && col != j) {
                     System.out.println("Warp Jump!!!!!!!");
                     return worker.solveMaze(i,j,dir);
                 }
             }
         }
     }
     return false;
  }


}

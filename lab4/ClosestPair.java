import java.util.*;
import java.io.*;

class Point {
  int x;
  int y;
  
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

public class ClosestPair {

  static void printPoint(Point[] points) {
    for(Point pt: points)
      System.out.print("(" + pt.x + " " + pt.y + ")");
    System.out.println();
  }

  public static void main(String[] args) {
    int n = 6, min = 20, max = 21;
    Point[] points = new Point[n];

		Random rand = new Random();

    for (int i = 0; i < n; i++) {
      points[i] = rand.nextInt(max);
    }

    printPoint(points);

  }
}
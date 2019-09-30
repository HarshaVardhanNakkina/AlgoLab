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
  public static Comparator<Point> sortByXComparator = new Comparator<Point>() {
    @Override
    public int compare(Point p1, Point p2) {
      return p1.x - p2.x;
    }
  };
  public static Comparator<Point> sortByYComparator = new Comparator<Point>() {
    @Override
    public int compare(Point p1, Point p2) {
      return p1.y - p2.y;
    }
  };

  static void printPoint(Point[] points) {
    for(Point pt: points)
      System.out.print("(" + pt.x + "," + pt.y + ")");
    System.out.println();
  }

  static double distance(Point p1, Point p2) {
    double xDiff = p1.x - p2.x;
    double yDiff = p1.y - p2.y;
    // System.out.println(xDiff +" "+ yDiff);
    return Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
  }

  static Point[] bruteForceClosestPair(Point[] p) {
    double minDist = Integer.MAX_VALUE;
    Point[] closestPair = new Point[2];
    for(int i = 0; i < p.length - 1; i++) {
      for (int j = i + 1; j < p.length; j++) {
        double dist = distance(p[i], p[j]);
        if(dist < minDist){
          minDist = dist;
          closestPair[0] = p[i];
          closestPair[1] = p[j];
        }
      }
    }
    return closestPair;
  }

  static Point[] verifyWithStrip(Point[] curClosest, ArrayList<Point> strip, double curMinDist) {
    Point[] closestPair = curClosest.clone();
    for(int i = 0; i < strip.size() - 1; i++){
      for(int j = i + 1; j < strip.size(); j++){
        double curDist = distance(strip.get(i), strip.get(j));
        if(curDist < curMinDist){
          curMinDist = curDist;
          closestPair[0] = strip.get(i);
          closestPair[1] = strip.get(j);
        }
      }
    }
    return closestPair;
  }

  static Point[] sortByY(Point[] x) {
    int mid = (x.length - 1)/ 2;
    Point[] y = new Point[x.length];
    Point[] yl = Arrays.copyOfRange(x, 0, mid + 1);
    Point[] yr = Arrays.copyOfRange(x, mid + 1, x.length);
    int i = 0, j = 0, k = 0;
    while(i < yl.length && j < yr.length) {
      if(yl[i].y <= yr[j].y) {
        y[k++] = yl[i];
        i++;
      }else {
        y[k++] = yr[j];
        j++;
      }
    }
    while(i < yl.length) {
      y[k++] = yl[i];
      i++;
    }
    while(j < yr.length) {
      y[k++] = yr[j];
      j++;
    }
    return y;
  }

  static Point[] findClosestPair(Point[] x, Point[] y){
    if(x.length < 4)
      return bruteForceClosestPair(x);
    
    Point[] closestPair =  new Point[2];
    int mid = (x.length - 1) / 2;
    Point[] xl = Arrays.copyOfRange(x, 0, mid + 1);
    Point[] xr = Arrays.copyOfRange(x, mid + 1, x.length);
    
    Point[] yl = sortByY(xl);
    Point[] yr = sortByY(xr);
    
    Point[] closestPairLeft = findClosestPair(xl, yl);
    Point[] closestPairRight = findClosestPair(xr, yr);

    double closestDistLeft = distance(closestPairLeft[0],closestPairLeft[1]);
    double closestDistRight = distance(closestPairRight[0],closestPairRight[1]);

    if(closestDistLeft < closestDistRight) {
      closestPair = closestPairLeft.clone();
    }else {
      closestPair = closestPairRight.clone();
    }

    double delta = Math.min(closestDistLeft, closestDistRight);
    
    ArrayList<Point> strip = new ArrayList<Point>();
    for(int i = 0; i < y.length; i++) {
      if(distance(y[i], x[mid]) < delta) strip.add(y[i]);
    }

    return verifyWithStrip(closestPair, strip, delta);
  }


  public static void main(String[] args) {
    InputStreamReader ir = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(ir);
    
    int n = 10; //** n = 10 by default
    try {
      n = Integer.parseInt(br.readLine());
    } catch (IOException ioe) {
      System.out.println(ioe);
    }
    Point[] points = new Point[n];

    Random rand = new Random();
    int min = n, max = n + 1;
    for (int i = 0; i < n; i++) {
      points[i] = new Point(rand.nextInt(max + min) - min, rand.nextInt(max + min) - min);
    }

    Point[] x = points.clone();
    Point[] y = points.clone();

    Arrays.sort(x, sortByXComparator);
    Arrays.sort(y, sortByYComparator);

    printPoint(x);

    Point[] closestPair = findClosestPair(x, y);
    printPoint(closestPair);
    System.out.println(distance(closestPair[0], closestPair[1]));
    
  }
}
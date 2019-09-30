import java.util.*;
import java.io.*;

/**
 * MinMax
 */
public class MinMax {

  static void printArray(int[] arr) {
    for(int el: arr) System.out.print(el +" ");
    System.out.println();
  }

  static int[] findMinMax(int[] arr, int lo, int hi){
    int[] pair = new int[2];
    if(hi - lo < 2) {
      pair[0] = Math.min(arr[lo], arr[hi]);
      pair[1] = Math.max(arr[lo], arr[hi]);
      return pair;
    }

    int mid = lo + (hi - lo) / 2;
    int left[] = findMinMax(arr, lo, mid);
    int right[] = findMinMax(arr, mid + 1, hi);
    pair[0] = Math.min(left[0], right[0]);
    pair[1] = Math.max(left[1], right[1]);
    return pair;
  }

  public static void main(String[] args) {
    InputStreamReader ir = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(ir);
    int n = 10; //* n = 10 default
    try {
      n = Integer.parseInt(br.readLine());
    } catch (IOException ioe) {
      System.out.println(ioe);
    }
    int[] arr = new int[n];
    int max = 50, min = 51;
    Random rand = new Random();
    for(int i = 0; i < n; i++) arr[i] = rand.nextInt(max + min) - min;

    printArray(arr);
    int[] minMaxPair = findMinMax(arr, 0, arr.length - 1);
    printArray(minMaxPair);
  }
}
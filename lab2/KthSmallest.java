import java.util.*;
import java.io.*;

/**
 * KthSmallest
 */
public class KthSmallest {

    static void printArray(double[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static void swap(double[] arr, int lo, int hi) {
        double temp = arr[lo];
        arr[lo] = arr[hi];
        arr[hi] = temp;
    }

    static int partition(double[] arr, int lo, int hi, double pivot) {

        while (lo < hi) {
            while (arr[lo] < pivot)
                lo = lo + 1;

            while (arr[hi] > pivot)
                hi = hi - 1;

            if (lo >= hi)
                return hi;

            swap(arr, lo, hi);
            lo = lo + 1;
            hi = hi - 1;
        }
        return hi;

    }

    static void findPseudoMedian(double arr[], int i, int j, double[] pseudoMeds) {
        double[] temp = Arrays.copyOfRange(arr, i, j + 1);
        // int pLen = pseudoMeds.length;
        int tLen = temp.length;
        Arrays.sort(temp);
        if (tLen % 2 == 0) {
            pseudoMeds[i / 5] = (temp[tLen / 2] + temp[tLen / 2 - 1]) / 2;
        } else {
            pseudoMeds[i / 5] = temp[tLen / 2];
        }
    }

    static double devide5Blocks(double arr[]) {

        double[] pseudoMeds = new double[(int) Math.ceil(arr.length / 5.0)];

        for (int i = 0; i < arr.length; i += 5) {
            int j = i + 4;
            if (j > arr.length - 1) {
                j = j - (j - (arr.length - 1));
            }
            // System.out.println(i + " " + j);
            findPseudoMedian(arr, i, j, pseudoMeds);
        }
        // System.out.println("pseudo meds");
        // printArray(pseudoMeds);

        if (pseudoMeds.length == 1)
            return pseudoMeds[0];

        if (pseudoMeds.length == 2)
            return pseudoMeds[1];
        // return (pseudoMeds[pseudoMeds.length / 2] + pseudoMeds[pseudoMeds.length / 2
        // - 1]) / 2;

        return devide5Blocks(pseudoMeds);
    }

    static double getMedianOfMeds(double[] arr) {
        return devide5Blocks(arr);
    }

    static double findKthSmallest(double[] arr, int lo, int hi, int k) {

        if (lo == hi)
            return arr[lo];

        double medianOfMedians = getMedianOfMeds(arr);

        // System.out.println("MoM " + medianOfMedians);

        int pivotInd = partition(arr, lo, hi, medianOfMedians);

        // System.out.println("pivot Index: " + pivotInd);

        if (k - 1 == pivotInd)
            return arr[k - 1];
        else if (k - 1 < pivotInd)
            return findKthSmallest(arr, lo, pivotInd - 1, k);
        else
            return findKthSmallest(arr, pivotInd + 1, hi, k);

    }

    static double[] readInput(String filePath) throws IOException {
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        int n = Integer.parseInt(br.readLine());
        double[] arr = new double[n];

        String[] arrStr = br.readLine().split(" ");

        for (int i = 0; i < n; i++) {
            arr[i] = Double.parseDouble(arrStr[i]);
        }

        br.close();
        fr.close();

        return arr;

    }

    public static void main(String[] args) throws IOException {
        // double[] arr = { 328, -98, -72, 931, 473, 622, 821, 871, 302, 577, 25, -98,
        // 920, 516, 339, -54, 170, 437, 981,
        // 443, 252, 376, 965, 119, 477, 683, 122, 939, 200, 432 };
        // Arrays.sort(arr);
        // printArray(arr);
        // System.out.println(arr[14]);
        // FileWriter fr = new FileWriter("./sizeandtime.txt", true);
        // fr.write("{name: 'quicksort',\nvalues:[");
        // long startTime = System.currentTimeMillis();
        // System.out.println(findKthSmallest(arr, 0, arr.length - 1, 15));
        // long endTime = System.currentTimeMillis();
        // System.out.println("Time taken: " + (endTime - startTime));

        for (int i = 0; i < 8; i++) {
            double[] arr = readInput("./testcases/" + (i + 1) + ".txt");
            long startTime = System.currentTimeMillis();
            System.out.println(findKthSmallest(arr, 0, arr.length - 1, arr.length / 2 - 1));
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken: " + (endTime - startTime));
            // timeTaken[i] = endTime - startTime;
            // printArray(arr);
            // System.out.println(arr.length + " " + timeTaken[i]);
            // fr.write("{size:" + arr.length + ",\n" + "time:" + timeTaken[i] + "},\n");
            System.out.println("____________________");
        }
    }
}
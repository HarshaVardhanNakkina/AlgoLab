import java.util.*;

/**
 * KthSmallestTrial
 */
public class KthSmallestTrial {

    static void swap(double arr[], int i, int j) {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void printArray(double[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    static int pivot()

    static int selecKthSmallest(double[] arr, int lo, int hi, int k) {
        if (lo == hi)
            return lo;

        int medianOfMeds = pivot(arr, lo, hi);
    }

    public static void main(String[] args) {
        double[] arr = { 328, -98, -72, 931, 473, 622, 821, 871, 302, 577, 25, -98, 920, 516, 339, -54, 170, 437, 981,
                443, 252, 376, 965, 119, 477, 683, 122, 939, 200, 432 };

        selecKthSmallest(arr, lo, hi, 5); // array, left exterme, right extreme, k

    }
}
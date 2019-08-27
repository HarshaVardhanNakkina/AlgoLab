
/**
 * KthSmallestWiki
 */
import java.util.*;

public class KthSmallestWiki {

    static void swap(double[] arr, int i, int j) {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static int partition5(double[] arr, int lo, int hi) {
        int i = lo + 1;
        while (i <= hi) {
            int j = i;
            while (j > lo && arr[j - 1] > arr[j]) {
                swap(arr, j - 1, j);
                j--;
            }
            i++;
        }
        return (int) Math.floor((lo + hi) / 2);
    }

    static int pivot(double[] arr, int lo, int hi) {
        if ((hi - lo) < 5)
            return partition5(arr, lo, hi);
        for (int i = lo; i <= hi; i += 5) {
            int subRight = i + 4;
            if (subRight > hi)
                subRight = hi;
            int median5 = partition5(arr, i, subRight);
            swap(arr, median5, lo + (int) Math.floor((i - lo) / 5));
        }
        int mid = (hi - lo) / 10 + lo + 1;
        return selectKthSmallest(arr, lo, lo + (int) Math.floor((hi - lo) / 5), mid);
    }

    static int partition(double[] arr, int lo, int hi, int pivotIndex, int k) {
        double pivotVal = arr[pivotIndex];
        swap(arr, pivotIndex, hi);
        int storeIndex = lo;
        for (int i = lo; i < hi; i++) {
            if (arr[i] < pivotVal) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        int storeIndexEq = storeIndex;
        for (int i = storeIndex; i < hi; i++) {
            if (arr[i] == pivotVal) {
                swap(arr, storeIndexEq, i);
                storeIndexEq++;
            }
        }
        swap(arr, hi, storeIndexEq);

        if (k < storeIndex)
            return storeIndex;
        if (k <= storeIndexEq)
            return k;
        return storeIndexEq;
    }

    static int selectKthSmallest(double[] arr, int lo, int hi, int k) {
        while (true) {
            if (lo == hi)
                return lo;
            int pivotIndex = pivot(arr, lo, hi);
            pivotIndex = partition(arr, lo, hi, pivotIndex, k);
            if (k == pivotIndex)
                return k;
            else if (k < pivotIndex)
                hi = pivotIndex - 1;
            else
                lo = pivotIndex + 1;
        }
    }

    public static void main(String[] args) {
        double[] arr = { 328, -98, -72, 931, 473, 622, 821, 871, 302, 577, 25, -98, 920, 516, 339, -54, 170, 437, 981,
                443, 252, 376, 965, 119, 477, 683, 122, 939, 200, 432 };

        int reqIndex = selectKthSmallest(arr, 0, arr.length - 1, 5);
        System.out.println(arr[reqIndex]);
        Arrays.sort(arr);
        System.out.println(arr[4]);

    }
}
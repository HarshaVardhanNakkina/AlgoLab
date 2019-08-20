import java.io.*;
import java.util.*;

public class Mergesort {

	static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	static void printArray(int[] arr) {
		int n = arr.length;
		for (int i = 0; i < n; i++)
			System.out.print(arr[i] + " ");
		System.out.println();
	}

	static void mergesortMerge(int[] arr, int lo, int mid, int hi) {

		int[] left = Arrays.copyOfRange(arr, lo, mid + 1);
		int[] right = Arrays.copyOfRange(arr, mid + 1, hi + 1);
		int nLeft = left.length;
		int nRight = right.length;

		int i = 0, j = 0, k = lo;
		while (i < nLeft && j < nRight) {
			if (left[i] <= right[j]) {
				arr[k++] = left[i++];
			} else {
				arr[k++] = right[j++];
			}
		}
		while (i < nLeft) {
			arr[k++] = left[i++];
		}
		while (j < nRight) {
			arr[k++] = right[j++];
		}
	}

	static void mergesortSplit(int[] a, int lo, int hi) {
		if (lo < hi) {
			int mid = lo + (hi - lo) / 2;
			mergesortSplit(a, lo, mid);
			mergesortSplit(a, mid + 1, hi);
			mergesortMerge(a, lo, mid, hi);
		}
	}

	static void mergesort(int[] arr) {
		int n = arr.length;
		// int[] dup = Arrays.copyOf(arr, n);
		mergesortSplit(arr, 0, n - 1);
	}

	static int[] readInput(String filePath) throws IOException {
		FileReader fr = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fr);
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];

		String[] arrStr = br.readLine().split(" ");

		for (int i = 0; i < n; i++) {
			arr[i] = Integer.parseInt(arrStr[i]);
		}

		br.close();
		fr.close();

		return arr;

	}

	public static void main(String[] args) throws IOException {
		// int[] testArr = {3,5,6,2,1,4};
		// mergesort(testArr);
		// printArray(testArr);
		long[] timeTaken = new long[8];
		FileWriter fr = new FileWriter("./sizeandtime.txt", true);
		fr.write("{name: 'mergesort',\nvalues:[");
		for (int i = 0; i < 8; i++) {
			int[] arr = readInput("./testcases/" + (i + 1) + ".txt");
			long startTime = System.currentTimeMillis();
			mergesort(arr);
			long endTime = System.currentTimeMillis();
			timeTaken[i] = endTime - startTime;
			// printArray(arr);
			System.out.println(arr.length + " " + timeTaken[i]);
			fr.write("{size:" + arr.length + ",\n" + "time:" + timeTaken[i] + "},\n");
			System.out.println("===========");
		}
		fr.write("]}\n");
		fr.close();
	}
}
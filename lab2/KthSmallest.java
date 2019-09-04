import java.util.*;
import java.io.*;

class KthSmallest {

	static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	static void printArray(int[] arr, int lo, int hi) {
		for (int i = lo; i <= hi; i++)
			System.out.print(arr[i] + " ");
		System.out.println();
	}

	static int medianOfFive(int[] arr, int lo, int hi) {
		int i = lo + 1;

		while (i <= hi) {
			int j = i;
			while (j > lo && arr[j - 1] > arr[j]) {
				swap(arr, j - 1, j);
				j--;
			}
			i++;
		}

		return arr[lo + (hi - lo) / 2];

	}

	static int partition(int[] arr, int lo, int hi, int pivot, int k) {

		int lessPartInd = lo;
		for (int i = lo; i <= hi; i++) {
			if (arr[i] < pivot) {
				swap(arr, i, lessPartInd);
				lessPartInd++;
			}
		}

		int eqPartInd = lessPartInd;

		for (int i = eqPartInd; i <= hi; i++) {
			if (arr[i] == pivot) {
				swap(arr, i, eqPartInd);
				eqPartInd++;
			}
		}

		// System.out.println(lessPartInd +" "+eqPartInd);
		// return 0;

		if (k < lessPartInd)
			return lessPartInd;
		else if (k < eqPartInd)
			return k;

		return eqPartInd - 1;

	}

	static int medianOfMedians(int[] arr, int lo, int hi) {
		// System.out.println("medianOfMedians");
		int n = hi - lo + 1;
		int[] medians = new int[(int) Math.ceil(n / 5.0)];
		int mediansInd = 0;

		if (hi - lo < 5)
			return medianOfFive(arr, lo, hi);

		for (int i = lo; i <= hi; i += 5) {
			int fiveMore = i + 4;
			if (fiveMore > hi)
				fiveMore = hi;

			medians[mediansInd++] = medianOfFive(arr, i, fiveMore);

		}

		// printArray(medians, 0, medians.length - 1);

		int mid = medians.length / 2;

		return findKthSmallest(medians, 0, medians.length - 1, mid);

		// return 0;

	}

	static int findKthSmallest(int[] arr, int lo, int hi, int k) {
		// System.out.println("findKthSmallest");
		int pivot = medianOfMedians(arr, lo, hi);
		// System.out.println(pivot);
		int pivotIndex = partition(arr, lo, hi, pivot, k);

		if (k == pivotIndex)
			return arr[k];
		else if (k < pivotIndex)
			return findKthSmallest(arr, lo, pivotIndex - 1, k);

		return findKthSmallest(arr, pivotIndex + 1, hi, k);
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
	// public static void main(String[] args) {
	// int[] arr = { 1, 2, 4, 5, 3, 6, 7, 2, 3, 9, 5, 7 };
	// System.out.println("3rd " + findKthSmallest(arr, 0, arr.length - 1, 3));
	// System.out.println("5th " + findKthSmallest(arr, 0, arr.length - 1, 5));
	// System.out.println("1st " + findKthSmallest(arr, 0, arr.length - 1, 1));
	// System.out.println("7th " + findKthSmallest(arr, 0, arr.length - 1, 7));
	// System.out.println("11th " + findKthSmallest(arr, 0, arr.length - 1, 11));

	// for (int i = 0; i < arr.length; i++)
	// System.out.print(i + " ");
	// System.out.println();
	// Arrays.sort(arr);
	// printArray(arr, 0, arr.length - 1);

	// arr = new int[] { 4, 5, 2, 9, 2, 6, 8, 2, 3, 5 };
	// System.out.println("3rd " + findKthSmallest(arr, 0, arr.length - 1, 3));
	// System.out.println("5th " + findKthSmallest(arr, 0, arr.length - 1, 5));
	// System.out.println("1st " + findKthSmallest(arr, 0, arr.length - 1, 1));
	// System.out.println("7th " + findKthSmallest(arr, 0, arr.length - 1, 7));
	// System.out.println("9th " + findKthSmallest(arr, 0, arr.length - 1, 9));

	// for (int i = 0; i < arr.length; i++)
	// System.out.print(i + " ");
	// System.out.println();
	// Arrays.sort(arr);
	// printArray(arr, 0, arr.length - 1);

	// arr = new int[] { 9, 2, 4, 5, 1, 6, 5, 8, 6, 9 };
	// System.out.println("3rd " + findKthSmallest(arr, 0, arr.length - 1, 3));
	// System.out.println("5th " + findKthSmallest(arr, 0, arr.length - 1, 5));
	// System.out.println("1st " + findKthSmallest(arr, 0, arr.length - 1, 1));
	// System.out.println("7th " + findKthSmallest(arr, 0, arr.length - 1, 7));
	// System.out.println("9th " + findKthSmallest(arr, 0, arr.length - 1, 9));

	// for (int i = 0; i < arr.length; i++)
	// System.out.print(i + " ");
	// System.out.println();
	// Arrays.sort(arr);
	// printArray(arr, 0, arr.length - 1);
	// }

	public static void main(String[] args) throws IOException {
		for (int i = 0; i < 8; i++) {
			int[] arr = readInput("./testcases/" + (i + 1) + ".txt");

			long startTime = System.currentTimeMillis();
			System.out.println((arr.length - 1) + "th smallest " + findKthSmallest(arr, 0, arr.length - 1, arr.length - 1));
			long endTime = System.currentTimeMillis();
			long timeTaken = endTime - startTime;

			// Arrays.sort(arr);

			// System.out.println(arr[1011]);

			System.out.println("Size: " + arr.length + " Time taken: " + timeTaken + " ms");
			System.out.println("\n");
		}
	}
}
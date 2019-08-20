import java.io.*;

public class Quicksort {

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

	static int quicksortPartition(int[] arr, int lo, int hi) {
		int pivot = arr[lo + (hi - lo) / 2];
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

	static void quicksort(int[] arr, int lo, int hi) {
		if (lo < hi) {
			int pivotInd = quicksortPartition(arr, lo, hi);
			quicksort(arr, lo, pivotInd);
			quicksort(arr, pivotInd + 1, hi);
		}
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
		// int[] testArr = { 3, 5, 6, 2, 1, 4 };
		// quicksort(testArr, 0, testArr.length - 1);
		// printArray(testArr);
		long[] timeTaken = new long[8];
		FileWriter fr = new FileWriter("./sizeandtime.txt", true);
		fr.write("{name: 'quicksort',\nvalues:[");
		for (int i = 0; i < 8; i++) {
			int[] arr = readInput("./testcases/" + (i + 1) + ".txt");
			long startTime = System.currentTimeMillis();
			quicksort(arr, 0, arr.length - 1);
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
import java.io.*;

public class Selectionsort {

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

	static void selectionsort(int[] arr) {
		int n = arr.length;
		for (int i = 0; i < n - 1; i++) {
			int minAt = i;
			for (int j = i + 1; j < n; j++) {
				if (arr[j] < arr[minAt])
					minAt = j;
			}
			if (minAt != i) {
				swap(arr, i, minAt);
			}
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
		// int[] testArr = {6,5,4,3,2,1};
		// selectionsort(testArr);
		// printArray(testArr);
		long[] timeTaken = new long[8];
		FileWriter fr = new FileWriter("./sizeandtime.txt", true);
		fr.write("{name: 'selectionsort',\nvalues:[");
		for (int i = 0; i < 8; i++) {
			int[] arr = readInput("./testcases/" + (i + 1) + ".txt");
			long startTime = System.currentTimeMillis();
			selectionsort(arr);
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
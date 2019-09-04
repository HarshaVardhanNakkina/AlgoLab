import java.io.*;
import java.util.*;

public class TestCasesGen {

	public static void main(String[] args) throws IOException {
		// 4.txt, 6.txt best case
		// 5.txt, 7.txt worst cases
		int n = 120000, min = 200000, max = 200001;
		FileWriter fw = new FileWriter("./7.txt");
		Random rand = new Random();
		fw.write(String.valueOf(n) + "\n");
		for (int i = n; i >= 0; i--) {
			fw.write(String.valueOf(i) + " ");
			// fw.write(String.valueOf(rand.nextInt(max + min) - min) + " ");
		}
		fw.close();
	}
}
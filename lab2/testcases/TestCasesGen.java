import java.io.*;
import java.util.*;

public class TestCasesGen {

	public static void main(String[] args) throws IOException {
		int n = 150000, min = 200, max = 200;
		FileWriter fw = new FileWriter("./8.txt");
		Random rand = new Random();
		fw.write(String.valueOf(n) + "\n");
		for (int i = 0; i < n; i++) {
			fw.write(String.valueOf(rand.nextInt(max + min) - min) + " ");
		}
		fw.close();
	}
}
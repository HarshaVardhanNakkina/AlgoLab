package testcases;

import java.io.*;
import java.util.*;

public class TestCasesGen {

	public static void main(String[] args) throws IOException {
		int n = 30000, min = 200000, max = 200001;
		FileWriter fw = new FileWriter("./3.txt");
		Random rand = new Random();
		fw.write(String.valueOf(n) + "\n");
		for (int i = 0; i < n; i++) {
			fw.write(String.valueOf(rand.nextInt(max + min) - min) + " ");
		}
		fw.close();
	}
}
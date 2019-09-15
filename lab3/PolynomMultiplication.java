import java.util.*;
import java.io.*;

/**
 * PolynomMultiplication
 */

class ComplexNumber {
	int real;
	int img;

	ComplexNumber() {
		this.real = 0;
		this.img = 0;
	}

	ComplexNumber(double real, double img) {
		this.real = (int) real;
		this.img = (int) img;
	}

	ComplexNumber mul(ComplexNumber c) {
		ComplexNumber res = new ComplexNumber();

		res.real = (this.real * c.real) - (this.img * c.img);
		res.img = (this.real * c.img) + (this.img * c.real);

		return res;
	}

	ComplexNumber add(ComplexNumber c) {
		ComplexNumber res = new ComplexNumber();

		res.real = (this.real + c.real);
		res.img = (this.real + c.img);

		return res;
	}

	ComplexNumber sub(ComplexNumber c) {
		ComplexNumber res = new ComplexNumber();

		res.real = (this.real - c.real);
		res.img = (this.real - c.img);

		return res;
	}

}

public class PolynomMultiplication {

	static InputStreamReader ir = new InputStreamReader(System.in);
	static BufferedReader br = new BufferedReader(ir);

	static void printArray(ComplexNumber[] arr) {
		for (ComplexNumber el : arr)
			System.out.print("(" + el.real + " " + el.img + ") ");
		System.out.println();
	}

	static int getExtendedSize(int deg) {

		int n = (int) (Math.log10(deg) / Math.log10(2));
		int extendedSize = (int) Math.pow(2, n + 1);

		return extendedSize;
	}

	static ComplexNumber[] getPolynomials(int extendedSize) throws IOException {

		ComplexNumber[] arr = new ComplexNumber[extendedSize];
		String[] coeffs = br.readLine().split(" ");

		int i = 0;

		for (; i < coeffs.length; i++) {
			arr[i] = new ComplexNumber(Integer.parseInt(coeffs[i]), 0);
		}
		for (; i < extendedSize; i++) {
			arr[i] = new ComplexNumber(0, 0);
		}
		return arr;
	}

	static ComplexNumber[] TransformPolynomial(ComplexNumber[] poly, int size, int inversion) {

		if (size == 1)
			return poly;

		// double exp = ((inversion * 2 * Math.PI) / size);
		// ComplexNumber wn = new ComplexNumber(1, 0);
		// ComplexNumber w1 = new ComplexNumber(Math.cos(exp), Math.sin(exp));
		ComplexNumber[] w = new ComplexNumber[size];
		for (int i = 0; i < size; i++) {
			double alpha = 2 * Math.PI * i / size;
			w[i] = new ComplexNumber(Math.cos(alpha), Math.sin(alpha));
		}

		printArray(w);

		ComplexNumber[] A0 = new ComplexNumber[size / 2];
		ComplexNumber[] A1 = new ComplexNumber[size / 2];

		for (int i = 0; i < size / 2; i++) {
			A0[i] = new ComplexNumber();
			A0[i].real = poly[i * 2].real;
			A0[i].img = poly[i * 2].img;

			A1[i] = new ComplexNumber();
			A1[i].real = poly[i * 2 + 1].real;
			A1[i].img = poly[i * 2 + 1].img;
		}

		ComplexNumber[] Y0 = TransformPolynomial(A0, size / 2, inversion);
		ComplexNumber[] Y1 = TransformPolynomial(A1, size / 2, inversion);

		ComplexNumber[] Y = new ComplexNumber[size];

		for (int i = 0; i < size / 2; i++) {
			ComplexNumber tmp1 = Y0[i].add(Y1[i].mul(w[i]));

			Y[i] = new ComplexNumber();
			Y[i].real = tmp1.real;
			Y[i].img = tmp1.img;

			ComplexNumber tmp2 = Y0[i].sub(Y1[i].mul(w[i]));

			Y[(size / 2) + i] = new ComplexNumber();
			Y[(size / 2) + i].real = tmp2.real;
			Y[(size / 2) + i].img = tmp2.img;

			// ComplexNumber tmp3 = wn.mul(w1);

			// wn.real = tmp3.real;
			// wn.img = tmp3.img;

		}
		return Y;

	}

	static ComplexNumber[] multiplyPolynomials(ComplexNumber[] a, ComplexNumber[] b, ComplexNumber[] c, int size) {
		for (int i = 0; i < size; i++) {
			ComplexNumber product = a[i].mul(b[i]);
			c[i] = new ComplexNumber();
			c[i].real = product.real;
			c[i].img = product.img;
		}
		return c;
	}

	public static void main(String[] args) throws IOException {

		int degA, degB; // with space between them

		String[] degrees = br.readLine().split(" ");
		degA = Integer.parseInt(degrees[0]);
		degB = Integer.parseInt(degrees[1]);

		int extendedSize = getExtendedSize(degA + degB);

		// System.out.println("extendedSize " + extendedSize);

		ComplexNumber[] polyA = getPolynomials(extendedSize);
		ComplexNumber[] polyB = getPolynomials(extendedSize);

		printArray(polyA);
		printArray(polyB);

		ComplexNumber[] convertedA = TransformPolynomial(polyA, extendedSize, 1);
		ComplexNumber[] convertedB = TransformPolynomial(polyB, extendedSize, 1);

		printArray(convertedA);
		printArray(convertedB);

		ComplexNumber[] resPoly = new ComplexNumber[extendedSize];
		multiplyPolynomials(convertedA, convertedB, resPoly, extendedSize);

		ComplexNumber[] convertedRes = TransformPolynomial(resPoly, extendedSize, -1);

		for (int i = 0; i < extendedSize; i++) {
			convertedRes[i].real = convertedRes[i].real / extendedSize;
			convertedRes[i].img = convertedRes[i].img / extendedSize;
		}

		printArray(convertedRes);

		br.close();
		ir.close();
	}
}
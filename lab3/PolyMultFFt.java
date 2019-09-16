import java.text.DecimalFormat;
import java.util.*;
import java.io.*;
import java.lang.*;


class Complex {
  double real;
  double img;

  Complex() {
    this.real = 0;
    this.img = 0;
  }

  Complex(double real, double img) {
    this.real = real;
    this.img = img;
  }

  Complex mul(Complex c) {
    Complex res = new Complex();

    res.real = (this.real * c.real) - (this.img * c.img);
    res.img = (this.real * c.img) + (this.img * c.real);

    return res;
  }

  Complex add(Complex c) {
    Complex res = new Complex();

    res.real = (this.real + c.real);
    res.img = (this.real + c.img);

    return res;
  }

  Complex sub(Complex c) {
    Complex res = new Complex();

    res.real = (this.real - c.real);
    res.img = (this.real - c.img);

    return res;
  }

}

public class PolyMultFFt {

  static int noOfCoefficients = 3;
  static int extendedSize = 1 << noOfCoefficients;

  static Complex[] p = new Complex[extendedSize];
  static Complex[] q = new Complex[extendedSize];

  static Complex[] omega = new Complex[extendedSize];
  static Complex[] omegaInverse = new Complex[extendedSize];

  static void printArray(Complex[] p) {
    for(Complex c: p) {
      System.out.print("(" + c.real +" "+ c.img + ")");  
    }
    System.out.println();
  }

  // splits the polynomial into two arrays based on odd and even indices
  public static Complex[] split(Complex[] a, int start) {
    Complex[] splitted = new Complex[a.length / 2];
    int i = 0;
    while (start < a.length) {
      splitted[i] = a[start];
      start += 2;
      i++;
    }

    return splitted;
  }

  // The function that computes the fourier transform
  public static Complex[] FFTmult(Complex[] a, int length, int pow) {
    if (length == 1) {
      return a;
    }

    Complex[] aEven = split(a, 0);
    Complex[] aOdd = split(a, 1);

    // recursion
    Complex[] fEven = FFTmult(aEven, length / 2, 2 * pow);
    Complex[] fOdd = FFTmult(aOdd, length / 2, 2 * pow);
    Complex[] f = new Complex[length];
    // combining the solution
    for (int i = 0; i < length / 2; i++) {
      Complex temp = omega[i * pow].mul(fOdd[i]);
      f[i] = fEven[i].add(temp);
      f[i + (length / 2)] = fEven[i].sub(temp);
    }
    return f;
  }

  public static void main(String[] args) throws IOException {
    
    double temp;
    int min = 20, max = 21;
    // assign sizes and values randomly
    Random randomGenerator = new Random();

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] pValues = br.readLine().split(" ");
    String[] qValues = br.readLine().split(" ");

    // System.out.println(extendedSize);

    for (int i = 0; i < noOfCoefficients; i++) {
      temp = randomGenerator.nextInt(max + min) - min;
      p[i] = new Complex(Double.parseDouble(pValues[i]), 0);

      temp = randomGenerator.nextInt(max + min) - min;
      q[i] = new Complex(Double.parseDouble(qValues[i]), 0);
    }
    // padding with zeros
    for (int t = noOfCoefficients; t < extendedSize; t++) {
      p[t] = new Complex(0, 0);
      q[t] = new Complex(0, 0);
    }

    // printArray(p);
    // printArray(q);

    for (int a = 0; a < omega.length; a++) {
      omega[a] = new Complex(Math.cos(Math.PI * a / noOfCoefficients), Math.sin(Math.PI * a / noOfCoefficients));
    }

    // stores the inverse of the complex 8th root of unity
    for (int a = 0; a < omega.length; a++) {
      omegaInverse[a] = new Complex(omega[a].real, -omega[a].img);
    }

    // Computing the transforms
    Complex[] pFFT = FFTmult(p, p.length, 1);
    Complex[] qFFT = FFTmult(q, q.length, 1);
    Complex[] afterMult = new Complex[extendedSize];

    // point by point multiplication
    for (int t = 0; t < extendedSize; t++)
      afterMult[t] = pFFT[t].mul(qFFT[t]);

    // using the same array for the inverse values
    for (int a = 0; a < omega.length; a++) {
      omega[a] = new Complex(Math.cos(Math.PI * a / noOfCoefficients), -Math.sin(Math.PI * a / noOfCoefficients));
    }
    Complex[] inverseFFT = FFTmult(afterMult, afterMult.length, 1);
    Complex[] answer = new Complex[inverseFFT.length];
    System.out.println("actual output");
    for (int t = 0; t < answer.length; t++) {
      System.out.print((inverseFFT[t].real / p.length) + " ");
    }

    System.out.println();

  }
}
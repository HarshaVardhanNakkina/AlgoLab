import java.util.*;
import java.io.*;


class Complex {
  double real;
  double img;
  Complex(double real, double img) {
    this.real = real;
    this.img = img;
  }

  Complex mul(Complex c) {
    Complex res = new Complex(0,0);

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

public class FFT {

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  Complex[] omega, omegaInverse;

  static int getExtendedSize(int size) {
    int i = 1;
    while(true) {
      if(i >= size)
        break;
      i *= 2;
    }
    return i * 2;
  }

  static Complex[] getPolynomials(int deg, int extendedSize) {

    Complex[] poly = new Complex[extendedSize];

    int i = 0;

    String[] values = br.readLine().split(" ");

    for(; i < deg; i++)
      poly[i] = new Complex(Integer.parseInt(values[i]), 0);
    
    for(; i < extendedSize; i++)
      poly[i] = new Complex(0, 0);

    return poly;

  }

  static Complex[] split(Complex[] poly, int start) {
    Complex[] splitted = new Complex[poly.length / 2];
    int i = 0;
    while (start < poly.length) {
      splitted[i] = a[start];
      start += 2;
      i++;
    }

    return splitted;
  }

  static Complex[] FFT(Complex[] poly, int size, int inverse) {
    double exp = Math.PI * 2 * inverse / size;
    Complex wn = new Complex(1, 0);
    Complex w1 = new Complex(Math.cos(exp), Math.sin(exp));

    if(size == 1)
      return poly;

    Complex[] even = split(poly, 0);
    Complex[] odd = split(poly, 1);

    Complex[] fEven = FFT(even, size / 2, inverse);
    Complex[] fOdd = FFT(even, size / 2, inverse);

    Complex[] f = new Complex[size];

    for(int i = 0; i < size / 2; i++) {
      Complex temp = wn.mul(fOdd[i]);
      f[i] = fEven[i].add(temp);
      f[i + (size / 2)] = fEven[i].sub(temp);
      wn = wn.mul(w1);
    }

    return f;

  }

  public static void main(String[] args) throws IOException {
    int deg = Integer.parseInt(br.readLine());
    int extendedSize = getExtendedSize(deg + 1);

    Complex[] p = getPolynomials(deg, extendedSize);
    Complex[] q = getPolynomials(deg, extendedSize);

    Complex[] pFFT = FFT(p, extendedSize, 1);
    Complex[] qFFT = FFT(q, extendedSize, 1);

    Complex[] afterMulti = new Complex[extendedSize];

    for (int i = 0; i < extendedSize; i++) {
      afterMulti[i] = pFFT[i].mul(qFFT[i]);
    }

  }
}
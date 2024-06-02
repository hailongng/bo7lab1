package polynomial;
import java.io.*;

public class Driver {
	
	public static void printf(Polynomial p)
	{
		for (int i = 0; i < p.coefficients.length; i++)
		{
			if (p.exponents[i] == 0)
			{
				System.out.print(p.coefficients[i]);
				if (i < p.coefficients.length - 1)
				{
					if (p.coefficients[i + 1] > 0)
					{
						System.out.print("+");
					}
				}
				continue;
			}
			System.out.print(p.coefficients[i] + "x^" + p.exponents[i]);
			if (i < p.coefficients.length - 1)
			{
				if (p.coefficients[i + 1] > 0)
				{
					System.out.print("+");
				}
			}
		}
		System.out.println();
	}
	
	public static void test_normalPolynomial() {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,5};
        int [] e1 = {1, 4};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {-2,-9, 10};
        int [] e2 = {1, 3, 5};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        printf(s);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        System.out.println("s(-1) = " + s.evaluate(-1));
        if(s.hasRoot(-1))
            System.out.println("-1 is a root of s");
        else
            System.out.println("-1 is not a root of s");
    }
	
	public static void test_sum() {
        double [] c1 = {0.1};
        double [] c2 = {0.12};
        double [] c3 = {-0.03};
        int [] e1 = {0};
        Polynomial p1 = new Polynomial(c1, e1);
        Polynomial p2 = new Polynomial(c2, e1);
        Polynomial p3 = new Polynomial(c3, e1);
        Polynomial p4 = p1.add(p2);
        Polynomial p5 = p4.add(p3);
        printf(p1);
        printf(p2);
        printf(p3);
        printf(p4);
        printf(p5);
        if (p5.hasRoot(42069)) {
            System.out.println("balls");
        } else {
            System.out.println("sack");
        }

        System.out.println(p5.evaluate(42069));
    }
	public static void main(String[] args) {
		Polynomial p = new Polynomial();
		printf(p);
		double[] c1 = {-2, 1, 4};
		int[] e1 = {0, 2, 3};
		double[] c2 = {2, 2, 5};
		int[] e2 = {1, 4, 5};
		Polynomial p1 = new Polynomial(c1, e1);
		Polynomial p2 = new Polynomial(c2, e2);
		Polynomial product12 = p1.multiply(p2);
		Polynomial product21 = p2.multiply(p1);
		double[] c3 = {1, -2, 4};
		int[] e3 = {2, 0, 3};
		double[] c4 = {5, 2, 2};
		int[] e4 = {5, 4, 1};
		Polynomial p3 = new Polynomial(c3, e3);
		Polynomial p4 = new Polynomial(c4, e4);
		Polynomial product34 = p3.multiply(p4);
		Polynomial product43 = p4.multiply(p3);
		Polynomial sum12 = p1.add(p2);
		Polynomial sum34 = p3.add(p4);
		printf(sum12);
		printf(sum34);
		printf(product12);
		printf(product21);
		printf(product34);
		printf(product43);
		File f00 = new File("test00.txt");
		File f01 = new File("test01.txt");
		File f02 = new File("test02.txt");
		File f03 = new File("test03.txt");
		Polynomial fromfile00 = new Polynomial(f00);
		Polynomial fromfile01 = new Polynomial(f01);
		Polynomial fromfile02 = new Polynomial(f02);
		Polynomial fromfile03 = new Polynomial(f03);
		System.out.print("fromfile00: "); printf(fromfile00);
		System.out.print("fromfile01: "); printf(fromfile01);
		System.out.print("fromfile02: "); printf(fromfile02);
		System.out.print("fromfile03: "); printf(fromfile03);
		fromfile00.saveToFile("output0.txt");
		fromfile01.saveToFile("output1.txt");
		fromfile02.saveToFile("output2.txt");
		fromfile03.saveToFile("output3.txt");
		Polynomial product0001 = fromfile00.multiply(fromfile01);
		Polynomial product0100 = fromfile01.multiply(fromfile00);
		Polynomial sum0203 = fromfile02.add(fromfile03);
		Polynomial sum0302 = fromfile03.add(fromfile02);
		printf(product0001);
		printf(product0100);
		printf(sum0203);
		printf(sum0302);
		test_normalPolynomial();
		test_sum();
	}

}

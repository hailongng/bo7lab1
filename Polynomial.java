package polynomial;
import java.lang.Math;

public class Polynomial {
	double[] coefficients;
	
	public Polynomial()
	{
		coefficients = new double[1];
		coefficients[0] = 0.0;
	}
	
	public Polynomial(double[] arr)
	{
		int len = arr.length;
		coefficients = new double[len];
		for (int i = 0; i < len; i++)
		{
			coefficients[i] = arr[i];
		}
	}
	
	public Polynomial add(Polynomial p)
	{
		int len;
		if (coefficients.length < p.coefficients.length)
		{
			len = coefficients.length;
			for (int i = 0; i < len; i++)
			{
				p.coefficients[i] += coefficients[i];
			}
			return p;
		}
		else 
		{
			len = p.coefficients.length;
			for (int i = 0; i < len; i++)
			{
				coefficients[i] += p.coefficients[i];
			}
			return this;
		}
	}
	
	public double evaluate(double x)
	{
		double res = 0.0;
		for (int i = 0; i < coefficients.length; i++)
		{
			res += coefficients[i] * Math.pow(x, i);
		}
		return res;
	}
	
	public boolean hasRoot(double x)
	{
		double res = evaluate(x);
		return res == 0;
	}
}

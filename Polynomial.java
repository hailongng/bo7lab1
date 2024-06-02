package polynomial;
import java.lang.Math;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Polynomial {
	double[] coefficients;						// Non-zero coefficients
	int[] exponents;
	
	public Polynomial()
	{
		coefficients = new double[1];
		exponents = new int[1];
		coefficients[0] = 0.0;
		exponents[0] = 0;
	}
	
	public Polynomial(double[] coeff_arr, int[] exp_arr)
	{
		int len = coeff_arr.length;
		coefficients = new double[len];
		exponents = new int[len];
		for (int i = 0; i < len; i++)
		{
			coefficients[i] = coeff_arr[i];
			exponents[i] = exp_arr[i];
		}
	}
	
	public Polynomial(File myOF)
	{
		try
		{
			Scanner myReader = new Scanner(myOF);
			String data = myReader.nextLine();
			String new_data = "";
			myReader.close();
			for (int i = 0; i < data.length(); i++)
			{
				if ((data.charAt(i) == '-' || data.charAt(i) == '+') && new_data.length() != 0)
				{
					new_data += ' ';
				}
				new_data += data.charAt(i);
			}
			String[] processed = new_data.split(" ");
			coefficients = new double[processed.length];
			exponents = new int[processed.length];
			for (int i = 0; i < processed.length; i++)
			{
				if (processed[i].indexOf('x') == -1)				// Deg = 0
				{
					coefficients[i] = Double.parseDouble(processed[i]);
					exponents[i] = 0;
					continue;
				}
				String[] pt = processed[i].split("x");
				if (pt[0].equals("+"))
				{
					coefficients[i] = 1.0;
				}
				else if (pt[0].equals("-"))
				{
					coefficients[i] = -1.0;
				}
				else
				{
					coefficients[i] = Double.parseDouble(pt[0]);
				}
				if (pt.length == 1)
				{
					exponents[i] = 1;
				}
				else
				{
					exponents[i] = Integer.parseInt(pt[1]);
				}
			}
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
	}
	
	public void saveToFile(String outfile)
	{
		String ans = "";
		for (int i = 0; i < coefficients.length; i++)
		{
			if (exponents[i] == 0)
			{
				if (coefficients[i] > 0)
				{
					ans += "+" + coefficients[i];
				}
				else
				{
					ans += "-" + coefficients[i];
				}
				continue;
			}
			if (coefficients[i] > 0)
			{
				ans += "+";
			}
			ans += (coefficients[i] + "x" + exponents[i]);
		}
		if (ans.charAt(0) == '+')
		{
			ans = ans.substring(1, ans.length());
		}
		try
		{
			FileWriter output = new FileWriter(outfile, false);
			output.write(ans);
			output.close();
		}
		catch (IOException error)
		{
			System.out.println("Error occured: ");
			error.printStackTrace();
		}
	}
	
	
	public Polynomial add(Polynomial p)
	{
		/*
		int max_len = Math.max(p.coefficients.length, this.coefficients.length);
		int min_len = Math.min(p.coefficients.length, this.coefficients.length);
		double [] final_c = new double[max_len];
		for (int i = 0; i < min_len; i++)
		{
			final_c[i] = p.coefficients[i] + this.coefficients[i];
		}
		for (int i = min_len; i < max_len; i++)
		{
			if (p.coefficients.length == max_len)
			{
				final_c[i] = p.coefficients[i];
			}
			else
			{
				final_c[i] = this.coefficients[i];
			}
		}
		*/
		int this_deg = Arrays.stream(exponents).max().getAsInt();
		int p_deg = Arrays.stream(p.exponents).max().getAsInt();
		int max_len = Math.max(this_deg, p_deg) + 1;
		double[] res_coeff = new double[max_len];
		for (int i = 0; i < exponents.length; i++)		//[1, 8, 7, 2], [1, 4, 3, 6, 8, 10]
		{
			res_coeff[exponents[i]] += coefficients[i];
			int corresponding_power_id = -1;
			for (int j = 0; j < p.exponents.length; j++)
			{
				if (p.exponents[j] == exponents[i])
				{
					corresponding_power_id = j; 
				}
			}
			if (corresponding_power_id != -1)
			{
				res_coeff[exponents[i]] += p.coefficients[corresponding_power_id];
			}
		}
		for (int j = 0; j < p.exponents.length; j++)
		{
			int corresponding_power_id = -1;
			for (int i = 0; i < exponents.length; i++)
			{
				if (p.exponents[j] == exponents[i])
				{
					corresponding_power_id = i;
				}
			}
			if (corresponding_power_id == -1)
			{
				res_coeff[p.exponents[j]] += p.coefficients[j];
			}
		}
		int final_len = 0;
		for (int i = 0; i < res_coeff.length; i++)
		{
			if (res_coeff[i] != 0)
			{
				final_len += 1;
			}
		}
		double[] final_coeff = new double[final_len];
		int[] final_exp = new int[final_len];
		int id = 0;
		for (int i = 0; i < max_len; i++)
		{
			if (res_coeff[i] != 0)
			{
				final_coeff[id] = res_coeff[i];
				final_exp[id] = i;
				id++;
			}
		}
		return new Polynomial(final_coeff, final_exp);
		
	}
	
	public double evaluate(double x)
	{
		double res = 0.0;
		for (int i = 0; i < coefficients.length; i++)
		{
			res += (coefficients[i] * Math.pow(x, exponents[i]));
		}
		return res;
	}
	
	public boolean hasRoot(double x)
	{
		double res = evaluate(x);
		return res == 0;
	}
	
	public Polynomial multiply(Polynomial p)
	{
		int this_deg = Arrays.stream(this.exponents).max().getAsInt();
		int p_deg = Arrays.stream(p.exponents).max().getAsInt();
		int max_len = this_deg + p_deg + 1;
		double[] res_coeff = new double[max_len];			// Does contains zeroes
		for (int i = 0; i < p.exponents.length; i++)
		{
			for (int j = 0; j < this.exponents.length; j++)
			{
				res_coeff[p.exponents[i] + this.exponents[j]] += (p.coefficients[i] * this.coefficients[j]);
			}
		}
		int final_len = 0;
		for (int i = 0; i < max_len; i++)
		{
			if (res_coeff[i] != 0)
			{
				final_len += 1;
			}
		}
		double[] final_coeff = new double[final_len];
		int[] final_exp = new int[final_len];
		int id = 0;
		for (int i = 0; i < max_len; i++)
		{
			if (res_coeff[i] != 0)
			{
				final_coeff[id] = res_coeff[i];
				final_exp[id] = i;
				id++;
			}
		}
		return new Polynomial(final_coeff, final_exp);
		
	}
}

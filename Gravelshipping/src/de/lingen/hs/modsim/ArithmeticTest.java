package de.lingen.hs.modsim;

import java.math.BigDecimal;

public class ArithmeticTest
{

	public static void main(String[] args)
	{
		/* int a = 1 / 0;
		System.out.println(a); 		ArithmeticException */ 
		
		double b = 1.0 / 0;
		System.out.println(b);
		
		System.out.println(Integer.MAX_VALUE);
		int c = 100000 * 100000;
		System.out.println(c);
		
		long d = 100000l * 100000;
		System.out.println(d);
		
		System.out.println(0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1);
		
		BigDecimal bd = new BigDecimal("0.1");
		System.out.println(bd.add(bd).add(bd).add(bd).add(bd).add(bd).add(bd).add(bd).add(bd).add(bd));
	}
}

package de.lingen.hs.modsim;

public class Interests
{
	public static void main(String[] args)
	{
		double capital = 1000;
		double interestRate = 1.456;
		int years = 100;
		
		double newCapital = capital * Math.pow(interestRate / 100 + 1, years);
		
		System.out.println( String.format("%.2f€ after %d years with interestrate %.2f%% = %.2f€.", 
				capital, years, interestRate, newCapital) );
	}
}

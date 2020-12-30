package de.lingen.hs.modsim;

public class SomePrintLines
{
	private void showFirstFiveLines()
	{
		System.out.println("Verhaften Sie die üblichen Verdächtigen!");
		System.out.println(true);
		System.out.println(-273);
		System.out.println(1.618);
		System.out.println("Anfang ".concat("Ende"));
	}
	
	
	private void showLastFourLines()
	{
		System.out.println(3 + 7);
		System.out.print("Anfang");
		System.out.print("\tMitte");
		System.out.println("\tEnde");
	}
	
	
	public static void main(String[] args)
	{
		SomePrintLines spl = new SomePrintLines();
		spl.showFirstFiveLines();		
		spl.showLastFourLines();
	}
}

package de.lingen.hs.modsim.des.model_gravelshipping;

import de.lingen.hs.modsim.des.core.Event;
import de.lingen.hs.modsim.des.core.EventQueue;
import de.lingen.hs.modsim.des.core.Simulation;

public class GravelShipping extends Simulation
{
	public static Integer gravelToShip = 50000;
	public static Integer unsuccessfullLoadingSizes = 0;
	public static Integer unsuccessfullLoadings = 0;
	
	public static Integer gravelShipped = 0;
	public static Integer successfullLoadingSizes = 0;
	public static int successfullLoadings = 0;

	private static final Integer GRAVEL_TO_SHIP_INITIALLY = gravelToShip;
	private static final int NUM_TRUCKS = 12;
	private static final int NUM_LOADING_DOCKS = 4;
	private static final int NUM_WEIGHING_STATIONS = 1;
	
	public static void main(String[] args)
	{
		EventQueue eventQueue = EventQueue.getInstance();
		
		for (int i = 0; i < NUM_TRUCKS; i++)
			eventQueue.add(new Event(0l, GravelShippingEvents.Loading, new Truck("T" + i), LoadingDock.class, null));

		for (int i = 0; i < NUM_LOADING_DOCKS; i++)
			new LoadingDock("LD" + i);

		for (int i = 0; i < NUM_WEIGHING_STATIONS; i++)
			new WeighingStation("WS" + i);

		GravelShipping gs = new GravelShipping();
		long timeStep = gs.simulate();
		
		// output some gravel shipping stats
		System.out.println("test1");
		System.out.println("Gravel shipped\t\t\t= " + gravelShipped + "t");
		System.out.println("Mean time per gravel unit\t= " + (double) timeStep / gravelShipped + " minutes");
		
		System.out.println(String.format("Successfull loadings\t\t= %d(%.2f%%), mean size %.2ft", successfullLoadings,
				(double) successfullLoadings / (successfullLoadings + unsuccessfullLoadingSizes) * 100,
				(double) successfullLoadingSizes / successfullLoadings));

		System.out.println(String.format("Unsuccessfull loadings\t\t= %d(%.2f%%), mean size %.2ft", unsuccessfullLoadings,
				(double) unsuccessfullLoadings / (successfullLoadings + unsuccessfullLoadingSizes) * 100,
				(double) unsuccessfullLoadingSizes / unsuccessfullLoadings));
		System.out.println("test");
	}

	@Override
	protected void printEveryStep()
	{
		System.out.println(String.format(" shipped/toShip : %dt(%.2f%%) / %dt", gravelShipped, (double) gravelShipped / GRAVEL_TO_SHIP_INITIALLY * 100, gravelToShip));	
	}

}

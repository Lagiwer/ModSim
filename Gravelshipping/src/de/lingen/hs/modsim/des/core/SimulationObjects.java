package de.lingen.hs.modsim.des.core;

import java.util.ArrayList;

public class SimulationObjects extends ArrayList<SimulationObject>
{
	private static final long serialVersionUID = 1L;

	
	private SimulationObjects()
	{
		// intentionally left blank
	}
	
	
	private static class Inner
	{
		private static SimulationObjects simulationObjects = new SimulationObjects();
	}
	
	
	public static SimulationObjects getInstance()
	{
		return Inner.simulationObjects;
	}
}

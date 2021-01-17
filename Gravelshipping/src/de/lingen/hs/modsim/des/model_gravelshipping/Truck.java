package de.lingen.hs.modsim.des.model_gravelshipping;

import de.lingen.hs.modsim.des.core.SimulationObject;
import de.lingen.hs.modsim.des.core.SimulationObjects;

public class Truck extends SimulationObject
{
	private String name = null;
	private Integer loadedWithTons = null;
	
	
	public Truck(String name)
	{
		super();
		this.name = name;
		SimulationObjects.getInstance().add(this);
	}
	
	public void load(int weight) 
	{
		loadedWithTons = weight;
	}
	
	public void unload()
	{
		loadedWithTons = null;
	}
	
	public Integer getLoad()
	{
		return loadedWithTons;
	}
	
	@Override
	public String toString()
	{
		return name + (loadedWithTons != null ? "(" + loadedWithTons + "t" + ")" : "");
	}


	@Override
	public boolean simulate(long timeStep)
	{
		// intentionally doing nothing
		return false;
	}

}

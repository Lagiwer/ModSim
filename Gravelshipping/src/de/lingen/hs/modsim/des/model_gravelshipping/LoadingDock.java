package de.lingen.hs.modsim.des.model_gravelshipping;

import de.lingen.hs.modsim.des.core.Event;
import de.lingen.hs.modsim.des.core.EventQueue;
import de.lingen.hs.modsim.des.core.Randomizer;
import de.lingen.hs.modsim.des.core.SimulationObject;
import de.lingen.hs.modsim.des.core.SimulationObjects;

public class LoadingDock extends SimulationObject
{
	private String name = null;
	private Truck truckCurrentlyLoaded = null;
	
	private static EventQueue eventQueue = EventQueue.getInstance();
	
	private static Randomizer loadingWeight = null;
	private static Randomizer loadingTime = null;
	private static Randomizer drivingToWeighingStationTime = null;
	
	
	public LoadingDock(String name)
	{
		this.name = name;
		
		loadingWeight = new Randomizer();
		loadingWeight.addProbInt(0.3, 34);
		loadingWeight.addProbInt(0.6, 38);
		loadingWeight.addProbInt(1.0, 41);

		loadingTime = new Randomizer();
		loadingTime.addProbInt(0.3, 60);
		loadingTime.addProbInt(0.8, 120);
		loadingTime.addProbInt(1.0, 180);

		drivingToWeighingStationTime = new Randomizer();
		drivingToWeighingStationTime.addProbInt(0.5, 30);
		drivingToWeighingStationTime.addProbInt(0.78, 45);
		drivingToWeighingStationTime.addProbInt(1.0, 60);

		SimulationObjects.getInstance().add(this);
	}
	
	
	@Override
	public String toString()
	{
		return "Loading Dock: " + name + " Truck: " + (truckCurrentlyLoaded != null ? truckCurrentlyLoaded : "---");
	}
	
	
	@Override
	public boolean simulate(long timeStep)
	{
		if (truckCurrentlyLoaded == null
				&& GravelShipping.gravelToShip > 0)
		{
			Event event = eventQueue.next(timeStep, true, GravelShippingEvents.Loading, this.getClass(), null);
			
			if (event != null
					&& event.getObjectAttached() != null
					&& event.getObjectAttached().getClass() == Truck.class)
			{
				eventQueue.remove(event);
				
				truckCurrentlyLoaded = (Truck) event.getObjectAttached();
				truckCurrentlyLoaded.load(Math.min(loadingWeight.nextInt(), GravelShipping.gravelToShip));
				GravelShipping.gravelToShip -= truckCurrentlyLoaded.getLoad();
				
				eventQueue.add(new Event(timeStep + truckCurrentlyLoaded.addUtilization(loadingTime.nextInt()),
						GravelShippingEvents.LoadingDone, truckCurrentlyLoaded, null, this));
				
				utilStart(timeStep);
				return true;
			}
		} 
		else
		{
			Event event = eventQueue.next(timeStep, true, GravelShippingEvents.LoadingDone, null, this);
			if (event != null
					&& event.getObjectAttached() != null
					&& event.getObjectAttached().getClass() == Truck.class)
			{
				eventQueue.remove(event);
				eventQueue.add(new Event(timeStep + event.getObjectAttached().addUtilization(drivingToWeighingStationTime.nextInt()),
						GravelShippingEvents.Weighing, truckCurrentlyLoaded, WeighingStation.class, null));
				
				truckCurrentlyLoaded = null;
				utilStop(timeStep);
				return true;
			}
		}
		return false;
	}
}

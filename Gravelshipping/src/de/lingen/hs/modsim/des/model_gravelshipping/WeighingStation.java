package de.lingen.hs.modsim.des.model_gravelshipping;

import de.lingen.hs.modsim.des.core.Event;
import de.lingen.hs.modsim.des.core.EventQueue;
import de.lingen.hs.modsim.des.core.Randomizer;
import de.lingen.hs.modsim.des.core.SimulationObject;
import de.lingen.hs.modsim.des.core.SimulationObjects;

public class WeighingStation extends SimulationObject
{
	private static final int TIME_TO_WEIGH_TRUCK = 10;
	private static final int MAXLOAD = 40;
	
	private String name = null;
	private Truck truckCurrentlyWeighted = null;
	
	private static EventQueue eventQueue = EventQueue.getInstance();
	
	private static Randomizer drivingToUnloadingDock = null;
	private static Randomizer drivingToLoadingDock  = null;
	
	
	public WeighingStation(String name)
	{
		this.name = name;
		
		drivingToUnloadingDock = new Randomizer();
		drivingToUnloadingDock.addProbInt(0.5, 120);
		drivingToUnloadingDock.addProbInt(0.8, 150);
		drivingToUnloadingDock.addProbInt(1.0, 180);

		drivingToLoadingDock = new Randomizer();
		drivingToLoadingDock.addProbInt(0.5, 30);
		drivingToLoadingDock.addProbInt(1.0, 45);
		
		SimulationObjects.getInstance().add(this);
	}
	
	
	@Override
	public String toString()
	{
		return "Weighing Station: " + name + " Truck: " + (truckCurrentlyWeighted != null ? truckCurrentlyWeighted : "---");
	}
	
	
	// TODO: change logic 
	@Override
	public boolean simulate(long timeStep)
	{
		// truck to weigh?
		Event event = eventQueue.next(timeStep, true, GravelShippingEvents.Weighing, this.getClass(), null);
		if (truckCurrentlyWeighted == null
				&& event != null
				&& event.getObjectAttached() != null
				&& event.getObjectAttached().getClass() == Truck.class)
		{
			eventQueue.remove(event);
			
			truckCurrentlyWeighted = (Truck) event.getObjectAttached();
			eventQueue.add(new Event(timeStep + truckCurrentlyWeighted.addUtilization(TIME_TO_WEIGH_TRUCK),
					GravelShippingEvents.WeighingDone, truckCurrentlyWeighted, null, this));
			
			utilStart(timeStep);
			return true;
		}
		
		// weighing done ?
		event = eventQueue.next(timeStep, true, GravelShippingEvents.WeighingDone, null, this);
		if (event != null
				&& event.getObjectAttached() != null
				&& event.getObjectAttached().getClass() == Truck.class)
		{
			eventQueue.remove(event);
			final Integer truckToWeighLoad = truckCurrentlyWeighted.getLoad();
			long driveToUnLoadingDock = 0;
			
			// drive back to loading dock! too much weight loaded
			if (truckToWeighLoad != null && truckToWeighLoad > MAXLOAD)
			{
				GravelShipping.gravelToShip += truckToWeighLoad;
				GravelShipping.unsuccessfullLoadingSizes += truckToWeighLoad;
				GravelShipping.unsuccessfullLoadings++;
				driveToUnLoadingDock = truckCurrentlyWeighted.addUtilization(drivingToUnloadingDock.nextInt());
				
			}
			else // unload at unloading dock! successful shipping
			{
				GravelShipping.gravelShipped += truckToWeighLoad;
				GravelShipping.successfullLoadingSizes += truckToWeighLoad;
				GravelShipping.successfullLoadings++;
				driveToUnLoadingDock = truckCurrentlyWeighted.addUtilization(drivingToLoadingDock.nextInt());
				
				
			}
			
			eventQueue.add(new Event(timeStep + driveToUnLoadingDock, GravelShippingEvents.Loading, 
					truckCurrentlyWeighted, LoadingDock.class, null));
			
			truckCurrentlyWeighted.unload();
			truckCurrentlyWeighted = null;
			utilStop(timeStep);
			return true;
		}
		
		return false;
	}
}

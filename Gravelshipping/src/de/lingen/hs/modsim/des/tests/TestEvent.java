package de.lingen.hs.modsim.des.tests;

import de.lingen.hs.modsim.des.core.Event;
import de.lingen.hs.modsim.des.core.EventQueue;
import de.lingen.hs.modsim.des.core.Time;
import de.lingen.hs.modsim.des.model_gravelshipping.GravelShippingEvents;
import de.lingen.hs.modsim.des.model_gravelshipping.Truck;

public class TestEvent
{

	public static void main(String[] args)
	{
		Truck t1 = new Truck("T1");
		Event e = new Event(3000l, GravelShippingEvents.Loading, null, Truck.class, t1);
		System.out.println(e);
		
		EventQueue eq = EventQueue.getInstance();
		eq.add(e);
		eq.add(new Event(200l, GravelShippingEvents.Loading, null, Truck.class, t1));
		
		System.out.println("Current time : " + Time.stepsToString(250l));
		
		System.out.println("event found : " + 
				eq.next(250l, true, null, null, null));
		
		eq.remove(e);
	}

}

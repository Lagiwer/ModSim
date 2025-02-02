package de.lingen.hs.modsim.des.core;

import java.util.ArrayList;
import java.util.Collections;

public class EventQueue extends ArrayList<Event>
{
	private static final long serialVersionUID = 1L;
	
	
	private EventQueue()
	{
		super();
	}
	
	private static class Inner
	{
		private static EventQueue eventqueue = new EventQueue();
	}
	
	public static EventQueue getInstance()
	{
		return Inner.eventqueue;
	}
	
	public boolean add(Event e)
	{
		boolean success = super.add(e);
		System.out.println("- addEvent '" + e + "' " + this);
		return success;
	}
	
	public void remove(Event e)
	{
		super.remove(e);
		System.out.println("- removeEvent '" + e + "' " + this);
	}
	
	public Event next(long timeStep, boolean lookIntoPastEvents, 
			UniqueEventDescription description, 					// possibly null from here - as filter
			Class<? extends SimulationObject> receivingClass, 
			SimulationObject receivingObject)
	{
		// filter events 
		ArrayList<Event> filteredEvents = new ArrayList<Event>(this.size());
		for (Event e : this)
		{
			if ( (!lookIntoPastEvents && timeStep <= e.getTimeStep() || lookIntoPastEvents && timeStep >= e.getTimeStep())
				&& (receivingClass == null || receivingClass == e.getReceivingClass())
				&& (receivingObject == null || receivingObject == e.getReceivingObject()) 
				&& (description == null || description == e.getDescription()) )
			{
				filteredEvents.add(e);
			}
		}
		
		Collections.sort(filteredEvents);
		
		if (filteredEvents.size() > 0)
			return filteredEvents.get(0);
		
		return null;
	}
}

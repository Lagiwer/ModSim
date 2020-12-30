package de.lingen.hs.modsim.des.core;

public class Event implements Comparable<Event>
{
	private Long timeStep = null;
	private SimulationObject objectAttached = null;

	private SimulationObject receivingObject = null;
	private Class<? extends SimulationObject> receivingClass = null;

	private UniqueEventDescription description;
	
	public Event(Long timeStep, UniqueEventDescription description, SimulationObject objectAttached,
			 Class<? extends SimulationObject> receivingClass, SimulationObject receivingObject)
	{
		super();
		this.timeStep = timeStep;
		this.objectAttached = objectAttached;
		this.receivingObject = receivingObject;
		this.receivingClass = receivingClass;
		this.description = description;
	}

	public Long getTimeStep()
	{
		return timeStep;
	}
	
	public SimulationObject getObjectAttached()
	{
		return objectAttached;
	}

	public SimulationObject getReceivingObject()
	{
		return receivingObject;
	}

	public Class<? extends SimulationObject> getReceivingClass()
	{
		return receivingClass;
	}

	public UniqueEventDescription getDescription()
	{
		return description;
	}

	@Override
	public int compareTo(Event event)
	{
		return timeStep.compareTo(event.getTimeStep());
	}
	
	@Override
	public String toString()
	{
		return Time.stepsToString(timeStep) + " " + description.get();
	}
}

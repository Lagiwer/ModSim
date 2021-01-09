package de.lingen.hs.modsim.des.core;


import javafx.scene.chart.XYChart;

public abstract class Simulation
{
	protected abstract void printEveryStep();
	
	
	public long simulate()
	{
		EventQueue eventqueue = EventQueue.getInstance();
		SimulationObjects simulationObjects = SimulationObjects.getInstance();
		
		long numberOfSteps = 1;
		long timeStep = 0;
		
		Event e = null;
		
		
		// as long as events are in queue
		do
		{
			System.out.print(numberOfSteps++ + ". " + Time.stepsToString(timeStep) + " " + eventqueue);
			printEveryStep();
			
			// at least one simulationobject did something = consumes events, creates events
			boolean oneSimulationObjectDidSomething;
			do {
				oneSimulationObjectDidSomething = false;
				
				// each simulation object is asked to simulate itself
				for (SimulationObject so : simulationObjects)
				{
					if (so.simulate(timeStep))
					{
						oneSimulationObjectDidSomething = true;
						System.out.println("= " + so);
					}
				}
				
			} while (oneSimulationObjectDidSomething);
			
			System.out.println();
			
			// progress time a little
			timeStep++;
			// switch time to next event
			e = eventqueue.next(timeStep, false, null, null, null);
			if (e != null)
				timeStep = e.getTimeStep();
			
		} while (e != null);
		
		timeStep--; // correction after last step / no event source
		printPostSimStats(timeStep);
		return timeStep;
		
	}


	private void printPostSimStats(long timeStep)
	{
		System.out.println("------------------------------------");
		double utilSumPerSimClass = 0.0;
		int sumObjectsSimClass = 0;
		Class<? extends SimulationObject> simulationObjectClass = null;
		
		final SimulationObjects simulationObjects = SimulationObjects.getInstance();
		
		for (SimulationObject simulationObject : simulationObjects)
		{
			double utilSimObject = (double) simulationObject.getTimedUtilized() / timeStep * 100;
			
			if (simulationObjectClass == simulationObject.getClass())
			{
				utilSumPerSimClass += utilSimObject;
				sumObjectsSimClass++;
			}
			else // a new simulation objects (class)
			{
				if (simulationObjectClass != null && sumObjectsSimClass > 1)
					System.out.println(String.format("tUtilization Class %s = %.2f %%", simulationObjectClass.getName(), utilSumPerSimClass / sumObjectsSimClass));
				
				simulationObjectClass = simulationObject.getClass();
				utilSumPerSimClass = utilSimObject;
				sumObjectsSimClass = 1;
			}
			
			System.out.println(String.format("fUtilization %s = %.2f %%", simulationObject, utilSimObject +1 ));
			
		}
		
		if (sumObjectsSimClass > 1)
			System.out.println(String.format("Utilization Class %s = %.2f %%", simulationObjectClass.getName(), utilSumPerSimClass / sumObjectsSimClass));
		System.out.println("------------------------------------");
		
		
		
		
		
	}
	
	
	public static XYChart.Series<Number, Number> test123(int xAxis, int yAxis)
	{
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName("Running Times in ms");
		
			
		
		series.getData().add(new XYChart.Data<Number, Number>(15, 25));
		series.getData().add(new XYChart.Data<Number, Number>(25, 30));
		series.getData().add(new XYChart.Data<Number, Number>(27, 35));
		
		
		return series;
	}
}


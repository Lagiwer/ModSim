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
		//Arrays
		// Index = truck#
		
		
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

	static double[] truckLoad = new double[12];
	private void printPostSimStats(long timeStep)
	{
		
		System.out.println("------------------------------------");
		double utilSumPerSimClass = 0.0;
		int sumObjectsSimClass = 0;
		Class<? extends SimulationObject> simulationObjectClass = null;
		//int i = 0;
		final SimulationObjects simulationObjects = SimulationObjects.getInstance();
		for (SimulationObject simulationObject : simulationObjects)
		{
			//i++;
			double utilSimObject = (double) simulationObject.getTimedUtilized() / timeStep * 100;
			
			if (simulationObjectClass == simulationObject.getClass())
			{
				utilSumPerSimClass += utilSimObject;
				sumObjectsSimClass++;
			}
			else // a new simulation objects (class)
			{
				if (simulationObjectClass != null && sumObjectsSimClass > 1)
					System.out.println(String.format("adbUtilization Class %s = %.2f %%", simulationObjectClass.getName(), utilSumPerSimClass / sumObjectsSimClass));
				
				simulationObjectClass = simulationObject.getClass();
				utilSumPerSimClass = utilSimObject;
				sumObjectsSimClass = 1;
			}
			
			//truckLoad[i] += utilSimObject;
			System.out.println(String.format("yeeetUtilization %s = %.2f %%", simulationObject, utilSimObject ));
			
		}
		
		
		if (sumObjectsSimClass > 1)
			System.out.println(String.format("Utilization Class %s = %.2f %%", simulationObjectClass.getName(), utilSumPerSimClass / sumObjectsSimClass));
		System.out.println("------------------------------------");
		
		
		
		
		
	}
	
	
	public static XYChart.Series<Number, Number> test123(int xAxis, int yAxis)
	{
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName("Truck load in percent");
		
		int i = 0;
		for(double x : truckLoad)
		{
			i++;
			series.getData().add(new XYChart.Data<Number,Number>(i, x));
			series.getData().add(new XYChart.Data<Number, Number>(0, 100));
		}
		
		//series.getData().add(new XYChart.Data<Number, Number>("t//est", 30));
		//series.getData().add(new XYChart.Data<Number, Number>(27, 35));
		
		
		return series;
	}
}


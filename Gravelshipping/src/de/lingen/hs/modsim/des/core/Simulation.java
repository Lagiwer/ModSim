package de.lingen.hs.modsim.des.core;


import javafx.scene.chart.XYChart;

import java.io.*;
import java.text.DecimalFormat;

import de.lingen.hs.modsim.des.model_gravelshipping.*;
                                                                                                                       

public abstract class Simulation
{
	protected abstract void printEveryStep();
	static Long[] localtimestamp = new Long[12];
	
		public long simulate()
	{
		EventQueue eventqueue = EventQueue.getInstance();
		SimulationObjects simulationObjects = SimulationObjects.getInstance();
		
		long numberOfSteps = 1;
		long timeStep = 0;
		int i= -1;
		
		Event e = null;
		//Arrays
		// Index = truck#
		
		
		// as long as events are in queue
		
		do
		{	
			System.out.print(numberOfSteps++ + ". " + Time.stepsToString(timeStep) + " " + eventqueue);
			localtimestamp[i+1]=timeStep;
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
	static double ws = 0;
	static double[] truckLoad = new double[12];
	
	static double[] ldLoad = new double[4];
	private void printPostSimStats(long timeStep)
	{
		
		System.out.println("------------------------------------");
		double utilSumPerSimClass = 0.0;
		int sumObjectsSimClass = 0;
		Class<? extends SimulationObject> simulationObjectClass = null;
		int i = 0;
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
					System.out.println(String.format("Utilization Class %s = %.2f %%", simulationObjectClass.getName(), utilSumPerSimClass / sumObjectsSimClass));
				
				simulationObjectClass = simulationObject.getClass();
				utilSumPerSimClass = utilSimObject;
				sumObjectsSimClass = 1;
				
			}
			if(i <= 11) {
				truckLoad[i] += utilSimObject;	
			}
			else if(i>=12 && i <= 15)
			{
				//System.out.println(utilSimObject);
				ldLoad[i - 12] += utilSimObject;
			}
			i++;
			System.out.println(String.format("Utilization %s = %.2f %%", simulationObject, utilSimObject ));
			
		}
		
		if (sumObjectsSimClass > 1)
			System.out.println(String.format("Utilization Class %s = %.2f %%", simulationObjectClass.getName(), utilSumPerSimClass / sumObjectsSimClass));
		System.out.println("------------------------------------");
		ws = (utilSumPerSimClass / sumObjectsSimClass);

		
	}
	
	public static double getWs() {
		return ws;
	}
	
	public static double getTLoad1() {
		return truckLoad[0];
	}
	public static double getTLoad2() {
		return truckLoad[1];
	}
	public static double getTLoad3() {
		return truckLoad[2];
	}
	public static double getTLoad4() {
		return truckLoad[3];
	}
	public static double getTLoad5() {
		return truckLoad[4];
	}
	public static double getTLoad6() {
		return truckLoad[5];
	}
	public static double getTLoad7() {
		return truckLoad[6];
	}
	public static double getTLoad8() {
		return truckLoad[7];
	}
	public static double getTLoad9() {
		return truckLoad[8];
	}
	public static double getTLoad10() {
		return truckLoad[9];
	}
	public static double getTLoad11() {
		return truckLoad[10];
	}
	public static double getTLoad12() {
		return truckLoad[11];
	}
	
	public static double getldLoad1() {
		return ldLoad[0];
	}
	public static double getldLoad2() {
		return ldLoad[1];
	}
	public static double getldLoad3() {
		return ldLoad[2];
	}
	public static double getldLoad4() {
		return ldLoad[3];
	}
	
	public static XYChart.Series<Number, Number> TruckLoadGraph() 

	
	
	{
		GravelShipping.main(null);
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName("Dock load in percent");
		int i = 0;
		for(double x : truckLoad)
		{
			series.getData().add(new XYChart.Data<Number,Number>(i + 1, x));
			i++;

			
		}
		return series;
	}

	
	public static XYChart.Series<Number, Number> DockLoadGraph()
	{
		
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName("Dock load in percent");
		int i = 0;
		for(double x : ldLoad)
		{
			series.getData().add(new XYChart.Data<Number,Number>(i +1 , x));
			i++;
		}
		return series;
	}
	

	

	
	public static XYChart.Series<Number, Number> SimulationWriter() throws IOException 
	{
	    Writer writer = null;
	    DecimalFormat df = new DecimalFormat("#.##");
	    
        try {
        File file = new File("C:\\Users\\noetz\\Desktop\\Daten.csv");
        writer = new BufferedWriter(new FileWriter(file));
        
        XYChart.Series<Number, Number> series5 = new XYChart.Series<Number, Number>();
		int i = 0;
		int j = -1;
		for(double x : truckLoad)
		{
			series5.getData().add(new XYChart.Data<Number,Number>(i + 1, x));
						i++;
			String z = df.format(x);
			
			String ausgabe = "Truck Nr.: " + Double.toString(i) + "\t\t Auslastung: " + z + "% " +" " + "Zeit: " + Time.stepsToString(localtimestamp[j+1]) + " \r" ;
			writer.write(ausgabe);
		}
		
		
		
		
		XYChart.Series<Number, Number> series6 = new XYChart.Series<Number, Number>();
		String space ="\n\n";
		writer.write(space);
            for(double y : ldLoad)
    		{
    			series6.getData().add(new XYChart.Data<Number,Number>(i +1 , y));
    			String u = df.format(y);
    			
    			String ausgabe = "Dock Nr.: " + Double.toString(i-11) + "\t\t Auslastung: " + u + "%" + "\r" ;
                writer.write(ausgabe);
    			i++;
    		}
        writer.write(space);
        
        
		}catch (Exception ex) {
	        ex.printStackTrace();
        }finally {

            writer.flush();
             writer.close();
		
        }
        
   
        
		return null;
		
	}
	
	
	
}

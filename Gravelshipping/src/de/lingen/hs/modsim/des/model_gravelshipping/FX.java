package de.lingen.hs.modsim.des.model_gravelshipping;

import de.lingen.hs.modsim.des.core.Simulation;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.application.Application;


import java.text.DecimalFormat;


public class FX extends Application {
	

	
	private static final int PIXEL_X = 1100;
	private static final int PIXEL_Y = 920;

	public static void main(String[] args) {
		
		launch(args);
		
	}

	

	
	public void start(Stage stage) throws Exception
	{
		
		
		Series<Number, Number> DatenErsterGraph = Simulation.TruckLoadGraph();

		
		
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		
		ScatterChart<Number, Number> sc = new ScatterChart<Number, Number>(xAxis, yAxis);
		xAxis.setLabel("# Trucks");
		yAxis.setLabel("%");
		sc.getData().add(DatenErsterGraph);
		sc.setPrefSize(500, 400);
		sc.setTitle("Truck load in percent");
		
		
		Series<Number, Number> DatenZweiterGraph = Simulation.DockLoadGraph();

		
		
		NumberAxis xAxis1 = new NumberAxis();
		NumberAxis yAxis1 = new NumberAxis();
		
		ScatterChart<Number, Number> sc2 = new ScatterChart<Number, Number>(xAxis1, yAxis1);
		xAxis1.setLabel("# Docks");
		yAxis1.setLabel("%");
		sc2.getData().add(DatenZweiterGraph);
		sc2.setPrefSize(500, 400);
		sc2.setTitle("Dock load in percent");

		
		stage.setTitle("Gravelshipping");
		VBox vb = new VBox();
		GridPane zusatzDaten = new GridPane();
		
		Label shipped = new Label("\tGravel shipped: "+ GravelShipping.gravelShipped);
		shipped.setFont(Font.font("Arial", FontWeight.BOLD,12));
		
		Label mtpgu = new Label("\tMean time per gravel unit: " + (double)GravelShipping.timeStep / GravelShipping.gravelShipped + " minutes");
		mtpgu.setFont(Font.font("Arial", FontWeight.BOLD,12));
		
		Label sulo = new Label(String.format("\tSuccessfull loadings= %d(%.2f%%), mean size %.2ft", GravelShipping.successfullLoadings,
				(double) GravelShipping.successfullLoadings / (GravelShipping.successfullLoadings + GravelShipping.unsuccessfullLoadingSizes) * 100,
				(double) GravelShipping.successfullLoadingSizes / GravelShipping.successfullLoadings));
		sulo.setFont(Font.font("Arial", FontWeight.BOLD,12));
		
		Label usulo = new Label(String.format("\tUnsuccessfull loadings= %d(%.2f%%), mean size %.2ft", GravelShipping.unsuccessfullLoadings,
				(double) GravelShipping.unsuccessfullLoadings / (GravelShipping.successfullLoadings + GravelShipping.unsuccessfullLoadingSizes) * 100,
				(double) GravelShipping.unsuccessfullLoadingSizes / GravelShipping.unsuccessfullLoadings));
		usulo.setFont(Font.font("Arial", FontWeight.BOLD,12));
		zusatzDaten.add(shipped, 0, 1);
		zusatzDaten.add(mtpgu, 1, 1);
		zusatzDaten.add(sulo, 2, 1);
		zusatzDaten.add(usulo, 3, 1);
		
		
		
					
		
		DecimalFormat df = new DecimalFormat("#.##");
				
		GridPane TLDaten = new GridPane();
		Label t1 = new Label("Truck 1: " + df.format(Simulation.getTLoad1())+"%");
		Label t2 = new Label("Truck 2: " + df.format(Simulation.getTLoad2())+"%");
		Label t3 = new Label("Truck 3: " + df.format(Simulation.getTLoad3())+"%");
		Label t4 = new Label("Truck 4: " + df.format(Simulation.getTLoad4())+"%");
		Label t5 = new Label("Truck 5: " + df.format(Simulation.getTLoad5())+"%");
		Label t6 = new Label("Truck 6: " + df.format(Simulation.getTLoad6())+"%");
		Label t7 = new Label("Truck 7: " + df.format(Simulation.getTLoad7())+"%");		
		Label t8 = new Label("Truck 8: " + df.format(Simulation.getTLoad8())+"%");
		Label t9 = new Label("Truck 9: " + df.format(Simulation.getTLoad9())+"%");
		Label t10 = new Label("Truck 10: " + df.format(Simulation.getTLoad10())+"%");
		Label t11 = new Label("Truck 11: " + df.format(Simulation.getTLoad11())+"%");
		Label t12 = new Label("Truck 12: " + df.format(Simulation.getTLoad12())+"%");
		Label spacingVorne = new Label("\t\t");
		Label spacing = new Label("\t\t\t\t\t\t");
		Label spacingVorne1 = new Label("\t\t\t\t\t\t\t\t\t\t\t");
		Label spacing1 = new Label("\t\t\t\t\t\t\t");
		
		
		t1.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		t2.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		t3.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		t4.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		t5.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		t6.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		t7.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		t8.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		t9.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		t10.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		t11.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		t12.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		
		Label ld1 = new Label("Dock 1: " + df.format(Simulation.getldLoad1())+"%");
		Label ld2 = new Label("Dock 2: " + df.format(Simulation.getldLoad2())+"%");
		Label ld3 = new Label("Dock 3: " + df.format(Simulation.getldLoad3())+"%");
		Label ld4 = new Label("Dock 4: " + df.format(Simulation.getldLoad4())+"%");
		
		ld1.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		ld2.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		ld3.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		ld4.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		
		TLDaten.add(spacingVorne, 0, 1);
		TLDaten.add(t1, 1, 1);
		TLDaten.add(t2, 1, 2);
		TLDaten.add(t3, 1, 3);
		TLDaten.add(t4, 1, 4);
		TLDaten.add(t5, 1, 5);
		TLDaten.add(t6, 1, 6);
		TLDaten.add(spacing, 2, 1);
		TLDaten.add(t7, 3, 1);
		TLDaten.add(t8, 3, 2);
		TLDaten.add(t9, 3, 3);
		TLDaten.add(t10, 3, 4);
		TLDaten.add(t11, 3, 5);
		TLDaten.add(t12, 3, 6);
		TLDaten.add(spacingVorne1, 4 , 1);
		TLDaten.add(ld1, 5 , 1);
		TLDaten.add(ld2, 5 , 2);
		TLDaten.add(spacing1, 6, 1);
		TLDaten.add(ld3, 7 , 1);
		TLDaten.add(ld4, 7 , 2);
		
		
		
		
		
		
		vb.getChildren().addAll(sc,TLDaten, sc2, zusatzDaten );
		stage.setScene(new Scene(vb, PIXEL_X, PIXEL_Y));
		stage.show();
	}
	
		
	}



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


public class FX extends Application {
	

	
	private static final int PIXEL_X = 1100;
	private static final int PIXEL_Y = 850;

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
		GridPane test = new GridPane();
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
		test.add(shipped, 0, 1);
		test.add(mtpgu, 1, 1);
		test.add(sulo, 2, 1);
		test.add(usulo, 3, 1);
		vb.getChildren().addAll(sc, sc2, test);
		stage.setScene(new Scene(vb, PIXEL_X, PIXEL_Y));
		stage.show();
	}
	
		
	}



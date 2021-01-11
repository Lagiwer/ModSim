package de.lingen.hs.modsim.des.model_gravelshipping;

import de.lingen.hs.modsim.des.core.Simulation;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
/*import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;*/

public class FX extends Application {
	
	private static final int ITEMS = 20;
	private static final int ITERATIONS = 5;
	
	private static final int PIXEL_X = 1000;
	private static final int PIXEL_Y = 900;

	public static void main(String[] args) {
		
		launch(args);
		
	}

	

	
	public void start(Stage stage) throws Exception
	{
		
		
		Series<Number, Number> DatenErsterGraph = Simulation.test123();

		
		
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		
		ScatterChart<Number, Number> sc = new ScatterChart<Number, Number>(xAxis, yAxis);
		xAxis.setLabel("# Trucks");
		yAxis.setLabel("%");
		sc.getData().add(DatenErsterGraph);
		sc.setPrefSize(500, 400);
		sc.setTitle("Truck load in percent");
		
		
		Series<Number, Number> DatenZweiterGraph = Simulation.test1234();

		stage.setTitle("test");
		
		NumberAxis xAxis1 = new NumberAxis();
		NumberAxis yAxis1 = new NumberAxis();
		
		ScatterChart<Number, Number> sc2 = new ScatterChart<Number, Number>(xAxis1, yAxis1);
		xAxis1.setLabel("# Docks");
		yAxis1.setLabel("%");
		sc2.getData().add(DatenZweiterGraph);
		sc2.setPrefSize(500, 400);
		sc2.setTitle("Dock load in percent");

		
		VBox vb = new VBox();
		vb.getChildren().addAll(sc, sc2);
		stage.setScene(new Scene(vb, PIXEL_X, PIXEL_Y));
		stage.show();
	}
	
		
	}



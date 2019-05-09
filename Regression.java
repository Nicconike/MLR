import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Regression extends Application {

	public AnchorPane addAnchorPane() {

		AnchorPane anchorpane = new AnchorPane();
		Text text = new Text("Multiple Linear Regression");

		text.setStyle("-fx-font: 30 Arial;");

		try {
			Image image = new Image(
					new FileInputStream("C:\\Users\\Nikhil\\Downloads\\Documents\\Datasets\\JavaFX_Logo.png"));

			ImageView imageView = new ImageView(image);
			imageView.setX(400);
			imageView.setY(15);
			imageView.setFitHeight(250);
			imageView.setFitWidth(250);
			imageView.setPreserveRatio(true);

			anchorpane.getChildren().addAll(imageView, text);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		AnchorPane.setBottomAnchor(text, 8.0);
		AnchorPane.setTopAnchor(text, 10.0);
		AnchorPane.setLeftAnchor(text, 330.0);
		anchorpane.autosize();

		return anchorpane;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static GridPane addGridPane() {
		GridPane grid = new GridPane();
		Text text = new Text("Number of Independent Variables");
		Text t = new Text();
		Label label = new Label("Read File Data");
		Button read = new Button("Read");
		Button submit = new Button("Submit");
		TextField tf = new TextField();
		Text t1 = new Text();
		Alert alert = new Alert(AlertType.INFORMATION);
		Alert alert1 = new Alert(AlertType.ERROR);

		tf.setPromptText("Input no. of independent variables");
		text.setFont(Font.font("HANGING_BASELINE", FontWeight.BOLD, 16));
		t1.setFont(Font.font("HANGING_BASELINE", FontWeight.BOLD, 16));
		label.setFont(Font.font("TrueType", FontWeight.BOLD, 16));

		double[] x1 = new double[50];
		double[] x2 = new double[50];
		double[] x3 = new double[50];
		double[] x4 = new double[50];
		// double[] x5 = new double[50];
		double[] y = new double[50];
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				String[] array = null;
				String data = new String();
				String row = new String();
				int count = 0;

				try {
					BufferedReader br = new BufferedReader(
							new FileReader("C:\\Users\\Nikhil\\eclipse-workspace\\Startups.csv"));
					row = br.readLine();
					data = br.readLine();
					System.out.println(row);
					System.out.println();

					while (data != null) {

						array = data.split(",");
						x1[count] = Double.parseDouble(array[0]);
						x2[count] = Double.parseDouble(array[1]);
						x3[count] = Double.parseDouble(array[2]);
						y[count] = Double.parseDouble(array[3]);
						/*
						 * x4[count] = Double.parseDouble(array[4]); x5[count] =
						 * Double.parseDouble(array[5]);
						 */

						System.out.println(x1[count] + " " + x2[count] + " " + x3[count] + " " + y[count]);
						count++;
						data = br.readLine();
					}
					br.close();

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					System.out.println("FileNotFoundException Caught");
				} catch (IOException e1) {
					e1.printStackTrace();
					System.out.println("IOException Caught");
				} catch (RuntimeException e1) {
					e1.printStackTrace(System.out);
					System.out.println("NullPointerException Caught");
				}

				alert.setContentText("File Read!");
				alert.show();
			}
		};
		read.setOnAction(event);

		EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				tf.textProperty().addListener((arg0, oldValue, newValue) -> {
					if (newValue != null) {
						if ((tf.getText().matches("[1-3]") || tf.getText().isEmpty())) {
							t.setText(tf.getText() + " taken as Input");
							alert.setContentText("Input taken");
						} else {
							alert1.setContentText(tf.getText() + " cannot be the input");
							alert1.show();
						}
					}
				});
			}
		};
		submit.setOnAction(event1);

		Button plot = new Button("Plot Graph");
		Button clear = new Button("Clear & Reset");
		Text t2 = new Text();

		NumberAxis xaxis = new NumberAxis();
		xaxis.setLabel("Money Spent(₹) -->");
		NumberAxis yaxis = new NumberAxis();
		yaxis.setLabel("Profit(₹) -->");

		LineChart linechart = new LineChart(xaxis, yaxis);
		linechart.setTitle("Startups Profit Analysis");
		linechart.autosize();
		EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				tf.clear();
				t.setText(null);
				t1.setText(null);
				t2.setText(null);
				linechart.getData().clear();
				alert.setContentText("Plotted Data Cleared!");
				alert.show();
				DropShadow dropShadow = new DropShadow();
				dropShadow.setOffsetX(10.0);
				dropShadow.setOffsetY(5.0);
				dropShadow.setRadius(10);
				dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
				clear.setEffect(dropShadow);
			};
		};
		clear.setOnAction(event3);

		EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				double[] yhat = new double[50];
				double[] yhat1 = new double[50];
				double[] yhat2 = new double[50];
				double[] yhat3 = new double[50];
				double[] residual = new double[50];
				double[] residual1 = new double[50];
				double[] residual2 = new double[50];
				double[] residual3 = new double[50];
				double[] final_y = new double[50];
				double[] final_y1 = new double[50];
				double[] final_y2 = new double[50];
				double[] final_y3 = new double[50];
				double RMSE = 0, RMSE1 = 0, RMSE2 = 0, RMSE3 = 0, Final_RMSE = 0;
				double α = 0, β = 0, β1 = 0, β2 = 0, β3 = 0;

				XYChart.Series series1 = new XYChart.Series();
				XYChart.Series series2 = new XYChart.Series();
				XYChart.Series series3 = new XYChart.Series();
				XYChart.Series series4 = new XYChart.Series();
				XYChart.Series series5 = new XYChart.Series();
				XYChart.Series series6 = new XYChart.Series();
				XYChart.Series series7 = new XYChart.Series();
				XYChart.Series series8 = new XYChart.Series();

				α = Math.abs(Line_Plot.intercept(x1, y));
				int i;
				int value = Integer.parseInt(tf.getText());

				if (read.onMouseClickedProperty() == null) {
					alert1.setContentText("Cannot Plot the data without reading!");
					alert1.show();

				} else if (submit.onMouseClickedProperty() == null) {
					alert1.setContentText("Cannot Plot the data without submitting the input!");
					alert1.show();

				} else if (tf.getText().isEmpty()) {
					alert1.setContentText("Cannot Plot the data without any input!");
					alert1.show();

				} else if (value == 1) {
					β = Math.abs(Line_Plot.coefficient(x1, y));

					series1.setName("Original Data");
					series2.setName("Data's Best Fit");

					System.out.println("x1 \t ŷ \t final ŷ");
					
					for (i = 0; i < x1.length; i++) {
						yhat[i] = (x1[i] * β) + α;
						residual[i] = Math.pow(y[i] - yhat[i], 2);
						RMSE = Math.sqrt(Line_Plot.mean(residual));
						final_y[i] += (x1[i] * β) + RMSE;

						series1.getData().add(new XYChart.Data(x1[i], y[i]));
						series2.getData().add(new XYChart.Data(x1[i], final_y[i]));
						
						System.out.println(x1[i] + " " + yhat[i] + " " + final_y[i]);
					}
					linechart.getData().addAll(series1, series2);

					t2.setText("y=" + α + "+" + β + "x" + "+" + RMSE);
					System.out.println();
					System.out.println("Equation of line generated from dataset");
					System.out.println("y=" + α + "+" + β + "x" + "+" + RMSE);

					alert.setContentText("Graph Plotted!");
					alert.show();

				} else if (value == 2) {
					β = Math.abs(Line_Plot.coefficient(x1, y));
					β1 = Math.abs(Line_Plot.coefficient(x2, y));
					series1.setName("R&D Spent");
					series2.setName("R&D Best Fit");
					series3.setName("Administration Spent");
					series4.setName("Administration Best Fit");

					for (i = 0; i < x1.length; i++) {
						yhat[i] = (x1[i] * β) + α;
						yhat1[i] = (x2[i] * β1) + α;
						residual[i] = Math.pow(y[i] - yhat[i], 2);
						residual1[i] = Math.pow(y[i] - yhat1[i], 2);
						RMSE = Math.sqrt(Line_Plot.mean(residual));
						RMSE1 = Math.sqrt(Line_Plot.mean(residual1));
						final_y[i] += (x1[i] * β) + RMSE;
						final_y1[i] += (x2[i] * β1) + RMSE1;
						Final_RMSE = RMSE + RMSE1;

						series1.getData().add(new XYChart.Data(x1[i], y[i]));
						series2.getData().add(new XYChart.Data(x1[i], final_y[i]));
						series3.getData().add(new XYChart.Data(x2[i], y[i]));
						series4.getData().add(new XYChart.Data(x2[i], final_y1[i]));
						System.out.println(x1[i] + " " + x2[i] + " " + yhat[i] + " " + final_y[i] + " " + yhat1[i] + " "
								+ final_y1[i]);
					}
					linechart.getData().addAll(series1, series2, series3, series4);
					t2.setText("y=" + α + "+" + β + "x1" + "+" + β1 + "x2" + "+" + Final_RMSE);
					System.out.println();
					System.out.println("Equation of line generated from dataset");
					System.out.println("y=" + α + "+" + β + "x1" + "+" + β1 + "x2" + "+" + Final_RMSE);

					alert.setContentText("Graph Plotted!");
					alert.show();

				} else if (value == 3) {
					β = Math.abs(Line_Plot.coefficient(x1, y));
					β1 = Math.abs(Line_Plot.coefficient(x2, y));
					β2 = Math.abs(Line_Plot.coefficient(x3, y));
					series1.setName("Original Data");
					series2.setName("Best Fit");
					series3.setName("Original Data");
					series4.setName("Best Fit");
					series5.setName("Original Data");
					series6.setName("Best Fit");

					for (i = 0; i < x1.length; i++) {
						yhat[i] = (x1[i] * β) + α;
						yhat1[i] = (x2[i] * β1) + α;
						yhat2[i] = (x3[i] * β2) + α;
						residual[i] = Math.pow(y[i] - yhat[i], 2);
						residual1[i] = Math.pow(y[i] - yhat1[i], 2);
						residual2[i] = Math.pow(y[i] - yhat2[i], 2);
						RMSE = Math.sqrt(Line_Plot.mean(residual));
						RMSE1 = Math.sqrt(Line_Plot.mean(residual1));
						RMSE2 = Math.sqrt(Line_Plot.mean(residual2));
						final_y[i] += (x1[i] * β) + RMSE;
						final_y1[i] += (x2[i] * β1) + RMSE1;
						final_y2[i] += (x2[i] * β2) + RMSE2;
						Final_RMSE = RMSE + RMSE1 + RMSE2;

						series1.getData().add(new XYChart.Data(x1[i], y[i]));
						series2.getData().add(new XYChart.Data(x1[i], final_y[i]));
						series3.getData().add(new XYChart.Data(x2[i], y[i]));
						series4.getData().add(new XYChart.Data(x2[i], final_y1[i]));
						series5.getData().add(new XYChart.Data(x3[i], y[i]));
						series6.getData().add(new XYChart.Data(x3[i], final_y2[i]));
						System.out.println(x1[i] + " " + x2[i] + " " + yhat[i] + " " + final_y[i] + " " + yhat1[i] + " "
								+ final_y1[i] + " " + yhat2[i] + " " + final_y2[i]);
					}

					linechart.getData().addAll(series1, series2, series3, series4, series5, series6);
					t2.setText("y=" + α + "+" + β + "x1" + "+" + β1 + "x2" + "+" + β2 + "x3" + "+" + Final_RMSE);
					System.out.println();
					System.out.println("Equation of line generated from dataset");
					System.out
							.println("y=" + α + "+" + β + "x1" + "+" + β1 + "x2" + "+" + β2 + "x3" + "+" + Final_RMSE);

					alert.setContentText("Graph Plotted!");
					alert.show();

				} else if (value == 4) {
					β = Math.abs(Line_Plot.coefficient(x1, y));
					β1 = Math.abs(Line_Plot.coefficient(x2, y));
					β2 = Math.abs(Line_Plot.coefficient(x3, y));
					β3 = Math.abs(Line_Plot.coefficient(x4, y));
					series1.setName("Open");
					series2.setName("Open Best Fit");
					series3.setName("Low");
					series4.setName("Low Best Fit");
					series5.setName("High");
					series6.setName("High Best Fit");
					series7.setName(" Spent");
					series8.setName(" Best Fit");

					for (i = 0; i < x1.length; i++) {
						yhat[i] = (x1[i] * β) + α;
						yhat1[i] = (x2[i] * β1) + α;
						yhat2[i] = (x3[i] * β2) + α;
						yhat3[i] = (x4[i] * β3) + α;
						residual[i] = Math.pow(y[i] - yhat[i], 2);
						residual1[i] = Math.pow(y[i] - yhat1[i], 2);
						residual2[i] = Math.pow(y[i] - yhat2[i], 2);
						residual3[i] = Math.pow(y[i] - yhat3[i], 2);
						RMSE = Math.sqrt(Line_Plot.mean(residual));
						RMSE1 = Math.sqrt(Line_Plot.mean(residual1));
						RMSE2 = Math.sqrt(Line_Plot.mean(residual2));
						RMSE3 = Math.sqrt(Line_Plot.mean(residual3));
						final_y[i] += (x1[i] * β) + RMSE;
						final_y1[i] += (x2[i] * β1) + RMSE1;
						final_y2[i] += (x3[i] * β2) + RMSE2;
						final_y3[i] += (x4[i] * β3) + RMSE3;
						Final_RMSE = RMSE + RMSE1 + RMSE2 + RMSE3;

						series1.getData().add(new XYChart.Data(x1[i], y[i]));
						series2.getData().add(new XYChart.Data(x1[i], final_y[i]));
						series3.getData().add(new XYChart.Data(x2[i], y[i]));
						series4.getData().add(new XYChart.Data(x2[i], final_y1[i]));
						series5.getData().add(new XYChart.Data(x3[i], y[i]));
						series6.getData().add(new XYChart.Data(x3[i], final_y2[i]));
						series5.getData().add(new XYChart.Data(x4[i], y[i]));
						series6.getData().add(new XYChart.Data(x4[i], final_y3[i]));
						System.out.println(x1[i] + " " + x2[i] + " " + yhat[i] + " " + final_y[i] + " " + yhat1[i] + " "
								+ final_y1[i] + " " + yhat2[i] + " " + final_y2[i] + " " + yhat3[i] + " "
								+ final_y3[i]);
					}

					linechart.getData().addAll(series1, series2, series3, series4, series5, series6, series7, series8);
					t2.setText("y=" + α + "+" + β + "x1" + "+" + β1 + "x2" + "+" + β2 + "x3" + "+" + β3 + "x4" + "+"
							+ β3 + "x4" + "+" + Final_RMSE);
					System.out.println();
					System.out.println("Equation of line generated from dataset");
					System.out.println("y=" + α + "+" + β + "x1" + "+" + β1 + "x2" + "+" + β2 + "x3" + "+" + β3 + "x4"
							+ "+" + Final_RMSE);

					alert.setContentText("Graph Plotted!");
					alert.show();
				}

				t1.setText("Equation of line generated from dataset");
				System.out.println();

				DropShadow dropShadow = new DropShadow();
				dropShadow.setRadius(5.0);
				dropShadow.setOffsetX(3.0);
				dropShadow.setOffsetY(3.0);
				dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

				plot.setEffect(dropShadow);
			}
		};
		plot.setOnAction(event2);
		plot.setPrefSize(100, 20);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		grid.add(text, 0, 0);
		grid.add(tf, 0, 1);
		grid.add(submit, 0, 3);
		grid.add(t, 0, 2);
		grid.add(label, 1, 0);
		grid.add(read, 1, 2);
		grid.add(plot, 1, 4);
		grid.add(t2, 1, 8);
		grid.add(t1, 1, 7);
		grid.add(clear, 0, 6);
		grid.add(linechart, 1, 6);
		grid.autosize();

		return grid;
	}

	@Override
	public void start(Stage stage) {
		BorderPane border = new BorderPane();
		border.setTop(addAnchorPane());
		border.setCenter(addGridPane());
		border.autosize();

		Scene scene = new Scene(border, 1180, 800);

		stage.sizeToScene();
		stage.setTitle("Regression");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
}
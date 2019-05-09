import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Line_Plot extends Application {

	public static double mean(double[] array) {
		double sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		return sum / array.length;
	}

	public static double covariance(double[] array1, double[] array2) {
		double xmean = mean(array1);
		double ymean = mean(array2);
		double result = 0;

		for (int i = 0; i < array1.length; i++) {
			result += (array1[i] - xmean) * (array2[i] - ymean);
		}

		result /= array1.length - 1;
		return result;
	}

	public static double variance(double[] array) {
		double mean = mean(array);
		double var = 0;

		for (int i = 0; i < array.length; i++) {
			var += Math.pow(array[i] - mean, 2);
		}
		return var / (array.length - 1);
	}

	public static double coefficient(double[] array1, double[] array2) {

		return (covariance(array1, array2)) / variance(array1);
	}

	public static double intercept(double[] array1, double[] array2) {

		return (mean(array2)) - (coefficient(array1, array2) * mean(array1));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(Stage stage) {
		double[] array1 = new double[30];
		double[] array2 = new double[30];
		double[] array3 = new double[30];
		double α = 0, β = 0;
		String[] array;
		NumberAxis x = new NumberAxis();
		x.setLabel("Experience(Years) -->");
		NumberAxis y = new NumberAxis();
		y.setLabel("Salary(₹) -->");

		stage.setTitle("Line Plot");

		ScatterChart scatterchart = new ScatterChart(x, y);
		scatterchart.setTitle("Years of Experience vs Salary");

		XYChart.Series series1 = new XYChart.Series();
		XYChart.Series series2 = new XYChart.Series();

		series1.setName("Data");
		series2.setName("Fitted Data");
		String row;
		int counter = 0;
		float sum[] = new float[30];
		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\Users\\Nikhil\\eclipse-workspace\\Salary_Data.csv"))) {
			row = br.readLine();
			System.out.println(row+",Predicted Salary");
			while (row != null) {
				
				row = br.readLine();
				array = row.split(",");
				array1[counter] = Float.parseFloat(array[0]);
				array2[counter] = Float.parseFloat(array[1]);
				α = intercept(array1, array2);
				β = coefficient(array1, array2);
				array3[counter]=(array1[counter] * β) + α;
				sum[counter] += array1[counter] * array2[counter];
				
				series1.getData().add(new XYChart.Data(array1[counter],array3[counter]));
				
				series2.getData().add(new XYChart.Data(array1[counter],sum[counter]));
				
				System.out.println(array1[counter]+" "+array2[counter]+ " "+ array3[counter]);
				counter++;
			    
			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("FileNotFoundException Caught");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException Caught");
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println("RuntimeException Caught");
			System.out.println("It's a NullPointerException");
		}
		System.out.println();
		System.out.println("Equation of line generated from dataset");
		System.out.println("y=" + α +"+"+ β + "x");
		Group root = new Group(scatterchart);
		
		scatterchart.getData().add(series1);
		scatterchart.getData().add(series2);
		Scene scene = new Scene(root, 600, 500);
		
		
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
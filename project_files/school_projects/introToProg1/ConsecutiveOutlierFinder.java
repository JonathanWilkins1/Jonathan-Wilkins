import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ConsecutiveOutlierFinder {

	public static void main(String[] args) {
		double[] dataArray = {10.0, 11.0, 9.0, 100.0, 105.0, 10.0, 10.0, 10.0, 10.0, 10.0, 9.9, 10.1};
		String[] timeArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
		double dataMean = mean(dataArray);
		double dataDeviation = stdDev(dataArray, dataMean);
		System.out.println(dataMean);
		System.out.println(dataDeviation);
		printConsecutiveOutliers(timeArray, dataArray);
	}
	
	public static double mean(double[] dataArray) {
		double total = 0.0;
		for (double element : dataArray) {
			total += element;
		}
		double dataMean = total / dataArray.length;
		return dataMean;
	}
	
	public static double stdDev(double[] dataArray, double mean) {
		double dataDeviation = 0.0;
		for (double element : dataArray) {
			dataDeviation += Math.pow(element - mean, 2);
		}
		dataDeviation = Math.sqrt(dataDeviation/(dataArray.length - 1));
		return dataDeviation;
	}
	
	public static void printConsecutiveOutliers(String[] timeArray, double[] dataArray) {
		boolean isPreviousOutlier = false;
		double dataMean = mean(dataArray);
		for (int index = 0; index < dataArray.length; index ++) {
			if (Math.abs(dataArray[index] - dataMean) > (2 * stdDev(dataArray, dataMean))) {
				if (isPreviousOutlier == true) {
					System.out.println(timeArray[index - 1] + " and " + timeArray[index] + " were both outliers.");
				}
				isPreviousOutlier = true;
			}
		}
	}
	public static void findOutliersInFile(String dataFilename) throws FileNotFoundException {
		System.out.println("Starting to process " + dataFilename);
		File dataFile = new File(dataFilename);
		Scanner dataFileScanner = new Scanner(dataFile);
		int dataEntries = dataFileScanner.nextInt();
		String[] timeArray = new String[dataEntries];
		double[] dataArray = new double[dataEntries];
		while (dataFileScanner.hasNextLine()) {
			Scanner dataLineScanner = new Scanner()
		}
		System.out.println("Finished processing " + dataFilename);
	}
}

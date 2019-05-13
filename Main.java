import java.io.*;
import java.awt.*;
import java.util.*;
public class Main{
    String fileName = "em_data.txt";
    double data[]; // Holds the data
    int k;

    public static void main(String [] args){
        Main m = new Main(args);
    }

    public Main(String [] args){

        if(args.length != 2)
            error("Incorrect Usage! Instead use:\njava Main <filePath> <K>");

        fileName = args[0];             // Set the fileName from the users input 
        k = Integer.parseInt(args[1]);  // Set k as the users input


        readInFile(); // Read in data
        System.out.println("K = " + k);
        System.out.println("Read in " + data.length + " data points");

    }

    public void readInFile(){
        BufferedReader reader;
        ArrayList<Double> tmpArray = new ArrayList<Double>();
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			while (line != null) { // Continue to read in lines as long as they exist
                tmpArray.add(Double.parseDouble(line)); // Add double to  the tmpArray
				line = reader.readLine(); // Read in next line
			}
			reader.close(); // Close BufferedReader when done
		} catch (IOException e) {
			e.printStackTrace();
        }
        data = new double[tmpArray.size()];
        for(int i = 0; i < tmpArray.size(); i++){
            data[i] = tmpArray.get(i);
        }
    }

    // Calculates the mean of the input 
    public double calcMean(double array[]){
        double sum = 0;
        int length = array.length;
        for(Double d : array)
            sum += d;

        return (sum/length);
    }

    // Calculates the variance of the input
    public double calcVariance(double array[]){
        double mean = calcMean(array);
        double sum = 0;
        for(double d : array)
            sum += (d - mean)*(d - mean);
        return sum/(array.length - 1);
    }

    // Calculates the standard deviation of the input
    public double calcStdDev(double array[]){
        return Math.sqrt(calcVariance(array));
    }

    public void error(String message){
        System.out.println("ERROR: " + message);
        System.exit(-1);
    }
}
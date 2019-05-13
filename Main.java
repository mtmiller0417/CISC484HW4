import java.io.*;
import java.awt.*;
import java.util.*;
public class Main{
    String fileName = "em_data.txt";
    double data[]; // Holds the data

    public static void main(String [] args){
        Main m = new Main(args);
    }

    public Main(String [] args){
        readInFile(); // Read in data
        System.out.println("Read in " + data.length + " data points");
        System.out.println("Mean is " + calcMean(data));
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

    public double calcMean(double array[]){
        double sum = 0;
        int length = array.length;
        for(Double d : array)
            sum += d;

        return (sum/length);
    }
}
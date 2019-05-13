import java.io.*;
import java.awt.*;
import java.util.*;
public class Main{
    String fileName = "em_data.txt";
    Double data[]; // Holds the data

    public static void main(String [] args){
        Main m = new Main(args);
    }

    public Main(String [] args){
        data = readInFile(); // Read in data
        System.out.println("Read in " + data.length + " data points");
    }

    public Double[] readInFile(){
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
        return (Double [])tmpArray.toArray(new Double[tmpArray.size()]); // Return array version of tmpArray arrayList
    }
}
import java.io.*;
import java.awt.*;
import java.util.*;

public class Main{
    	String fileName = "em_data.txt";
    	double data[]; // Holds the data
    	int k = 2;
	int n;
    	double means[]; //Holds k means
    	double v[]; //holds k variances
	double alphak[];
	double nk[];
	double w[][];
	double density[][];

	public static void main(String [] args){
        	Main m = new Main(args);
    	}

    	public Main(String [] args){
		if(args.length != 2)
			error("Incorrect usage! Correct usage:\njava Main <filePath> <K>");
		fileName = args[0];
		k = Integer.parseInt(args[1]);

		readInFile(); // Read in data
		n = data.length;
		System.out.println("K = " + k);
	        System.out.println("Read in " + data.length + " data points");
        	System.out.println("Mean is " + calcMean(data));
		initializeMV();
		for(int i = 0; i < k; i++)
			System.out.println("Mean : "+ means[i] + " variance: " + v[i]);
		
		alphak = new double[k];
		for(int j = 0; j < k; j++){
			alphak[j] = 1.0/(double)k;
		}
		nk = new double[k];
		w = new double[n][k];
		density = new double[n][k];

		fill_density();
		e_step();
		m_step();

		for(int i = 0; i < k; i++)
			System.out.println("Mean : "+ means[i] + " variance: " + v[i]);

	}

	public void e_step(){
		for(int i = 0; i < n; i++){
			double denominator = 0;
			for(int j = 0; j < k ; j++)
				denominator += (density[i][j]*alphak[j]);
			for(int j = 0; j < k; j++)
				w[i][j] = density[i][j]*alphak[j]/denominator;
		}
		System.out.println("e_step!");		
	}

	public void m_step(){
		for(int j = 0; j < k; j++){
			double n_sum = 0;
			double m_sum = 0;
			double v_sum = 0;
			for(int i = 0; i < n; i++){
				n_sum += w[i][j];
				m_sum += (w[i][j]*data[i]);
			}
			nk[j] = n_sum;
			alphak[j] = nk[j]/n;
			means[j] = m_sum/nk[j];

			for(int i = 0; i < n; i++){
				v_sum += (w[i][j]*Math.pow((data[i] - means[j]),2));
			}
			v[j] = v_sum/nk[j];
		}
		System.out.println("m_step!");
	}

	public void fill_density(){
		for(int j = 0; j < k; j++)
			for(int i = 0; i < n; i++){
				density[i][j] = calcPrDensity(data[i], means[j], v[j]);
			}
		System.out.println("filled density!");
	}
	

    	public void initializeMV(){
		means = new double[k];
		v = new double[k];

		double min = data[0];
		double max = data[0];
		for(int i =0; i < data.length; i++){
			if (data[i] > max)
				max =  data[i];
			else if (data[i] < min)
				min = data[i]; 
		}

		Random r = new Random();
		for(int j = 0; j < k; j++){
			means[j] = r.nextDouble()*(max - min)  + min;
		}

		for(int j = 0; j < k; j++){
			v[j] = 0;
			double sum = 0;
			for (int i = 0; i < data.length; i++){
				double diff = Math.pow((data[i] - means[j]), 2);
				sum += diff;
			}
			v[j] = sum/(data.length -  1);
		} 
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
	
	public double calcPrDensity(double x, double mean, double var){
		double pow = (-((x - mean) * (x - mean))) / (2 * var);
		double numerator = Math.pow(Math.E, pow);
		double denominator = Math.sqrt(2 * Math.PI * var);
		return numerator / denominator;
	}

    	public double calcMean(double array[]){
        	double sum = 0;
        	int length = array.length;
        	for(Double d : array)
            		sum += d;

        	return (sum/length);
	}
	
	public void error(String message){
		System.out.println("ERROR: " + message);
		System.exit(-1);
	}
}

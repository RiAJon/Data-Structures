package search_tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVParser {
	
	private CSVParser() {};
	
	public static ArrayList<Data> parse(String fileName) {
        String csvFilePath = fileName; 
        Data data;
        ArrayList<Data> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
        	
            String line;
            boolean isFirstLine = true; // Track the header line
            
            while ((line = br.readLine()) != null) {
            	
            	if (isFirstLine) { 
                    isFirstLine = false; // Skip the first line (header)
                    continue;
                }
            	
                String[] values = line.split(",");
                data = new Data(values[0], values[1], values[2]);
                
                // Ensure there are at least 3 values to avoid an ArrayIndexOutOfBoundsException
                if (values.length < 3) {
                    System.out.println("Skipping malformed line: " + Arrays.toString(values));
                    continue;
                }
                
                //System.out.println("Parsed values: " + Arrays.toString(values));
                
                dataList.add(data);
            }
            
        } catch (IOException e) {
				System.out.println("Sorry, no file exists with that name.");
			}
        
        return dataList;
        
    }

}

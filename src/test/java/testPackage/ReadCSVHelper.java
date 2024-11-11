package testPackage;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;

public class ReadCSVHelper {
	
	public static String[][] readDataFromCSV(String filePath) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
        	List<String[]> credentials = reader.readAll();
        	
        	String[][] result = new String[credentials.size()][];
        	for(int i = 0; i< credentials.size(); i++) {
        		result[i] = credentials.get(i);
        	}
        	
        	return result;
        }
    }
}

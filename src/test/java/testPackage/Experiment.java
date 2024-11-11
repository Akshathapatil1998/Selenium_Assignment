package testPackage;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Experiment {

	public static void main(String[] args) throws IOException {
		String[][] test = ReadCSVHelper.readDataFromCSV("/Users/xinyihu/Desktop/NEU/info6255/SeleniumProject/login_credentials.csv");
		
		for (String[] row : test) {
            for (String value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
	}

}

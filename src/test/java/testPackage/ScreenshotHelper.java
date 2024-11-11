package testPackage;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotHelper {
	
	public static void takeScreenshot(WebDriver driver, String testName, int counter) {
        // Convert WebDriver object to TakesScreenshot
        TakesScreenshot screenshot = (TakesScreenshot) driver;

        // Capture screenshot as File
        File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

        // Define the destination file path
        String fileName = "Screenshot_for_" + testName + "_" + counter + ".png";
        File destinationFile = new File("/Users/user/Documents/Selenium Project/Screenshots/" + fileName);

        try {
            // Copy the captured screenshot to the destination file
            FileUtils.copyFile(sourceFile, destinationFile);
//            System.out.println("Screenshot taken: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

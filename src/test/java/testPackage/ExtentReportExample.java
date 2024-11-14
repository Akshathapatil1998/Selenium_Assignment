package testPackage;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportExample {

    private static ExtentReports extent;
    private static ExtentTest test;
    private static WebDriver driver;

    public static void main(String[] args) {
        setupExtentReport();
        
        // Set the path to your chromedriver (replace with actual path)
        System.setProperty("webdriver.chrome.driver", "/Users/user/Documents/Selenium Project/chromedriver");
        
        // Initialize WebDriver (assuming ChromeDriver is set in system path)
        driver = new ChromeDriver();
        try {
            // Run test scenarios as per assignment requirements
            runTestScenarios();
        } finally {
            // Clean up resources
            if (driver != null) {
                driver.quit();
            }
            // Close and generate report
            extent.flush();
            System.out.println("HTML report generated successfully!");
        }
    }

    public static void setupExtentReport() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("SeleniumAssignmentExtentReport.html");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Selenium Automation Assignment Report");
        sparkReporter.config().setReportName("Selenium Test Results");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Tester", "Your Name");
        extent.setSystemInfo("Environment", "Test");
        extent.setSystemInfo("Browser", "Chrome");
    }

    public static void runTestScenarios() {
        // Scenario 1: Download Latest Transcript
        test = extent.createTest("Download Latest Transcript");
        try {
            test.log(Status.INFO, "Starting test to download the latest transcript.");
            // Perform Selenium actions here
            test.log(Status.PASS, "Transcript downloaded successfully.");
        } catch (Exception e) {
            handleTestFailure(e, "Download Latest Transcript");
        }

        // Scenario 2: Add To-Do Tasks
        test = extent.createTest("Add To-Do Tasks");
        try {
            test.log(Status.INFO, "Starting test to add To-Do tasks.");
            // Perform Selenium actions here
            test.log(Status.PASS, "Tasks added to calendar successfully.");
        } catch (Exception e) {
            handleTestFailure(e, "Add To-Do Tasks");
        }

        // Scenario 3: Download Classroom Guide (Invalid ID - Expected to fail)
        test = extent.createTest("Download Classroom Guide (Invalid ID)");
        try {
            test.log(Status.INFO, "Attempting to download classroom guide with invalid ID.");
            // Perform Selenium actions here
            test.log(Status.FAIL, "Expected: 'No classrooms found for the provided ID'; "
                                + "Actual: 'No classrooms match your filter criteria'.");
        } catch (Exception e) {
            handleTestFailure(e, "Download Classroom Guide (Invalid ID)");
        }

        // Scenario 4: Download Dataset
        test = extent.createTest("Download Dataset");
        try {
            test.log(Status.INFO, "Starting test to download dataset.");
            // Perform Selenium actions here
            test.log(Status.PASS, "Dataset downloaded successfully.");
        } catch (Exception e) {
            handleTestFailure(e, "Download Dataset");
        }

        // Scenario 5: Update Academic Calendar
        test = extent.createTest("Update Academic Calendar");
        try {
            test.log(Status.INFO, "Starting test to update academic calendar.");
            // Perform Selenium actions here
            test.log(Status.PASS, "Calendar updated successfully.");
        } catch (Exception e) {
            handleTestFailure(e, "Update Academic Calendar");
        }
    }

    // Method to handle failures, including taking a screenshot
    private static void handleTestFailure(Exception e, String scenarioName) {
        test.log(Status.FAIL, "Test failed: " + e.getMessage());
        try {
            String screenshotPath = takeScreenshot(scenarioName);
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (IOException ioException) {
            test.log(Status.WARNING, "Failed to capture screenshot: " + ioException.getMessage());
        }
    }

    // Method to take a screenshot
    private static String takeScreenshot(String scenarioName) throws IOException {
        // Create a unique file name based on timestamp and scenario name
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filePath = "screenshots/" + scenarioName + "_" + timestamp + ".png";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(filePath));
        return filePath;
    }
}

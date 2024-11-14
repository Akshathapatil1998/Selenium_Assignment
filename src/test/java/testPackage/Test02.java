

package testPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Story;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test02 {
    private WebDriver driver;
    private int counter = 0; // Initialize counter for screenshots

    @BeforeClass
    public void setUp() {
        // Set up ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/Users/user/Documents/Selenium Project/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(dataProvider = "userData")
    @Story("Canvas Login")
    @Description("Login to Northeastern portal, handle trust browser prompt, and navigate to Canvas.")
    public void canvasLogin(String username, String domain, String password) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        // Step 1: Open login page
        driver.get("https://me.northeastern.edu");

        // Step 2: Enter Username
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input")));
        usernameField.sendKeys(username + "@" + domain);
        ScreenshotHelper.takeScreenshot(driver, "canvasLogin", ++counter);

        WebElement nextButton = driver.findElement(By.id("idSIButton9"));
        nextButton.click();

        // Step 3: Enter Password
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.name("passwd")));
        passwordField.sendKeys(password);
        ScreenshotHelper.takeScreenshot(driver, "canvasLogin", ++counter);

        WebElement signInButton = driver.findElement(By.id("idSIButton9"));
        signInButton.click();

        // Step 4: Handle "Trust this browser" prompt if it appears
        try {
            WebElement trustBrowserButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("trust-browser-button")));
            trustBrowserButton.click();
            ScreenshotHelper.takeScreenshot(driver, "canvasLogin", ++counter);
            System.out.println("Trusted this browser.");
        } catch (Exception e) {
            System.out.println("Trust browser prompt not found. Continuing...");
        }

        try {
            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9"))); // or any other identifier for "Continue" button
            continueButton.click();
            ScreenshotHelper.takeScreenshot(driver, "canvasLogin", ++counter);
            System.out.println("Clicked 'Continue' after trusting the browser.");
        } catch (Exception e) {
            System.out.println("Continue button not found. Continuing...");
        }

        // Step 5: Navigate to Canvas
        driver.get("https://northeastern.instructure.com");

        // Step 7: Open Calendar
        WebElement calendarLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#global_nav_calendar_link > div.menu-item-icon-container > svg")));
        ScreenshotHelper.takeScreenshot(driver, "canvasCalendar", ++counter);
        calendarLink.click();

        // Read event data from CSV
        String[][] data = eventData();
        List<String[]> eventsData = Arrays.asList(data);

        // Log CSV content for debugging
        System.out.println("CSV Event Data:");
        for (String[] eventDetails : eventsData) {
            System.out.println(Arrays.toString(eventDetails));
            createEvent(eventDetails); // Create each event
        }

        // Verify if Canvas loaded
        try {
            wait.until(ExpectedConditions.titleContains("Canvas"));
            System.out.println("Canvas opened successfully.");
        } catch (Exception e) {
            System.out.println("Canvas did not load as expected. Please verify login credentials or steps.");
        }
    }

    private void createEvent(String[] eventDetails) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        // Click on the Add Button (to create a new event)
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Create New Event']")));
        ScreenshotHelper.takeScreenshot(driver, "canvasCalendar", ++counter);
        addButton.click();

        System.out.println("Adding event with details: " + Arrays.toString(eventDetails));

        // Fill in event details from CSV
        WebElement titleField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='TextInput_0']")));
        titleField.clear();  // Clear the field before entering new data
        titleField.sendKeys(eventDetails[0]); // Title from CSV

        // Locate and click on the time dropdown to display the options
        WebElement timeFieldDropdown = driver.findElement(By.xpath("//*[@id='edit_calendar_event_form_holder']/form/fieldset/span/span/span/span/span/span/span[3]/span/span[1]/span/label/span/span/span[2]/span/div/span/span/span"));
        timeFieldDropdown.click();

        // After opening the dropdown, find the specific time option (e.g., "12:00 AM") and click on it
        WebElement timeOption = driver.findElement(By.xpath("//*[text()='1:00 AM']"));
        timeOption.click();

        // Locate and click on the end time dropdown to display the options
        WebElement endTimeDropdown = driver.findElement(By.xpath("//*[@id='edit_calendar_event_form_holder']/form/fieldset/span/span/span/span/span/span/span[3]/span/span[2]/span/label/span/span/span[2]/span"));
        endTimeDropdown.click();

        // After opening the dropdown, find the specific end time option (e.g., "1:00 AM") and click on it
        WebElement endTimeOption = driver.findElement(By.xpath("//*[text()='10:00 AM']"));
        endTimeOption.click();

        // Locate and click on the calendar dropdown to display the options
        WebElement calendarDropdown = driver.findElement(By.xpath("//*[@id=\"edit_calendar_event_form_holder\"]/form/fieldset/span/span/span/span/span/span/span[6]/span/label/span/span/span[2]/span"));
        calendarDropdown.click();

        // Take a screenshot
        ScreenshotHelper.takeScreenshot(driver, "canvasCalendar", ++counter);

        // Find and click the Submit button
        WebElement submitButton = driver.findElement(By.xpath("//*[@id='edit-calendar-event-submit-button']"));
        submitButton.click();

        // Wait for the action to complete (adjust the time as needed)
        TimeUnit.SECONDS.sleep(2);

        // Wait a bit to ensure the event is added
        Thread.sleep(2000); // Adjust based on actual loading time needed
    }

    @DataProvider(name = "userData")
    public String[][] userData() throws IOException {
        return ReadCSVHelper.readDataFromCSV("/Users/user/Documents/Selenium Project/login_credentials.csv");
    }

    // Helper to read event data from CSV
    public String[][] eventData() throws IOException {
        return ReadCSVHelper.readDataFromCSV("/Users/user/Documents/Selenium Project/calander.csv");
    }

    @AfterClass
    public void tearDown() {
        // Close the browser after tests are done
        if (driver != null) {
            driver.quit();
        }
    }
}



package testPackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.print.PrintOptions;



@Epic("Suite")
@Feature("Transcript Feature")
public class Test01 {
    public static WebDriver driver;
    //private WebDriver driver;
    private String testName = "Transcript Download";
    private int counter = 0;
    
    
    

    @BeforeTest
    public void setUp() {
        // Setting up Chrome options for download behavior
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", "/Users/user/Documents/Selenium Project"); // change this path
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);
        prefs.put("plugins.always_open_pdf_externally", true);

        options.setExperimentalOption("prefs", prefs);
        
        // Set up WebDriver
        System.setProperty("webdriver.chrome.driver", "/Users/user/Documents/Selenium Project/chromedriver"); // change this path
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }
    
    @Test(dataProvider="userData")
	@Story("Valid Login")
	@Description("Test the login functionality with valid credentials.")
	public void portalLogin(String username, String domain, String password) throws InterruptedException, IOException {
        
		driver.get("https://me.northeastern.edu");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // passing user-name
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input")));
        usernameField.sendKeys(username +"@"+ domain);
        
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        
        WebElement nextButton = driver.findElement(By.id("idSIButton9"));
        nextButton.click();
        
        // passing password
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.name("passwd")));
        passwordField.sendKeys(password);
        
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        
        WebElement signInButton = driver.findElement(By.id("idSIButton9"));
        signInButton.click();
        
        // trust browser
        WebElement trustBrowserButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("trust-browser-button")));
        trustBrowserButton.click();
        
        wait.until(ExpectedConditions.urlToBe("https://login.microsoftonline.com/common/federation/OAuth2ClaimsProvider"));
        WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Yes']")));
        
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        
        yesButton.click();
    }
    
    @Test(dataProvider="userData")
	@Story("Transcript Download")
	@Description("Log in to My Transcript and view graduate transcript.")
    public void transcriptDownload(String username, String domain, String password) throws InterruptedException, IOException {
        
        driver.get("https://me.northeastern.edu");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        WebElement resourcesTab = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Resources"))); 
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        resourcesTab.click();
        
       
        WebElement academicsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='resource-tab-Academics,_Classes_&_Registration']")));
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        academicsLink.click();

        WebElement myTranscriptsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Transcript")));
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        myTranscriptsLink.click();
        
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        
        TimeUnit.SECONDS.sleep(1);
        
        // passing user-name
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
	    usernameField.sendKeys(username);
	    ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
	
	    //passing password
	    WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
	    passwordField.sendKeys(password);
	    ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
	    
	 
	    
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("_eventId_proceed")));
        loginButton.click();
        
      
        
        driver.switchTo().frame("duo_iframe");
        WebElement sendPushButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Send Me a Push')]")));
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        sendPushButton.click();
        driver.switchTo().defaultContent();
        
        // select transcript type
        WebElement transcriptLevelDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='levl']")));
        Select transcriptLevelSelect = new Select(transcriptLevelDropdown);
        transcriptLevelSelect.selectByVisibleText("Graduate");
        
        WebElement transcriptTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='tprt']")));
        Select transcriptTypeSelect = new Select(transcriptTypeDropdown);
        transcriptTypeSelect.selectByVisibleText("Audit Transcript");
        
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Submit']"));
        submitButton.click();
        
        TimeUnit.SECONDS.sleep(1);
        
        // Print the page to PDF
        PrintsPage printsPage = (PrintsPage) driver;
        Path printPage = Paths.get("/Users/xinyihu/Desktop/NEU/info6255/SeleniumProject/Downloads/My_Transcript.pdf");
    	Pdf print = printsPage.print(new PrintOptions());
    	Files.write(printPage, OutputType.BYTES.convertFromBase64Png(print.getContent()));

        TimeUnit.SECONDS.sleep(1);
    }
	
	@DataProvider(name = "userData")
	public String[][] userData() throws IOException {
		String[][] data = ReadCSVHelper.readDataFromCSV("/Users/user/Documents/Selenium Project/login_credentials.csv");
	
		return data;
	}
}
	
//	@AfterTest
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//        
//}



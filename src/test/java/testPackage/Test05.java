
package testPackage;
 
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
 
@Epic("Suite")
@Feature("Academic Calendar")
public class Test05 {
    private String testName = "Scenario 5";
    private int counter = 0;
    public static WebDriver driver;
 
    @Test
    @Story("Academic Calendar Review")
    @Description("View academic calendar and unselect graduate calendar.")
    public void academicCalendar() throws InterruptedException {
        WebDriver driver = Test01.driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
 
        driver.get("https://me.northeastern.edu");
 
        // Wait for the Resources tab to be clickable
        WebElement resourcesTab = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Resources")));
        resourcesTab.click();
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        // Click on Academics, Classes & Registration
        WebElement academicsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='resource-tab-Academics,_Classes_&_Registration']")));
        academicsLink.click();
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        // Click on the Academic Calendar
        WebElement academicCalendarLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Academic Calendar")));
        academicCalendarLink.click();
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        // Switch to the new window
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
 
        // Click on the Academic Calendar link
        WebElement calendarLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='https://registrar.northeastern.edu/article/academic-calendar/']")));
        calendarLink.click();
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        // Wait for and switch to the iframe
        WebElement selectCalendarIframe = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='trumba.spud.7.iframe']")));
        driver.switchTo().frame(selectCalendarIframe);
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        
        // Try locating the checkbox element again
        WebElement graduateCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'mixItem')]")));
        graduateCheckbox.click();
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        
        // Switch back to the main content
        driver.switchTo().defaultContent();
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        
        // Scroll to the bottom of the page
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
        // Wait for and switch to the correct calendar iframe
        WebElement calendarIframe = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='trumba.spud.7.iframe']")));
        driver.switchTo().frame(calendarIframe);
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);


 
 //WebElement addCalendarButton = driver.findElement(By.cssSelector("#ctl04_ctl84_ctl00_buttonAtmc > span"));
        WebElement addCalendarButton = driver.findElement(By.xpath("//*[@id=\'ctl04_ctl84_ctl00_buttonAtmc\']/span"));
        addCalendarButton.click();
        
       
            System.out.println("Button 'Add to My Calendar' is present and visible!");
    }



@AfterTest
public void tearDown() {
  if (driver != null) {
      driver.quit();
  }
}
}















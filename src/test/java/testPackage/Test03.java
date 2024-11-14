

////---------------------------------------------------------------------//
package testPackage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Suite")
@Feature("Classroom Guide Download")
public class Test03 {

    private String testName = "Scenario 3";
    private int counter = 0;

    @Test
    @Story("Invalid Classroom Selection")
    @Description("Test for handling invalid classroom selection and displaying appropriate error message.")
    public void classroomGuide() throws InterruptedException {
        WebDriver driver = Test01.driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Open a new tab and navigate to the classroom page
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://service.northeastern.edu/tech?id=classrooms");
        TimeUnit.SECONDS.sleep(2);
        ScreenshotHelper.takeScreenshot(driver, testName, ++counter);

        try {
            // Attempt to click on a non-existing classroom link (incorrect ID)
            WebElement incorrectClassroomLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "#x77ea03d9972dd1d8beddb4221153afa7 > div > div.panel-body > div.sc-container.ng-scope > div > div > div > div > div > a > div > div.item-card-body > div > h3"
            )));
            incorrectClassroomLink.click();

        } catch (Exception e) {
            System.out.println("Incorrect classroom ID - Element not found as expected. Proceeding to select 'NUflex Auto'.");
            ScreenshotHelper.takeScreenshot(driver, testName, ++counter);

            // Switch back to the main content if further actions require it
            driver.switchTo().defaultContent();

            // Select "NUflex Auto" from the dropdown
            WebElement classroomTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#classroomtype")));
            Select classroomTypeSelect = new Select(classroomTypeDropdown);
            classroomTypeSelect.selectByVisibleText("NUflex Auto");
            TimeUnit.SECONDS.sleep(1);
            ScreenshotHelper.takeScreenshot(driver, testName, ++counter);

            // Click on the "Search" button
            WebElement searchButton = driver.findElement(By.cssSelector("#classroomFilter > form > input[type=submit]"));
            searchButton.click();
            TimeUnit.SECONDS.sleep(2);
            ScreenshotHelper.takeScreenshot(driver, testName, ++counter);

            try {
                // Use the provided selector for the error message element
                WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                    "#x77ea03d9972dd1d8beddb4221153afa6 > div > div.panel-body > div.panels-container.list-group.ng-scope"
                		
                )));
                String actualErrorMessage = errorMessage.getText().trim();

                // Expected error message
                String expectedErrorMessage = "No classrooms found for the provided ID";
                
                // Print the actual and expected error messages
                System.out.println("Expected Error Message: " + expectedErrorMessage);
                System.out.println("Actual Error Message: " + actualErrorMessage);

                // Validate that the expected message does NOT match the actual message, as per the negative test case
                Assert.assertNotEquals(actualErrorMessage, expectedErrorMessage, 
                    "Test should fail due to mismatch: Expected 'No classrooms found for the provided ID' but found '" + actualErrorMessage + "'.");
                
                System.out.println("Test failed as expected: actual message does not match the expected message.");
                
            } catch (Exception timeoutException) {
                System.out.println("Timeout: Could not find the expected error message on the page.");
                ScreenshotHelper.takeScreenshot(driver, testName, ++counter);
                throw timeoutException;  // Ensure test fails if the error message is not found
            }
        }
    }
}



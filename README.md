


# Selenium Automation Project - INFO 6255

This repository contains Selenium automation test scenarios for the Northeastern University portal. The tests are written in Java using Selenium WebDriver and follow a data-driven approach with automated reporting using ExtentReports.

## Prerequisites

- **Java JDK**: Ensure that Java JDK is installed and configured.
- **Maven**: Install Maven for dependency management.
- **Selenium WebDriver**: Set up ChromeDriver or an alternative WebDriver.
- **Browser**: Google Chrome (or as specified in your WebDriver setup).
- **ExtentReports**: Used for HTML reporting of test results.

## Setup

### Clone the Repository:

```bash
git clone https://github.com/Akshathapatil1998/Selenium_Assignment
cd Selenium-Assignment
```

### Project Setup:

1. Open the project in your preferred IDE (e.g., IntelliJ, Eclipse).
2. Import dependencies using Maven (`pom.xml` manages all required dependencies).

### WebDriver Configuration:

- Download and configure **ChromeDriver** or **GeckoDriver** for Firefox.
- Ensure the WebDriver executable is added to your system path or configured in the project.

### Excel File for Data-Driven Testing:

- The tests require an Excel file for test data (e.g., login credentials, to-do tasks).
- Place the Excel file in the designated `data` folder and reference it in the test classes.

## Configuration

- **HTML Report**: The test scenarios will automatically generate an HTML report using ExtentReports, saved in the project root directory as `ExtentReport.html`.
- **Screenshots**: Screenshots will be captured before and after each major step and saved in a `screenshots` folder for each scenario.

---

## Test Scenarios

Each test scenario includes assertions, data-driven test inputs, and is designed to execute in sequence without manual intervention.

### Test Scenario 1: Download the Latest Transcript

**Overview**: Automates the download of the latest transcript from the MyNEU portal.

**Test Steps**:
1. Login to the MyNEU portal.
2. Navigate to the Student Hub.
3. Go to "Academics, Classes, and Registration" > "My Transcripts".
4. Select `Transcript Level: Graduate` and `Transcript Type: Audit Transcript`.
5. Click on "Print" and save the transcript as a PDF.

**Expected Outcome**: The transcript is downloaded successfully.

### Test Scenario 2: Add Two To-Do Tasks

**Overview**: Automates adding two To-Do tasks in Canvas.

**Test Steps**:
1. Login to Canvas and open the Calendar.
2. Click the "+" button to add a new task.
3. Read task details (title, date, time) from an external Excel file and input them into the event dialog.
4. Verify both tasks appear in the calendar.

**Expected Outcome**: Both tasks are added successfully to the calendar.

### Test Scenario 3: Download a Classroom Guide (Negative Test)

**Overview**: Attempts to download a classroom guide with an invalid classroom ID to trigger an error.

**Test Steps**:
1. Login to the service portal at [https://service.northeastern.edu/tech?id=classrooms](https://service.northeastern.edu/tech?id=classrooms).
2. Enter an invalid classroom ID.
3. Select "NUFlex Auto" from the Classroom Location dropdown.
4. Click the search button and observe the error message.

**Expected Outcome**: Displays "No classrooms found for the provided ID."  
**Actual Outcome**: Displays "No classrooms match your filter criteria."

**Result**: Expected failure, demonstrating a negative test case.

### Test Scenario 4: Download a Dataset

**Overview**: Automates the download of a dataset from the Northeastern University Library’s Digital Repository.

**Test Steps**:
1. Open Northeastern Library Search.
2. Navigate to the "Digital Repository Service".
3. Select "Datasets" from Featured Content.
4. Click "Zip File" to download the dataset.

**Expected Outcome**: The dataset downloads successfully as a zip file.

### Test Scenario 5: Update the Academic Calendar

**Overview**: Automates the update of the Academic Calendar on the Student Hub.

**Test Steps**:
1. Navigate to the Student Hub and click "Resources".
2. Go to "Academics, Classes, and Registration" > "Academic Calendar".
3. Uncheck any one calendar checkbox from the list.
4. Verify that the "Add to My Calendar" button is displayed.

**Expected Outcome**: The "Add to My Calendar" button appears after calendar settings are updated.

---

## Usage

### Run Tests:

- Each test scenario can be executed individually as a TestNG test case.
- Alternatively, configure all tests in a `testng.xml` file to run as a suite.

### Assertions and Verification:

- Each test includes at least two assertions and one verification step, validating expected outcomes.

### Data-Driven Testing:

- Data is dynamically loaded from an Excel file, eliminating hard-coded values.

### Screenshots:

- Screenshots are taken at key steps and saved to the scenario’s folder.

## Reporting

### HTML Report: It is recommended to remove the dependecy for the extent library and the java code before running along with test scenarios. The html report can be accessed after it is generated in the project files on the system.

- ExtenReport can be integrated by following [https://www.youtube.com/watch?v=eF_Vb-d4kIo&t=153s]

- An HTML report is generated using ExtentReports after test execution. The report includes:
  - **Test Scenario Name**
  - **Actual Result**
  - **Expected Result**
  - **Pass/Fail Status**
- Open `SeleniumAssignmentExtentReport.html` in a browser to view detailed results.

### Screenshots:

- Saved in the `Screenshots` folder, organized by scenario name.

--- 


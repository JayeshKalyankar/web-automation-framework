package tests;

import base.BaseTest;
import org.testng.annotations.*;
import org.testng.ITestResult;
import pages.WebFormPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.aventstack.extentreports.Status;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;



public class FormTest extends BaseTest {
    WebFormPage formPage;

    @BeforeMethod
    public void navigateToForm() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        formPage = new WebFormPage(driver);
        test = extent.createTest("Navigate to Form Page");
        test.log(Status.INFO, "Opened form page");
    }

    @DataProvider(name = "formData")
    public Object[][] testData() {
        return new Object[][] {
            {"TestUser1", "pass123"},
            {"TestUser2", "secure456"}
        };
    }

    @Test(dataProvider = "formData")
    public void submitFormTest(String username, String password) {
        test = extent.createTest("Form Submission Test - " + username);
        try {
            formPage.fillForm(username, password);
            test.log(Status.INFO, "Filled form with data");
            String message = formPage.getMessage();
            assert message.contains("Received!") : "Form not submitted";
            test.log(Status.PASS, "Form submitted successfully and received message");
        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed due to: " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            File dest = new File("test-output/screenshot_" + time + ".png");
            try {
                Files.copy(src.toPath(), dest.toPath());
                test.addScreenCaptureFromPath(dest.getAbsolutePath());
                test.log(Status.INFO, "Screenshot attached");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

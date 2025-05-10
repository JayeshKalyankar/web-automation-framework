package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class WebFormPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By textInput = By.name("my-text");
    By password = By.name("my-password");
    By dropdown = By.name("my-select");
    By checkbox = By.id("my-check-1");
    By submit = By.tagName("button");
    By message = By.id("message");

    public WebFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait max 10 sec
    }

    public void fillForm(String inputText, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(textInput)).sendKeys(inputText);
        wait.until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(pass);
        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).sendKeys("Two");
        wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();
    }

    public String getMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(message)).getText();
    }
}

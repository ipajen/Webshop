package features;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class StepDefinitionAbout {
    private static WebDriver driver;

    @BeforeAll
    public static void createDriver() {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--no-sandbox");
        option.addArguments("--disable-dev-shm-usage");
        option.addArguments("--headless");
        option.addArguments("--disable-gpu");
        option.addArguments("--window-size=1920,1200");
        driver = new ChromeDriver(option);
    }

    // Verify that the "To all products" button on About page leads to the Products page
    // Author: Ingela Bladh
    @When("User clicks To all products button")
    public void userClicksToAllProductsButton() {
        clickElement("body > div.container.my-5 > div > div > button");
    }

    // Author: Ingela Bladh
    private void clickElement(String cssSelector) {
        WebDriverWait wait = createWebDriverWait();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(cssSelector))).click();
    }

    // Author: Ingela Bladh
    private WebDriverWait createWebDriverWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @AfterAll
    public static void quitDriver() {
        driver.quit();
    }

}

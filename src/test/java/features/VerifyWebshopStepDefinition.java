package features;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerifyWebshopStepDefinition {
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

    //Verify that the Shop link works
    //Author: Ingela Bladh
    @Given("User visits {string}")
    public void userVisits(String webshopUrl) {
        driver.get(webshopUrl);
    }

    @When("User clicks {string}")
    public void userClicks(String shopLink) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/header/div/div/ul/li[2]/a"))).click();
    }

    @Then("The current url should be {string}")
    public void theCurrentUrlShouldBe(String expectedUrl) {
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @AfterAll
    public static void quitDriver() {
        driver.quit();
    }

}

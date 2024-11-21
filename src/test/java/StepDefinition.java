import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinition {

    private static WebDriver driver;  // Instance variable for WebDriver
    private  static WebDriverWait wait;

    @Before
    public  static void createDriver() {
        // Setup ChromeDriver options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origin=*");
        options.addArguments("incognito");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-blink-features=AutomationControlled");
        // Uncomment below for headless mode if required
        // options.addArguments("--headless");

        // Initialize WebDriver
        driver = new ChromeDriver(options);

        // Initialize WebDriverWait
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Navigate to the Webpage
        driver.get("https://webshop-agil-testautomatiserare.netlify.app/");
    }

    @After
    public void tearDown() {
        // Close the browser if the driver is not null
        if (driver != null) {
            driver.quit();
        }
    }

    // Check the website Title & Heading
    //    Author: Barnali Mohanty

    @Given("Webshop is available")
    public void webshopIsAvailable() {
    }

    @When("User checks the title")
    public void userChecksTheTitle() {
        // Placeholder step for checking the title,No need of code
    }

    @Then("the title should be {string}")
    public void theTitleOfThePageShouldBe(String expectedTitle) {
        // Get the title of the current page
        String actualTitle = driver.getTitle();
        assertEquals(expectedTitle, actualTitle, "Page title does  match the expected value.");
    }

    @Then("the heading should be {string}")
    public void theHeadingShouldBe(String expectedHeadingText) {
        // Locate the main heading using a CSS selector
        WebElement heading = driver.findElement(By.cssSelector("body > header > div > div > a > h1"));

        // Validate the text of the heading
        assertEquals(expectedHeadingText, heading.getText(), "Page heading does  match the expected value.");
    }
}

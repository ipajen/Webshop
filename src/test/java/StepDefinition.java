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
         options.addArguments("--headless");

        // Initialize WebDriver
        driver = new ChromeDriver(options);

        // Initialize WebDriverWait
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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

    // Check the main text on the webpage "This shop is all you need" exist
    //    Author: Barnali Mohanty

    @Given("Webshop is available")
    public void webshopIsAvailable() {
    }


    @When("User checks the main heading")
    public void userChecksTheMainHeading() {
        // Placeholder: Action will be verified in the next step
    }

    @Then("the main heading should be {string}")
    public void theMainHeadingShouldBe(String expectedHeading) {
        // Locate the element
        WebElement heading = driver.findElement(By.cssSelector("h2.display-4.fw-bold.lh-1"));

        // Get the text of the element
        String actualHeading = heading.getText();

        // Print the heading text
        System.out.println("The main heading text is: " + actualHeading);


        // Assert the text matches the expected value
        assertEquals(expectedHeading, actualHeading, "The main heading text does  match the expected value.");
    }







}


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StepDefinition {

    private static WebDriver driver;
    @BeforeAll
    public static void createDriver()
    {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origin=*");
        option.addArguments("incognito");
        option.addArguments("-start-maximized");
        option.addArguments("--disable-infobars");
        option.addArguments("--disable-blink-features=AutomationControlled");
        option.addArguments("--headless");

        driver = new ChromeDriver(option);
        driver.get("https://webshop-agil-testautomatiserare.netlify.app/");
    }

    @Then("the copyright text should be {string}")
    //Author: Jarko Piironen
    public void theCopyrightTextShouldBe(String expectedCopyrightText) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement websiteCopyright = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("  body > div:nth-child(3) > footer > p"))
        );
      
        // Get the text from the copyright element
        String websiteCopyrightText = websiteCopyright.getText();
        System.out.println(" - Copyright text: " + websiteCopyrightText);

        assertEquals(expectedCopyrightText, websiteCopyrightText);
    }

    @Given("Webshop is available")
    public void webshopIsAvailable() {
    }


    @When("User visits webshop-agil-testautomatiserare.netlify.app")
    public void userVisitsWebshopAgilTestautomatiserareNetlifyApp() {
    }

    //Verify that there are no uncaught syntax errors in the browser console logs
    @Then("the console logs should not contain errors")
    //Author: Jarko Piironen
    public void theConsoleLogsShouldNotContainErrors() {
            LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
            boolean syntaxErrorFound = false;
            List<LogEntry> Alllogs = logs.getAll();
            for (LogEntry entry : Alllogs) {
                System.out.println(driver + " Console Error test " + entry.getMessage());
                if (entry.getMessage().contains("Uncaught SyntaxError")) {
                    syntaxErrorFound = true;

                }
            }
        assertTrue(!syntaxErrorFound, "Test failed: Uncaught SyntaxError found in JavaScript logs.");
        }
    }


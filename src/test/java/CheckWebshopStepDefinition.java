import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class CheckWebshopStepDefinition {

    private static WebDriver driver;  // Instance variable for WebDriver
    private static WebDriverWait wait;

    private WebElement imgElement;

    @Before
    public static void createDriver() {
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

    // Check the website Title & Heading
    //    Author: Barnali Mohanty

    @Given("Webshop.Netify is available")
    public void webshopIsAvailable() {
    }

    @When("User checks the image element")
    public void userChecksTheImageElement() {
        // Locate the <img> element
        imgElement = driver.findElement(By.cssSelector("img.rounded-lg-3"));
        assertNotNull(imgElement, "Image element not found on the page.");
    }

    @Then("the image source should be {string}")
    public void theImageSourceShouldBe(String expectedSrc) {
        // Get the `src` attribute value
        String actualSrc = imgElement.getAttribute("src");

        // Print and assert the `src`
        System.out.println("Image source: " + actualSrc);
        assertEquals(expectedSrc, actualSrc, "The image source URL does  match.");
    }

    @Then("the image height should be {string}")
    public void theImageHeightShouldBe(String expectedHeight) {
        // Get the `height` attribute value
        String actualHeight = imgElement.getAttribute("height");

        // Print and assert the `height`
        System.out.println("Image height: " + actualHeight);
        assertEquals(expectedHeight,actualHeight,"The height matches");
    }

    @Then("the image should have an alt text")
    public void theImageShouldHaveAnAltText() {
        String altText = imgElement.getAttribute("alt");
        System.out.println("Image alt text: '" + altText + "'");

        // Assert the `alt` attribute exists
        assertNotNull(altText, "The image does not have an alt attribute.");

        // Allow empty `alt` text for decorative images (optional)
        if (altText.isEmpty()) {
            System.out.println("Warning: The image has an empty alt attribute.");
        } else {
            System.out.println("The image alt text is: " + altText);
        }
    }
    //  verify Billing and Payment headings on check out page
    @Given("the user is on the webshop homepage")
    public void theUserIsOnTheWebshopHomepage() {

    }
    @When("the user scrolls down to the {string} link")
    public void theUserScrollsDownToTheLink(String linkText) {
        // Locate the element by its link text
        WebElement element = driver.findElement(By.linkText(linkText));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @Then("the user clicks the {string} link")
    public void theUserClicksTheLink(String linkText) {
        // wait till the element is visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Locate the element by its link text and click it
        WebElement element = driver.findElement(By.linkText(linkText));
        element.click();
    }
    @When("the user scrolls down to the {string} heading")
    public void theUserScrollsDownToTheHeading(String headingText) {
        //Wait till the page loads
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Locate the element by its text
        WebElement headingElement = driver.findElement(By.xpath("//h4[@class='mb-3' and text()='" + headingText + "']"));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headingElement);
    }
    @Then("the {string} heading text should be {string}")
    public void theHeadingTextShouldBe(String headingType, String expectedText) {
        // Locate the heading element by its text
        WebElement headingElement = driver.findElement(By.xpath("//h4[@class='mb-3' and text()='" + expectedText + "']"));

        // Verify the text
        String actualText = headingElement.getText();
        assertEquals(expectedText, actualText, headingType + " heading text does not match.");
    }


}


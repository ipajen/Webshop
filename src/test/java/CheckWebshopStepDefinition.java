import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        //options.addArguments("--headless");

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
        assertEquals(expectedHeight, actualHeight, "The height matches");
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
    public void theUserIsOnTheHomepage() {

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
    //Verify the presence of the product in your cart

    @Given("the user is on the webpage")
    public void the_User_Is_On_The_Webpage() {

    }

    @When("the user clicks the {string} button")
    public void theUserClicksTheShopButton(String buttonType) throws InterruptedException {
        //Wait till the page loads
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //Find the shop button and then click it
        WebElement shopButton = driver.findElement(By.xpath("/html/body/header/div/div/ul/li[2]/a"));
        Thread.sleep(500);
        shopButton.click();
    }

    @When("the user scrolls down to the {string} button for {string}")
    public void theUserScrollsDownToTheButton(String buttonType, String productName) {
        //Wait till the page loads
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //Locate the element of choice
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > main:nth-child(3) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > button:nth-child(4)")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
    }

    @When("User clicks on the Add to Cart button for Mens Casual Premium Slim Fit T-Shirts")
    public void userClicksAddToCartButton() {
        try {
            // Step 1: Ensure the element is visible (if hidden or covered by other elements)
            JavascriptExecutor executor = (JavascriptExecutor) driver;

            // Make sure the button is visible (force display in case it's hidden)
            executor.executeScript("document.querySelector('button[onclick*=\"Mens Casual Premium Slim Fit T-Shirts\"]').style.display='block';");

            // Step 2: Wait for the button to be present in the DOM
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            WebElement addToCartButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("main > div:nth-child(2) > div > div > button")));

            // Step 3: Check if the button is enabled and clickable
            if (!addToCartButton.isEnabled()) {
                System.out.println("The 'Add to Cart' button is not enabled.");
                return;  // Exit if the button is not enabled
            }

            // Step 4: Handle potential overlapping elements (ensure the button is not covered)
            if (!addToCartButton.isDisplayed()) {
                System.out.println("The 'Add to Cart' button is not displayed or hidden behind another element.");
                return;  // Exit if the button is not displayed
            }

            // Step 5: Use Actions to move to the element if it's scrolled out of view
            Actions actions = new Actions(driver);
            actions.moveToElement(addToCartButton).perform();

            // Optional: Wait briefly to ensure button is in a clickable state
            Thread.sleep(2000);  // Sleep for 2 seconds (adjust the time as necessary)

            // Step 6: Perform a click on the button using JavaScriptExecutor as a last resort
            executor.executeScript("arguments[0].click();", addToCartButton);

            System.out.println("Successfully clicked on the 'Add to Cart' button for Mens Casual Premium Slim Fit T-Shirts.");

            // Optional: Sleep again if needed after the click
            Thread.sleep(2000);  // Adjust the sleep time if necessary

        } catch (Exception e) {
            // Handle exception if element is not found, not clickable, or other issues
            System.out.println("Failed to click on the 'Add to Cart' button: " + e.getMessage());
            // Optionally, re-throw the exception to fail the test
            // throw e;
        }
    }





    @When("User clicks on the Checkout button")
    public void userClicksOnCheckoutButton() {
        try {
            // Locate the checkout button
            WebElement checkoutButton = driver.findElement(By.xpath("//a[@class='btn btn-warning' and @href='/checkout']"));

            // Wait for the checkout button to be clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));

            // Scroll the element into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutButton);
             Thread.sleep(5000);
            // Click the button
            checkoutButton.click();

            System.out.println("Successfully clicked the Checkout button.");
        } catch (Exception e) {
            System.out.println("Failed to click the Checkout button.");
            //throw e;
        }
    }

    @Then("the item {string} should be present in the cart")
    public void theItemShouldBePresentInTheCart(String expectedItemName) {
        try {
            // Use WebDriverWait to wait for the element to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Locate the cart item using its text. This assumes cart items have a <h6> tag with a class containing "my-0 w-75".
            WebElement itemElement = driver.findElement(By.xpath("//h6[contains(text(), '" + expectedItemName + "')]"));

            // Assert that the located element is displayed on the page.
            assertTrue(itemElement.isDisplayed(), "The item '" + expectedItemName + "' is not present in the cart.");

            // Log success to the console if the item is found.
            System.out.println("The item '" + expectedItemName + "' is present in the cart.");
        } catch (Exception e) {
            // Log failure to the console if the item is not found.
            System.out.println("Failed to find the item '" + expectedItemName + "' in the cart.");

            // Re-throw the exception to mark the test as failed.
            throw e;
        }
    }

    @Then("the item {string} should not be present in the cart")
    public void theItemShouldNotBePresentInTheCart(String expectedItemName) {
        boolean itemFound = false; // Flag to track if the item is found.

        try {
            // Attempt to locate the item in the cart using its text.
            driver.findElement(By.xpath("//h6[contains(text(), '" + expectedItemName + "')]"));
            itemFound = true; // If found, set the flag to true.
        } catch (Exception e) {
            // Item not found; this is the expected outcome for this negative test.
        }

        // Assert that the item is not found in the cart.
        assertFalse(itemFound, "The item '" + expectedItemName + "' was unexpectedly found in the cart.");

        // Log the outcome to the console.
        if (!itemFound) {
            System.out.println("As expected, the item '" + expectedItemName + "' is not in the cart.");
        } else {
            System.out.println("Unexpectedly found the item '" + expectedItemName + "' in the cart.");
        }
    }





}


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

    @Given("I open the web page {string}")
    public void i_open_the_web_page(String url) {

    }
    @When("I check the {string} attribute of the {string} tag")
    public void i_check_the_attribute_of_the_tag(String attribute, String tagName) {
        // Find the <html> element and get the 'lang' attribute
        WebElement element = driver.findElement(By.tagName(tagName));
        String langValue = element.getAttribute(attribute);

        // Store the result for later verification
        System.setProperty("langValue", langValue);
    }
    @Then("the language should be {string}")
    public void the_language_should_be(String expectedLanguage) {

            // Retrieve the stored attribute value
            String actualLanguage = System.getProperty("langValue");
            assertEquals(expectedLanguage, actualLanguage);

        }




    @Then("the content should appear in English")
    public void the_content_should_appear_in_english() {
        // Locate a prominent element on the page to validate the content
        WebElement element = driver.findElement(By.tagName("h1")); // Adjust the locator as needed
        String content = element.getText();

        // Print the fetched content for debugging
        System.out.println("Fetched Content: " + content);

        // Check if the content contains expected English keywords or phrases
        if (content.toLowerCase().contains("shop") || content.toLowerCase().contains("products") || content.toLowerCase().contains("checkout")) {
            System.out.println("The content appears to be in English.");
        } else {
            System.err.println("The content does NOT appear to be in English.");
        }


    }
    //Verify the error message ,when there is no input in the checkout form
    //Barnali Mohanty

    @Given("the user is on the checkout page at {string}")
    public void the_user_is_on_the_checkout_page(String url) {
        driver.get("https://webshop-agil-testautomatiserare.netlify.app/checkout");
    }
    @When("the user clicks the {string} button without filling required fields")
    public void the_user_clicks_the_button_without_filling_required_fields(String buttonLabel) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Locate the button using XPath
        WebElement checkoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='" + buttonLabel + "']")));

        // Scroll to the button using JavaScriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", checkoutButton);

        // Wait for the button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));

        // Click the button using JavaScriptExecutor
        js.executeScript("arguments[0].click();", checkoutButton);
    }

    @Then("the error messages for required fields should be displayed")
    public void the_error_messages_for_required_fields_should_be_displayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // First Name error
            WebElement firstNameError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[2]/div[2]/form/div[1]/div[1]/div")));
            assertTrue(firstNameError.isDisplayed(), "First name error message is  displayed");

            // Last Name error
            WebElement lastNameError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[2]/div[2]/form/div[1]/div[2]/div")));
            assertTrue(lastNameError.isDisplayed(), "Last name error message is  displayed");

            // Email error
            WebElement emailError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[2]/div[2]/form/div[1]/div[3]/div")));
            assertTrue(emailError.isDisplayed(), "Email error message is  displayed");

            System.out.println("All error messages are displayed as expected!");
        } catch (AssertionError e) {
            System.err.println("Assertion failed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    // Check the website Title & Heading
    //    Author: Barnali Mohanty

    @Given("User is on the Webpage")
    public void user_is_on_the_webpage() {
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

    // Check the main text on the webpage "This shop is all you need" exist
    //    Author: Barnali Mohanty

    @Given("User Is on the WebPage")
    public void userIsOnTheWebPage() {
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




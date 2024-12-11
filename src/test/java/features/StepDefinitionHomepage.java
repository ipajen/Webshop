package features;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StepDefinitionHomepage {

    static WebDriver driver;
    private WebElement imgElement;
    private static final String HOMEPAGE_URL = "https://webshop-agil-testautomatiserare.netlify.app/";

    @BeforeAll
    public static void createDriver() {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origin=*");
        option.addArguments("incognito");
        option.addArguments("-start-maximized");
        option.addArguments("--disable-infobars");
        option.addArguments("--disable-blink-features=AutomationControlled");
        option.addArguments("--headless");

        driver = new ChromeDriver(option);
    }

    //Verify that the website copyright text displays correctly
    //Author: Jarko Piironen
    @Given("Webshop is available")
    public void webshopIsAvailable() {
        driver.get(HOMEPAGE_URL);
    }

    //Author: Jarko Piironen
    @Then("the copyright text should be {string}")
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

    //Verify that there are no uncaught syntax errors in the browser console logs
    //Author: Jarko Piironen
    @Then("the console logs should not contain errors")
    public void theConsoleLogsShouldNotContainErrors() {
        LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
        boolean syntaxErrorFound = false;
        List<LogEntry> Alllogs = logs.getAll();
        for (LogEntry entry : Alllogs) {
            // System.out.println(driver + " Console Error test " + entry.getMessage());
            if (entry.getMessage().contains("Uncaught SyntaxError")) {
                syntaxErrorFound = true;

            }
        }
        assertFalse(syntaxErrorFound, "Test failed: Uncaught SyntaxError found in JavaScript logs.");
    }

    //Validate SSL certificate
    //Author: Jarko Piironen
    @Then("the SSL certificate should be valid and not expiring in {int} days")
    public void theSSLCertificateShouldBeValidAndNotExpiringInDays(int minDays) {
        try {
            long daysRemaining = getDaysRemaining();

            // Check if the certificate is valid and has more than 30 days before expiry
            assertTrue(daysRemaining > minDays, "SSL Certificate is expiring in less than " + minDays + " days!");
            System.out.println("SSL Certificate is valid and expires in " + daysRemaining + " days.");
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Failed to retrieve SSL certificate: " + e.getMessage());
        }
    }

    //Author: Jarko Piironen
    private static long getDaysRemaining() throws IOException {
        String siteUrl = "https://webshop-agil-testautomatiserare.netlify.app";
        URL url = new URL(siteUrl);

        // Get the SSL certificate and check its validity

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.connect();

        // Retrieve the SSL certificate
        X509Certificate cert = (X509Certificate) connection.getServerCertificates()[0];
        Date expiryDate = cert.getNotAfter();
        Date currentDate = new Date();
        return (expiryDate.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24);
    }

    // Verify footer navigation links
    // Author: Jarko Piironen
    @When("the user clicks the following {string} link")
    public void theUserClicksTheFollowingLink(String linkText) {
        String selector = getLinkSelector(linkText);

        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement linkElement;

            // Attempt to locate the link with the primary selector
            try {
                linkElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                System.out.println("Link found with primary selector: " + selector);
            } catch (TimeoutException e) {
                // Fallback to locating the link by visible text
                System.out.println("Primary selector failed for link: " + linkText + ". Trying By.linkText.");
                linkElement = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)));
            }

            // Scroll into view and ensure the element is clickable
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", linkElement);
            Thread.sleep(500); // Let the page stabilize after scrolling
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -100);"); // Offset for sticky headers

            // Retry clicking with JavaScript if necessary
            try {
                linkElement.click();
                System.out.println("Clicked on the link: " + linkText);
            } catch (ElementClickInterceptedException ex) {
                System.out.println("Element click intercepted. Attempting JavaScript click.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkElement);
            }

        } catch (Exception e) {
            System.out.println("Failed to click the link: " + linkText + " using selector: " + selector + ". Error: " + e.getMessage());
            Assertions.fail("Could not click the link: " + linkText);
        }
    }

    // Author: Jarko Piironen
    private String getLinkSelector(String linkText) {
        // Fallback selectors for more dynamic handling
        return switch (linkText.toLowerCase()) {
            case "home" -> "[data-footer-link='home'], body > div:nth-child(3) > footer > ul > li:nth-child(1) > a";
            case "shop" -> "[data-footer-link='shop'], body > div:nth-child(3) > footer > ul > li:nth-child(2) > a";
            case "checkout" ->
                    "[data-footer-link='checkout'], body > div:nth-child(3) > footer > ul > li:nth-child(4) > a";
            case "about" -> "[data-footer-link='about'], body > div:nth-child(3) > footer > ul > li:nth-child(3) > a";
            default -> "a"; // Default selector if no match found
        };
    }

    // Author: Jarko Piironen
    @Then("the {string} page should be displayed")
    public void thePageShouldBeDisplayed(String page) {
        // Wait for the URL to change and assert the current URL
        boolean isCorrectPage = new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.urlToBe(page));
        Assertions.assertTrue(isCorrectPage, "Expected page URL: " + page + ", but got: " + driver.getCurrentUrl());
    }

    // Verify the image element on the webpage
    // Author: Barnali Mohanty
    @When("User checks the image element")
    public void userChecksTheImageElement() {
        // Locate the <img> element
        imgElement = driver.findElement(By.cssSelector("img.rounded-lg-3"));
        assertNotNull(imgElement, "Image element not found on the page.");
    }

    // Author: Barnali Mohanty
    @Then("the image source should be {string}")
    public void theImageSourceShouldBe(String expectedSrc) {
        // Get the `src` attribute value
        String actualSrc = imgElement.getAttribute("src");

        // Print and assert the `src`
        System.out.println("Image source: " + actualSrc);
        assertEquals(expectedSrc, actualSrc, "The image source URL does  match.");
    }

    // Author: Barnali Mohanty
    @And("the image height should be {string}")
    public void theImageHeightShouldBe(String expectedHeight) {
        // Get the `height` attribute value
        String actualHeight = imgElement.getAttribute("height");

        // Print and assert the `height`
        System.out.println("Image height: " + actualHeight);
        assertEquals(expectedHeight, actualHeight, "The height matches");
    }

    // Author: Barnali Mohanty
    @And("the image should have an alt text")
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

    // Check if the declared language and content language match
    // Author: Barnali Mohanty
    @When("I check the {string} attribute of the {string} tag")
    public void i_check_the_attribute_of_the_tag(String attribute, String tagName) {
        // Find the <html> element and get the 'lang' attribute
        WebElement element = driver.findElement(By.tagName(tagName));
        String langValue = element.getAttribute(attribute);

        // Store the result for later verification
        assertNotNull(langValue);
        System.setProperty("langValue", langValue);
    }

    // Author: Barnali Mohanty
    @Then("the language should be {string}")
    public void the_language_should_be(String expectedLanguage) {

        // Retrieve the stored attribute value
        String actualLanguage = System.getProperty("langValue");
        assertEquals(expectedLanguage, actualLanguage);
    }

    // Author: Barnali Mohanty
    @And("the content should appear in English")
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

    // Check website title
    // Author: Barnali Mohanty
    @When("User checks the title")
    public void userChecksTheTitle() {
        // Placeholder step for checking the title,No need of code
    }

    // Author: Barnali Mohanty
    @Then("the title should start with {string}")
    public void theTitleOfThePageShouldBe(String expectedTitle) {
        // Get the title of the current page
        String actualTitle = driver.getTitle();
        assertNotNull(actualTitle);
        assertTrue(actualTitle.startsWith(expectedTitle));
        System.out.println(actualTitle);
        System.out.println(expectedTitle);
    }

    // Author: Barnali Mohanty
    @And("the heading should be {string}")
    public void theHeadingShouldBe(String expectedHeadingText) {
        // Locate the main heading using a CSS selector
        WebElement heading = driver.findElement(By.cssSelector("body > header > div > div > a > h1"));

        // Validate the text of the heading
        assertEquals(expectedHeadingText, heading.getText(), "Page heading does  match the expected value.");
    }

    // User checks the main heading text
    // Author: Barnali Mohanty
    @When("User checks the main heading")
    public void userChecksTheMainHeading() {
        // Placeholder: Action will be verified in the next step
    }

    // Author: Barnali Mohanty
    @Then("the main heading should be {string}")
    public void theMainHeadingShouldBe(String expectedHeading) {
        // Locate the element
        WebElement heading = driver.findElement(By.cssSelector("h2.display-4.fw-bold.lh-1"));

        // Get the text of the element
        String actualHeading = heading.getText();

        // Print the heading text
        System.out.println("The main heading text is: " + actualHeading);

        // Assert the text matches the expected value
        assertEquals(expectedHeading, actualHeading, "The main heading does match the expected value.");
    }

    // Verify header navigation links work
    // Author: Ingela Bladh
    @When("User clicks link with {string}")
    public void userClicksLinkWith(String cssSelector) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(cssSelector))).click();
    }

    // Author: Ingela Bladh
    @Then("The page displayed should be {string}")
    public void thePageDisplayedShouldBe(String expectedPage) {
        assertEquals(HOMEPAGE_URL + expectedPage, driver.getCurrentUrl());
    }
}

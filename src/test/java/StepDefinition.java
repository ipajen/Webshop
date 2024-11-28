import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.BeforeAll;
import org.junit.Assert;
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
import java.net.URL;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.List;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StepDefinition {

    static WebDriver driver;

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

    //Verify that the website copyright text displays correctly
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

    //Validate SSL certificate
    //Author: Jarko Piironen
    @Then("the SSL certificate should be valid and not expiring in {int} days")
    public void theSSLCertificateShouldBeValidAndNotExpiringInDays(int minDays) {
        try {
        String siteUrl = "https://webshop-agil-testautomatiserare.netlify.app";
        URL url = new URL(siteUrl);

        // Get the SSL certificate and check its validity

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.connect();

            // Retrieve the SSL certificate
            X509Certificate cert = (X509Certificate) connection.getServerCertificates()[0];
            Date expiryDate = cert.getNotAfter();
            Date currentDate = new Date();
            long daysRemaining = (expiryDate.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24);

     // Check if the certificate is valid and has more than 30 days before expiry
        assertTrue(daysRemaining > minDays, "SSL Certificate is expiring in less than " + minDays + " days!");
        System.out.println("SSL Certificate is valid and expires in " + daysRemaining + " days.");
    } catch (Exception e) {
        e.printStackTrace();
        Assertions.fail("Failed to retrieve SSL certificate: " + e.getMessage());
        }
    }

    // Validate Search Functionality
    // Author: Jarko Piironen
    @When("User visits the products page at {string}")
    public void userVisitsTheProductsPageAt(String productsUrl) {
        try {
            // Wait for the "Products" link to be clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement productsLink = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("header > div > div > ul > li:nth-child(2) > a"))
            );

            // Click the "Products" link
            productsLink.click();
            System.out.println("Clicked on the Products link from the homepage successfully.");
        } catch (Exception e) {
            System.out.println("Failed to click on the Products link: " + e.getMessage());
            Assertions.fail("Test failed because the 'Products' link could not be clicked.");
        }
    }

    @And("User searches for {string}")
    public void userSearchesFor(String searchQuery) {
        try {
            // Wait for the specific element containing the expected product name to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement productElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#main > div:nth-child(1) > div > div > h3"))
            );

            // Ensure the element contains the expected product name text
            //NOTE: This part is needed as if the search is made to quickly it will not find anything until page loaded
            // (Not working with only extra wait)
            if (productElement.getText().contains("Fjällräven - Foldsack No. 1 Backpack, Fits 15 Laptops")) {
                System.out.println("Found the expected product: Fjällräven - Foldsack No. 1 Backpack.");
            } else {
                System.out.println("Expected product not found.");
            }

            // Wait for the "search" bar to be visible and clickable
            WebElement searchbar = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("search"))
            );

            // Input the search query into the search bar
            searchbar.clear(); //clears the box
            searchbar.sendKeys(searchQuery);
            System.out.println("Search for '" + searchQuery + "' initiated successfully.");
        } catch (Exception e) {
            System.out.println("Failed to perform search operation: " + e.getMessage());
            Assertions.fail("Test failed because the search operation could not be completed.");
        }
    }

    @Then("the search results should display items related to {string}")
    public void theSearchResultsShouldDisplayItemsRelatedTo(String searchQuery) {
        try {
            // Wait for the specific element in the search results to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement resultItem = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#main > div:nth-child(1) > div > div > h3"))
            );

            // Get the text of the result item
            String resultText = resultItem.getText();

            // Validate that the text matches the expected search query
            Assertions.assertTrue(
                    resultText.contains(searchQuery),
                    "Expected search result text to contain: '" + searchQuery + "', but got: '" + resultText + "'."
            );

            System.out.println("Search results contain the expected item: '" + searchQuery + "'.");

        } catch (Exception e) {
            System.out.println("Failed to validate search results: " + e.getMessage());
            Assertions.fail("Test failed because the expected search result was not found.");
        }

    }


    // Verify navigation links
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

    @Then("the {string} page should be displayed")
    public void thePageShouldBeDisplayed(String page) {
        // Wait for the URL to change and assert the current URL
        boolean isCorrectPage = new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.urlToBe(page));
        Assertions.assertTrue(isCorrectPage, "Expected page URL: " + page + ", but got: " + driver.getCurrentUrl());
    }

    private String getLinkSelector(String linkText) {
        // Fallback selectors for more dynamic handling
        switch (linkText.toLowerCase()) {
            case "home":
                return "[data-footer-link='home'], body > div:nth-child(3) > footer > ul > li:nth-child(1) > a";
            case "shop":
                return "[data-footer-link='shop'], body > div:nth-child(3) > footer > ul > li:nth-child(2) > a";
            case "checkout":
                return "[data-footer-link='checkout'], body > div:nth-child(4) > footer > ul > li:nth-child(3) > a";
            case "about":
                return "[data-footer-link='about'], body > div:nth-child(3) > footer > ul > li:nth-child(4) > a";
            default:
                return "a"; // Default selector if no match found

    //Validate Filter Functionality
    //Author: Jarko Piironen

    @When("the user clicks on {string}")
    public void theUserClicksOn(String filter) throws InterruptedException {
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String cssSelector;

        // Map each filter to its corresponding CSS selector
        switch (filter) {
            case "All":
                cssSelector = "body > div.container.mt-5 > div > ul > li:nth-child(1) > a";
                break;
            case "Men's clothing":
                cssSelector = "body > div.container.mt-5 > div > ul > li:nth-child(2) > a";
                break;
            case "Women's clothing":
                cssSelector = "body > div.container.mt-5 > div > ul > li:nth-child(3) > a";
                break;
            case "Jewelery":
                cssSelector = "body > div.container.mt-5 > div > ul > li:nth-child(4) > a";
                break;
            case "Electronics":
                cssSelector = "body > div.container.mt-5 > div > ul > li:nth-child(5) > a";
                break;
            default:
                throw new IllegalArgumentException("Invalid filter: " + filter);
        }

        try {
            // Wait for the element to be clickable
            WebElement filterElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
            filterElement.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            // Fail the test explicitly if the element cannot be clicked
            Assert.fail("Failed to click on the filter: " + filter + ". Error: " + e.getMessage());
        }
    }

    @Then("the user verifies that the {string} loads its respective products")
    public void theUserVerifiesThatTheLoadsItsRespectiveProducts(String filter) {
       try {
            // Try waiting for the 'main' element to become visible
            WebElement mainElement = driver.findElement(By.id("main"));

            // Retrieve all div elements under the 'main' element
            List<WebElement> divsUnderMain = mainElement.findElements(By.cssSelector("div"));
            System.out.println("divs under main WITHOUT all: " + divsUnderMain);

            if (filter.equals("All")) {
                // For the "All" filter, verify that at least one div under 'main' has the 'col' class
                Assert.assertFalse("No divs under 'main' with 'col' class displayed under 'All' filter", divsUnderMain.isEmpty());

                boolean colClassExists = false;
                for (WebElement div : divsUnderMain) {
                    String classAttribute = div.getAttribute("class");
                    System.out.println("Found class for div: " + classAttribute); // Log the class attribute
                    if ("col".equals(classAttribute)) {
                        colClassExists = true;
                    }
                }
                Assert.assertTrue("No div with class 'col' found under 'All' filter", colClassExists);
            } else {
                // For other filters, ensure that no div under 'main' has the 'col' class
                List<WebElement> divsUnderMain2 = mainElement.findElements(By.cssSelector("div"));
                System.out.println("divs under main: " + divsUnderMain2);
                Assert.assertTrue("No divs under 'main' displayed under filter '" + filter + "'", divsUnderMain2.isEmpty());

                for (WebElement div : divsUnderMain2) {
                    String classAttribute = div.getAttribute("class");
                    System.out.println("Found class for div: " + classAttribute); // Log the class attribute
                    Assert.assertFalse("Div under filter '" + filter + "' should not have class 'col'", "col".equals(classAttribute));
                }
            }
        } catch (Exception e) {
            // Handle any exception that might occur
            System.out.println("An error occurred: " + e.getMessage());
            throw e;
        }
    }
}
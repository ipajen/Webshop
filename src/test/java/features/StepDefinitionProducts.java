package features;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StepDefinitionProducts {

    static WebDriver driver;

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

    // Validate Search Functionality
    // Author: Jarko Piironen
    @Given("User visits the products page at {string}")
    public void userVisitsTheProductsPageAt(String productsUrl) {
        driver.get(productsUrl);
    }

    // Author: Jarko Piironen
    @When("User searches for {string}")
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

    // Author: Jarko Piironen
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
            Assertions.fail("Failed to click on the filter: " + filter + ". Error: " + e.getMessage());
        }
    }

    // Author: Jarko Piironen
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
                Assertions.assertFalse(divsUnderMain.isEmpty(), "No divs under 'main' with 'col' class displayed under 'All' filter");

                boolean colClassExists = false;
                for (WebElement div : divsUnderMain) {
                    String classAttribute = div.getAttribute("class");
                    System.out.println("Found class for div: " + classAttribute); // Log the class attribute
                    if ("col".equals(classAttribute)) {
                        colClassExists = true;
                    }
                }
                Assertions.assertTrue(colClassExists, "No div with class 'col' found under 'All' filter");
            } else {
                // For other filters, ensure that no div under 'main' has the 'col' class
                List<WebElement> divsUnderMain2 = mainElement.findElements(By.cssSelector("div"));
                System.out.println("divs under main: " + divsUnderMain2);
                Assertions.assertTrue(divsUnderMain2.isEmpty(), "No divs under 'main' displayed under filter '" + filter + "'");

                for (WebElement div : divsUnderMain2) {
                    String classAttribute = div.getAttribute("class");
                    System.out.println("Found class for div: " + classAttribute); // Log the class attribute
                    Assertions.assertFalse("col".equals(classAttribute), "Div under filter '" + filter + "' should not have class 'col'");
                }
            }
        } catch (Exception e) {
            // Handle any exception that might occur
            System.out.println("An error occurred: " + e.getMessage());
            throw e;
        }
    }

    // Verify product has all elements
    // Author: Ingela Bladh
    @Then("Product should have all elements")
    public void productShouldHaveAllElements() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        List<WebElement> list = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("main"))).findElements(By.className("col"));

        for (WebElement card : list) {
            // Image
            assertNotNull(card.findElement(By.tagName("img")));

            WebElement cardBody = card.findElement(By.className("card-body"));
            assertNotNull(cardBody);

            // Title
            assertNotNull(cardBody.findElement(By.tagName("h3")));
            // Price
            assertNotNull(cardBody.findElement(By.className("fs-5")).findElement(By.tagName("strong")));
            // Description
            assertNotNull(cardBody.findElement(By.className("card-text")));
            // Add to cart button
            assertNotNull(cardBody.findElement(By.tagName("button")));
        }
    }
}

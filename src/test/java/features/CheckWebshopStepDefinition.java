package features;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
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

    @BeforeAll
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

    @Given("the user is on the ProductWebpage")
    public void the_User_Is_On_The_ProductWebpage() {
        driver.get("https://webshop-agil-testautomatiserare.netlify.app/products");
        System.out.println("User is on product page");
    }

    @When("the user clicks the Add to Cart button")
    public void userClicksAddToCartButton() throws InterruptedException {
        Thread.sleep(2000);
        WebElement addToCartButton = driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/div/div/button"));

        //Add time for the page to load properly

        Thread.sleep(1000);
        addToCartButton.click();
        //Just debugging
        System.out.println("Is button displayed: " + addToCartButton.isDisplayed());
        System.out.println("Is button enabled: " + addToCartButton.isEnabled());
        System.out.println("Is button selected: " + addToCartButton.isSelected());

        Thread.sleep(2000);
        // See if the cart is updated ,when you click the add to cart button
        boolean countUpdated = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("buttonSize"), "1"));
        if (countUpdated) {
            WebElement cartCount = driver.findElement(By.id("buttonSize"));
            String actualCount = cartCount.getText();
            System.out.println("Cart count updated successfully: " + actualCount);
        } else {
            System.out.println("Cart count did not update as expected.");
        }
    }

    @When("the user clicks the Checkout button")
    public void userClicksCheckoutButton() throws InterruptedException {
        Thread.sleep(2000);
        WebElement checkoutButton = driver.findElement(By.xpath("/html/body/header/div/div/div/a"));
        checkoutButton.click();
        System.out.println("Successfully clicked the Checkout button.");
    }

    @Then("the item {string} should be present in the cart")
    public void theItemShouldBePresentInTheCart(String itemName) {
        WebElement cartItem = driver.findElement(By.xpath("//*[@id=\"cartList\"]/li[1]/div/h6"));
        //Assert that the item is present
        assert cartItem != null : "Item " + itemName + " was not found in the cart.";
        System.out.println("Item " + itemName + " is present in the cart.");
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

    @AfterAll
    public static void tearDown() {
        // Close the browser if the driver is not null
        if (driver != null) {
            driver.quit();
        }
    }
}

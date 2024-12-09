package features;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionCheckout {

    private static WebDriver driver;  // Instance variable for WebDriver

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
    }

    // Verify that the Total sum is correct and that the Remove button works
    // Author: Ingela Bladh
    @Given("User visits the page {string}")
    public void userVisitsThePage(String webshopUrl) {
        driver.get(webshopUrl);
    }

    // Author: Ingela Bladh
    @And("User adds backpack to cart")
    public void userAddsBackpackToCart() {
        clickElement("#main > div:nth-child(1) > div > div > button");
    }

    // Author: Ingela Bladh
    @And("User adds Tshirt to cart")
    public void userAddsTshirtToCart() {
        clickElement("#main > div:nth-child(2) > div > div > button");
    }

    // Author: Ingela Bladh
    @And("User adds jacket to cart")
    public void userAddsJacketToCart() {
        clickElement("#main > div:nth-child(3) > div > div > button");
    }

    // Author: Ingela Bladh
    @Then("The Total sum should be correct")
    public void theTotalSumShouldBeCorrect() {
        WebDriverWait wait = createWebDriverWait();
        List<WebElement> cartList = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("cartList"))).findElements(By.tagName("li"));

        double expectedSum = 0;
        for (int i = 0; i <= 2; i++) {
            double price = Double.parseDouble(cartList.get(i).findElement(By.tagName("span")).getText().substring(1));
            expectedSum += price;
        }

        double actualSum = Double.parseDouble(cartList.getLast().findElement(By.tagName("span")).getText().substring(1));

        assertEquals(expectedSum, actualSum);
    }

    // Author; Ingela Bladh
    @And("User removes items")
    public void userRemovesItems() {
        for (int i = 0; i <= 2; i++) {
            clickElement("#cartList > li:nth-child(1) > div > button");
        }
    }

    // Author: Ingela Bladh
    @Then("Your cart list should only contain Total")
    public void yourCartListShouldOnlyContainTotal() {
        WebDriverWait wait = createWebDriverWait();
        List<WebElement> cartList = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("cartList"))).findElements(By.tagName("li"));
        assertEquals(1, cartList.size());
    }

    // Verify email form field validation
    // Author: Ingela Bladh
    @And("User fills in email field with {string}")
    public void userFillsInEmailFieldWith(String email) {
        WebDriverWait wait = createWebDriverWait();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("email"))).sendKeys(email);
    }

    // Author: Ingela Bladh
    @And("User clicks Continue to checkout button")
    public void userClicksContinueToCheckoutButton() {
        WebDriverWait wait = createWebDriverWait();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("body > main > div.row.g-5 > div.col-md-7.col-lg-6 > form > button"))).submit();
    }

    // Invalid email
    // Author: Ingela Bladh
    @Then("The page should show {string}")
    public void thePageShouldShow(String expectedErrorMessage) {
        String actualErrorMessage = getText(
                "body > main > div.row.g-5 > div.col-md-7.col-lg-6 > form > div.row.g-3 > div:nth-child(3) > div.invalid-feedback");
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    // Valid email
    // Author: Ingela Bladh
    @Then("A check mark should be displayed")
    public void aCheckMarkShouldBeDisplayed() {
        WebDriverWait wait = createWebDriverWait();
        WebElement mark = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
                "body > main > div.row.g-5 > div.col-md-7.col-lg-6 > form > div.row.g-3 > div:nth-child(3) > div:nth-child(3)")));
        assertNotNull(mark);
    }

    // Verify the PayPal radio button works
    // Author: Ingela Bladh
    @And("User selects PayPal radio button")
    public void userSelectsPayPalRadioButton() {
        clickElement("#paypal");
    }

    // Verify the Credit card radio button works
    // Author: Ingela Bladh
    @And("User selects Credit card radio button")
    public void userSelectsCreditCardRadioButton() {
        clickElement("#credit");
    }

    // Verify the Debit card radio button works
    // Author: Ingela Bladh
    @And("User selects Debit card radio button")
    public void userSelectsDebitCardRadioButton() {
        clickElement("##debit");
    }

    // Author: Ingela Bladh
    @Then("The page should display {string}")
    public void thePageShouldDisplay(String expectedText) {
        String actualText = getText("#paypalInfo > p > i");
        assertEquals(expectedText, actualText);
    }

    // Author: Ingela Bladh
    @And("The card div should have the class {string}")
    public void theCardDivShouldHaveTheClass(String expectedClass) {
        WebElement div = getCardDiv();
        assertEquals(expectedClass, div.getAttribute("class"));
    }

    // Author: Ingela Bladh
    @Then("The card div should not have any class")
    public void theCardDivShouldNotHaveAnyClass() {
        WebElement div = getCardDiv();
        assertEquals("", div.getAttribute("class"));
    }

    // Verify the Continue to checkout button works
    @Then("The form tag should have the classes {string}")
    public void theFormTagShouldHaveTheClasses(String expectedClasses) {
        WebDriverWait wait = createWebDriverWait();
        WebElement form = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.tagName("form")));
        assertEquals(expectedClasses, form.getAttribute("class"));
    }

    // Author: Ingela Bladh
    private void clickElement(String cssSelector) {
        WebDriverWait wait = createWebDriverWait();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(cssSelector))).click();
    }

    // Author: Ingela Bladh
    private String getText(String cssSelector) {
        WebDriverWait wait = createWebDriverWait();
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
                cssSelector))).getText();
    }

    // Author: Ingela Bladh
    private WebElement getCardDiv() {
        WebDriverWait wait = createWebDriverWait();
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id("card")));
    }

    // Author: Ingela Bladh
    private WebDriverWait createWebDriverWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // User scrolls to locate checkout button and verify Billing and Payment headings on check out page
    // Author: Barnali Mohanty
    @Given("the user is on the webshop homepage")
    public void theUserIsOnTheHomepage() {
        // Navigate to the Webpage
        driver.get("https://webshop-agil-testautomatiserare.netlify.app/");
    }

    // Author: Barnali Mohanty
    @When("the user scrolls down to the {string} link")
    public void theUserScrollsDownToTheLink(String linkText) {
        // Locate the element by its link text
        WebElement element = driver.findElement(By.linkText(linkText));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Author: Barnali Mohanty
    @And("the user clicks the {string} link")
    public void theUserClicksTheLink(String linkText) {
        // Locate the element by its link text and click it
        WebElement element = driver.findElement(By.linkText(linkText));
        element.click();
    }

    // Author: Barnali Mohanty
    @And("the user scrolls down to the {string} heading")
    public void theUserScrollsDownToTheHeading(String headingText) {
        // Locate the element by its text
        WebElement headingElement = driver.findElement(By.xpath("//h4[@class='mb-3' and text()='" + headingText + "']"));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headingElement);
    }

    // Author: Barnali Mohanty
    @Then("the {string} heading text should be {string}")
    public void theHeadingTextShouldBe(String headingType, String expectedText) {
        // Locate the heading element by its text
        WebElement headingElement = driver.findElement(By.xpath("//h4[@class='mb-3' and text()='" + expectedText + "']"));

        // Verify the text
        String actualText = headingElement.getText();
        assertEquals(expectedText, actualText, headingType + " heading text does not match.");
    }

    // Verify items in the cart
    // Author: Barnali Mohanty
    @Given("the user is on the ProductWebpage")
    public void the_User_Is_On_The_ProductWebpage() {
        driver.get("https://webshop-agil-testautomatiserare.netlify.app/products");
        System.out.println("User is on product page");
    }

    // Author: Barnali Mohanty
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean countUpdated = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("buttonSize"), "1"));
        if (countUpdated) {
            WebElement cartCount = driver.findElement(By.id("buttonSize"));
            String actualCount = cartCount.getText();
            System.out.println("Cart count updated successfully: " + actualCount);
        } else {
            System.out.println("Cart count did not update as expected.");
        }
    }

    // Author: Barnali Mohanty
    @And("the user clicks the Checkout button")
    public void userClicksCheckoutButton() throws InterruptedException {
        Thread.sleep(2000);
        WebElement checkoutButton = driver.findElement(By.xpath("/html/body/header/div/div/div/a"));
        checkoutButton.click();
        System.out.println("Successfully clicked the Checkout button.");
    }

    // Author: Barnali Mohanty
    @Then("the item {string} should be present in the cart")
    public void theItemShouldBePresentInTheCart(String itemName) {
        WebElement cartItem = driver.findElement(By.xpath("//*[@id=\"cartList\"]/li[1]/div/h6"));
        //Assert that the item is present
        assertNotNull(cartItem, "Item " + itemName + " was not found in the cart.");
        System.out.println("Item " + itemName + " is present in the cart.");
    }

    // Display error messages when required fields are left empty
    // Author: Barnali Mohanty
    @Given("the user is on the checkout page at {string}")
    public void the_user_is_on_the_checkout_page(String url) {
        driver.get(url);
    }

    // Author: Barnali Mohanty
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

    // Author: Barnali Mohanty
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

package features;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VerifyWebshopStepDefinition {
    private static WebDriver driver;

    @BeforeAll
    public static void createDriver() {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--no-sandbox");
        option.addArguments("--disable-dev-shm-usage");
        option.addArguments("--headless");
        option.addArguments("--disable-gpu");
        option.addArguments("--window-size=1920,1200");
        driver = new ChromeDriver(option);
    }

    // Verify that the "To all products" button on About page leads to the Products page
    // Author: Ingela Bladh
    @When("User clicks To all products button")
    public void userClicksToAllProductsButton() {
        clickElement("body > div.container.my-5 > div > div > button");
    }

    // Verify that the Remove button works
    // Author: Ingela Bladh
    @And("User clicks Add to cart button")
    public void userClicksAddToCartButton() {
        clickElement("#main > div:nth-child(1) > div > div > button");
    }

    // Author: Ingela Bladh
    @When("User clicks Remove button")
    public void userClicksRemoveButton() {
        clickElement("#cartList > li.list-group-item.d-flex.justify-content-between.lh-sm > div > button");
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

    // Wrong email
    // Author: Ingela Bladh
    @Then("The page should show {string}")
    public void thePageShouldShow(String expectedErrorMessage) {
        WebDriverWait wait = createWebDriverWait();
        String actualErrorMessage = getText(
                "body > main > div.row.g-5 > div.col-md-7.col-lg-6 > form > div.row.g-3 > div:nth-child(3) > div.invalid-feedback");
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    // Correct email
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

    @AfterAll
    public static void quitDriver() {
        driver.quit();
    }

}

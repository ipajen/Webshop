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

    // Author: Ingela Bladh
    @Given("User visits {string}")
    public void userVisits(String webshopUrl) {
        driver.get(webshopUrl);
    }

    // Verify that the Shop link works
    // Author: Ingela Bladh
    @When("User clicks Shop link")
    public void userClicksShopLink() {
        clickElement("body > header > div > div > ul > li:nth-child(2) > a");
    }

    // Verify that the Checkout button works
    // Author: Ingela Bladh
    @When("User clicks Checkout button")
    public void userClicksCheckoutButton() {
        clickElement("body > header > div > div > div > a");
    }

    // Verify that the Home image link works
    // Author: Ingela Bladh
    @When("User clicks Home image link")
    public void userClicksHomeImageLink() {
        clickElement("body > header > div > div > a > h1");
    }

    // Verify that the Home link works
    // Author: Ingela Bladh
    @When("User clicks Home link")
    public void userClicksHomeLink() {
        clickElement("body > header > div > div > ul > li:nth-child(1) > a");
    }

    // Author: Ingela Bladh
    @Then("The current url should be {string}")
    public void theCurrentUrlShouldBe(String expectedUrl) {
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    // Verify that the Products button has the text "All products"
    // Author: Ingela Bladh
    @Then("The button text should be {string}")
    public void theButtonTextShouldBe(String expectedButtonText) {
        WebDriverWait wait = createWebDriverWait();
        String actualButtonText = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("body > div.container.my-5 > div > div.col-lg-7.p-3.p-lg-5.pt-lg-3 > div > button"))).getText();
        assertEquals(expectedButtonText, actualButtonText);
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

    // Verify product has all elements
    // Author: Ingela Bladh
    @Then("Product should have all elements")
    public void productShouldHaveAllElements() {

        WebDriverWait wait = createWebDriverWait();

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
    @Then("An error message should be displayed")
    public void anErrorMessageShouldBeDisplayed() {
        WebDriverWait wait = createWebDriverWait();
        String actualErrorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
                "body > main > div.row.g-5 > div.col-md-7.col-lg-6 > form > div.row.g-3 > div:nth-child(3) > div.invalid-feedback"))).getText();
        assertEquals("Please enter a valid email address for shipping updates.", actualErrorMessage);
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

    // Author: Ingela Bladh
    private void clickElement(String cssSelector) {
        WebDriverWait wait = createWebDriverWait();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(cssSelector))).click();
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

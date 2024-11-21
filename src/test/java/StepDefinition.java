import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.net.ssl.HttpsURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StepDefinition {

    private static WebDriver driver;
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
}


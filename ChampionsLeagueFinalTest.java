import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class ChampionsLeagueFinalTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testPageLoadAndNavigation() {
        String expectedUrl = "https://www.footballticketnet.com/champions-league/champions-league-final-2025";

        // Load page
        driver.get(expectedUrl);
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl(), "URL mismatch");

        // Check breadcrumb links
        for (WebElement breadcrumb : driver.findElements(By.cssSelector(".breadcrumb a"))) {
            wait.until(ExpectedConditions.elementToBeClickable(breadcrumb));
            Assertions.assertTrue(breadcrumb.isDisplayed(), "Breadcrumb not visible: " + breadcrumb.getText());
        }

        // Click "View All Matches" link
        WebElement viewAllMatches = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View All Matches")));
        viewAllMatches.click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("matches"), "'View All Matches' navigation failed");
        driver.navigate().back();

        // Click logo and verify redirect
        WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".site-logo")));
        logo.click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("footballticketnet.com"), "Logo did not redirect to homepage");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

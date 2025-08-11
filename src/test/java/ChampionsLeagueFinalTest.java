import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class ChampionsLeagueFinalTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        // Automatically sets up the correct ChromeDriver
        WebDriverManager.chromedriver().setup();
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
        List<WebElement> breadcrumbs = driver.findElements(By.cssSelector(".breadcrumb a"));
        for (WebElement breadcrumb : breadcrumbs) {
            wait.until(ExpectedConditions.elementToBeClickable(breadcrumb));
            Assertions.assertTrue(breadcrumb.isDisplayed(), "Breadcrumb not visible: " + breadcrumb.getText());
        }

        // Click "View All Matches" link (if present)
        List<WebElement> viewAllMatchesLinks = driver.findElements(By.linkText("View All Matches"));
        if (!viewAllMatchesLinks.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(viewAllMatchesLinks.get(0))).click();
            System.out.println("Clicked 'View All Matches' link.");
        } else {
            System.out.println("'View All Matches' link not found.");
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

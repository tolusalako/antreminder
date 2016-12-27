package net.csthings.antreminder;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import net.csthings.AntreminderApplication;

@org.testng.annotations.Test
@WebIntegrationTest({ "server.port=8080", "management.port=0" })
@Transactional
@SpringApplicationConfiguration(classes = { AntreminderApplication.class, SecurityContextHolder.class })
public class FunctionalTests extends AbstractTestNGSpringContextTests {
    private Logger LOG = LoggerFactory.getLogger(FunctionalTests.class);

    // @Autowired
    // private MockMvc mvc;
    //
    // @MockBean
    // IndexController indexController;

    private static final String path = "test\\chromedriver.exe";

    @Value("http://${app.host}")
    private String url;

    public static WebDriver driver;
    private String email = "test@csthings.net";
    private String password = "super secret";

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", path);
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
    }

    @BeforeMethod
    public void beforeMethod() {
        LOG.info("+++++++++++++Running Test+++++++++++++++");
        // driver.setJavascriptEnabled(true);
        driver.navigate().to(url);
        LOG.info("Currently at: {}", driver.getCurrentUrl());

    }

    @AfterMethod
    public void afterMethod() {
        LOG.info("++++++++++++++++++++++++++++++++++++++++");
    }

    public void testLogin() {
        driver.findElement(By.id("form-login-email")).sendKeys(email);
        driver.findElement(By.id("form-login-password")).sendKeys(password);
        driver.findElement(By.id("form-login-submit")).click();

        // Confirm logged in
        WebElement loggedIn = driver.findElement(By.id("text-login-message"));
        Assert.assertNotNull(loggedIn);
        Assert.assertEquals(loggedIn.getText(), "Hello, " + email);

        // Navigate to authenticated reminders
        String remindersUrl = url + "/reminders";
        driver.navigate().to(remindersUrl);
        Assert.assertTrue(driver.getCurrentUrl().equals(remindersUrl));
    }

}

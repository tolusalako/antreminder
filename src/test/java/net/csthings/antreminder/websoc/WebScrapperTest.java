package net.csthings.antreminder.websoc;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import net.csthings.AntreminderApplication;
import net.csthings.antreminder.provider.ServiceProvider;
import net.csthings.common.utils.WebScrapper;
import net.csthings.config.WebSocSettings;

@SpringApplicationConfiguration(classes = { AntreminderApplication.class, ServiceProvider.class, })
@WebAppConfiguration
public class WebScrapperTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebSocSettings websocSettings;

    Logger LOG = LoggerFactory.getLogger(WebScrapperTest.class);
    File cache;
    WebScrapper ws;
    List<WebElement> forms;

    @BeforeClass
    public void initTest() {
        cache = new File(websocSettings.getSearchFragment());
        Assert.assertTrue(cache.exists());
        ws = new WebScrapper("file://" + cache.getAbsolutePath());
        forms = ws.driver.findElements(By.xpath("/html/body/form"));
        Assert.assertEquals(forms.size(), 1, "There should be only one form.");
    }

    @Test
    public void getFormScrap() throws IOException {
        File form = new File(websocSettings.getFormFragment());
        Assert.assertTrue(form.exists());
        String data = forms.get(0).getAttribute("innerHTML");
        // FileUtils.writeStringToFile(form, data, Charset.forName("UTF-8"));
        Assert.assertNotNull(data);
    }

    @Test
    public void getDepartments() {
        List<WebElement> data = ws.driver.findElements(By.xpath("/html/body/form/table/tbody/*"));
        Assert.assertEquals(data.size(), 19, "There should be 19.");
        LOG.debug("Got {} items", data.size());
        data.stream().map(f -> f.getText()).forEach(System.out::println);
    }

    @Test
    public void tableElementTest() {
        List<WebElement> tables = ws.driver.findElements(By.xpath("/html/body/form/table/tbody/tr[4]/td[3]/select/*"));
        Assert.assertTrue(tables.size() > 0);
        tables.stream().map(f -> f.getText()).forEach(System.out::println);
    }
}

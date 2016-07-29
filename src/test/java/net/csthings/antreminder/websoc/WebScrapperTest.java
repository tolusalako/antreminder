package net.csthings.antreminder.websoc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import net.csthings.AntreminderApplication;
import net.csthings.config.WebSocSettings;
import net.csthings.provider.ServiceProvider;

@SpringApplicationConfiguration(classes = { AntreminderApplication.class})
@WebAppConfiguration
public class WebScrapperTest {

    Logger LOG = LoggerFactory.getLogger(WebScrapperTest.class);
    File cache;
    WebScrapper ws;
    List<WebElement> forms;

    @BeforeClass
    public void initTest() {
        cache = new File("resources/templates/websoc/websoc.html");
        ws = new WebScrapper("file://" + cache.getAbsolutePath());
        forms = ws.driver.findElements(By.xpath("/html/body/form"));
    }

    @Test
    public void getFormScrap() throws IOException {
        Assert.assertEquals(forms.size(), 1, "There should be only one form.");
        File form = new File("resources/templates/websoc/form.html");
        String data = forms.get(0).getAttribute("innerHTML");
        FileUtils.writeStringToFile(form, data, Charset.forName("UTF-8"));
    }

    @Test
    public void getDepartments() {
        List<WebElement> data = ws.driver.findElements(By.xpath("/html/body/form/table/tbody/*"));
        Assert.assertEquals(data.size(), 19, "There should be 19.");
        LOG.info("Got {} items", data.size());
        data.stream().map(f -> f.getText()).forEach(System.out::println);
        ;

    }

    @Test
    public void tableElementTest() {
        List<WebElement> tables = ws.driver.findElements(By.xpath("/html/body/form/table/tbody/tr[4]/td[3]/select/*"));
        tables.stream().map(f -> f.getText()).forEach(System.out::println);
    }
}

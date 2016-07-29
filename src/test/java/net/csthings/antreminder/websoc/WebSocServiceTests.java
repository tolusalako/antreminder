package net.csthings.antreminder.websoc;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import net.csthings.AntreminderApplication;
import net.csthings.antreminder.websoc.WebSocUtils.Category;
import net.csthings.antreminder.websoc.service.WebSocService;
import net.csthings.common.utils.Pair;
import net.csthings.provider.ServiceProvider;

@SpringApplicationConfiguration(classes = { AntreminderApplication.class, ServiceProvider.class })
@WebAppConfiguration
public class WebSocServiceTests extends AbstractTestNGSpringContextTests {

    @Autowired
    WebSocService service;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    @Test
    public void basicWebSoc() throws IOException {
        File html = service.getFormHtml();
        Assert.assertTrue(html.exists());
    }

    @Test
    public void websocNewForm() throws IOException {
        File form = service.generateNewFormHtml();
        Assert.assertTrue(form.exists());
        Assert.assertEquals(sdf.format(form.lastModified()), sdf.format((new Date()).getTime()));
    }

    @Test
    public void dataTest() {
        List<Pair<String, String>> rez = WebSocUtils.parseDeptElement(service.getFormData(Category.DEPARTMENT));
        rez.stream().forEach(System.out::println);
        Assert.assertEquals(rez.size(), WebSocUtils.NUM_CATEGORIES);
    }
}

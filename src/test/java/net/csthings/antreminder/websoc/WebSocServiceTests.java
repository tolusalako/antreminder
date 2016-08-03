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
import net.csthings.adapters.FormMVCAdapter;
import net.csthings.antreminder.provider.ServiceProvider;
import net.csthings.antreminder.websoc.service.WebSocService;
import net.csthings.antreminder.websoc.utils.Category;
import net.csthings.antreminder.websoc.utils.WebSocParser;
import net.csthings.common.utils.Pair;

@SpringApplicationConfiguration(classes = { AntreminderApplication.class, ServiceProvider.class, FormMVCAdapter.class })
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

    @Test(enabled = true)
    public void websocNewForm() throws IOException {
        File form = service.generateNewFormHtml();
        Assert.assertTrue(form.exists());
        Assert.assertEquals(sdf.format(form.lastModified()), sdf.format((new Date()).getTime()));
    }

    @Test
    public void dataTest() {
        List<Pair<String, String>> rez = WebSocParser.parseDeptElement(service.getFormData(Category.DEPARTMENT));
        Assert.assertNotNull(rez);
        Assert.assertNotEquals(rez.size(), 0);
        rez.stream().forEach(System.out::println);
    }
}

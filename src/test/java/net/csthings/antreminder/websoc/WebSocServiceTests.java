package net.csthings.antreminder.websoc;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import net.csthings.AntreminderApplication;
import net.csthings.antreminder.adapters.FormMVCAdapter;
import net.csthings.antreminder.provider.ServiceProvider;
import net.csthings.antreminder.websoc.impl.WebSocServiceImpl;
import net.csthings.antreminder.websoc.service.WebSocService;
import net.csthings.antreminder.websoc.utils.Category;
import net.csthings.antreminder.websoc.utils.Pair;
import net.csthings.antreminder.websoc.utils.WebSocParser;

@SpringApplicationConfiguration(classes = { AntreminderApplication.class, ServiceProvider.class, FormMVCAdapter.class })
@WebAppConfiguration
public class WebSocServiceTests extends AbstractTestNGSpringContextTests {
    Logger LOG = LoggerFactory.getLogger(WebSocServiceTests.class);

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
    public void websocInnerForm(){
        String innerForm = service.generateInnerFormHtml();
        Assert.assertNotNull(innerForm);
        Assert.assertNotEquals(innerForm, "");
        LOG.info(innerForm);
    }

    @Test
    public void dataTest() {
        List<Pair<String, String>> rez = WebSocParser.parseDeptElement(service.getFormData(Category.DEPARTMENT));
        Assert.assertNotNull(rez);
        Assert.assertNotEquals(rez.size(), 0);
        rez.stream().forEach(System.out::println);
    }
}

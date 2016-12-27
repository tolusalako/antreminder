package net.csthings.antreminder.websoc;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import net.csthings.AntreminderApplication;
import net.csthings.antreminder.provider.ServiceProvider;
import net.csthings.antreminder.websoc.service.WebSocService;

@SpringApplicationConfiguration(classes = { AntreminderApplication.class, ServiceProvider.class })
@WebAppConfiguration
public class WebSocServiceTests extends AbstractTestNGSpringContextTests {
    Logger LOG = LoggerFactory.getLogger(WebSocServiceTests.class);

    @Autowired
    WebSocService service;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    @Test
    public void websocInnerForm() {
        String innerForm = service.generateInnerFormHtml();
        Assert.assertNotNull(innerForm);
        Assert.assertNotEquals(innerForm, "");
        LOG.info(innerForm);
    }
}

package net.csthings.antreminder.websoc;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import net.csthings.AntreminderApplication;
import net.csthings.antreminder.websoc.WebSocUtils.Category;
import net.csthings.antreminder.websoc.service.WebSocService;
import net.csthings.common.utils.Pair;
import net.csthings.config.ModulesConfig;
import net.csthings.provider.ServiceProvider;

@SpringApplicationConfiguration(classes = { AntreminderApplication.class, ServiceProvider.class })
@WebAppConfiguration
public class WebSocServiceTests extends AbstractTestNGSpringContextTests {

    @Autowired
    WebSocService service;

    @Test
    public void basicWebSoc() throws IOException {
        File html = service.getFormHtml();
        Assert.assertTrue(html.exists());
    }
    
    
    @Test
    public void dataTest(){
        List<Pair<String, String>> rez =  WebSocUtils.parseDeptElement(service.getFormData(Category.DEPARTMENT));
        rez.stream().forEach(System.out::println);
    }
}

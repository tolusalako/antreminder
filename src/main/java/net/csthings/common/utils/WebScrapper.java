package net.csthings.common.utils;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebScrapper {
    Logger LOG = LoggerFactory.getLogger(WebScrapper.class);
    public HtmlUnitDriver driver;

    public WebScrapper() {
        driver = new HtmlUnitDriver();
    }
    

    public WebScrapper(String url) {
        this();
        driver.get(url);
    }
   

}

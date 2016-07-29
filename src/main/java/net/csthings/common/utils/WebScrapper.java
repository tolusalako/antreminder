package net.csthings.common.utils;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebScrapper {
	Logger LOG = LoggerFactory.getLogger(WebScrapper.class);
	public HtmlUnitDriver driver;

	public WebScrapper(String url) {
		driver = new HtmlUnitDriver();
		driver.get(url);
		//TODO: timeout? async if not local?
	}

}

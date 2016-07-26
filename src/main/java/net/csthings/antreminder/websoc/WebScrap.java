package net.csthings.antreminder.websoc;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebScrap {
	Logger LOG = LoggerFactory.getLogger(WebScrap.class);
	public HtmlUnitDriver driver;

	public WebScrap(String url) {
		driver = new HtmlUnitDriver();
		driver.get(url);
		//TODO: timeout? async if not local?
		LOG.debug("Now on {}.", driver.getTitle());
	}

}

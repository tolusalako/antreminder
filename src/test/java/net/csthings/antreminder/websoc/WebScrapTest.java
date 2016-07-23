package net.csthings.antreminder.websoc;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class WebScrapTest {
	Logger LOG = LoggerFactory.getLogger(WebScrapTest.class);
	@Test
	public void scrap1(){
		WebScrap ws = new WebScrap(Constants.UCI_WEBSOC_URL);
		List<WebElement> forms = ws.driver.findElements(By.xpath("/html/body/form"));
		LOG.debug("Got {} items.", forms.size());
		for (WebElement e : forms){
			LOG.info(e.getAttribute("outerHTML"));
		}	
	}

}

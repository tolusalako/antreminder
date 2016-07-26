package net.csthings.antreminder.websoc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
public class WebScrapTest {
	Logger LOG = LoggerFactory.getLogger(WebScrapTest.class);
    File cache = new File("src/main/resources/websoc/cache/search.html");
	WebScrap ws = new WebScrap("file://"+cache.getAbsolutePath());
	List<WebElement> forms = ws.driver.findElements(By.xpath("/html/body/form"));
	
	@Test
	public void getFormScrap() throws IOException{
		Assert.assertEquals(forms.size(), 1, "There should be only one form.");
		File form = new File("src/main/resources/websoc/form.frag");
		String data = forms.get(0).getAttribute("innerHTML");
		FileUtils.writeStringToFile(form, data, Charset.forName("UTF-8"));
	}
	
	@Test
	public void getDepartments(){
		List<WebElement> data = ws.driver.findElements(By.xpath("/html/body/form/table/tbody/*"));
		Assert.assertEquals(data.size(), 19, "There should be 19.");
		LOG.info("Got {} items", data.size());
		data.stream().map(f -> f.getText()).forEach(System.out::println);;
		
	}
	
	@Test
	public void test(){
//        Map<String, String> env = System.getenv();
//		  for (String envName : env.keySet()) {
//	            System.out.format("%s=%s%n",
//	                              envName,
//	                              env.get(envName));
//	        }
		 System.out.println(System.getProperty("user.dir"));
	}
}

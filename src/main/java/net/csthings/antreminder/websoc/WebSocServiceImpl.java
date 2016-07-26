package net.csthings.antreminder.websoc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.testng.Assert;

import net.csthings.config.AppSettings;
import net.csthings.config.WebSocSettings;

@Singleton	
public class WebSocServiceImpl implements WebSocService{
	Logger LOG = LoggerFactory.getLogger(WebSocServiceImpl.class);
	
	
	@Autowired
	private WebSocSettings websocSettings;
        @Autowired
        private AppSettings appSettings;
	
	//@Value("${websoc.url}")
	private String url;
	//@Value("${websoc.cache.search.page}")
	private String searchCache;
	//@Value("${websoc.cache.search.form}")
	private String formCache;
	//@Value("${app.utils.encoding}")
	private String encoding;
	
	private WebScrap ws; 
	private List<WebElement> formElement;
	private List<WebElement> formDataElement;
	private File form;
	
	@PostConstruct
	public void init(){
		this.url = websocSettings.getUrl();
		this.searchCache = websocSettings.getCacheSearchPage();
		this.formCache = websocSettings.getCacheSearchForm();
		this.encoding = appSettings.getEncoding();
		
		File cache = new File(searchCache);
		
		//Setup selenium with local cache if it exists
		if (cache.exists()){
			LOG.debug("Using cache: file://{}", searchCache);
			ws = new WebScrap("file://"+cache.getAbsolutePath());
		}else{
			LOG.debug("Using Url: {}", url);
			ws = new WebScrap(url);	//TODO: cache this for future
		}
		formElement = ws.driver.findElements(By.xpath("/html/body/form"));
		formDataElement = ws.driver.findElements(By.xpath("/html/body/form/table/tbody/*"));
		
		form = new File(formCache);
		
		LOG.info("Generated WebSoc Form with {} forms & {}/{} data elements", formElement.size(), formDataElement.size(), WebSocUtils.NUM_CATEGORIES);
	}
	
	public File getFormHtml() throws IOException {
		if (!form.exists()){
			LOG.debug("Generating new Cache form");
			String data = formElement.get(0).getAttribute("innerHTML");
			FileUtils.writeStringToFile(form, data, Charset.forName(encoding));
		}
		return form;
	}
	
	public String getFormData(WebSocUtils.Categories category){
		return formDataElement.get(category.getValue()).getText();
	}

}
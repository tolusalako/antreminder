package net.csthings.antreminder.websoc.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.csthings.antreminder.websoc.service.WebSocService;
import net.csthings.antreminder.websoc.utils.Category;
import net.csthings.common.utils.WebScrapper;
import net.csthings.config.AppSettings;
import net.csthings.config.WebSocSettings;

/*-
 * Created on: Jul 28, 2016
 * @author Toluwanimi Salako
 * Last edited: Jul 28, 2016
 * @purpose - Service for https://www.reg.uci.edu/perl/WebSoc
 */

@Singleton
public class WebSocServiceImpl implements WebSocService {
    Logger LOG = LoggerFactory.getLogger(WebSocServiceImpl.class);

    public final String FORM_FRAGMENT = "form";

    @Autowired
    private WebSocSettings websocSettings;
    @Autowired
    private AppSettings appSettings;

    private String baseUrl;
    private String searchUrl;
    private String searchCache;
    private String formCache;
    private String encoding;
    private String url;

    private WebScrapper ws;
    private List<WebElement> formElement;
    private List<WebElement> formDataElement;
    private File form;

    @PostConstruct
    private void init() {
        this.baseUrl = websocSettings.getBaseUrl();
        this.searchUrl = websocSettings.getSearchUrl();
        this.searchCache = websocSettings.getSearchFragment();
        this.formCache = websocSettings.getFormFragment();
        this.encoding = appSettings.getEncoding();

        form = new File(formCache);
        File cache = new File(searchCache);

        // Setup selenium with local cache if it exists
        if (cache.exists())
            url = "file://" + cache.getAbsolutePath();
        else
            url = baseUrl;
        // TODO: cache this page for future? But we only use form...
        LOG.debug("Using Url: {}", url);

        ws = new WebScrapper(url);
    }

    /**
     * Returns the form section from url. Saves to a new file if it DNE;
     */
    @Override
    public File getFormHtml() throws IOException {
        formElement = ws.driver.findElements(By.xpath("/html/body/form"));
        if (!form.exists()) {
            LOG.debug("Generating new form from {}", url);
            String data = formElement.get(0).getAttribute("innerHTML");
            FileUtils.writeStringToFile(form, data, Charset.forName(encoding));
        }
        return form;
    }

    /**
     * Updates the existing form html with latest data
     */
    @Override
    public File generateNewFormHtml() throws IOException {
        // Re init WebScrapper with non static url
        WebScrapper ws = new WebScrapper(baseUrl);
        formElement = ws.driver.findElements(By.xpath("/html/body/form"));
        LOG.debug("Generating new form from {}", url);
        String data = formElement.get(0).getAttribute("innerHTML");
        // Replace the generated data's action to ours. We need to intercept it
        // to prevent redirection
        data = data.replaceFirst("action=\"(.+)\"",
                String.format("action=\"%s\" th:fragment=\"%s\"", searchUrl, FORM_FRAGMENT));
        FileUtils.writeStringToFile(form, data, Charset.forName(encoding));
        return form;
    }

    /**
     * Returns the data in the specified category of the form
     */
    @Override
    public List<String> getFormData(Category category) {
        List<String> result = new ArrayList<>();
        String path = String.format("/html/body/form/table/tbody/tr[%s]/td[3]/select/*", category.getValue());
        formDataElement = ws.driver.findElements(By.xpath(path));
        formDataElement.stream().map(f -> f.getText()).forEach(result::add);
        return result;
    }

    public void setWebScrapper(WebScrapper ws) {
        this.ws = ws;
    }

}

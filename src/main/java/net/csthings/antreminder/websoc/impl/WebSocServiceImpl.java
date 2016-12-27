package net.csthings.antreminder.websoc.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.csthings.antreminder.config.WebSocSettings;
import net.csthings.antreminder.websoc.service.WebSocService;

/*-
 * Created on: Jul 28, 2016
 * @author Toluwanimi Salako
 * Last edited: Jul 28, 2016
 * @purpose - Service for https://www.reg.uci.edu/perl/WebSoc
 */

@Singleton
public class WebSocServiceImpl implements WebSocService {
    Logger LOG = LoggerFactory.getLogger(WebSocServiceImpl.class);

    @Autowired
    private WebSocSettings websocSettings;
    private String url;

    @PostConstruct
    private void init() {
        url = websocSettings.getBaseUrl();
    }

    /**
     * Strips form from its tag returning only the table.
     */
    @Override
    public String generateInnerFormHtml() {

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        }
        catch (IOException e) {
            LOG.error("Could not connect to " + url, e);
        }
        Element form = doc.select("form > table").first();
        return form.outerHtml();
    }

}

/**
 * Copyright (c) 2016-2017 Toluwanimi Salako. http://csthings.net

 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
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
